package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class SanPham {
	private String maSanPham;
	private String tenSanPham;
	private String loaiSanPham;
	private String nhaCungCap;
	private LocalDate ngaySanXuat;
	private LocalDate hanSuDung;
	private int soLuong;
	
	
	
	public SanPham(String maSanPham, String tenSanPham, String loaiSanPham, String nhaCungCap, LocalDate ngaySanXuat,
			LocalDate hanSuDung, int soLuong) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.loaiSanPham = loaiSanPham;
		this.nhaCungCap = nhaCungCap;
		this.ngaySanXuat = ngaySanXuat;
		this.hanSuDung = hanSuDung;
		this.soLuong = soLuong;
	}



	public String getMaSanPham() {
		return maSanPham;
	}



	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}



	public String getTenSanPham() {
		return tenSanPham;
	}



	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}



	public String getLoaiSanPham() {
		return loaiSanPham;
	}



	public void setLoaiSanPham(String loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}



	public String getNhaCungCap() {
		return nhaCungCap;
	}



	public void setNhaCungCap(String nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}



	public LocalDate getNgaySanXuat() {
		return ngaySanXuat;
	}



	public void setNgaySanXuat(LocalDate ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}



	public LocalDate getHanSuDung() {
		return hanSuDung;
	}



	public void setHanSuDung(LocalDate hanSuDung) {
		this.hanSuDung = hanSuDung;
	}



	public int getSoLuong() {
		return soLuong;
	}



	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maSanPham);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SanPham other = (SanPham) obj;
		return Objects.equals(maSanPham, other.maSanPham);
	}
	
	
	
}
