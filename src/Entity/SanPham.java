package Entity;

import java.time.LocalDate;

public class SanPham {
	private String maSanPham;
	private String tenSanPham;
	private String loaiSanPham;
	private String nhaCungCap;
	private LocalDate ngaySanXuat;
	private LocalDate hanSuDung;
	private int soLuongKho;
	private double donGia;

	public SanPham(String maSanPham, String tenSanPham, String loaiSanPham, String nhaCungCap, LocalDate ngaySanXuat, LocalDate hanSuDung, int soLuongKho, double donGia) {
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.loaiSanPham = loaiSanPham;
		this.nhaCungCap = nhaCungCap;
		this.ngaySanXuat = ngaySanXuat;
		this.hanSuDung = hanSuDung;
		this.soLuongKho = soLuongKho;
		this.donGia = donGia;
	}

	public SanPham() {
		this.maSanPham = null;
		this.tenSanPham = null;
		this.loaiSanPham = null;
		this.nhaCungCap = null;
		this.ngaySanXuat = null;
		this.hanSuDung = null;
		this.soLuongKho = 0;
		this.donGia = 0.0;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public String getLoaiSanPham() {
		return loaiSanPham;
	}

	public String getNhaCungCap() {
		return nhaCungCap;
	}

	public LocalDate getNgaySanXuat() {
		return ngaySanXuat;
	}

	public LocalDate getHanSuDung() {
		return hanSuDung;
	}

	public int getSoLuongKho() {
		return soLuongKho;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public void setLoaiSanPham(String loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

	public void setNhaCungCap(String nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public void setNgaySanXuat(LocalDate ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}

	public void setHanSuDung(LocalDate hanSuDung) {
		this.hanSuDung = hanSuDung;
	}

	public void setSoLuongKho(int soLuongKho) {
		this.soLuongKho = soLuongKho;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	@Override
	public String toString() {
		return "SanPham{" +
				"maSanPham='" + maSanPham + '\'' +
				", tenSanPham='" + tenSanPham + '\'' +
				", loaiSanPham='" + loaiSanPham + '\'' +
				", nhaCungCap='" + nhaCungCap + '\'' +
				", ngaySanXuat=" + ngaySanXuat +
				", hanSuDung=" + hanSuDung +
				", soLuongKho=" + soLuongKho +
				", donGia=" + donGia +
				'}' + "\n";
	}
}
