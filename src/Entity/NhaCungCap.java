/*
 * @ (#) NhaCungCap.java   1.0     4/27/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package Entity;

/*
 * @description:
 * @author:
 * @date:  4/27/2025
 * @version:    1.0
 */
public class NhaCungCap {
    private String maNCC ;
    private String tenNCC;
    private String soDT;
    private String diaChi;


    public NhaCungCap(String maNCC, String tenNCC, String soDienThoai, String diaChi) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.soDT = soDienThoai;
        this.diaChi = diaChi;
    }

    public NhaCungCap() {
        super();
    }

    public NhaCungCap(String maNCC) {
        this.maNCC = maNCC;
    }

    //ấc vc
            // sỹ bâu
    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "NhaCungCap{" +
                "maNCC='" + maNCC + '\'' +
                ", tenNCC='" + tenNCC + '\'' +
                ", soDienThoai='" + soDT + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
