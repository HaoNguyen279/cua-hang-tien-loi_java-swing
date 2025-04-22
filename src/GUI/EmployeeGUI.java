/*
 * @ (#) EmployeeGUI.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package GUI;

import ConnectDB.ConnectDB;
import DAO.SanPham_DAO;
import Entity.SanPham;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * @description: Giao diện chính dành cho nhân viên của cửa hàng
 * @author: Nguyễn Minh Hào
 * @date:  4/21/2025
 * @version:    1.0
 */
public class EmployeeGUI extends JFrame implements ActionListener , MouseListener {
    private JLabel lblTenNhanVien, lblMaNhanVien, lblMaSanPham;
    private JTextField  txtMaSanPham,txtTongTien, txtGiamGia,txtTongThanhTien;
    private JButton btnThemSanPham, btnXoaSanPham, btnTimSanPham,
            btnTheThanhVien, btnKhuyenMai, btnKieuThanhToan, btnXuatHoaDonTam, btnKetCa, btnXuatHoaDon;
    private JTable tblSanPhamHoaDon, tblSanPhamKho;
    private DefaultTableModel dtmSanPhamHoaDon, dtmSanPhamKho;

    private ArrayList<SanPham> listSanPham;
    private int giamGia = 50;
    public EmployeeGUI(String username, String name){
        super();
        
        
        
        JLabel userid = new JLabel("userid: " + username);
        JLabel employeeName = new JLabel("name: "+ name);


        Font fntMid = new Font("Roboto", Font.PLAIN, 18);


        // table 1
        String[] cols = {"Mã sản phẩm" , "Tên sản phẩm" , "Loại sản phẩm","Số luợng", "Đơn giá"};
        dtmSanPhamHoaDon = new DefaultTableModel(cols,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // can only edit on column 3
            }
        };
        tblSanPhamHoaDon = new JTable(dtmSanPhamHoaDon);
        tblSanPhamHoaDon.setRowHeight(30);
        tblSanPhamHoaDon.setFont(fntMid);
        tblSanPhamHoaDon.getTableHeader().setFont(fntMid);

        JTextField txtCellTextField = new JTextField(); // Temporary component
        txtCellTextField.setFont(fntMid);
        DefaultCellEditor cellEditor = new DefaultCellEditor(txtCellTextField); // tạo 1 jtextfield tạm thời khi click vào cell

        tblSanPhamHoaDon.setDefaultEditor(Object.class,cellEditor);
        Object[] sp = {"SP123" , "Nuoc ngot" , "Nuoc uong",123, 8500};



