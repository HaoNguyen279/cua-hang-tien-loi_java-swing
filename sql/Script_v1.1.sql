USE master
GO

-- Check nếu tên của CSDL này tồn tại rồi thì drop database để tạo mới
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'CHTL')
BEGIN
    ALTER DATABASE CHTL SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE CHTL;
END
GO

-- Tạo CSDL CHTL
CREATE DATABASE CHTL
GO

USE CHTL
GO

CREATE TABLE NhanVien (
    MaNhanVien nvarchar(10)  NOT NULL,
    TenNhanVien nvarchar(50)  NULL,
    NgaySinh datetime  NULL,
    NgayVaoLam datetime      NULL,
    ChucVu nvarchar(50)  NOT NULL,
    CONSTRAINT PK_NhanVien PRIMARY KEY CLUSTERED (MaNhanVien ASC)
)
GO

CREATE TABLE KhachHang (
    MaKH nvarchar(10)  NOT NULL,
    TenKH nvarchar(50)  NULL,
    SoDT nvarchar(10)  NULL,
    HangThanhVien nvarchar(50)  NULL,
    DiemThanhVien int           NULL,
    NgayDangKi datetime      NULL,
    CONSTRAINT PK_KhachHang PRIMARY KEY CLUSTERED (MaKH ASC)
)
GO

CREATE TABLE SanPham (
    MaSP nvarchar(50)  NOT NULL,
    TenSP nvarchar(50)  NULL,
    LoaiSP nvarchar(50)  NULL,
    NhaCC nvarchar(100)  NULL,
    NgaySX datetime  NULL,
    HanSD datetime   NULL,
    SoLuongKho int NOT NULL,
    DonGia float NOT NULL,
    CONSTRAINT PK_SanPham PRIMARY KEY CLUSTERED (MaSP ASC)
)
GO

CREATE TABLE HoaDon (
    MaHD nvarchar(50) NOT NULL,
    MaNV nvarchar(10) NOT NULL,
    MaKH nvarchar(10) NULL,
    NgayLap datetime NULL,
    CONSTRAINT PK_HoaDon PRIMARY KEY CLUSTERED (MaHD ASC)
)
GO

CREATE TABLE CTHoaDon (
    MaHD nvarchar(50)  NOT NULL,
    MaSP nvarchar(50)  NOT NULL,
    SoLuong int  NOT NULL,
    DonGia float  NOT NULL,
    CONSTRAINT PK_CTHoaDon PRIMARY KEY CLUSTERED (MaHD ASC, MaSP ASC)
)
GO

CREATE TABLE TaiKhoan (
    username nvarchar(10)  NOT NULL,
    password nvarchar(50)  NOT NULL,
    role nvarchar(50)  NULL,
    CONSTRAINT PK_TaiKhoan PRIMARY KEY CLUSTERED (username ASC)
)
GO

-- Set up ràng buộc  khóa ngoại  cho các bảng 
ALTER TABLE TaiKhoan
ADD CONSTRAINT FK_TaiKhoan_NhanVien
FOREIGN KEY (username) REFERENCES NhanVien (MaNhanVien)
GO

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_NhanVien
FOREIGN KEY (MaNV) REFERENCES NhanVien (MaNhanVien)
GO

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_KhachHang
FOREIGN KEY (MaKH) REFERENCES KhachHang (MaKH)
GO

ALTER TABLE CTHoaDon
ADD CONSTRAINT FK_CTHoaDon_HoaDon
FOREIGN KEY (MaHD) REFERENCES HoaDon (MaHD)
GO

ALTER TABLE CTHoaDon
ADD CONSTRAINT FK_CTHoaDon_SanPham
FOREIGN KEY (MaSP) REFERENCES SanPham (MaSP)
GO

CREATE TRIGGER trg_Insert_HoaDon
ON HoaDon
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewMaHD VARCHAR(10);
    DECLARE @LastNumber INT;

    -- Lấy mã cuối cùng đã tồn tại
    SELECT @LastNumber = 
        CAST(RIGHT(MAX(MaHD), 3) AS INT)
    FROM HoaDon;

    -- Nếu bảng trống thì bắt đầu từ 1
    IF @LastNumber IS NULL
        SET @LastNumber = 0;

    -- Tăng số lên 1 và tạo mã mới
    SET @LastNumber = @LastNumber + 1;
    SET @NewMaHD = 'HD' + RIGHT('000' + CAST(@LastNumber AS VARCHAR), 3);

    -- Thêm bản ghi với mã mới
    INSERT INTO HoaDon (MaHD, MaNV, MaKH, NgayLap)
    SELECT @NewMaHD, MaNV, MaKH, NgayLap
    FROM inserted;
END;

GO

use CHTL
GO

