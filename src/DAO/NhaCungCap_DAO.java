/*
 * @ (#) NhaCungCap_DAO.java   1.0     4/27/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package DAO;

import ConnectDB.ConnectDB;
import Entity.NhaCungCap;
import Entity.NhanVien;

import java.sql.*;
import java.util.ArrayList;

/*
 * @description: Data Access Object (DAO) của entity Nhà Cung Cấp
 * @author: Nguyễn Minh Hào
 * @date:  4/27/2025
 * @version:    1.0
 */
public class NhaCungCap_DAO {
    ArrayList<NhaCungCap> dsncc;
    NhaCungCap ncc;

    public NhaCungCap_DAO() {
        dsncc = new ArrayList<NhaCungCap>();
        ncc = new NhaCungCap();
    }

    public ArrayList<NhaCungCap> getListNhaCungCap(){ // done
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM NhaCungCap";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String mancc = rs.getString(1);
                String tenncc = rs.getString(2);
                String soDt  = rs.getString(3);
                String diaChi = rs.getString(4);

                NhaCungCap ncc1 = new NhaCungCap(mancc, tenncc, soDt, diaChi);
                dsncc.add(ncc1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsncc;
    }

    public NhaCungCap getNhaCungCap(String mancc) { // done
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM NhaCungCap where MaNCC = ?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setString(1, mancc);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                String mancc1 = rs.getString(1);
                String tenncc = rs.getString(2);
                String soDt  = rs.getString(3);
                String diaChi = rs.getString(4);

                ncc = new NhaCungCap(mancc1, tenncc, soDt, diaChi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ncc;
    }

    public boolean update(NhaCungCap ncc){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n =0 ;
        try {
            stmt = con.prepareStatement("update NhaCungCap set TenNCC=?,SoDT=?,DiaChi=? where MaNV=?");
            stmt.setString(1, ncc.getTenNCC());
            stmt.setString(2,ncc.getSoDT());
            stmt.setString(3, ncc.getDiaChi());
            stmt.setString(4, ncc.getMaNCC());

            n = stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }

    public boolean create(NhaCungCap ncc) throws SQLException{
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        stmt = con.prepareStatement("insert into" +" NhaCungCap values(?,?,?,?)");
        stmt.setString(1, ncc.getMaNCC());
        stmt.setString(2, ncc.getTenNCC());
        stmt.setString(3, ncc.getSoDT());
        stmt.setString(4, ncc.getDiaChi());
        n =stmt.executeUpdate();

        return n>0;
    }



}
