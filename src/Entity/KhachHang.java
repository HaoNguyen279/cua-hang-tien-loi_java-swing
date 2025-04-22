package Entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String hangThanhVien;
    private int diemThanhVien;
    private LocalDate ngayDangKyTV;
    private ArrayList<HoaDon> lsMuaHang; //này là lịch sử mua hàng

    public KhachHang(String maKhachHang, String tenKhachHang, String soDienThoai, String hangThanhVien,
                     int diemThanhVien, LocalDate ngayDangKyTV, ArrayList<HoaDon> lsMuaHang) {
        super();
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.hangThanhVien = hangThanhVien;
        this.diemThanhVien = diemThanhVien;
        this.ngayDangKyTV = ngayDangKyTV;
        this.lsMuaHang = lsMuaHang;
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
    public LocalDate getNgayDangKyTV() {
        return ngayDangKyTV;
    }
    public void setNgayDangKyTV(LocalDate ngayDangKyTV) {
        this.ngayDangKyTV = ngayDangKyTV;
    }
    public ArrayList<HoaDon> getLsMuaHang() {
        return lsMuaHang;
    }
    public void setLsMuaHang(ArrayList<HoaDon> lsMuaHang) {
        this.lsMuaHang = lsMuaHang;
    }

    public void capNhatHangThanhVien(){
        if(diemThanhVien > 1000){
            setHangThanhVien("Kim cương");
        } else if(diemThanhVien >= 500){
            setHangThanhVien("Vàng");
        } else if(diemThanhVien >= 100){
            setHangThanhVien("Bạc");
        } else {
            setHangThanhVien("Thành viên mới");
        }
    }
}
