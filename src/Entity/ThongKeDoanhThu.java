package Entity;

import java.sql.Date;

public class ThongKeDoanhThu {
	
	private Date ngay;
	private int soLuongHD;
	private int soLuongSP;
	private double tongDoanhThu;
	
	public ThongKeDoanhThu() {
		super();
	}


	public ThongKeDoanhThu(Date ngay, int soLuongHD, int soLuongSP, double tongDoanhThu) {
		super();
		this.ngay = ngay;
		this.soLuongHD = soLuongHD;
		this.soLuongSP = soLuongSP;
		this.tongDoanhThu = tongDoanhThu;
	}

	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public int getSoLuongHD() {
		return soLuongHD;
	}

	public void setSoLuongHD(int soLuongHD) {
		this.soLuongHD = soLuongHD;
	}

	public int getSoLuongSP() {
		return soLuongSP;
	}

	public void setSoLuongSP(int soLuongSP) {
		this.soLuongSP = soLuongSP;
	}

	public double getTongDoanhThu() {
		return tongDoanhThu;
	}

	public void setTongDoanhThu(double tongDoanhThu) {
		this.tongDoanhThu = tongDoanhThu;
	}

	@Override
	public String toString() {
		return "ThongKeDoanhThu [ngay=" + ngay + ", soLuongHD=" + soLuongHD + ", soLuongSP=" + soLuongSP
				+ ", tongDoanhThu=" + tongDoanhThu + "]";
	}

}
