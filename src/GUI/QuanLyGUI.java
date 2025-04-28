/*
 * @ (#) QuanLyGUI.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */
package GUI;

import DAO.*;
import Entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.NhanVien_DAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLyGUI extends JFrame implements ActionListener, MouseListener {
    private JTextField txtMaNV, txtTenNV, txtChucVu, txtNgaySinh,txtNgayVaoLam;
    private JTextField txtMaSP, txtTenSP, txtLoaiSP,
            txtNgaySX, txtHanSD, txtSoLuong, txtDonGia;
    private JComboBox<String> cboMaNhaCungCap;
    private JTable tableSanPham;
    private DefaultTableModel tableModelSanPham;
    private JTable tableNhanVien;
    private DefaultTableModel tableModelNhanVien;


    private JPanel pnlSanPham, pnlNhanVien;
    private JButton btnXoaNhanVien, btnThemSanPham,btnXemDoanhThu,btnThoat,
            btnSuaSanPham, btnXoaSanPham, btnSuaTTNhanVien, btnTaoTKNhanVien,btnXoaTrang,btnThayDoiMK;

    private ArrayList<SanPham> listSp;
    private ArrayList<NhanVien> listNv;
    private SanPham_DAO sp_dao = new SanPham_DAO();
    private NhanVien_DAO nv_dao = new NhanVien_DAO();
    private TaiKhoan_DAO tk_dao = new TaiKhoan_DAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final Font fntMid = new Font("Roboto", Font.PLAIN, 18);
    private static  String mk;
    public QuanLyGUI() {
        pnlSanPham = new JPanel();
        pnlSanPham.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlSanPham.setBackground(Color.LIGHT_GRAY);

        pnlNhanVien = new JPanel();
        pnlNhanVien.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlNhanVien.setBackground(Color.LIGHT_GRAY);

        btnThemSanPham = new JButton("Thêm sản phẩm");
        btnSuaSanPham = new JButton("Sửa sản phẩm");
        btnXoaSanPham = new JButton("Xóa sản phẩm");
        btnTaoTKNhanVien = new JButton("Thêm nhân viên");
        btnSuaTTNhanVien = new JButton("Sửa tt nhân viên");
        btnXoaNhanVien = new JButton("Xóa nhân viên");
        btnXoaTrang = new JButton("Xóa trắng");
        btnThayDoiMK = new JButton("Thay đổi mật khẩu");
        btnXemDoanhThu = new JButton("Xem doanh thu");
        btnThoat = new JButton("Thoát");


        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(20, 20));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        leftPanel.setOpaque(false);

        //pnl Sản Phẩm
        pnlSanPham.setLayout(new BorderLayout());
        Box boxMainSanPham = Box.createVerticalBox();
        Box bSp1, bSp2, bSp3, bSp4, bSp5;
        JLabel lblMaSp, lblTenSp, lblLoaiSp, lblNhaCungCap, lblNgaySX, lblHanSD, lblSoLuong, lblDonGia;

        boxMainSanPham.add(Box.createVerticalStrut(10));
        boxMainSanPham.add(bSp1= Box.createHorizontalBox());
        boxMainSanPham.add(Box.createHorizontalStrut(10));
        bSp1.add(Box.createHorizontalStrut(10));
        bSp1.add(lblMaSp = new JLabel("Mã sản phẩm: "));
        bSp1.add(txtMaSP = new JTextField());
        bSp1.add(Box.createHorizontalStrut(10));
        bSp1.add(lblTenSp = new JLabel("Tên sản phẩm: "));
        bSp1.add(txtTenSP = new JTextField());
        bSp1.add(Box.createHorizontalStrut(10));
        boxMainSanPham.add(Box.createVerticalStrut(10));


        boxMainSanPham.add(bSp2= Box.createHorizontalBox());
        boxMainSanPham.add(Box.createHorizontalStrut(10));
        bSp2.add(Box.createHorizontalStrut(10));
        bSp2.add(lblLoaiSp = new JLabel("Loại sản phẩm: "));
        bSp2.add(txtLoaiSP = new JTextField());
        bSp2.add(Box.createHorizontalStrut(10));
        bSp2.add(lblNhaCungCap = new JLabel("Nhà cung cấp: "));
        //List maNCC
        cboMaNhaCungCap = new JComboBox<>();
        NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
        ArrayList<String> listMaNCC = new ArrayList<>(ncc_dao.getListNhaCungCap().stream()
                .map(NhaCungCap::getMaNCC)
                .toList());
        for( String mancc : listMaNCC){
            cboMaNhaCungCap.addItem(mancc);
        }

        bSp2.add(cboMaNhaCungCap);
        bSp2.add(Box.createHorizontalStrut(10));
        boxMainSanPham.add(Box.createVerticalStrut(10));

        boxMainSanPham.add(bSp3= Box.createHorizontalBox());
        boxMainSanPham.add(Box.createHorizontalStrut(10));
        bSp3.add(Box.createHorizontalStrut(10));
        bSp3.add(lblNgaySX = new JLabel("Ngày sản xuất: "));
        bSp3.add(txtNgaySX = new JTextField());
        bSp3.add(Box.createHorizontalStrut(10));
        bSp3.add(lblHanSD = new JLabel("Hạn sử dụng: "));
        bSp3.add(txtHanSD = new JTextField());
        bSp3.add(Box.createHorizontalStrut(10));
        boxMainSanPham.add(Box.createVerticalStrut(10));

        boxMainSanPham.add(bSp4= Box.createHorizontalBox());
        boxMainSanPham.add(Box.createHorizontalStrut(10));
        bSp4.add(Box.createHorizontalStrut(10));
        bSp4.add(lblSoLuong = new JLabel("Số lượng kho: "));
        bSp4.add(txtSoLuong = new JTextField());
        bSp4.add(Box.createHorizontalStrut(10));
        bSp4.add(lblDonGia = new JLabel("Đơn giá: "));
        bSp4.add(txtDonGia = new JTextField());
        bSp4.add(Box.createHorizontalStrut(10));
        boxMainSanPham.add(Box.createVerticalStrut(10));

        boxMainSanPham.add(bSp5= Box.createHorizontalBox());
        boxMainSanPham.add(Box.createHorizontalStrut(10));
        String[] headerSanPham = "Mã Sp;Tên Sp;Loại Sp;Nhà cung cấp;Ngày SX;Hạn SD;Số lượng;Đơn giá".split(";");
        tableModelSanPham = new DefaultTableModel(headerSanPham, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // can only edit on column 3
            }
        };
        tableSanPham = new JTable(tableModelSanPham);
        tableSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// chỉnh single selection mode
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        tableSanPham.setRowHeight(30);
        bSp5.add(scrollSanPham);

        lblMaSp.setPreferredSize(lblLoaiSp.getPreferredSize());
        lblTenSp.setPreferredSize(lblLoaiSp.getPreferredSize());
        lblNhaCungCap.setPreferredSize(lblLoaiSp.getPreferredSize());
        lblNgaySX.setPreferredSize(lblLoaiSp.getPreferredSize());
        lblHanSD.setPreferredSize(lblLoaiSp.getPreferredSize());
        lblSoLuong.setPreferredSize(lblLoaiSp.getPreferredSize());
        lblDonGia.setPreferredSize(lblLoaiSp.getPreferredSize());
        pnlSanPham.add(boxMainSanPham);

        //pnl Nhân Viên
        pnlNhanVien.setLayout(new BorderLayout());
        Box boxMainNhanVien = Box.createVerticalBox();
        Box bNv1, bNv2, bNv3;
        JLabel lblMaNv, lblTenNv, lblChucVu, lblNgaySinh, lblNgayVaoLam;

        boxMainNhanVien.add(Box.createVerticalStrut(10));
        boxMainNhanVien.add(bNv1= Box.createHorizontalBox());
        boxMainNhanVien.add(Box.createHorizontalStrut(10));
        bNv1.add(Box.createHorizontalStrut(10));
        bNv1.add(lblMaNv = new JLabel("Mã Nhân viên: "));
        bNv1.add(txtMaNV = new JTextField());
        bNv1.add(Box.createHorizontalStrut(10));
        bNv1.add(lblTenNv = new JLabel("Tên Nhân viên: "));
        bNv1.add(txtTenNV = new JTextField());
        bNv1.add(Box.createHorizontalStrut(10));
        boxMainNhanVien.add(Box.createVerticalStrut(10));

        boxMainNhanVien.add(Box.createVerticalStrut(10));
        boxMainNhanVien.add(bNv2= Box.createHorizontalBox());
        boxMainNhanVien.add(Box.createHorizontalStrut(10));
        bNv2.add(Box.createHorizontalStrut(10));
        bNv2.add(lblChucVu = new JLabel("Chức vụ: "));
        bNv2.add(txtChucVu = new JTextField());
        bNv2.add(Box.createHorizontalStrut(10));
        bNv2.add(lblNgaySinh = new JLabel("Ngày sinh: "));
        bNv2.add(txtNgaySinh = new JTextField());
        bNv2.add(Box.createHorizontalStrut(10));
        bNv2.add(lblNgayVaoLam = new JLabel("Ngày vào làm: "));
        bNv2.add(txtNgayVaoLam = new JTextField());
        bNv2.add(Box.createHorizontalStrut(10));
        boxMainNhanVien.add(Box.createVerticalStrut(10));

        boxMainNhanVien.add(bNv3= Box.createHorizontalBox());
        boxMainNhanVien.add(Box.createHorizontalStrut(10));
        String[] headerNhanVien = "Mã Nv;Tên Nv;Chức vụ;Ngày sinh;Ngày vào làm".split(";");
        tableModelNhanVien = new DefaultTableModel(headerNhanVien, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // can only edit on column 3
            }
        };
        tableNhanVien = new JTable(tableModelNhanVien);
        tableNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// chỉnh single selection mode
        JScrollPane scrollNhanVien = new JScrollPane(tableNhanVien);
        tableNhanVien.setRowHeight(30);
        bNv3.add(scrollNhanVien);

        lblMaNv.setPreferredSize(lblTenNv.getPreferredSize());
        lblChucVu.setPreferredSize(lblTenNv.getPreferredSize());
        lblNgaySinh.setPreferredSize(lblTenNv.getPreferredSize());
        lblNgayVaoLam.setPreferredSize(lblTenNv.getPreferredSize());
        pnlNhanVien.add(boxMainNhanVien);

        leftPanel.add(pnlSanPham);
        leftPanel.add(pnlNhanVien);

        JPanel rightPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        rightPanel.setOpaque(false);

        rightPanel.add(taoButtonPanel(btnThemSanPham));
        rightPanel.add(taoButtonPanel(btnSuaSanPham));
        rightPanel.add(taoButtonPanel(btnXoaSanPham));
        rightPanel.add(taoButtonPanel(btnTaoTKNhanVien));
        rightPanel.add(taoButtonPanel(btnSuaTTNhanVien));
        rightPanel.add(taoButtonPanel(btnXoaNhanVien));
        rightPanel.add(taoButtonPanel(btnXoaTrang));
        rightPanel.add(taoButtonPanel(btnThayDoiMK));
        rightPanel.add(taoButtonPanel(btnXemDoanhThu));
        rightPanel.add(taoButtonPanel(btnThoat));

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);

        btnXoaNhanVien.addActionListener(this);
        btnThemSanPham.addActionListener(this);
        btnSuaSanPham.addActionListener(this);
        btnXoaSanPham.addActionListener(this);
        btnSuaTTNhanVien.addActionListener(this);
        btnTaoTKNhanVien.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnThayDoiMK.addActionListener(this);
        btnXemDoanhThu.addActionListener(this);
        btnThoat.addActionListener(this);


        tableSanPham.addMouseListener(this);
        tableNhanVien.addMouseListener(this);

        ConnectDB.getInstance().connect();
        loadData_sanPham();
        loadData_nhanVien();
        setTitle("Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnXoaNhanVien) {
            try {
                String maNV = txtMaNV.getText();
                java.util.Date dateNS = sdf.parse(txtNgaySinh.getText());
                try {
                    int result = JOptionPane.showConfirmDialog(
                            this,
                            "Bạn có chắc muốn xóa?",
                            "Xác nhận",
                            JOptionPane.YES_NO_OPTION
                    );
                    if(result == JOptionPane.YES_OPTION) {
                        nv_dao.delete(maNV);
                        loadData_nhanVien();
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Đã Hủy!",
                                "Hủy", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Xóa thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Hãy chọn nhân viên!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else if (o == btnThemSanPham) {

            themSanPhamAction();
        } else if (o == btnSuaSanPham) {
            String maSp = txtMaSP.getText();
            if(maSp.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm!");
                return;
            }
            else if(!listSp.contains(new SanPham(maSp))){
                JOptionPane.showMessageDialog(this,"Mã sản phẩm này không tồn tại, không thể cập nhật!");
                return;
            }
            try {

                String tenSp = txtTenSP.getText();
                String loaiSp = txtLoaiSP.getText();
                NhaCungCap nhaCungCap = new NhaCungCap(cboMaNhaCungCap.getSelectedItem().toString());
                java.util.Date dateSX = sdf.parse(txtNgaySX.getText());
                Date ngaySX = new java.sql.Date(dateSX.getTime());
                java.util.Date dateHSD = sdf.parse(txtHanSD.getText());
                Date hanSD = new java.sql.Date(dateHSD.getTime());
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                double donGia = Double.parseDouble(txtDonGia.getText());
                SanPham sp = new SanPham(maSp, tenSp, loaiSp, nhaCungCap, ngaySX, hanSD, soLuong, donGia);
                try {
                    sp_dao.update(sp);
                    System.out.println(sp);
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thành công",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadData_sanPham();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }


            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Hãy chọn sản phẩm!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }


        } else if (o == btnXoaSanPham) {
            try {
                String maSp = txtMaSP.getText();
                java.util.Date dateSX = sdf.parse(txtNgaySX.getText());
                try {
                    int result = JOptionPane.showConfirmDialog(
                            this,
                            "Bạn có chắc muốn xóa?",
                            "Xác nhận",
                            JOptionPane.YES_NO_OPTION
                    );
                    if(result == JOptionPane.YES_OPTION) {
                        sp_dao.delete(maSp);
                        loadData_sanPham();
                        xoaTrang();
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Đã Hủy!",
                                "Hủy", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Xóa thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Hãy chọn sản phẩm!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else if (o == btnSuaTTNhanVien) {
            try {
                String maNV = txtMaNV.getText();
                String tenNV = txtTenNV.getText();
                String chucvu = txtChucVu.getText();
                java.util.Date dateNS = sdf.parse(txtNgaySinh.getText());
                Date ngaySinh = new java.sql.Date(dateNS.getTime());
                java.util.Date dateVL = sdf.parse(txtNgayVaoLam.getText());
                Date ngayVaoLam = new java.sql.Date(dateVL.getTime());
                NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, ngayVaoLam, chucvu);
                try {
                    nv_dao.update(nv);
                    System.out.println(nv);
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thành công",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadData_nhanVien();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }


            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Hãy chọn nhân viên ",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else if (o == btnTaoTKNhanVien) {
            if(!validateNhanVien()) {
                return ;
            }
            try {

                // Mã Sp;Tên Sp;Loại Sp;Nhà cung cấp;Ngày SX;Hạn SD;Số lượng;Đơn giá
                String maNV = txtMaNV.getText();
                String tenNV = txtTenNV.getText();
                String chucvu = txtChucVu.getText();
                java.util.Date dateNS = sdf.parse(txtNgaySinh.getText());
                Date ngaySinh = new java.sql.Date(dateNS.getTime());
                java.util.Date dateVL = sdf.parse(txtNgayVaoLam.getText());
                Date ngayVaoLam = new java.sql.Date(dateVL.getTime());


                if (maNV.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(this,
                            "Hãy nhập mã sản phẩm!",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, ngayVaoLam, chucvu);

                    try {
                        nv_dao.create(nv);
                        TaiKhoan tk = new TaiKhoan(maNV, "1", chucvu);
                        tk_dao.create(tk);
                        System.out.println(nv);
                        JOptionPane.showMessageDialog(this,
                                "Thêm nhân viên thành công!",
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadData_nhanVien();
                        xoaTrang();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,
                                "Trùng mã nhân viên !",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Dữ liệu nhập vào không hợp lệ!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(o == btnXoaTrang) {
            xoaTrang();
        }
        else if(o == btnThayDoiMK) {

            try {

                String maNV = txtMaNV.getText();
                String tenNV = txtTenNV.getText();
                String chucVu = txtChucVu.getText();
                java.util.Date dateNS = sdf.parse(txtNgaySinh.getText());
                String matKhau =  nhapMatKhau();
                System.out.println(matKhau);
                TaiKhoan tk = new TaiKhoan(maNV, matKhau, chucVu);
                try {
                    tk_dao.update(tk);
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thành công",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadData_nhanVien();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Cập nhật thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Hãy chọn nhân viên ",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
        else  if(o == btnXemDoanhThu){
            HoaDon_DAO hd_dao = new HoaDon_DAO();
            ArrayList<ThongKeDoanhThu> listTK = new ArrayList<ThongKeDoanhThu>();

            listTK = hd_dao.getThongKe();
            System.out.println(listTK);
            new DoanhThuDialog(this,listTK);
            System.out.println("haha");
        }
        else if (o == btnThoat){
            int choice =  JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn thoát chứ!");
            if(choice == JOptionPane.YES_OPTION){
                this.dispose();
                new DangNhapGUI();
            }
        }
    }

    public String nhapMatKhau() {
        JTextField txtMatKhau = new JTextField(20);
        mk = null;
        JButton btnOk = new JButton("Xác nhận");

        btnOk.setFont(fntMid);
        btnOk.setMaximumSize(new Dimension(200,50));
        btnOk.setMinimumSize(new Dimension(200,50));
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box pnlMatKhau = Box.createVerticalBox();
        pnlMatKhau.add(Box.createVerticalStrut(10));
        pnlMatKhau.add(txtMatKhau);
        pnlMatKhau.add(Box.createVerticalStrut(50));
        pnlMatKhau.add(btnOk);

        JOptionPane optionPane = new JOptionPane(
                pnlMatKhau, // component hiển thị trong dialog
                JOptionPane.PLAIN_MESSAGE, // loại thông báo, PLAIN là ko có biểu tượng thông báo
                JOptionPane.DEFAULT_OPTION, // Kiểu lựa chọn mặc định, do tự custom lại nút nên set là DEFAULT
                null, // icon cho JDialog
                new Object[]{}, // mảng button
                null // button mặc định
        );
        JDialog dialog = optionPane.createDialog("Nhập mật khẩu mới");
        btnOk.addActionListener(e -> {
            mk = txtMatKhau.getText();
            if(mk.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this,
                        "Không được để trống!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else {
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
        return mk;

    }

    private void xoaTrang() {
        // Xóa trắng Jtextfield sản phẩm
        txtMaSP.setEditable(true);
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtLoaiSP.setText("");
        cboMaNhaCungCap.setSelectedIndex(0);
        txtNgaySX.setText("");
        txtHanSD.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtMaSP.requestFocus();
        // Xóa trắng Jtextfield nhân viên
        txtMaNV.setEditable(true);
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtChucVu.setText("");
        txtNgaySinh.setText("");
        txtNgayVaoLam.setText("");

    }

    private void themSanPhamAction() {
        if(!validateSanPham()) {
            return ;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // Mã Sp;Tên Sp;Loại Sp;Nhà cung cấp;Ngày SX;Hạn SD;Số lượng;Đơn giá
            String maSp = txtMaSP.getText();
            String tenSp = txtTenSP.getText();
            String loaiSp = txtLoaiSP.getText();
            NhaCungCap nhaCungCap = new NhaCungCap(cboMaNhaCungCap.getSelectedItem().toString());
            java.util.Date dateSX = sdf.parse(txtNgaySX.getText());
            Date ngaySX = new java.sql.Date(dateSX.getTime());
            java.util.Date dateHSD = sdf.parse(txtHanSD.getText());
            Date hanSD = new java.sql.Date(dateHSD.getTime());
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            double donGia = Double.parseDouble(txtDonGia.getText());

            if (maSp.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this,
                        "Hãy nhập mã sản phẩm!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                SanPham sp = new SanPham(maSp, tenSp, loaiSp, nhaCungCap, ngaySX, hanSD, soLuong, donGia);

                try {
                    sp_dao.create(sp);
                    System.out.println(sp);
                    JOptionPane.showMessageDialog(this,
                            "Thêm sản phẩm thành công!",
                            "Lỗi", JOptionPane.INFORMATION_MESSAGE);
                    loadData_sanPham();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Trùng mã sản phẩm !",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Dữ liệu nhập vào không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if(o == tableSanPham){
            txtMaSP.setEditable(false);
            int row = tableSanPham.getSelectedRow();
            txtMaSP.setText(tableSanPham.getValueAt(row,0).toString());
            txtTenSP.setText(tableSanPham.getValueAt(row, 1).toString());
            txtLoaiSP.setText(tableSanPham.getValueAt(row, 2).toString());
            cboMaNhaCungCap.setSelectedItem(tableSanPham.getValueAt(row, 3).toString());
            txtNgaySX.setText(tableSanPham.getValueAt(row,4).toString());
            txtHanSD.setText(tableSanPham.getValueAt(row, 5).toString());
            txtSoLuong.setText(tableSanPham.getValueAt(row, 6).toString());
            txtDonGia.setText(tableSanPham.getValueAt(row, 7).toString());
        }
        else if(o == tableNhanVien){
            txtMaNV.setEditable(false);
            int row = tableNhanVien.getSelectedRow();
            txtMaNV.setText(tableNhanVien.getValueAt(row, 0).toString());
            txtTenNV.setText(tableNhanVien.getValueAt(row, 1).toString());
            txtChucVu.setText(tableNhanVien.getValueAt(row, 2).toString());
            txtNgaySinh.setText(tableNhanVien.getValueAt(row, 3).toString());
            txtNgayVaoLam.setText(tableNhanVien.getValueAt(row, 4).toString());
        }
    }
    public void loadData_sanPham(){
        tableModelSanPham.setRowCount(0); // reset table
        SanPham_DAO spDao = new SanPham_DAO();
        listSp = spDao.getListSanPham();
        for (SanPham sanPham : listSp) {
            Object[] rowData = {
                    sanPham.getMaSanPham(),
                    sanPham.getTenSanPham(),
                    sanPham.getLoaiSanPham(),
                    sanPham.getNhaCungCap().getMaNCC(),
                    sanPham.getNgaySanXuat(),
                    sanPham.getHanSuDung(),
                    sanPham.getSoLuongKho() == 0 ? "Hết hàng" : sanPham.getSoLuongKho(),
                    sanPham.getDonGia()

            };
            tableModelSanPham.addRow(rowData);
        }
    }
    public void loadData_nhanVien(){
        tableModelNhanVien.setRowCount(0); // reset table
        NhanVien_DAO nv_dao = new NhanVien_DAO();
        listNv = nv_dao.getListNhanVien();
        for (NhanVien nv : listNv) {
            Object[] rowData = {
                    nv.getMaNhanVien(),
                    nv.getTenNhanVien(),
                    nv.getChucVu(),
                    nv.getNgaySinh(),
                    nv.getNgayVaoLam()

            };
            tableModelNhanVien.addRow(rowData);
        }
    }
    private boolean validateSanPham() {
        // Check if any field is empty
        if (txtMaSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hãy nhập mã sản phẩm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtMaSP.requestFocus();
            return false;
        }

        if (txtTenSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hãy nhập tên sản phẩm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtTenSP.requestFocus();
            return false;
        }

        if (txtLoaiSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hãy nhập loại sản phẩm!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtLoaiSP.requestFocus();
            return false;
        }

//        if (txtNhaCungCap.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this,
//                    "Hãy nhập nhà cung cấp!",
//                    "Thông báo", JOptionPane.WARNING_MESSAGE);
//            txtNhaCungCap.requestFocus();
//            return false;
//        }

        // Date validation
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Strict date parsing

        try {
            java.util.Date dateSX = sdf.parse(txtNgaySX.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Ngày sản xuất không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgaySX.requestFocus();
            return false;
        }

        try {
            java.util.Date dateHSD = sdf.parse(txtHanSD.getText());

            // Check if expiry date is after manufacturing date
            if (dateHSD.before(sdf.parse(txtNgaySX.getText()))) {
                JOptionPane.showMessageDialog(this,
                        "Hạn sử dụng phải sau ngày sản xuất!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtHanSD.requestFocus();
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Hạn sử dụng không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtHanSD.requestFocus();
            return false;
        }

        // Number validation
        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Số lượng phải là số nguyên dương!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return false;
        }

        try {
            double donGia = Double.parseDouble(txtDonGia.getText());
            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Đơn giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtDonGia.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Đơn giá không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtDonGia.requestFocus();
            return false;
        }

        // All validations passed
        return true;
    }

    private boolean validateNhanVien() {
        // Check if any field is empty
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hãy nhập mã nhân viên!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        }

        if (txtTenNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hãy nhập tên nhân viên!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtTenNV.requestFocus();
            return false;
        }

        if (txtChucVu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hãy nhập chức vụ!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtChucVu.requestFocus();
            return false;
        }

        // Date validation
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Strict date parsing

        try {
            java.util.Date dateNS = sdf.parse(txtNgaySinh.getText());

            // Check if birth date is in the future
            if (dateNS.after(new java.util.Date())) {
                JOptionPane.showMessageDialog(this,
                        "Ngày sinh không thể trong tương lai!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNgaySinh.requestFocus();
                return false;
            }

            // Check if employee is at least 18 years old
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date());
            calendar.add(Calendar.YEAR, -18);
            if (dateNS.after(calendar.getTime())) {
                JOptionPane.showMessageDialog(this,
                        "Nhân viên phải đủ 18 tuổi!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNgaySinh.requestFocus();
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgaySinh.requestFocus();
            return false;
        }

        try {
            java.util.Date dateVL = sdf.parse(txtNgayVaoLam.getText());
            java.util.Date dateNS = sdf.parse(txtNgaySinh.getText());

            // Check if join date is after birth date
            if (dateVL.before(dateNS)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày vào làm phải sau ngày sinh!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNgayVaoLam.requestFocus();
                return false;
            }

            // Check if join date is in the future
            if (dateVL.after(new java.util.Date())) {
                JOptionPane.showMessageDialog(this,
                        "Ngày vào làm không thể trong tương lai!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNgayVaoLam.requestFocus();
                return false;
            }

            // Calculate age at join date
            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(dateNS);
            Calendar joinCal = Calendar.getInstance();
            joinCal.setTime(dateVL);

            int ageAtJoin = joinCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            if (joinCal.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) ||
                    (joinCal.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) &&
                            joinCal.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
                ageAtJoin--;
            }

            if (ageAtJoin < 18) {
                JOptionPane.showMessageDialog(this,
                        "Nhân viên phải đủ 18 tuổi khi vào làm!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNgayVaoLam.requestFocus();
                return false;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Ngày vào làm không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgayVaoLam.requestFocus();
            return false;
        }

        // Validate mã nhân viên format (assuming it should start with "nv" followed by numbers)
        String maNV = txtMaNV.getText().trim();
        if (!maNV.matches("nv\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Mã nhân viên phải có định dạng 'nv' theo sau bởi số (ví dụ: nv001)!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        }

        // Validate chức vụ (assuming it should be either "nhanvien" or "quanly" based on your table)
        String chucVu = txtChucVu.getText().trim().toLowerCase();
        if (!chucVu.equals("nhanvien") && !chucVu.equals("quanly")) {
            JOptionPane.showMessageDialog(this,
                    "Chức vụ phải là 'nhanvien' hoặc 'quanly'!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtChucVu.requestFocus();
            return false;
        }

        // All validations passed
        return true;
    }
    private void setFixedSize(JButton button,int width, int height) { // su dung gridbaglayout ko có tác dụng set size
        // nên tạo function set size cho button
        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
    }
    private JPanel taoButtonPanel(JButton button) {
        JPanel pnlBtn = new JPanel(new GridBagLayout());
        pnlBtn.setOpaque(false);  // xóa background của panel
        setFixedSize(button, 140, 80);
        pnlBtn.add(button);
        return pnlBtn;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