        // table 2
        String[] cols2 = {"Mã sản phẩm" , "Tên sản phẩm" , "Loại sản phẩm", "Đơn giá", "SL Kho"};
        dtmSanPhamKho = new DefaultTableModel(cols2,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // prevent edit on all cells
            }
        };
        tblSanPhamKho = new JTable(dtmSanPhamKho);
        tblSanPhamKho.setRowHeight(20);
        tblSanPhamKho.setFont(fntMid);
        tblSanPhamKho.getTableHeader().setFont(fntMid);
        Object[] sp2 = {"fr79" , "ts pmo" , "Sybau", 1234,999};
        dtmSanPhamKho.addRow(sp2);



        // Init JLabels
        lblMaNhanVien = new JLabel("Mã nhân viên" );
        lblMaSanPham = new JLabel("Nhập mã sản phẩm");
        txtMaSanPham = new JTextField(40);
        txtMaSanPham.setMaximumSize(new Dimension(txtMaSanPham.getWidth(), 50));


        // Init icon
        ImageIcon membership_icon = new ImageIcon("image/membership.png");
        ImageIcon voucher_icon = new ImageIcon("image/voucher.png");
        ImageIcon payment_icon = new ImageIcon("image/payment.png");
        ImageIcon bill_icon = new ImageIcon("image/bill.png");
        // Init JButtons
        btnThemSanPham = new JButton("Thêm sản phẩm");
        btnXoaSanPham = new JButton("Xóa sản phẩm");
        btnTimSanPham = new JButton("Tìm sản phẩm");
        btnTheThanhVien = new JButton("Nhập mã thành viên");
        btnKhuyenMai = new JButton("Nhập mã khuyến mãi");
        btnKieuThanhToan  = new JButton("Kiểu thanh toán");
        btnXuatHoaDonTam = new JButton("Xuất hóa đơn");
        btnKetCa = new JButton("Kết ca");
        btnXuatHoaDon = new JButton("Xuất hóa đơn");

        // ad icon
        btnTheThanhVien.setIcon(new ImageIcon(membership_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnKhuyenMai.setIcon(new ImageIcon(voucher_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnKieuThanhToan.setIcon(new ImageIcon(payment_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnXuatHoaDonTam.setIcon(new ImageIcon(bill_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));


        // Add ActionListener for buttons
        btnThemSanPham.addActionListener(this);
        btnXoaSanPham.addActionListener(this);
        btnTimSanPham.addActionListener(this);
        btnTheThanhVien.addActionListener(this);
        btnKhuyenMai.addActionListener(this);
        btnKieuThanhToan.addActionListener(this);
        btnXuatHoaDonTam.addActionListener(this);
        btnKetCa.addActionListener(this);
        btnXuatHoaDon.addActionListener(this);

        btnThemSanPham.setFont(fntMid);
        btnXoaSanPham.setFont(fntMid);
        btnTimSanPham.setFont(fntMid);
        btnTheThanhVien.setFont(fntMid);
        btnKhuyenMai.setFont(fntMid);
        btnKieuThanhToan.setFont(fntMid);
        btnXuatHoaDonTam.setFont(fntMid);
        btnKetCa.setFont(fntMid);
        btnXuatHoaDon.setFont(fntMid);

        // Add MouseListener for tables
        tblSanPhamHoaDon.addMouseListener(this);
        tblSanPhamKho.addMouseListener(this);


        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        // Panel 1
        JPanel table = new JPanel(new BorderLayout());

        JPanel pnlThanhTien = new JPanel(new BorderLayout());

        JPanel pnlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblTongTien = new JLabel("Tổng tiền");
        JLabel lblGiamGia = new JLabel("Giảm giá");
        JLabel lblTongThanhTien = new JLabel("Tổng thành tiền");
        lblTongThanhTien.setFont(fntMid);
        lblTongTien.setFont(fntMid);
        lblGiamGia.setFont(fntMid);
        lblTongTien.setPreferredSize(lblTongThanhTien.getPreferredSize());
        lblGiamGia.setPreferredSize(lblTongThanhTien.getPreferredSize());

        txtTongTien = new JTextField(20);
        txtGiamGia = new JTextField(20);
        txtTongThanhTien = new JTextField(20);

        txtTongThanhTien.setFont(fntMid);
        txtGiamGia.setFont(fntMid);
        txtTongTien.setFont(fntMid);

        txtTongThanhTien.setBorder(BorderFactory.createEmptyBorder());
        txtGiamGia.setBorder(BorderFactory.createEmptyBorder());
        txtTongTien.setBorder(BorderFactory.createEmptyBorder());

        txtTongThanhTien.setEditable(false);
        txtGiamGia.setEditable(false);
        txtTongTien.setEditable(false);

        txtTongThanhTien.setHorizontalAlignment(SwingConstants.RIGHT);
        txtGiamGia.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongTien.setHorizontalAlignment(SwingConstants.RIGHT);

        // temp ti xoa
        txtTongThanhTien.setText("1,099,000 VND");
        txtGiamGia.setText("10% - 109,900 VND");
        txtTongTien.setText("169,099.000 VND");

        pnlRow1.add(lblTongTien);
        pnlRow1.add(txtTongTien);

        pnlRow2.add(lblGiamGia);
        pnlRow2.add(txtGiamGia);

        pnlRow3.add(lblTongThanhTien);
        pnlRow3.add(txtTongThanhTien);

        Box boxThanhTien = Box.createVerticalBox();
        boxThanhTien.add(pnlRow1);
        boxThanhTien.add(pnlRow2);
        boxThanhTien.add(pnlRow3);

        pnlThanhTien.add(boxThanhTien,BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(tblSanPhamHoaDon);
        scrollPane.setPreferredSize(new Dimension(tblSanPhamHoaDon.getWidth(),600)); // ---- jackk
        table.add(scrollPane,BorderLayout.CENTER);
        table.add(pnlThanhTien, BorderLayout.SOUTH);


        ////  PNL     TREN  PHAI


        JPanel table1 = new JPanel(new GridLayout(1,1)); // Vì maximum size của button sẽ ko có tác dụng với FlowLayout
        table1.setBackground(Color.YELLOW);

        Box boxButtonRightTop = Box.createVerticalBox();

        setFixedSize(btnTheThanhVien);
        setFixedSize(btnKhuyenMai);
        setFixedSize(btnKieuThanhToan);
        setFixedSize(btnXuatHoaDonTam);
        setFixedSize(btnKetCa);

        btnTheThanhVien.setFocusPainted(false);
        btnKhuyenMai.setFocusPainted(false);
        btnKieuThanhToan.setFocusPainted(false);
        btnXuatHoaDonTam.setFocusPainted(false);
        btnKetCa.setFocusPainted(false);

        btnTheThanhVien.setBackground(Color.RED);
        btnKhuyenMai.setBackground(Color.GREEN);
        btnKieuThanhToan.setBackground(Color.CYAN);
        btnXuatHoaDonTam.setBackground(Color.CYAN);
        btnKetCa.setBackground(Color.CYAN);

        boxButtonRightTop.add(btnTheThanhVien);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnKhuyenMai);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnKieuThanhToan);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnXuatHoaDonTam);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnKetCa);

        boxButtonRightTop.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        JPanel pnlWrapper = new JPanel(new GridBagLayout());
        pnlWrapper.add(boxButtonRightTop);

        table1.add(pnlWrapper);


        ////  PNL     DUOI       TRAI ------------------------------------------


        JPanel table2 = new JPanel(new BorderLayout());
        table2.setBackground(new Color(223, 220, 213));


        Box boxButton = Box.createVerticalBox();
        btnThemSanPham.setMaximumSize(new Dimension(150,50));
        btnTimSanPham.setMaximumSize(new Dimension(150,50));
        btnXoaSanPham.setMaximumSize(new Dimension(150,50));
        btnXoaSanPham.setFocusPainted(false);
        btnTimSanPham.setFocusPainted(false);
        btnThemSanPham.setFocusPainted(false);
        btnXoaSanPham.setBackground(Color.RED);
        btnThemSanPham.setBackground(Color.GREEN);
        btnTimSanPham.setBackground(Color.CYAN);
        boxButton.add(btnThemSanPham);
        boxButton.add(Box.createVerticalStrut(30));
        boxButton.add(btnTimSanPham);
        boxButton.add(Box.createVerticalStrut(30));
        boxButton.add(btnXoaSanPham);
        boxButton.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));


        JPanel test69 = new JPanel(new BorderLayout());

        JScrollPane scrollPane2 = new JScrollPane(tblSanPhamKho);
//        scrollPane2.setPreferredSize(tblSanPhamKho.getPreferredSize());
        scrollPane2.setPreferredSize(new Dimension(500, tblSanPhamKho.getRowHeight() * 4 + tblSanPhamKho.getTableHeader().getPreferredSize().height));
        test69.add(scrollPane2,BorderLayout.CENTER);

        JPanel pnlContainerOfTxtMaSanPham = new JPanel();

        pnlContainerOfTxtMaSanPham.setBorder(BorderFactory.createEmptyBorder(0,0, 50,0));
        pnlContainerOfTxtMaSanPham.add(txtMaSanPham);
        test69.add(txtMaSanPham, BorderLayout.NORTH);


        table2.add(test69, BorderLayout.CENTER);
        table2.add(boxButton, BorderLayout.EAST);


        // Panel xanh duong DUOI PHAI ___        END
        JPanel table3 = new JPanel(new BorderLayout());

        JPanel pnlUserInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlUserInfo.add(userid);
        pnlUserInfo.add(employeeName);

        table3.add(pnlUserInfo, BorderLayout.SOUTH);
        table3.add(btnXuatHoaDon, BorderLayout.CENTER);


        // Panel xanh lá (3x2)
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 4; // giup container chiem 3 cot (column)
        gbc1.gridheight = 2; // container chiem 2 dong (row)
        gbc1.weightx = 4.0; // default value se la 1,
        // weight x/y - muc do ma component co the mo rong khi resize frame
        gbc1.weighty = 2.0; //
        gbc1.fill = GridBagConstraints.BOTH;
        container.add(table, gbc1);

        // Panel vàng (1x2)
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 4;
        gbc2.gridy = 0;
        gbc2.gridwidth = 1;
        gbc2.gridheight = 2;
        gbc2.weightx = 1.0;
        gbc2.weighty = 2.0;
        gbc2.fill = GridBagConstraints.BOTH;
        container.add(table1, gbc2);

        // Panel đỏ (3x1)
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.gridwidth = 4;
        gbc3.gridheight = 1;
        gbc3.weightx = 4.0;
        gbc3.weighty = 1.0;
        gbc3.fill = GridBagConstraints.BOTH;
        container.add(table2, gbc3);

        // Panel xanh dương (1x1)
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 4;
        gbc4.gridy = 2;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        gbc4.weightx = 1.0;
        gbc4.weighty = 1.0;
        gbc4.fill = GridBagConstraints.BOTH;
        container.add(table3, gbc4);


        ConnectDB.getInstance().connect();

        loadData_sanPham();


        setSize(1200,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main(String[] args) {
        new EmployeeGUI(null,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == btnThemSanPham){
            int rowSelected = tblSanPhamKho.getSelectedRow();
            if(rowSelected == -1){
                JOptionPane.showMessageDialog(this,"Vui lòng chọn sản phẩm từ table kho!");
                return;
            }
            Object[] data = {
                tblSanPhamKho.getValueAt(rowSelected,0),
                tblSanPhamKho.getValueAt(rowSelected,1),
                tblSanPhamKho.getValueAt(rowSelected,2),
                1,
                tblSanPhamKho.getValueAt(rowSelected,3)
            };
            dtmSanPhamHoaDon.addRow(data);
            updateSubTotal();
        }
        else if(o == btnTimSanPham){

        }
        else if(o == btnXoaSanPham){

        }
        else if(o == btnKhuyenMai){
            String maGiamGia = JOptionPane.showInputDialog(this,"Nhập mã giảm giá");

        }
        else if(o == btnTheThanhVien){

        }
        else if(o == btnKieuThanhToan){

        }
        else if(o == btnXuatHoaDonTam){

        }
        else if(o == btnKetCa){
            int option = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn tắt chứ?");
            if(option == JOptionPane.YES_OPTION){
                this.dispose();
                new LoginGUI();
            }
        }
    }
    private void updateSubTotal(){
        Double total = 0.0;
        Double soLuong = 0.0, donGia = 0.0;
        for(int i = 0; i < tblSanPhamHoaDon.getRowCount(); i++){
            soLuong = Double.parseDouble((tblSanPhamHoaDon.getValueAt(i,3)).toString());
            donGia = Double.parseDouble((tblSanPhamHoaDon.getValueAt(i,4)).toString());
            total += soLuong * donGia;

            System.out.println(txtTongThanhTien.getText());
        }
        total = total - (total/100)*giamGia;
        txtTongThanhTien.setText(total.toString() + " VND");
    }

    private void setFixedSize(JButton button) { // su dung gridbaglayout ko có tác dụng set size
        // nên tạo function set size cho button
        int width = 230;
        int height = 70;
        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
    }

    public void loadData_sanPham(){
        SanPham_DAO spDao = new SanPham_DAO();
        System.out.println("tét");
        listSanPham = spDao.getListSanPham();
        System.out.println(listSanPham);
        System.out.println("tét");

        for(int i = 0; i< listSanPham.size(); i++){

            Object[] rowData = {
            listSanPham.get(i).getMaSanPham(),
            listSanPham.get(i).getTenSanPham(),
            listSanPham.get(i).getLoaiSanPham(),
            listSanPham.get(i).getDonGia(),
            listSanPham.get(i).getSoLuongKho()
            };
            dtmSanPhamKho.addRow(rowData);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Object o = e.getSource();
        if(o == tblSanPhamKho){
            tblSanPhamHoaDon.clearSelection();
            int rowTblSanPhamKho = tblSanPhamKho.getSelectedRow();
            String maSpKho = tblSanPhamKho.getValueAt(rowTblSanPhamKho, 0).toString();
            txtMaSanPham.setText(maSpKho);
        }
        else if(o == tblSanPhamHoaDon){
            tblSanPhamKho.clearSelection();
            int rowTblSanPhamHoaDon = tblSanPhamHoaDon.getSelectedRow();
            String maSpHoaDon = tblSanPhamHoaDon.getValueAt(rowTblSanPhamHoaDon, 0).toString();
            txtMaSanPham.setText(maSpHoaDon);
        }
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
