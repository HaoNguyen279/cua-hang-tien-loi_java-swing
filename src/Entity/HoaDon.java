package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class HoaDon {
	private String maHoaDon;
	private LocalDate ngayMuaHang;
	private NhanVien nhanVienBan;
	private SanPham sanPhamDaMua;
	
	
	
	public HoaDon(String maHoaDon, LocalDate ngayMuaHang, NhanVien nhanVienBan, SanPham sanPhamDaMua) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayMuaHang = ngayMuaHang;
		this.nhanVienBan = nhanVienBan;
		this.sanPhamDaMua = sanPhamDaMua;
	}



	public String getMaHoaDon() {
		return maHoaDon;
	}



	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}



	public LocalDate getNgayMuaHang() {
		return ngayMuaHang;
	}



	public void setNgayMuaHang(LocalDate ngayMuaHang) {
		this.ngayMuaHang = ngayMuaHang;
	}



	public NhanVien getNhanVienBan() {
		return nhanVienBan;
	}



	public void setNhanVienBan(NhanVien nhanVienBan) {
		this.nhanVienBan = nhanVienBan;
	}



	public SanPham getSanPhamDaMua() {
		return sanPhamDaMua;
	}



	public void setSanPhamDaMua(SanPham sanPhamDaMua) {
		this.sanPhamDaMua = sanPhamDaMua;
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
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayMuaHang=" + ngayMuaHang + ", nhanVienBan=" + nhanVienBan
				+ ", sanPhamDaMua=" + sanPhamDaMua + "]";
	}
	
	
}
