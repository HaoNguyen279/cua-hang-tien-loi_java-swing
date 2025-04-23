package DAO;

import java.sql.*;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.TaiKhoan;

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
