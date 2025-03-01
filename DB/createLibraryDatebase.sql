CREATE DATABASE library;
GO

USE [library]
GO
/****** Object:  Table [dbo].[book]    Script Date: 2/22/2025 5:46:02 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[book](
	[isbn] [nvarchar](20) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[author] [nvarchar](30) NOT NULL,
	[introduction] [nvarchar](400) NULL,
 CONSTRAINT [PK_book] PRIMARY KEY CLUSTERED 
(
	[isbn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[borrowing_record]    Script Date: 2/22/2025 5:46:02 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[borrowing_record](
	[borrowing_record_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[inventory_id] [int] NOT NULL,
	[borrowing_time] [datetime] NOT NULL,
	[return_time] [datetime] NULL,
 CONSTRAINT [PK_borrowing_record] PRIMARY KEY CLUSTERED 
(
	[borrowing_record_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[inventory]    Script Date: 2/22/2025 5:46:02 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[inventory](
	[inventory_id] [int] IDENTITY(1,1) NOT NULL,
	[isbn] [nvarchar](20) NOT NULL,
	[store_time] [datetime] NOT NULL,
	[status_id] [int] NOT NULL,
 CONSTRAINT [PK_inventory] PRIMARY KEY CLUSTERED 
(
	[inventory_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status]    Script Date: 2/22/2025 5:46:02 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status](
	[status_id] [int] IDENTITY(1,1) NOT NULL,
	[status_name] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_status] PRIMARY KEY CLUSTERED 
(
	[status_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user]    Script Date: 2/22/2025 5:46:02 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[phone_number] [nchar](10) NOT NULL,
	[password] [nvarchar](256) NOT NULL,
	[user_name] [nvarchar](20) NOT NULL,
	[last_login_time] [datetime] NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_user_phone_number] UNIQUE NONCLUSTERED 
(
	[phone_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[borrowing_record]  WITH CHECK ADD  CONSTRAINT [FK_borrowing_record_inventory] FOREIGN KEY([inventory_id])
REFERENCES [dbo].[inventory] ([inventory_id])
GO
ALTER TABLE [dbo].[borrowing_record] CHECK CONSTRAINT [FK_borrowing_record_inventory]
GO
ALTER TABLE [dbo].[borrowing_record]  WITH CHECK ADD  CONSTRAINT [FK_borrowing_record_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[user] ([user_id])
GO
ALTER TABLE [dbo].[borrowing_record] CHECK CONSTRAINT [FK_borrowing_record_user]
GO
ALTER TABLE [dbo].[inventory]  WITH CHECK ADD  CONSTRAINT [FK_inventory_book] FOREIGN KEY([isbn])
REFERENCES [dbo].[book] ([isbn])
GO
ALTER TABLE [dbo].[inventory] CHECK CONSTRAINT [FK_inventory_book]
GO
ALTER TABLE [dbo].[inventory]  WITH CHECK ADD  CONSTRAINT [FK_inventory_status] FOREIGN KEY([status_id])
REFERENCES [dbo].[status] ([status_id])
GO
ALTER TABLE [dbo].[inventory] CHECK CONSTRAINT [FK_inventory_status]
GO

CREATE INDEX idx_user_id ON borrowing_record ([user_id]);
CREATE INDEX idx_inventory_id ON borrowing_record ([inventory_id]);

use library
INSERT INTO [status]
values ('在庫'),
('出借中'),
('整理中(歸還後未入庫)'),
('遺失'),
('損毀'),
('廢棄')

INSERT INTO book (isbn, name, author, introduction)
VALUES 
('978-0-393-04002-9', 'Introduction to Algorithms', 'Thomas H. Cormen', '本書提供對現代演算法研究的全面介紹，是計算機科學領域的經典參考資料。'),
('978-1-86197-876-9', 'Clean Code', 'Robert C. Martin', '這本書強調如何編寫可讀、可維護的程式碼，是軟體開發的重要參考書。'),
('978-0-201-63361-0', 'Design Patterns', 'Erich Gamma', '介紹物件導向設計中的經典設計模式，幫助開發人員提高系統的可重用性。'),
('978-0-596-52068-7', 'Head First Design Patterns', 'Eric Freeman', '以直觀有趣的方式介紹設計模式，適合初學者和進階使用者。'),
('978-0-13-235088-4', 'Clean Architecture', 'Robert C. Martin', '探討軟體架構設計的原則，幫助開發人員建立健全且易於維護的系統。'),
('978-1-4919-1889-0', 'Java Concurrency in Practice', 'Brian Goetz', '提供 Java 多執行緒程式設計的實用指南，深入探討併發程式設計技術。'),
('978-0-321-35668-0', 'Effective Java', 'Joshua Bloch', '總結 Java 平台開發中的最佳實踐，適合所有 Java 開發人員參考。'),
('978-0-596-52068-8', 'Spring in Action', 'Craig Walls', '全面介紹 Spring 框架的使用與實戰案例，適合 Spring 開發者。'),
('978-1-4919-1889-1', 'The Pragmatic Programmer', 'Andrew Hunt', '一本關於程式設計理念與軟體開發方法論的經典書籍。'),
('978-0-321-35668-1', 'Refactoring', 'Martin Fowler', '詳細介紹如何改善現有程式碼結構，提高程式可讀性與維護性。'),
('978-0-13-468599-1', 'Effective Modern C++', 'Scott Meyers', '針對現代 C++ 的實用技巧與最佳實踐進行深入探討。'),
('978-0-13-235088-5', 'Domain-Driven Design', 'Eric Evans', '介紹如何利用領域驅動設計解決複雜業務問題，是軟體設計的重要參考。'),
('978-0-596-52068-9', 'Cracking the Coding Interview', 'Gayle Laakmann McDowell', '為軟體工程面試提供大量範例與解題策略，是求職者必讀之作。'),
('978-0-321-35668-2', 'Working Effectively with Legacy Code', 'Michael Feathers', '探討如何安全地修改與維護舊程式碼，是解決遺留系統問題的實用指南。'),
('978-0-13-235088-6', 'Test Driven Development', 'Kent Beck', '通過實例說明如何採用測試驅動開發方法，從而改進程式設計。'),
('978-0-596-52069-0', 'Algorithms', 'Robert Sedgewick', '提供演算法基礎與實踐應用的綜合介紹，適合初學者與專業人士。'),
('978-1-4919-1889-2', 'Programming Pearls', 'Jon Bentley', '分享解決程式設計問題的思路與技巧，激發程式設計靈感。'),
('978-0-13-468599-2', 'Code Complete', 'Steve McConnell', '詳盡闡述軟體構造的各個層面，是編程實踐中的經典指南。'),
('978-0-201-03801-3', 'The Mythical Man-Month', 'Frederick P. Brooks Jr.', '探討軟體工程項目管理中的常見問題與挑戰，是軟體管理的經典著作。'),
('978-1-59327-584-6', 'You Don''t Know JS', 'Kyle Simpson', '深入淺出地講解 JavaScript 的核心概念，適合想全面了解 JS 的開發者。');

go 

DECLARE @isbn NVARCHAR(20);

-- 使用 cursor 遍歷 book 表中所有 ISBN (預計20筆)
DECLARE book_cursor CURSOR FOR
    SELECT isbn FROM book;
    
OPEN book_cursor;
FETCH NEXT FROM book_cursor INTO @isbn;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- 產生 3 至 12 之間的隨機數量副本
    DECLARE @copies INT;
    SET @copies = FLOOR(3 + (RAND(CHECKSUM(NEWID())) * (12 - 3 + 1)));
    
    DECLARE @i INT = 1;
    WHILE @i <= @copies
    BEGIN
        -- 產生隨機狀態 (1 至 6)
        DECLARE @status INT;
        SET @status = FLOOR(1 + (RAND(CHECKSUM(NEWID())) * 6));

		IF @status = 2 
        BEGIN
            SET @status = 1;
        END
        
        -- 產生隨機進館時間：例如介於 2020-01-01 到 2023-12-31 之間
        DECLARE @baseDate DATETIME = '2020-01-01';
        DECLARE @daysOffset INT;
        SET @daysOffset = FLOOR(RAND(CHECKSUM(NEWID())) * 1461);  -- 約4年 = 1461天
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