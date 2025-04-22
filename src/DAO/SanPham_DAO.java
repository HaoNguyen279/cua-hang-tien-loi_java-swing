/*
 * @ (#) SanPham_DAO.java   1.0     4/22/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package DAO;

import ConnectDB.ConnectDB;
import Entity.SanPham;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/*
 * @description: Data Access Object (DAO) của entity Sản phẩm
 * @author: Nguyễn Minh Hào
 * @date:  4/22/2025
 * @version:    1.0
 */
public class SanPham_DAO {
    ArrayList<SanPham> listSanPham;
    SanPham sanPham;

    public SanPham_DAO() {
         listSanPham = new ArrayList<>();
         sanPham = new SanPham();
    }
    public ArrayList<SanPham> getListSanPham(){
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM SanPham";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                String maSp = rs.getString(1);
                String tenSp = rs.getString(2);
                String loaiSp = rs.getString(3);
                String nhaCungCap = rs.getString(4);
                LocalDate ngaySx = rs.getDate(5).toLocalDate();
                LocalDate hanSd = rs.getDate(6).toLocalDate();
                int soLuongKho = rs.getInt(7);
                Double donGia = rs.getDouble(8);

                SanPham sp = new SanPham(maSp,tenSp,loaiSp, nhaCungCap,ngaySx,hanSd,soLuongKho,donGia);
                listSanPham.add(sp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;

    }

    public boolean update(SanPham sp){
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean create(SanPham sp){
        try{
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


}
