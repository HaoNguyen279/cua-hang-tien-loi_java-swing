package Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HoaDon {
	private String maHoaDon;
	private KhachHang khachHangMua;
	private NhanVien nhanVienBan;
	private Map<SanPham, Integer> sanPhamList;
	private Date ngayMuaHang;
	
	
	public HoaDon(KhachHang khachHangMua, NhanVien nhanVienBan, Map<SanPham, Integer> sanPhamList, Date ngayMuaHang) {
		super();
		this.khachHangMua = khachHangMua;
		this.nhanVienBan = nhanVienBan;
		this.sanPhamList = sanPhamList;
		this.ngayMuaHang = ngayMuaHang;
	}

	public HoaDon() {
		super();
	}


	public HoaDon(String maHoaDon, KhachHang khachHangMua, NhanVien nhanVienBan, Map<SanPham, Integer> sanPhamList,
			Date ngayMuaHang) {
		super();
		this.maHoaDon = maHoaDon;
		this.khachHangMua = khachHangMua;
		this.nhanVienBan = nhanVienBan;
		this.sanPhamList = new HashMap<SanPham, Integer>();
		this.ngayMuaHang = ngayMuaHang;
	}


	public String getMaHoaDon() {
		return maHoaDon;
	}



	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}



	public KhachHang getKhachHangMua() {
		return khachHangMua;
	}



	public void setKhachHangMua(KhachHang khachHangMua) {
		this.khachHangMua = khachHangMua;
	}



	public NhanVien getNhanVienBan() {
		return nhanVienBan;
	}



	public void setNhanVienBan(NhanVien nhanVienBan) {
		this.nhanVienBan = nhanVienBan;
	}



	public Map<SanPham, Integer> getSanPhamList() {
		return sanPhamList;
	}



	public void setSanPhamList(Map<SanPham, Integer> sanPhamList) {
		this.sanPhamList = sanPhamList;
	}



	public Date getNgayMuaHang() {
		return ngayMuaHang;
	}



	public void setNgayMuaHang(Date ngayMuaHang) {
		this.ngayMuaHang = ngayMuaHang;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHoaDon, other.maHoaDon);
	}



	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", khachHangMua=" + khachHangMua + ", nhanVienBan=" + nhanVienBan
				+ ", sanPhamList=" + sanPhamList + ", ngayMuaHang=" + ngayMuaHang + "]";
	}

	

}