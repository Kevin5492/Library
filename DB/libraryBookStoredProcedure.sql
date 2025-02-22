use library
-- 顯示所有有庫存而且可以借閱的書
CREATE PROCEDURE GetBorrowableBooks
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        b.isbn, 
        b.[name], 
        b.author, 
        b.introduction, 
        COUNT(i.inventory_id) AS borrowable
    FROM book AS b
    JOIN inventory AS i 
        ON i.isbn = b.isbn AND i.status_id = 1
    GROUP BY 
        b.isbn, 
        b.[name], 
        b.author, 
        b.introduction;
END
GO
-- 顯示所有有庫存而且可以借閱的書 並加上搜尋
CREATE PROCEDURE GetBorrowableBooksWithSearch
@searchInput  nvarchar(20)
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        b.isbn, 
        b.[name], 
        b.author, 
        b.introduction, 
        COUNT(i.inventory_id) AS borrowable
    FROM book AS b
    JOIN inventory AS i 
        ON i.isbn = b.isbn AND i.status_id = 1
	where b.isbn like '%'+@searchInput+'%'
	or b.[name]like '%'+@searchInput+'%'
	or  b.author like '%'+@searchInput+'%'
	or  b.introduction like '%'+@searchInput+'%'

    GROUP BY 
        b.isbn, 
        b.[name], 
        b.author, 
        b.introduction;
END
GO
-- 檢查書的數量是否足夠
CREATE PROCEDURE CheckIfBookIsEnough
@isbn nvarchar(20),
@bookCount int out
AS
BEGIN

	SELECT  @bookCount =count(inventory_id)
	FROM inventory
	Where isbn = @isbn AND status_id = 1
	Group by isbn

END
GO

-- 借書

CREATE PROCEDURE BorrowABook
    @isbn NVARCHAR(20),
    @userId INT,
    @currentTime DATETIME,
    @inventoryId INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @RowsAffected INT;

    BEGIN TRANSACTION;  -- 開始交易
    
    -- 找到一本可借出的庫存
    SELECT TOP 1 @inventoryId = inventory_id
    FROM inventory
    WHERE isbn = @isbn AND status_id = 1
    ORDER BY inventory_id;

    -- 如果找到可借的庫存，更新狀態
    IF @inventoryId IS NOT NULL
    BEGIN
        UPDATE inventory
        SET status_id = 2
        WHERE inventory_id = @inventoryId AND status_id = 1;  -- 確保仍然可借

        -- 檢查 UPDATE 是否成功
        SET @RowsAffected = @@ROWCOUNT;
        IF @RowsAffected = 0
        BEGIN
            ROLLBACK TRANSACTION;  -- 如果這筆庫存已經被借走，則回滾
            SET @inventoryId = NULL;
            RETURN;
        END

        -- 插入借閱紀錄
        INSERT INTO borrowing_record (user_id, inventory_id, borrowing_time)
        VALUES (@userId, @inventoryId, @currentTime);

        -- 檢查 INSERT 是否成功
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION;  -- 如果插入借閱紀錄失敗，則回滾
            SET @inventoryId = NULL;
            RETURN;
        END

        COMMIT TRANSACTION;  -- 交易成功提交
    END
    ELSE
    BEGIN
        -- 若沒有找到可借的庫存，則不進行任何更改
        ROLLBACK TRANSACTION;
        SET @inventoryId = NULL;
    END
END;
GO

-- 找到所有已借的書
CREATE PROCEDURE ShowCurrentBorrowBooks
@userId int
as
Begin
   Select	
			b.isbn,
			i.inventory_id,
			b.[name],
			b.author,
			b.introduction, 
			br.borrowing_time,
			br.borrowing_record_id
   from borrowing_record as br
   left join inventory as i on i.inventory_id = br.inventory_id 
   join book as b on b.isbn = i.isbn
   where br.[user_id] = @userId and br.return_time IS NULL
END
go

CREATE PROCEDURE ReturnABook
    @borrowRecordId INT,
    @currentTime DATETIME,
    @status INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;

        -- 更新借閱紀錄，設定歸還時間
        UPDATE borrowing_record
        SET return_time = @currentTime
        WHERE borrowing_record_id = @borrowRecordId;

        -- 更新庫存狀態為在庫 (1)
        UPDATE inventory
        SET status_id = 1
        WHERE inventory_id = (SELECT inventory_id FROM borrowing_record WHERE borrowing_record_id = @borrowRecordId);

        COMMIT TRANSACTION;
        SET @status = 0;  -- 成功
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        SET @status = -1;  -- 失敗
    END CATCH
END;
GO

CREATE PROCEDURE GetUserIdFromBorrowRecord
@borrowRecordId int,
@userId int out
as
BEGIN
	select @userId = [user_id] 
	from borrowing_record 
	where borrowing_record_id = @borrowRecordId
END