CREATE TRIGGER trg_Update_Membership
ON KhachHang AFTER UPDATE
AS
BEGIN
	DECLARE @NewDiemThanhVien int, @MaKH nvarchar(50)
	SELECT @NewDiemThanhVien = DiemThanhVien FROM inserted
	SELECT @MaKH = MaKH FROM inserted
	IF( @NewDiemThanhVien BETWEEN 100 AND 499 )
		BEGIN
			UPDATE KhachHang
			SET HangThanhVien = N'Vàng'
			WHERE MaKH = @MaKH
		END
	ELSE IF( @NewDiemThanhVien > 499 )
		BEGIN
			UPDATE KhachHang
			SET HangThanhVien = N'Kim Cương'
			WHERE MaKH = @MaKH
		END
	ELSE
		BEGIN
			UPDATE KhachHang
			SET HangThanhVien = N'Bạc'
			WHERE MaKH = @MaKH
		END
END;

GO

CREATE TRIGGER trg_SanPham
ON SanPham
AFTER UPDATE, INSERT
AS
BEGIN
	IF( SELECT SoLuongKho FROM inserted ) < 0
		BEGIN
			RAISERROR('LOI INSERT SO LUONG GIA TRI AM', 11,1);
			ROLLBACK TRAN
		END
END;

GO


		
-- 3 Table : NhanVien, SanPham, KhachHang là 3 table 'gốc' không phụ thuộc vào bảng nào nên ta INSERT VALUES trước các bảng còn lại


