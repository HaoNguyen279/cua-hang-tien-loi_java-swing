/*
 * @ (#) khachHang_DAO.java   1.0     4/23/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */

package DAO;


import Entity.KhachHang;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectDB.ConnectDB;

/*
 * @description: Data Access Object (DAO) của entity Khách hàng
 * @author: Nguyễn Minh Hào
 * @date:  4/23/2025
 * @version:    1.0
 */
public class KhachHang_DAO {
    ArrayList<KhachHang> dskh;
    KhachHang kh;
    public KhachHang_DAO(){
        dskh = new ArrayList<KhachHang>();
        kh = new KhachHang();
    }
    
    public ArrayList<KhachHang> getListKhachHang(){
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM KhachHang";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String makh = rs.getString(1);
                String tenkh = rs.getString(2);
                String soDienThoai = rs.getString(3);
                String hangThanhVien = rs.getString(4);
                int diemThanhVien = rs.getInt(4);
                Date ngayDKTV = rs.getDate(6);

                
                KhachHang nv1 = new KhachHang(makh, tenkh, soDienThoai, hangThanhVien,diemThanhVien, ngayDKTV);
                dskh.add(nv1);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dskh;
    }
    
    public KhachHang getKhachHang(String makh) {
    	try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT * FROM KhachHang where MaKH = ?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setString(1, makh);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
            	String makh1 = rs.getString(1);
                String tenkh = rs.getString(2);
                String soDienThoai = rs.getString(3);
                String hangThanhVien = rs.getString(4);
                int diemThanhVien = rs.getInt(5);
                Date ngayDKTV = rs.getDate(6);

                
                kh = new KhachHang(makh1, tenkh, soDienThoai, hangThanhVien,diemThanhVien, ngayDKTV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
    
    public boolean update(KhachHang kh){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n =0 ;
        try {
        	stmt = con.prepareStatement("update KhachHang set TenKH=?,SoDT=?,HangThanhVien=?,DiemThanhVien=? ,NgayDangKi=?, "
        			+ "where MaKH=?");
        	stmt.setString(1, kh.getTenKhachHang());
        	stmt.setString(2,kh.getSoDienThoai());
        	stmt.setString(3, kh.getHangThanhVien());
        	stmt.setInt(4, kh.getDiemThanhVien());
        	stmt.setDate(5, kh.getNgayDangKyTV());
        	stmt.setString(6, kh.getMaKhachHang());
        	n = stmt.executeUpdate();
        }catch (SQLException e) {
			e.printStackTrace();
		}
   
        return n>0;	
    }

    public boolean create(KhachHang kh){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
			stmt = con.prepareStatement("insert into" +" KhachHang values(?,?,?,?,?,?)");
			stmt.setString(1, kh.getMaKhachHang());
			stmt.setString(2, kh.getTenKhachHang());
			stmt.setString(3, kh.getSoDienThoai());
			stmt.setString(4, kh.getHangThanhVien());
			stmt.setInt(5, kh.getDiemThanhVien());
			stmt.setDate(6, kh.getNgayDangKyTV());
			n =stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return n>0;
	}
    
	public boolean delete(String makh){
		
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	    	ConnectDB.getInstance().connect();
			stmt = con.prepareStatement("delete from KhachHang where MaKH = ?");
			stmt.setString(1, makh);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return n>0;
	}
    	
    
    
    
    
    


}