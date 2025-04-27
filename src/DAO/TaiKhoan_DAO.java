/*
 * @ (#) TaiKhoan_DAO.java   1.0     4/23/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */

package DAO;

import java.sql.*;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.TaiKhoan;

/*
 * @description: Data Access Object (DAO) của entity Tài Khoản
 * @author: Huỳnh Gia Mân
 * @date:  4/23/2025
 * @version:    1.0
 */

public class TaiKhoan_DAO {
	ArrayList<TaiKhoan> dstk;
	TaiKhoan tk ;
	
	
	public TaiKhoan_DAO() {
		super();
		this.dstk = new ArrayList<TaiKhoan>();
		this.tk = new TaiKhoan();
	}
	
	public ArrayList<TaiKhoan> getListTaiKhoan(){
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM TaiKhoan";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String username = rs.getString(1);
                String password = rs.getString(2);
                String role = rs.getString(3);
                TaiKhoan tk2 = new TaiKhoan(username, password, role);
                dstk.add(tk2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstk;

    }

    public TaiKhoan getTaiKhoan(String manv) {
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM TaiKhoan where username = ?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setString(1, manv);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {

                String pass = rs.getString(2);
                String role = rs.getString(3);
                tk = new TaiKhoan(manv,pass,role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    public boolean create(TaiKhoan tk) throws SQLException{
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        stmt = con.prepareStatement("insert into" +" TaiKhoan values(?,?,?)");
        stmt.setString(1,tk.getUsername());
        stmt.setString(2, tk.getPassword());
        stmt.setString(3, tk.getRole());
        n =stmt.executeUpdate();
        return n>0;
    }

    public boolean update(TaiKhoan tk){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n =0 ;
        try {
            stmt = con.prepareStatement("update TaiKhoan set password=?,role=? "
                    + "where username=?");

            stmt.setString(1, tk.getPassword());
            stmt.setString(2, tk.getRole());
            stmt.setString(3,tk.getUsername());
            n = stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return n>0;
    }

    public boolean delete(String username){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("delete from TaiKhoan where username = ?");
            stmt.setString(1, username);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }


	
	
	
}
