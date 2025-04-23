package Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String hangThanhVien;
    private int diemThanhVien;
    private Date ngayDangKyTV;


    public KhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, String hangThanhVien,
                     int diemThanhVien, Date ngayDangKyTV) {
        super();
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.hangThanhVien = hangThanhVien;
        this.diemThanhVien = diemThanhVien;
        this.ngayDangKyTV = ngayDangKyTV;
    }
    
    
    

    public KhachHang() {
		super();
	}




	public KhachHang(String maKhachHang) {
        super();
        this.maKhachHang = maKhachHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    public String getTenKhachHang() {
        return tenKhachHang;
    }
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getHangThanhVien() {
        return hangThanhVien;
    }
    public void setHangThanhVien(String hangThanhVien) {
        this.hangThanhVien = hangThanhVien;
    }
    public int getDiemThanhVien() {
        return diemThanhVien;
    }
    public void setDiemThanhVien(int diemThanhVien) {
        this.diemThanhVien = diemThanhVien;
    }
    public Date getNgayDangKyTV() {
        return ngayDangKyTV;
    }
    public void setNgayDangKyTV(Date ngayDangKyTV) {
        this.ngayDangKyTV = ngayDangKyTV;
    }


    public void capNhatHangThanhVien(){
        if(diemThanhVien >= 500){
            setHangThanhVien("Kim cương");
        } else if(diemThanhVien >= 100){
            setHangThanhVien("Vàng");
        } else {
            setHangThanhVien("Bạc");
        }
    }
}
