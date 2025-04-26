package Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class SanPham {
	private String maSanPham;
	private String tenSanPham;
	private String loaiSanPham;
	private String nhaCungCap;
	private Date ngaySanXuat;
	private Date hanSuDung;
	private int soLuongKho;
	private double donGia;

	public SanPham(String maSanPham, String tenSanPham, String loaiSanPham, String nhaCungCap, Date ngaySx, Date hanSd, int soLuongKho, double donGia) {
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.loaiSanPham = loaiSanPham;
		this.nhaCungCap = nhaCungCap;
		this.ngaySanXuat = ngaySx;
		this.hanSuDung = hanSd;
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
	public SanPham(String maSp) {
		this.maSanPham = maSp;
		this.tenSanPham = null;
		this.loaiSanPham = null;
		this.nhaCungCap = null;
		this.ngaySanXuat = null;
		this.hanSuDung = null;
		this.soLuongKho = 0;
		this.donGia = 0.0;
	}

	public SanPham(String masp, Double dongia2) {
		// TODO Auto-generated constructor stub
		this.maSanPham = masp;
		this.tenSanPham = null;
		this.loaiSanPham = null;
		this.nhaCungCap = null;
		this.ngaySanXuat = null;
		this.hanSuDung = null;
		this.soLuongKho = 0;
		this.donGia = dongia2;
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

	public Date getNgaySanXuat() {
		return ngaySanXuat;
	}

	public Date getHanSuDung() {
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

	public void setNgaySanXuat(Date ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}

	public void setHanSuDung(Date hanSuDung) {
		this.hanSuDung = hanSuDung;
	}

	public void setSoLuongKho(int soLuongKho) {
		this.soLuongKho = soLuongKho;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
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
				'}';
	}
}
