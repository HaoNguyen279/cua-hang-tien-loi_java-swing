/*
 * @ (#) NhanVien_Dao.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package DAO;

import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectDB.ConnectDB;

/*
 * @description:
 * @author:
 * @date:  4/21/2025
 * @version:    1.0
 */
public class NhanVien_Dao {
    ArrayList<NhanVien> dsnv;
    NhanVien nv;
    public NhanVien_Dao(){
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
    
    
    
    
    public static void main(String[] args) {
		ConnectDB.getInstance().connect();
		NhanVien_Dao dao = new NhanVien_Dao();
		ArrayList<NhanVien> ds = new ArrayList<NhanVien>();
//		ds = dao.getListNhanVien();
//		for(NhanVien n : ds) {
//			System.out.println(n);
//		}
		NhanVien nv = dao.getNhanVien("101");
		System.out.println(nv);
		
		
	}
    


}
