/*
 * @ (#) SanPham_DAO.java   1.0     4/22/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package DAO;

import ConnectDB.ConnectDB;
import Entity.KhachHang;
import Entity.NhaCungCap;
import Entity.SanPham;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/*
 * @description: Data Access Object (DAO) của entity Sản phẩm
 * @author: Nguyễn Minh Hào, Huỳnh Gia Mân
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
                NhaCungCap nhaCungCap = new NhaCungCap(rs.getString(4));
                java.sql.Date ngaySx = rs.getDate(5);
                java.sql.Date hanSd = rs.getDate(6);
                int soLuongKho = rs.getInt(7);
                double donGia = rs.getDouble(8);

                SanPham sp = new SanPham(maSp, tenSp, loaiSp, nhaCungCap, ngaySx, hanSd, soLuongKho, donGia);
                listSanPham.add(sp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;

    }

    public boolean update(SanPham sp){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n =0 ;
        try {
        	stmt = con.prepareStatement("update SanPham set TenSP=?,LoaiSP=?,MaNCC=? ,NgaySX=?,HanSD=?,SoLuongKho=?,DonGia=? "
        			+ "where MaSP=?");
        	stmt.setString(1, sp.getTenSanPham());
        	stmt.setString(2,sp.getLoaiSanPham());
        	stmt.setString(3,sp.getNhaCungCap().getMaNCC());
        	stmt.setDate(4, sp.getNgaySanXuat());
        	stmt.setDate(5,sp.getHanSuDung());
        	stmt.setInt(6, sp.getSoLuongKho());
        	stmt.setDouble(7, sp.getDonGia());
        	stmt.setString(8, sp.getMaSanPham());
        	n = stmt.executeUpdate();
        }catch (SQLException e) {
			e.printStackTrace();
		}
   
        return n>0;	
    }

    public boolean create(SanPham sp) throws SQLException{
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;

        stmt = con.prepareStatement("insert into" +" SanPham values(?,?,?,?,?,?,?,?)");
        stmt.setString(1, sp.getMaSanPham());
        stmt.setString(2, sp.getTenSanPham());
        stmt.setString(3, sp.getLoaiSanPham());
        stmt.setString(4, sp.getNhaCungCap().getMaNCC());
        stmt.setDate(5, sp.getNgaySanXuat());
        stmt.setDate(6, sp.getHanSuDung());
        stmt.setInt(7,sp.getSoLuongKho());
        stmt.setDouble(8, sp.getDonGia());
        n =stmt.executeUpdate();

        return n>0;
    }

    public boolean delete(String masp){
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("delete from SanPham where MaSP = ?");
            stmt.setString(1, masp);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }
}
