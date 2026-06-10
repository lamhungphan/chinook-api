-- VW
CREATE VIEW vw_CustomerOrders AS
SELECT
   c.FirstName + ' '+ c.LastName AS FullName,
   c.Country,
   Sum(i.Total) AS TotalSpent
FROM
   Customer c
JOIN
   Invoice i ON c.CustomerId = i.CustomerId
GROUP BY c.FirstName, c.LastName, c.Country


-- VW
CREATE VIEW vw_TrackDetailInfo AS
SELECT 
	t.Name AS TrackName,
	g.Name AS GenreName,
	t.Milliseconds,
	RANK() OVER (PARTITION BY g.GenreId ORDER BY t.Milliseconds DESC) AS DurationRank
FROM Track t 
JOIN Album a ON t.AlbumId = a.AlbumId 
JOIN Artist a2 ON a.ArtistId = a2.ArtistId 
JOIN Genre g ON t.GenreId  = g.GenreId


-- SP
CREATE PROCEDURE sp_UpdateGenrePrice
	@GenreName NVARCHAR(120),
	@Percentage FLOAT
AS
BEGIN
	UPDATE Track
	SET UnitPrice = UnitPrice * (1 + @Percentage / 100)
	FROM Track t
	JOIN Genre g ON t.GenreId = g.GenreId
	WHERE g.Name = @GenreName;

	PRINT 'Đã cập nhật giá cho thể loại: ' + @GenreName;
END;


-- FUNC
CREATE FUNCTION fn_FormatDuration (@ms INT)
RETURNS NVARCHAR(10)
AS
BEGIN
   DECLARE @Minutes INT = @ms / 60000;
   DECLARE @Seconds INT = (@ms % 60000) / 1000;
   RETURN CAST(@Minutes AS NVARCHAR) + ':' + RIGHT('0' + CAST(@Seconds AS NVARCHAR), 2);
END;

SELECT Name, dbo.fn_FormatDuration(Milliseconds) FROM Track;


-- TRG
-- 1. Tạo bảng lưu log
CREATE TABLE TrackPriceLog (
    LogId INT IDENTITY(1,1) PRIMARY KEY,
    TrackId INT,
    OldPrice DECIMAL(10,2),
    NewPrice DECIMAL(10,2),
    ChangeDate DATETIME DEFAULT GETDATE()
);

-- 2. Tạo Trigger
CREATE TRIGGER trg_AfterPriceUpdate
ON Track
AFTER UPDATE
AS
BEGIN
    IF UPDATE(UnitPrice) -- Chỉ chạy nếu cột UnitPrice bị sửa
    BEGIN
        INSERT INTO TrackPriceLog (TrackId, OldPrice, NewPrice)
        SELECT d.TrackId, d.UnitPrice, i.UnitPrice
        FROM deleted d
        JOIN inserted i ON d.TrackId = i.TrackId;
    END
END;

EXEC sp_UpdateGenrePrice'Rock', 10;


-- CTE
WITH ArtistRevenue AS (
    -- Bước 1: Tính tổng doanh thu và số lượng bài hát đã bán của mỗi nghệ sĩ
    SELECT 
        art.Name AS ArtistName,
        SUM(il.UnitPrice * il.Quantity) AS TotalRevenue,
        COUNT(il.TrackId) AS TracksSold
    FROM Artist art
    JOIN Album alb ON art.ArtistId = alb.ArtistId
    JOIN Track t ON alb.AlbumId = t.AlbumId
    JOIN InvoiceLine il ON t.TrackId = il.TrackId
    GROUP BY art.Name
),
ArtistMetrics AS (
    -- Bước 2: Tính toán chỉ số trung bình dựa trên kết quả của CTE thứ nhất
    SELECT 
        ArtistName,
        TotalRevenue,
        TracksSold,
        (TotalRevenue / NULLIF(TracksSold, 0)) AS AvgRevenuePerTrack
    FROM ArtistRevenue
)
SELECT * 
FROM ArtistMetrics
WHERE TracksSold > 10 -- Chỉ lấy những nghệ sĩ có doanh số đáng kể
ORDER BY AvgRevenuePerTrack DESC;





-- ------------------------------------------------------------- TRANSACTION
-- Kịch bản Thành công (COMMIT)
BEGIN TRANSACTION; -- Bắt đầu giao dịch

BEGIN TRY
    -- 1. Thêm thể loại mới
    INSERT INTO Genre (GenreId, Name) 
    VALUES (100, 'Vinahouse');

    -- 2. Thêm bài hát thuộc thể loại đó
    INSERT INTO Track (TrackId, Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice)
    VALUES (10000, 'Lên Nhạc', 1, 1, 100, 'DJ Gemini', 180000, 5000000, 0.99);

    -- Nếu mọi thứ ổn, lưu vĩnh viễn vào DB
    COMMIT TRANSACTION;
    PRINT 'Giao dịch thành công!';
END TRY
BEGIN CATCH
    -- Nếu có bất kỳ lỗi nào, hủy bỏ toàn bộ
    ROLLBACK TRANSACTION;
    PRINT 'Có lỗi xảy ra, đã khôi phục trạng thái cũ!';
END CATCH;


-- Kịch bản Thất bại (ROLLBACK)
BEGIN TRANSACTION;

-- Bước 1: Thêm thể loại (Hợp lệ)
INSERT INTO Genre (GenreId, Name) VALUES (101, 'Indie Việt');

-- Bước 2: Thêm bài hát (LỖI - MediaTypeId 999 không tồn tại)
INSERT INTO Track (TrackId, Name, AlbumId, MediaTypeId, GenreId, Milliseconds, UnitPrice)
VALUES (10001, 'Bài hát lỗi', 1, 999, 101, 200000, 0.99);

