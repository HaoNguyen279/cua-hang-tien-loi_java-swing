package Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
	private String maNhanVien;
	private String tenNhanVien;
	private Date ngaySinh;//
	private Date ngayVaoLam;
	private String chucVu;
	
	
	
	public NhanVien() {
		super();
	}
	



	public NhanVien(String maNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
	}




	public NhanVien(String maNhanVien, String tenNhanVien, Date ngaySinh, Date ngayVaoLam, 
			String chucVu) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.ngaySinh = ngaySinh;
		this.ngayVaoLam = ngayVaoLam;
		
		this.chucVu = chucVu;
	}



	public String getMaNhanVien() {
		return maNhanVien;
	}



	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}



	public String getTenNhanVien() {
		return tenNhanVien;
	}



	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}



	public Date getNgaySinh() {
		return ngaySinh;
	}



	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}



	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}



	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}



	public String getChucVu() {
		return chucVu;
	}



	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}



	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", ngaySinh=" + ngaySinh
				+ ", ngayVaoLam=" + ngayVaoLam + ", chucVu=" + chucVu + "]";
	}

	
	
}
