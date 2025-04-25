/*
 * @ (#) HoaDonPanel.java   1.0     4/24/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package GUI;

import javax.swing.*;

/*
 * @description:
 * @author:
 * @date:  4/24/2025
 * @version:    1.0
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Entity.SanPham;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HoaDonDialog extends JDialog {
    private String maHoaDon;
    private Double thanhTien, giamGia,tongThanhTien, khachDua, tienThua;
    private JTable tblSanPham;
    private DefaultTableModel modelSanPham;
    private JTextField txtTongCong,txtGiamGia,txtTongSauGiamGia,txtKhachDua,txtTienThua;
    private JLabel lblNgayInHoaDon,lblMaHoaDon;
    private final DecimalFormat moneyFormat = new DecimalFormat("#,### VND");

    public HoaDonDialog(Map<SanPham, Integer> listsp,Double khach,int giamgia) {
        khoiTaoThanhPhan(listsp,khach,giamgia);
       
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void khoiTaoThanhPhan(Map<SanPham, Integer> listsp,Double Khach, int giamgia) {
        setLayout(new BorderLayout(10, 10));
        
        // Panel chứa thông tin công ty
        JPanel pnlThongTinCongTy = new JPanel();
        pnlThongTinCongTy.setLayout(new BoxLayout(pnlThongTinCongTy, BoxLayout.Y_AXIS));

        JLabel lblTenCongTy = new JLabel("Cửa Hàng Tiện Lợi SYBAI");
        lblTenCongTy.setFont(new Font("Arial", Font.BOLD, 16));
        lblTenCongTy.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDiaChi = new JLabel("104 Nguyễn Văn Bảo, Gò Vấp, TPHCM");
        lblDiaChi.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTieuDeHoaDon = new JLabel("HÓA ĐƠN",JLabel.CENTER);
        lblTieuDeHoaDon.setFont(new Font("Arial", Font.BOLD, 20));
        lblTieuDeHoaDon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ngày và mã hóa đơn
        JPanel pnlNgayMaHoaDon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNgayMaHoaDon.setAlignmentX(Component.RIGHT_ALIGNMENT);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayHienTai = dateFormat.format(new Date());

        lblNgayInHoaDon = new JLabel("Ngày in: "  + ngayHienTai, JLabel.RIGHT);
        lblMaHoaDon = new JLabel("Mã hóa đơn: " + maHoaDon, JLabel.RIGHT);
        lblMaHoaDon.setBorder(BorderFactory.createEmptyBorder(0,0,0,40));
        pnlNgayMaHoaDon.add(lblMaHoaDon);

        pnlNgayMaHoaDon.add(lblNgayInHoaDon);


        // Thêm các thành phần vào panel thông tin công ty
        pnlThongTinCongTy.add(Box.createVerticalStrut(10));
        pnlThongTinCongTy.add(lblTenCongTy);
        pnlThongTinCongTy.add(lblDiaChi);
        pnlThongTinCongTy.add(Box.createVerticalStrut(10));
        pnlThongTinCongTy.add(lblTieuDeHoaDon);
        pnlThongTinCongTy.add(Box.createVerticalStrut(5));

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.add(pnlThongTinCongTy, BorderLayout.CENTER);
        pnlHeader.add(pnlNgayMaHoaDon, BorderLayout.SOUTH);

        // Tạo bảng sản phẩm
        String[] tenCot = {"Mã SP", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền"};
        modelSanPham = new DefaultTableModel(tenCot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cannot edit in any cell
            }
        };

        tblSanPham = new JTable(modelSanPham);
        tblSanPham.setRowHeight(25);

        // Thiết lập kích thước cột
        tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrSanPham = new JScrollPane(tblSanPham);

        // Thêm dữ liệu mẫu vào bảng
        themDuLieuMau(listsp);

        // Panel chứa thông tin tổng tiền
        JPanel pnlTongTien = new JPanel(new GridLayout(5, 2, 10, 5));
        pnlTongTien.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        pnlTongTien.add(new JLabel("Tổng cộng:"));
        txtTongCong = new JTextField("45.000");
        txtTongCong.setEditable(false);
        pnlTongTien.add(txtTongCong);

        pnlTongTien.add(new JLabel("Giảm giá:"));
        txtGiamGia = new JTextField("0");
        txtGiamGia.setEditable(false);
        pnlTongTien.add(txtGiamGia);

        pnlTongTien.add(new JLabel("Tổng thành tiền:"));
        txtTongSauGiamGia = new JTextField("45.000");
        txtTongSauGiamGia.setEditable(false);
        pnlTongTien.add(txtTongSauGiamGia);

        pnlTongTien.add(new JLabel("Khách đưa"));
        txtKhachDua = new JTextField("45.000");
        txtKhachDua.setEditable(false);
        pnlTongTien.add(txtKhachDua);

        pnlTongTien.add(new JLabel("Tiền thừa: "));
        txtTienThua = new JTextField("45.000");
        txtTienThua.setEditable(false);
        pnlTongTien.add(txtTienThua);

        // Thêm các thành phần vào Dialog chính
        add(pnlHeader, BorderLayout.NORTH);
        add(scrSanPham, BorderLayout.CENTER);
        add(pnlTongTien, BorderLayout.SOUTH);

        // Thêm sự kiện để tính toán thành tiền và tổng tiền
        tinhTongTien(Khach,giamgia);
    }

    private void themDuLieuMau(Map<SanPham, Integer> listsp) {
        // Thêm dữ liệu mẫu vào bảng
    	for (Map.Entry<SanPham, Integer> entry : listsp.entrySet()) {
    	    SanPham key = entry.getKey();
    	    Integer value = entry.getValue();
//    	    double soLuong = Double.parseDouble(Integer.toString(value));
//            double donGia = Double.parseDouble(Double.toString(key.getDonGia()));
//            double thanhTien = soLuong * donGia;
//    	    System.out.println(soLuong);
//    	    System.out.println(donGia);
//            System.out.println(thanhTien);
            
    	    System.out.println("Mã: " + key + ", Số lượng: " + value);
    	    String[] rowdata = { key.getMaSanPham(), key.getTenSanPham(), Integer.toString(value), Double.toString(key.getDonGia())};
    	    modelSanPham.addRow(rowdata);
    	}
    	
    }
        

    private void tinhTongTien(Double khach, int giamgia) {
        double tongCong = 0;
        DecimalFormat df = new DecimalFormat("#,###");
        
        for (int i = 0; i < modelSanPham.getRowCount(); i++) {	
            Object soLuongObj = modelSanPham.getValueAt(i, 2);
            Object donGiaObj = modelSanPham.getValueAt(i, 3);
            
            if (soLuongObj != null && donGiaObj != null) {
                try {
                    // Chỉ xóa dấu phẩy trong chuỗi số
                    double soLuong = Double.parseDouble(soLuongObj.toString().replace(",", ""));
                    
                    // Chỉ xóa dấu phẩy trong chuỗi số, giữ lại dấu chấm thập phân
                    double donGia = Double.parseDouble(donGiaObj.toString().replace(",", ""));
                    
                    double thanhTien = soLuong * donGia;
                    
                    modelSanPham.setValueAt(df.format(thanhTien), i, 4);
                    tongCong += thanhTien;
                } catch (NumberFormatException ex) {
                    // Xử lý khi có lỗi chuyển đổi số
                }
            }
        }
        giamGia = tongCong*giamgia/100.0; 
        // Cập nhật các trường tổng tiền
        txtGiamGia.setText(Integer.toString(giamgia)+"%");
        txtTongCong.setText(df.format(tongCong));
        txtTongSauGiamGia.setText(df.format(tongCong - giamGia));
        txtKhachDua.setText(df.format(khach));
        tienThua = khach - (tongCong-giamGia);
        txtTienThua.setText(df.format(tienThua));
    }
    public void hienThiHoaDon() {
        setVisible(true);
    }

    
}
