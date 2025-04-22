/*
 * @ (#) EmployeeGUI.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementApp extends JFrame implements ActionListener {
    private JPanel pnlSanPham, pnlNhanVien;
    private JButton btnTaoMaGiamGia, btnXoaNhanVien, btnThemSanPham, btnXemMaGiamGia,
            btnSuaSanPham, btnXoaSanPham, btnSuaTTNhanVien, btnTaoTKNhanVien;

    public ManagementApp() {
        pnlSanPham = new JPanel();
        pnlSanPham.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlSanPham.setBackground(Color.LIGHT_GRAY);

        pnlNhanVien = new JPanel();
        pnlNhanVien.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlNhanVien.setBackground(Color.LIGHT_GRAY);

        btnTaoMaGiamGia = new JButton("Tạo mã giảm giá");
        btnXoaNhanVien = new JButton("Xóa nhân viên");
        btnThemSanPham = new JButton("Thêm sản phẩm");
        btnXemMaGiamGia = new JButton("sex");
        btnSuaSanPham = new JButton("Sửa sản phẩm");
        btnXoaSanPham = new JButton("Xóa sản phẩm");
        btnSuaTTNhanVien = new JButton("Sửa tt nhân viên");
        btnTaoTKNhanVien = new JButton("Tạo TK nhân viên");

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(64, 224, 208));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(20, 20));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        leftPanel.setOpaque(false);

        pnlSanPham.setLayout(new BorderLayout());
        JLabel lblSanPham = new JLabel("Table sản phẩm", SwingConstants.CENTER);
        lblSanPham.setFont(new Font("Arial", Font.BOLD, 16));
        pnlSanPham.add(lblSanPham, BorderLayout.CENTER);

        pnlNhanVien.setLayout(new BorderLayout());
        JLabel lblNhanVien = new JLabel("Table nhân viên", SwingConstants.CENTER);
        lblNhanVien.setFont(new Font("Arial", Font.BOLD, 16));
        pnlNhanVien.add(lblNhanVien, BorderLayout.CENTER);

        leftPanel.add(pnlSanPham);
        leftPanel.add(pnlNhanVien);

        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rightPanel.setOpaque(false);

        rightPanel.add(btnTaoMaGiamGia);
        rightPanel.add(btnXoaNhanVien);
        rightPanel.add(btnThemSanPham);
        rightPanel.add(btnXemMaGiamGia);
        rightPanel.add(btnSuaSanPham);
        rightPanel.add(btnXoaSanPham);
        rightPanel.add(btnSuaTTNhanVien);
        rightPanel.add(btnTaoTKNhanVien);

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);

        btnTaoMaGiamGia.addActionListener(this);
        btnXoaNhanVien.addActionListener(this);
        btnThemSanPham.addActionListener(this);
        btnXemMaGiamGia.addActionListener(this);
        btnSuaSanPham.addActionListener(this);
        btnXoaSanPham.addActionListener(this);
        btnSuaTTNhanVien.addActionListener(this);
        btnTaoTKNhanVien.addActionListener(this);

        setTitle("Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnTaoMaGiamGia) {
            JOptionPane.showInputDialog(this,"Nhập mã giảm giá");
        } else if (o == btnXoaNhanVien) {
            JOptionPane.showMessageDialog(this, "Xóa nhân viên clicked");
        } else if (o == btnThemSanPham) {
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm clicked");
        } else if (o == btnXemMaGiamGia) {
            JOptionPane.showMessageDialog(this, "Sex button clicked");
        } else if (o == btnSuaSanPham) {
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm clicked");
        } else if (o == btnXoaSanPham) {
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm clicked");
        } else if (o == btnSuaTTNhanVien) {
            JOptionPane.showMessageDialog(this, "Sửa tt nhân viên clicked");
        } else if (o == btnTaoTKNhanVien) {
            JOptionPane.showMessageDialog(this, "Tạo TK nhân viên clicked");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManagementApp();
        });
    }
}