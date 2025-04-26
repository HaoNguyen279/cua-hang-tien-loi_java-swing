/*
 * @ (#) NhanVien_Dao.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package DAO;

import Entity.NhanVien;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;

/*
 * @description:
 * @author:
 * @date:  4/21/2025
 * @version:    1.0
 */
public class NhanVien_DAO {
    ArrayList<NhanVien> dsnv;
    NhanVien nv;
    public NhanVien_DAO(){
        dsnv = new ArrayList<NhanVien>();
        nv = new NhanVien();
    }

    
    public ArrayList<NhanVien> getListNhanVien(){
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM NhanVien";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String manv = rs.getString(1);
                String tennv = rs.getString(2);
                Date ngaysinh = rs.getDate(3);
                Date ngayvaolam = rs.getDate(4);
                String role = rs.getString(5);
                
                NhanVien nv1 = new NhanVien(manv, tennv, ngaysinh, ngayvaolam, role);
                dsnv.add(nv1);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsnv;
    }
    
    public NhanVien getNhanVien(String manv) {
    	try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT * FROM NhanVien where MaNhanVien = ?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setString(1, manv);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
            	String manv1 = rs.getString(1);
                String tennv = rs.getString(2);
                Date ngaysinh = rs.getDate(3);
                Date ngayvaolam = rs.getDate(4);
                String role = rs.getString(5);
                nv = new NhanVien(manv1, tennv, ngaysinh, ngayvaolam, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }
    
    public boolean update(NhanVien nv){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n =0 ;
        try {
        	stmt = con.prepareStatement("update NhanVien set TenNhanVien=?,NgaySinh=?,NgayVaoLam=?,ChucVu=? where MaNhanVien=?");
        	stmt.setString(1, nv.getTenNhanVien());
        	stmt.setDate(2,nv.getNgaySinh());
        	stmt.setDate(3, nv.getNgayVaoLam());
        	stmt.setString(4, nv.getChucVu());
        	stmt.setString(5, nv.getMaNhanVien());
        	n = stmt.executeUpdate();
        }catch (SQLException e) {
			e.printStackTrace();
		}
   
        return n>0;	
    }

    public boolean create(NhanVien nv){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
			stmt = con.prepareStatement("insert into" +" NhanVien values(?,?,?,?,?)");
			stmt.setString(1, nv.getMaNhanVien());
			stmt.setString(2, nv.getTenNhanVien());
			stmt.setDate(3, nv.getNgaySinh());
			stmt.setDate(4, nv.getNgayVaoLam());
			stmt.setString(5, nv.getChucVu());
			n =stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return n>0;
	}
    
	public boolean delete(String manv){
		
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	    	ConnectDB.getInstance().connect();
			TaiKhoan_DAO dao = new TaiKhoan_DAO();
			dao.delete(manv);
			stmt = con.prepareStatement("delete from NhanVien where MaNhanVien = ?");
			stmt.setString(1, manv);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return n>0;
	}
    	
    
    
    
    

    


}