-- Kiểm tra:
IF @@ERROR <> 0
BEGIN
    ROLLBACK TRANSACTION;
    PRINT 'Đã Rollback! Thể loại "Indie Việt" cũng sẽ không bị thêm vào bảng Genre.';
END
ELSE
BEGIN
    COMMIT TRANSACTION;
END


-- Sử dụng Savepoint (Điểm kiểm soát)
BEGIN TRANSACTION;

-- Thao tác 1: Cập nhật giá nhạc Rock
UPDATE Track SET UnitPrice = UnitPrice + 0.1 WHERE GenreId = 1;

SAVE TRANSACTION BeforeJazzUpdate; -- Đặt một "cột mốc"

-- Thao tác 2: Cập nhật giá nhạc Jazz (nhưng giả sử ta đổi ý)
UPDATE Track SET UnitPrice = UnitPrice + 0.5 WHERE GenreId = 2;

-- Chỉ muốn hủy thao tác 2, giữ lại thao tác 1
ROLLBACK TRANSACTION BeforeJazzUpdate;

-- Hoàn tất
COMMIT TRANSACTION;





-- ------------------------------------------------------------- ADVANCE
-- Recursive CTE: Sơ đồ tổ chức nhân viên
WITH EmployeeHierarchy AS (
    -- Anchor member: Tìm người đứng đầu (người không báo cáo cho ai)
    SELECT 
        EmployeeId, 
        FirstName + ' ' + LastName AS EmployeeName, 
        ReportsTo, 
        1 AS [Level] -- Gốc là cấp 1
    FROM Employee
    WHERE ReportsTo IS NULL

    UNION ALL

    -- Recursive member: Tìm những người báo cáo cho người ở cấp trên
    SELECT 
        e.EmployeeId, 
        e.FirstName + ' ' + e.LastName, 
        e.ReportsTo, 
        eh.[Level] + 1
    FROM Employee e
    INNER JOIN EmployeeHierarchy eh ON e.ReportsTo = eh.EmployeeId
)
SELECT 
    REPLICATE('|---- ', [Level] - 1) + EmployeeName AS Hierarchy, 
    [Level]
FROM EmployeeHierarchy
ORDER BY [Level], ReportsTo;


-- Procedure: Xóa khách hàng kèm xử lý lỗi (TRY...CATCH)
CREATE PROCEDURE sp_DeleteCustomer
    @CustomerId INT
AS
BEGIN
    SET NOCOUNT ON; -- Tắt thông báo "x rows affected" để tối ưu

    BEGIN TRY
        -- 1. Kiểm tra sự tồn tại của khách hàng
        IF NOT EXISTS (SELECT 1 FROM Customer WHERE CustomerId = @CustomerId)
        BEGIN
            THROW 50001, 'Khách hàng này không tồn tại trong hệ thống.', 1;
        END

        -- 2. Kiểm tra xem có hóa đơn không (ràng buộc logic)
        IF EXISTS (SELECT 1 FROM Invoice WHERE CustomerId = @CustomerId)
        BEGIN
            THROW 50002, 'Không thể xóa khách hàng đã có hóa đơn phát sinh.', 1;
        END

        -- 3. Thực hiện xóa
        DELETE FROM Customer WHERE CustomerId = @CustomerId;
        PRINT 'Đã xóa khách hàng ID: ' + CAST(@CustomerId AS VARCHAR);

    END TRY
    BEGIN CATCH
        -- Trả về thông báo lỗi chi tiết
        PRINT 'LỖI: ' + ERROR_MESSAGE();
    END CATCH
END;
-- EXEC sp_DeleteCustomer @CustomerId = 1; (Sẽ báo lỗi vì khách hàng 1 đã mua hàng)


-- Cursor: Cập nhật tên nghệ sĩ "Popular"
DECLARE @ArtistId INT;
DECLARE @ArtistName NVARCHAR(120);
DECLARE @TrackCount INT;

-- 1. Khai báo Cursor lấy ID và Tên nghệ sĩ
DECLARE artist_cursor CURSOR FOR 
SELECT ArtistId, Name FROM Artist;

OPEN artist_cursor;

-- 2. Bắt đầu duyệt
FETCH NEXT FROM artist_cursor INTO @ArtistId, @ArtistName;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Kiểm tra số lượng bài hát của nghệ sĩ này
    SELECT @TrackCount = COUNT(t.TrackId)
    FROM Album a
    JOIN Track t ON a.AlbumId = t.AlbumId
    WHERE a.ArtistId = @ArtistId;

    -- 3. Nếu trên 10 bài thì cập nhật tên
    IF @TrackCount > 10 AND @ArtistName NOT LIKE '[Popular]%'
    BEGIN
        UPDATE Artist 
        SET Name = '[Popular] ' + @ArtistName 
        WHERE ArtistId = @ArtistId;
    END

    -- Duyệt dòng tiếp theo
    FETCH NEXT FROM artist_cursor INTO @ArtistId, @ArtistName;
END;

-- 4. Đóng và giải phóng bộ nhớ
CLOSE artist_cursor;
DEALLOCATE artist_cursor;

PRINT 'Hoàn thành việc gắn thẻ nghệ sĩ nổi tiếng.';


-- IX
SELECT TrackId, Name, Milliseconds, UnitPrice 
FROM Track 
WHERE Name LIKE 'Love%' AND Milliseconds > 200000;


CREATE NONCLUSTERED INDEX IX_Track_Name_Milliseconds
ON Track (Name, Milliseconds)
INCLUDE (UnitPrice);

DROP INDEX IX_Track_Name_Milliseconds ON Track;

