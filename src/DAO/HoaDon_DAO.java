/*
 * @ (#) HoaDon_DAO.java   1.0     4/23/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */

package DAO;



import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ConnectDB.ConnectDB;
import Entity.*;

/*
 * @description: Data Access Object (DAO) của entity Hóa Đơn
 * @author: Huỳnh Gia Mân
 * @date:  4/23/2025
 * @version:    1.0
 */

public class HoaDon_DAO {
	public ArrayList<HoaDon> dshd;
	public HoaDon hd;
	public HoaDon_DAO() {
		super();

		this.dshd = new ArrayList<HoaDon>();
		this.hd = new HoaDon();
	}

	public HoaDon getHoaDon (String maHD) {

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon where MaHD = ?";
			PreparedStatement statement = null;
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				ConnectDB.getInstance().connect();
				HoaDon_DAO dao = new HoaDon_DAO();
				Map<SanPham, Integer> ctsp = new HashMap<SanPham, Integer>();
				ctsp = dao.getallsanpam(maHD);
				String mahd = rs.getString(1);
				NhanVien nv = new NhanVien(rs.getString(2));
				KhachHang makh = new KhachHang(rs.getString(3));
				Date ngayLap = rs.getDate(4);
				hd = new HoaDon(mahd, makh, nv, ctsp, ngayLap);
				hd.setSanPhamList(ctsp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hd;
	}

	public Map<SanPham,Integer> getallsanpam(String maHD){
		Map<SanPham, Integer> ctsp = new HashMap<SanPham, Integer>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM CTHoaDon where MaHD = ?";
			PreparedStatement statement = null;
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String masp = rs.getString(2);
				int soLuong = rs.getInt(3);
				Double dongia = rs.getDouble(4);
				SanPham sp = new SanPham(masp, dongia);
				ctsp.put(sp, soLuong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ctsp;
	}

	public boolean create(HoaDon hoadon){
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("INSERT INTO HoaDon(MaNV, MaKH, NgayLap) VALUES (?, ?, ?)");
			stmt.setString(1, hoadon.getNhanVienBan().getMaNhanVien());
			if(hoadon.getKhachHangMua().getMaKhachHang()==null) {
				stmt.setString(2,null);
			}
			else {
				stmt.setString(2, hoadon.getKhachHangMua().getMaKhachHang());
			}

			stmt.setDate(3, hoadon.getNgayMuaHang());
			n =stmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n>0;

	}
	 public String getMaHD() {
		 String mahd="";
		 try {
			 ConnectDB.getInstance();
			 Connection con = ConnectDB.getConnection();
			 Statement statement = con.createStatement();
			 String sql = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";
			 ResultSet rs = statement.executeQuery(sql);
			 if (rs.next()) {
					mahd = rs.getString("MaHD");
			 }
		 }catch (SQLException e) {
			e.printStackTrace();
		}
		 return mahd;

	 }

	 public boolean createCTHoaDon(HoaDon hoadon) {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				PreparedStatement stmt1 = null;
				int n = 0;
				try {
					ConnectDB.getInstance().connect();
					HoaDon_DAO dao = new HoaDon_DAO();
					String maHD = dao.getMaHD();
					stmt1 = con.prepareStatement("insert into" +" CTHoaDon values(?,?,?,?)");
					for (Map.Entry<SanPham, Integer> entry : hoadon.getSanPhamList().entrySet()) {
						SanPham key = entry.getKey();
						Integer value = entry.getValue();
						stmt1.setString(1, maHD);
						stmt1.setString(2, key.getMaSanPham());
						stmt1.setInt(3, value);
						stmt1.setDouble(4, key.getDonGia());
						n =stmt1.executeUpdate();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return n>0;
	}
	public boolean updateNULL(String maNV) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n =0 ;
		try {
			stmt = con.prepareStatement("UPDATE HoaDon SET MaNV = NULL"
					+ "where MaNV=?");
			stmt.setString(1, maNV);
			n = stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return n>0;
	}
	public ArrayList<ThongKeDoanhThu> getThongKe() {
		ArrayList<ThongKeDoanhThu> thongke = new ArrayList<ThongKeDoanhThu>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			CallableStatement cstmt = con.prepareCall("{ call proc_ThongKeDoanhThuTheoNgay }");
			ResultSet rs = cstmt.executeQuery();
			if (rs.next()) {
				Date ngay = rs.getDate(1);
				int soLuongHD = rs.getInt(2);
				int soLuongSP = rs.getInt(3);
				double tongTien = rs.getDouble(4);
				ThongKeDoanhThu thongke1 = new ThongKeDoanhThu(ngay, soLuongHD, soLuongSP, tongTien);
				thongke.add(thongke1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thongke;
	}
}
		