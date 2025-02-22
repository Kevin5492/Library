/****** Script for SelectTopNRows command from SSMS  ******/
use library
INSERT INTO [status]
values ('�b�w'),
('�X�ɤ�'),
('��z��(�k�٫᥼�J�w)'),
('��'),
('�l��'),
('�o��')

INSERT INTO book (isbn, name, author, introduction)
VALUES 
('978-0-393-04002-9', 'Introduction to Algorithms', 'Thomas H. Cormen', '���Ѵ��ѹ�{�N�t��k��s���������СA�O�p�����ǻ�쪺�g��ѦҸ�ơC'),
('978-1-86197-876-9', 'Clean Code', 'Robert C. Martin', '�o���ѱj�զp��s�g�iŪ�B�i���@���{���X�A�O�n��}�o�����n�ѦҮѡC'),
('978-0-201-63361-0', 'Design Patterns', 'Erich Gamma', '���Ъ���ɦV�]�p�����g��]�p�Ҧ��A���U�}�o�H�������t�Ϊ��i���ΩʡC'),
('978-0-596-52068-7', 'Head First Design Patterns', 'Eric Freeman', '�H���[���쪺�覡���г]�p�Ҧ��A�A�X��Ǫ̩M�i���ϥΪ̡C'),
('978-0-13-235088-4', 'Clean Architecture', 'Robert C. Martin', '���Q�n��[�c�]�p����h�A���U�}�o�H���إ߰����B������@���t�ΡC'),
('978-1-4919-1889-0', 'Java Concurrency in Practice', 'Brian Goetz', '���� Java �h������{���]�p����Ϋ��n�A�`�J���Q�ֵo�{���]�p�޳N�C'),
('978-0-321-35668-0', 'Effective Java', 'Joshua Bloch', '�`�� Java ���x�}�o�����̨ι��A�A�X�Ҧ� Java �}�o�H���ѦҡC'),
('978-0-596-52068-8', 'Spring in Action', 'Craig Walls', '�������� Spring �ج[���ϥλP��ԮרҡA�A�X Spring �}�o�̡C'),
('978-1-4919-1889-1', 'The Pragmatic Programmer', 'Andrew Hunt', '�@������{���]�p�z���P�n��}�o��k�ת��g����y�C'),
('978-0-321-35668-1', 'Refactoring', 'Martin Fowler', '�ԲӤ��Цp��ﵽ�{���{���X���c�A�����{���iŪ�ʻP���@�ʡC'),
('978-0-13-468599-1', 'Effective Modern C++', 'Scott Meyers', '�w��{�N C++ ����Χޥ��P�̨ι��i��`�J���Q�C'),
('978-0-13-235088-5', 'Domain-Driven Design', 'Eric Evans', '���Цp��Q�λ���X�ʳ]�p�ѨM�����~�Ȱ��D�A�O�n��]�p�����n�ѦҡC'),
('978-0-596-52068-9', 'Cracking the Coding Interview', 'Gayle Laakmann McDowell', '���n��u�{���մ��Ѥj�q�d�һP���D�����A�O�D¾�̥�Ū���@�C'),
('978-0-321-35668-2', 'Working Effectively with Legacy Code', 'Michael Feathers', '���Q�p��w���a�ק�P���@�µ{���X�A�O�ѨM��d�t�ΰ��D����Ϋ��n�C'),
('978-0-13-235088-6', 'Test Driven Development', 'Kent Beck', '�q�L��һ����p��ĥδ����X�ʶ}�o��k�A�q�ӧ�i�{���]�p�C'),
('978-0-596-52069-0', 'Algorithms', 'Robert Sedgewick', '���Ѻt��k��¦�P������Ϊ���X���СA�A�X��Ǫ̻P�M�~�H�h�C'),
('978-1-4919-1889-2', 'Programming Pearls', 'Jon Bentley', '���ɸѨM�{���]�p���D������P�ޥ��A�E�o�{���]�p�F�P�C'),
('978-0-13-468599-2', 'Code Complete', 'Steve McConnell', '�Ժ��ĭz�n��c�y���U�Ӽh���A�O�s�{�����g����n�C'),
('978-0-201-03801-3', 'The Mythical Man-Month', 'Frederick P. Brooks Jr.', '���Q�n��u�{���غ޲z�����`�����D�P�D�ԡA�O�n��޲z���g��ۧ@�C'),
('978-1-59327-584-6', 'You Don''t Know JS', 'Kyle Simpson', '�`�J�L�X�a���� JavaScript ���֤߷����A�A�X�Q�����F�� JS ���}�o�̡C');

go 

DECLARE @isbn NVARCHAR(20);

-- �ϥ� cursor �M�� book ���Ҧ� ISBN (�w�p20��)
DECLARE book_cursor CURSOR FOR
    SELECT isbn FROM book;
    
OPEN book_cursor;
FETCH NEXT FROM book_cursor INTO @isbn;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- ���� 3 �� 12 �������H���ƶq�ƥ�
    DECLARE @copies INT;
    SET @copies = FLOOR(3 + (RAND(CHECKSUM(NEWID())) * (12 - 3 + 1)));
    
    DECLARE @i INT = 1;
    WHILE @i <= @copies
    BEGIN
        -- �����H�����A (1 �� 6)
        DECLARE @status INT;
        SET @status = FLOOR(1 + (RAND(CHECKSUM(NEWID())) * 6));

		IF @status = 2 
        BEGIN
            SET @status = 1;
        END
        
        -- �����H���i�]�ɶ��G�Ҧp���� 2020-01-01 �� 2023-12-31 ����
        DECLARE @baseDate DATETIME = '2020-01-01';
        DECLARE @daysOffset INT;
        SET @daysOffset = FLOOR(RAND(CHECKSUM(NEWID())) * 1461);  -- ��4�~ = 1461��
        DECLARE @store_time DATETIME;
        SET @store_time = DATEADD(DAY, @daysOffset, @baseDate);
        
        INSERT INTO inventory (isbn, store_time, status_id)
        VALUES (@isbn, @store_time, @status);
        
        SET @i = @i + 1;
    END
    
    FETCH NEXT FROM book_cursor INTO @isbn;
END

CLOSE book_cursor;
DEALLOCATE book_cursor;