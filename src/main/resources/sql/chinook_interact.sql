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

CREATE FUNCTION fn_FormatDuration (@ms INT)
RETURNS NVARCHAR(10)
AS
BEGIN
   DECLARE @Minutes INT = @ms / 60000;
   DECLARE @Seconds INT = (@ms % 60000) / 1000;
   RETURN CAST(@Minutes AS NVARCHAR) + ':' + RIGHT('0' + CAST(@Seconds AS NVARCHAR), 2);
END;

SELECT Name, dbo.fn_FormatDuration(Milliseconds) FROM Track;

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
	
