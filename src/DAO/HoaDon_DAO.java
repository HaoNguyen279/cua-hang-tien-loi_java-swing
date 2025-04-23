package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ConnectDB.ConnectDB;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.SanPham;
import Entity.TaiKhoan;

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
		public static void main(String[] args) {
			ConnectDB.getInstance().connect();
			HoaDon_DAO dao = new HoaDon_DAO();
			
			 SanPham sp1 = new SanPham("B403", "Chuột", "Thiết bị", "Logitech",null , null, 100, 150000);
		        SanPham sp2 = new SanPham("K501", "Bàn phím", "Thiết bị", "Razer",null, null, 50, 500000);
		        Map<SanPham, Integer> gioHang = new HashMap<>();
		        gioHang.put(sp1, 2);
		        gioHang.put(sp2, 1);
		        NhanVien nv = new NhanVien("nv1023");
		        KhachHang kh = new KhachHang(null);
		        	
		        HoaDon hoaDon = new HoaDon(null, kh, nv, gioHang, null);
		        hoaDon.setSanPhamList(gioHang);
		        dao.create(hoaDon);
		        if(dao.createCTHoaDon(hoaDon)) {
		    	System.out.println("yes");
		      }
		    }
		}
		