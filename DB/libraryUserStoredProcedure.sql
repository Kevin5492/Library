use library

go
-- �s�W�ϥΪ�

CREATE PROCEDURE InsertUser
    @phoneNumber nCHAR(10),
    @password NVARCHAR(256),
    @userName NVARCHAR(20),
	@userId INT OUTPUT
AS
BEGIN
    INSERT INTO [user] (phone_number, [password],[user_name] )
    VALUES (@phoneNumber, @password,@userName);

    SET @userId = SCOPE_IDENTITY();
END

GO
-- �ˬd ��� ���S������
CREATE PROCEDURE CheckIfPhoneIsValid
    @phoneNumber NVARCHAR(20),
    @isValid BIT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (SELECT 1 FROM [user] WHERE phone_number = @phoneNumber)
    BEGIN
        SET @isValid = 1;  
    END
    ELSE
    BEGIN
        SET @isValid = 0;  
    END
END

go 
--����K�X
CREATE PROCEDURE GetPassword
    @phoneNumber NVARCHAR(20),
    @password NVARCHAR(256) OUT
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT @password = [password]
    FROM [user]
    WHERE phone_number = @phoneNumber;
END
GO

-- ��粒�� ���J�n�J�ɶ�

CREATE PROCEDURE GetUserIdAndSetLoginTime
@phoneNumber NVARCHAR(20),
@currentTime datetime,
@userId INT OUT

AS
BEGIN
		update [user]
		set last_login_time = @currentTime
		where phone_number = @phoneNumber;

		SELECT @userId = [user_id]

		FROM [user]
		WHERE phone_number = @phoneNumber;
END