INSERT INTO SanPham (MaSP, TenSP, LoaiSP, NhaCC, NgaySX, HanSD, SoLuongKho, DonGia) VALUES
('NU001', N'Nước khoáng Lavie', N'Nước uống', N'Công ty TNHH La Vie', '2024-01-01', '2025-01-01', 100, 5000.00),
('NU002', N'Nước suối Aquafina', N'Nước uống', N'Công ty TNHH Nước Giải Khát Suntory PepsiCo Việt Nam', '2024-02-01', '2025-02-01', 150, 6000.00),
('NU003', N'Nước ép cam Tropicana', N'Nước uống', N'Công ty TNHH Thực phẩm và Nước giải khát Nam Việt', '2024-03-01', '2024-09-01', 80, 15000.00),
('DDH001', N'Cá ngừ đóng hộp', N'Đồ đóng hộp', N'Công ty Cổ phần Thực phẩm Sao Ta', '2024-01-15', '2026-01-15', 200, 25000.00),
('DDH002', N'Bắp ngọt đóng hộp', N'Đồ đóng hộp', N'Công ty TNHH Thực phẩm C.P. Việt Nam', '2024-02-15', '2026-02-15', 180, 18000.00),
('DDH003', N'Đậu xanh đóng hộp', N'Đồ đóng hộp', N'Công ty TNHH Thực phẩm Á Châu', '2024-03-15', '2026-03-15', 160, 20000.00),
('RC001', N'Cà chua', N'Rau củ', N'Hợp tác xã Rau sạch Đà Lạt', '2024-04-01', '2024-04-15', 300, 15000.00),
('RC002', N'Khoai tây', N'Rau củ', N'Công ty TNHH Nông sản Việt', '2024-04-05', '2024-05-05', 250, 12000.00),
('RC003', N'Cà rốt', N'Rau củ', N'Hợp tác xã Nông nghiệp Hữu cơ', '2024-04-10', '2024-05-10', 200, 10000.00),
('T001', N'Thịt bò tươi', N'Thịt', N'Công ty TNHH Thực phẩm Nam An', '2024-04-01', '2024-04-10', 50, 200000.00),
('T002', N'Thịt gà tươi', N'Thịt', N'Công ty TNHH Chăn nuôi CP Việt Nam', '2024-04-02', '2024-04-12', 60, 80000.00),
('T003', N'Thịt heo tươi', N'Thịt', N'Công ty TNHH Thực phẩm Masan', '2024-04-03', '2024-04-13', 70, 120000.00),
('B001', N'Bánh mì sandwich', N'Bánh', N'Công ty TNHH Bánh mì ABC', '2024-04-01', '2024-04-05', 100, 15000.00),
('B002', N'Bánh quy bơ', N'Bánh', N'Công ty TNHH Bánh kẹo Kinh Đô', '2024-04-02', '2024-10-02', 120, 20000.00),
('B003', N'Bánh kem socola', N'Bánh', N'Công ty TNHH Bánh ngọt Paris Gateaux', '2024-04-03', '2024-04-10', 80, 50000.00),
('K001', N'Kẹo socola', N'Kẹo', N'Công ty TNHH Kẹo Ngọt', '2024-01-01', '2025-01-01', 200, 10000.00),
('K002', N'Kẹo dẻo Haribo', N'Kẹo', N'Công ty TNHH Thực phẩm Orion Vina', '2024-02-01', '2025-02-01', 180, 15000.00),
('K003', N'Kẹo mút Chupa Chups', N'Kẹo', N'Công ty TNHH Perfetti Van Melle Việt Nam', '2024-03-01', '2025-03-01', 160, 5000.00),
('S001', N'Snack khoai tây', N'Snack', N'Công ty TNHH Snack Vui', '2024-01-15', '2024-07-15', 150, 10000.00),
('S002', N'Snack bắp rang', N'Snack', N'Công ty TNHH Thực phẩm PepsiCo Việt Nam', '2024-02-15', '2024-08-15', 140, 12000.00),
('S003', N'Snack rong biển', N'Snack', N'Công ty TNHH Thực phẩm Orion Vina', '2024-03-15', '2024-09-15', 130, 15000.00),
('NU124', N'Nước ngọt Coca Cola', N'Nước uống', N'Công ty TNHH Nước Giải Khát Coca-Cola Việt Nam', '2024-01-10', '2025-01-10', 200, 8000.00),
('NU457', N'Nước tăng lực Red Bull', N'Nước uống', N'Công ty TNHH Red Bull (Việt Nam)', '2024-02-10', '2025-02-10', 180, 15000.00),
('NU790', N'Nước ép táo', N'Nước uống', N'Công ty TNHH Thực phẩm và Nước giải khát Nam Việt', '2024-03-10', '2024-09-10', 160, 20000.00),
('DDH104', N'Đậu đỏ đóng hộp', N'Đồ đóng hộp', N'Công ty TNHH Thực phẩm Á Châu', '2024-01-20', '2026-01-20', 150, 22000.00),
('DDH105', N'Nấm đóng hộp', N'Đồ đóng hộp', N'Công ty Cổ phần Thực phẩm Sao Ta', '2024-02-20', '2026-02-20', 140, 25000.00),
('DDH106', N'Trái cây đóng hộp', N'Đồ đóng hộp', N'Công ty TNHH Thực phẩm C.P. Việt Nam', '2024-03-20', '2026-03-20', 130, 30000.00),
('RC204', N'Bắp cải', N'Rau củ', N'Hợp tác xã Rau sạch Đà Lạt', '2024-04-15', '2024-04-25', 200, 8000.00),
('RC205', N'Hành tây', N'Rau củ', N'Công ty TNHH Nông sản Việt', '2024-04-20', '2024-05-20', 180, 10000.00),
('RC206', N'Tỏi', N'Rau củ', N'Hợp tác xã Nông nghiệp Hữu cơ', '2024-04-25', '2024-05-25', 160, 15000.00),
('T004', N'Thịt cừu', N'Thịt', N'Công ty TNHH Thực phẩm Nam An', '2024-04-05', '2024-04-15', 40, 250000.00),
('T005', N'Thịt vịt', N'Thịt', N'Công ty TNHH Chăn nuôi CP Việt Nam', '2024-04-06', '2024-04-16', 50, 70000.00),
('T006', N'Thịt dê', N'Thịt', N'Công ty TNHH Thực phẩm Masan', '2024-04-07', '2024-04-17', 30, 180000.00),
('B004', N'Bánh donut', N'Bánh', N'Công ty TNHH Bánh mì ABC', '2024-04-04', '2024-04-08', 90, 20000.00),
('B005', N'Bánh bông lan', N'Bánh', N'Công ty TNHH Bánh kẹo Kinh Đô', '2024-04-05', '2024-10-05', 110, 25000.00),
('B006', N'Bánh tiramisu', N'Bánh', N'Công ty TNHH Bánh ngọt Paris Gateaux', '2024-04-06', '2024-04-13', 70, 60000.00),
('K004', N'Kẹo bạc hà', N'Kẹo', N'Công ty TNHH Kẹo Ngọt', '2024-01-05', '2025-01-05', 190, 8000.00),
('K005', N'Kẹo sữa', N'Kẹo', N'Công ty TNHH Thực phẩm Orion Vina', '2024-02-05', '2025-02-05', 170, 12000.00),
('K006', N'Kẹo gummy', N'Kẹo', N'Công ty TNHH Perfetti Van Melle Việt Nam', '2024-03-05', '2025-03-05', 150, 10000.00),
('S004', N'Snack phô mai', N'Snack', N'Công ty TNHH Snack Vui', '2024-01-25', '2024-07-25', 140, 12000.00),
('S005', N'Snack hành tây', N'Snack', N'Công ty TNHH Thực phẩm PepsiCo Việt Nam', '2024-02-25', '2024-08-25', 130, 15000.00),
('S006', N'Snack gà cay', N'Snack', N'Công ty TNHH Thực phẩm Orion Vina', '2024-03-25', '2024-09-25', 120, 18000.00),
('NU125', N'Nước cam ép', N'Nước uống', N'Công ty TNHH Thực phẩm và Nước giải khát Nam Việt', '2024-01-15', '2024-07-15', 100, 25000.00),
('NU458', N'Nước dừa tươi', N'Nước uống', N'Công ty TNHH Nước Giải Khát Suntory PepsiCo Việt Nam', '2024-02-15', '2024-08-15', 120, 10000.00),
('NU791', N'Nước chanh muối', N'Nước uống', N'Công ty TNHH La Vie', '2024-03-15', '2024-09-15', 110, 7000.00),
('DDH107', N'Súp đóng hộp', N'Đồ đóng hộp', N'Công ty Cổ phần Thực phẩm Sao Ta', '2024-01-25', '2026-01-25', 140, 30000.00),
('DDH108', N'Thịt hộp', N'Đồ đóng hộp', N'Công ty TNHH Thực phẩm C.P. Việt Nam', '2024-02-25', '2026-02-25', 130, 35000.00),
('DDH109', N'Pate gan', N'Đồ đóng hộp', N'Công ty TNHH Thực phẩm Á Châu', '2024-03-25', '2026-03-25', 120, 25000.00),
('RC207', N'Rau muống', N'Rau củ', N'Hợp tác xã Rau sạch Đà Lạt', '2024-04-20', '2024-04-30', 180, 5000.00),
('RC208', N'Cải thảo', N'Rau củ', N'Công ty TNHH Nông sản Việt', '2024-04-25', '2024-05-05', 160, 6000.00);

