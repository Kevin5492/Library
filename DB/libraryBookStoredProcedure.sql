use library
-- ��ܩҦ����w�s�ӥB�i�H�ɾ\����
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
-- ��ܩҦ����w�s�ӥB�i�H�ɾ\���� �å[�W�j�M
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
-- �ˬd�Ѫ��ƶq�O�_����
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

-- �ɮ�

CREATE PROCEDURE BorrowABook
    @isbn NVARCHAR(20),
    @userId INT,
    @currentTime DATETIME,
    @inventoryId INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @RowsAffected INT;

    BEGIN TRANSACTION;  -- �}�l���
    
    -- ���@���i�ɥX���w�s
    SELECT TOP 1 @inventoryId = inventory_id
    FROM inventory
    WHERE isbn = @isbn AND status_id = 1
    ORDER BY inventory_id;

    -- �p�G���i�ɪ��w�s�A��s���A
    IF @inventoryId IS NOT NULL
    BEGIN
        UPDATE inventory
        SET status_id = 2
        WHERE inventory_id = @inventoryId AND status_id = 1;  -- �T�O���M�i��

        -- �ˬd UPDATE �O�_���\
        SET @RowsAffected = @@ROWCOUNT;
        IF @RowsAffected = 0
        BEGIN
            ROLLBACK TRANSACTION;  -- �p�G�o���w�s�w�g�Q�ɨ��A�h�^�u
            SET @inventoryId = NULL;
            RETURN;
        END

        -- ���J�ɾ\����
        INSERT INTO borrowing_record (user_id, inventory_id, borrowing_time)
        VALUES (@userId, @inventoryId, @currentTime);

        -- �ˬd INSERT �O�_���\
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION;  -- �p�G���J�ɾ\�������ѡA�h�^�u
            SET @inventoryId = NULL;
            RETURN;
        END

        COMMIT TRANSACTION;  -- ������\����
    END
    ELSE
    BEGIN
        -- �Y�S�����i�ɪ��w�s�A�h���i�������
        ROLLBACK TRANSACTION;
        SET @inventoryId = NULL;
    END
END;
GO

-- ���Ҧ��w�ɪ���
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

        -- ��s�ɾ\�����A�]�w�k�ٮɶ�
        UPDATE borrowing_record
        SET return_time = @currentTime
        WHERE borrowing_record_id = @borrowRecordId;

        -- ��s�w�s���A���b�w (1)
        UPDATE inventory
        SET status_id = 1
        WHERE inventory_id = (SELECT inventory_id FROM borrowing_record WHERE borrowing_record_id = @borrowRecordId);

        COMMIT TRANSACTION;
        SET @status = 0;  -- ���\
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        SET @status = -1;  -- ����
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