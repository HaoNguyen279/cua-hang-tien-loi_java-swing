package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Entity.SanPham;
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
                TaiKhoan tk = new TaiKhoan(username, password, role);
                dstk.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstk;

    }
	public static void main(String[] args) {
		ConnectDB.getInstance().connect();
		TaiKhoan_DAO dao = new TaiKhoan_DAO();
		ArrayList<TaiKhoan> tk = new ArrayList<TaiKhoan>();
		tk=dao.getListTaiKhoan();
		for(TaiKhoan tv : tk) {
			System.out.println(tv);
		}
	}
	
	
	
}