GO

INSERT INTO NhanVien (MaNhanVien, TenNhanVien, NgaySinh, NgayVaoLam, ChucVu)
VALUES
('nv001', N'Lê Minh Tùng', '1995-06-12', '2022-04-01', 'nhanvien'),
('nv002', N'Phạm Thị Mai', '1998-09-23', '2023-01-15', 'quanly'),
('nv003', N'Đặng Hoàng Nam', '2000-11-05', '2021-08-20', 'nhanvien'),
('nv004', N'Ngô Trúc Quỳnh', '1997-04-30', '2022-10-10', 'nhanvien'),
('nv005', N'Trần Bảo Long', '1992-01-19', '2023-06-05', 'quanly'),
('nv006', N'Võ Minh Anh', '2001-12-01', '2024-03-18', 'nhanvien'),
('nv007', N'Hồ Thị Bích Ngọc', '1999-07-25', '2022-07-07', 'quanly'),
('nv008', N'Đinh Thanh Tâm', '1990-03-14', '2023-11-11', 'nhanvien'),
('nv009', N'Tạ Quốc Hưng', '2002-10-02', '2024-01-01', 'nhanvien'),
('nv010', N'Bùi Kim Chi', '1996-05-18', '2022-12-20', 'quanly');

GO

INSERT INTO TaiKhoan (username, password, role)
VALUES
('nv001', N'pass', 'nhanvien'),
('nv002', N'pass', 'quanly'),
('nv003', N'pass', 'nhanvien'),
('nv004', N'pass', 'nhanvien'),
('nv005', N'pass', 'quanly'),
('nv006', N'pass', 'nhanvien'),
('nv007', N'pass', 'quanly'),
('nv008', N'pass', 'nhanvien'),
('nv009', N'pass', 'nhanvien'),
('nv010', N'pass', 'quanly');

GO

INSERT INTO KhachHang (MaKH, TenKH, SoDT, HangThanhVien, DiemThanhVien, NgayDangKi) VALUES
('kh001', N'Lê Thị Minh Châu', '0901234567', N'Bạc', 85, '2024-06-10'),
('kh002', N'Trần Văn Thịnh', '0912345678', N'Vàng', 210, '2023-12-05'),
('kh003', N'Phạm Thị Quỳnh', '0987654321', N'Kim Cương', 740, '2023-03-14'),
('kh004', N'Ngô Bá Duy', '0909988776', N'Bạc', 42, '2023-09-01'),
('kh005', N'Hoàng Mai Anh', '0934567890', N'Vàng', 300, '2024-01-20'),
('kh006', N'Tô Quốc Huy', '0978123456', N'Kim Cương', 1050, '2022-07-17'),
('kh007', N'Đỗ Thị Hằng', '0966543210', N'Vàng', 470, '2023-04-30'),
('kh008', N'Nguyễn Khắc Tâm', '0943217890', N'Bạc', 10, '2024-03-25'),
('kh009', N'Vũ Thị Lan', '0911222333', N'Kim Cương', 515, '2022-11-02'),
('kh010', N'Lý Hoàng Dũng', '0939333444', N'Vàng', 150, '2023-08-15');

GO