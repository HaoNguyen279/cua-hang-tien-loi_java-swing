/*
 * @ (#) NhanVienGUI.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package GUI;


import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.SanPham_DAO;
import DAO.TaiKhoan_DAO;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.SanPham;
import Entity.TaiKhoan;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.DecimalFormat;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * @description: Giao diện chính dành cho nhân viên của cửa hàng
 * @author: Nguyễn Minh Hào
 * @date:  4/21/2025
 * @version:    1.0
 */
public class NhanVienGUI extends JFrame implements ActionListener , MouseListener {
    private JLabel lblTenNhanVien, lblMaNhanVien, lblMaSanPham, lblHangThanhVien, lblPhanTramGiam,lblHinhThucThanhToan,
    lblTongTien,lblGiamGia,lblTongThanhTien;
    private JTextField  txtMaSanPham,txtTongTien, txtGiamGia,txtTongThanhTien, txtHangThanhVien, txtPhanTramGiam, txtHinhThucThanhToan;
    private JButton btnThemSanPham, btnXoaSanPham, btnTimSanPham,
            btnTheThanhVien, btnTamDungBan, btnKieuThanhToan, btnXemLaiHoaDon, btnKetCa, btnXuatHoaDon,btnTimTheoTenSanPham;
    private JTable tblSanPhamHoaDon, tblSanPhamKho;
    private DefaultTableModel dtmSanPhamHoaDon, dtmSanPhamKho;

    private ArrayList<SanPham> listSanPham;
    private Map<SanPham, Integer> listSanPhamHoaDon = new HashMap<SanPham, Integer>();
    private Map<SanPham, Integer> listSanPhamHoaDonCu = new HashMap<SanPham, Integer>();
	private KhachHang khThanhVien = new KhachHang(null);
    private KhachHang khThanhVienCu = new KhachHang(null);
	private NhanVien nhanvienbanhang;

    private final String username;
    private final Font fntMid = new Font("Roboto", Font.PLAIN, 18);

	private double tienKhachPre = 0;
    // Final là một biến mà giá trị của nó không thể thay đổi sau khi được gán lần đầu.
	private int phamTramGiam;

  public NhanVienGUI(String username, String name){
        super("Quản lí bán hàng");
        this.username = username;

        // Khởi tạo JTable chứa các sản phẩm trong hóa đơn hiện tại
        String[] cols = {"Mã sản phẩm" , "Tên sản phẩm" , "Loại sản phẩm","Số luợng", "Đơn giá"};
        dtmSanPhamHoaDon = new DefaultTableModel(cols,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // can only edit on column 3
            }
        };

        tblSanPhamHoaDon = new JTable(dtmSanPhamHoaDon);
        tblSanPhamHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// chỉnh single selection mode
        tblSanPhamHoaDon.setRowHeight(30);
        tblSanPhamHoaDon.setFont(fntMid);
        tblSanPhamHoaDon.getTableHeader().setFont(fntMid);

        JTextField txtCellTextField = new JTextField(); // Temporary component
        txtCellTextField.setFont(fntMid);
        DefaultCellEditor cellEditor = new DefaultCellEditor(txtCellTextField); // Tạo 1 jtextfield tạm thời khi click vào cell
        tblSanPhamHoaDon.setDefaultEditor(Object.class,cellEditor);

        // Set up sự kiện khi người dùng thay đổi giá trị của ô ( số lượng )
        DefaultCellEditor editor = (DefaultCellEditor) tblSanPhamHoaDon.getDefaultEditor(Object.class);
        // DefaultCellEditor là 1 class dùng để xử lý chỉnh sửa data cell của JTable
        // (DefaultCellEditor) ép kiểu (casting) do getDefaultEditor của JTable return TableCellEditor

        editor.addCellEditorListener(new CellEditorListener() { // Lắng nghe sự kiện tại các cell của pnlSpHoaDonTongTien
            @Override
            public void editingStopped(ChangeEvent e) {
                int row = tblSanPhamHoaDon.getSelectedRow();
                int col = tblSanPhamHoaDon.getSelectedColumn();
                Object value = tblSanPhamHoaDon.getValueAt(row, col);

                System.out.println("Người dùng vừa nhập xong ô tại [" + row + "," + col + "] với giá trị: " + value);

                try {
					Integer.parseInt(tblSanPhamHoaDon.getValueAt(row, col).toString());
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("Nhập sai định dạng số!");
					tblSanPhamHoaDon.setValueAt(1, row, col);
					updateSubTotal();
					return;
				}

                String maSp = tblSanPhamHoaDon.getValueAt(row, 0).toString();
                int soLuongHienTaiCuaSpTrongHoaDon = 0;
                int soLuongNhanVienVuaNhap = Integer.parseInt(tblSanPhamHoaDon.getValueAt(row, col).toString());
                for(SanPham keySp : listSanPhamHoaDon.keySet()){ // map.keySet return a Set of key của map
                    if(keySp.getMaSanPham().equalsIgnoreCase(maSp))
                        soLuongHienTaiCuaSpTrongHoaDon = listSanPhamHoaDon.get(keySp);
                }
                int soLuongKhoCuaSanPham = listSanPham.stream()
                        .filter(sp -> sp.getMaSanPham().equalsIgnoreCase(maSp))
                        .map(SanPham::getSoLuongKho)
                        .findFirst()
                        .orElse(0);
                if(soLuongKhoCuaSanPham < soLuongNhanVienVuaNhap ){
                    JOptionPane.showMessageDialog(null,"Không đủ hàng!");
                    tblSanPhamHoaDon.setValueAt(soLuongHienTaiCuaSpTrongHoaDon, row, col);
                    return;
                }
                if(Integer.parseInt(tblSanPhamHoaDon.getValueAt(row, col).toString()) <= 0) {
                	tblSanPhamHoaDon.setValueAt(1, row, col);
                }
                updateSubTotal();
                for (SanPham sp : listSanPham) {
                    if (sp.getMaSanPham().equalsIgnoreCase(maSp)) {
                        listSanPhamHoaDon.put(sp, soLuongNhanVienVuaNhap);
                        break;
                    }
                }
            }
            @Override
            public void editingCanceled(ChangeEvent e) {
                System.out.println("Hủy chỉnh sửa");
            }
        });

        // Khởi tạo JTable chứa sản phẩm trong kho
        String[] cols2 = {"Mã sản phẩm" , "Tên sản phẩm" , "Loại sản phẩm", "Đơn giá", "SL Kho"};
        dtmSanPhamKho = new DefaultTableModel(cols2,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // prevent edit on all cells
            }
        };
        tblSanPhamKho = new JTable(dtmSanPhamKho);
        tblSanPhamKho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// chỉnh single selection mode
        tblSanPhamKho.setRowHeight(25);
        tblSanPhamKho.setFont(fntMid);
        tblSanPhamKho.getTableHeader().setFont(fntMid);

        // Khởi tạo JLabels
        lblMaNhanVien = new JLabel("userid: " + username);
        lblTenNhanVien = new JLabel("name: "+ name);
        lblMaNhanVien.setFont(fntMid);
        lblTenNhanVien.setFont(fntMid);

        lblMaSanPham = new JLabel("Nhập mã hoặc tên sản phẩm :");
        lblMaSanPham.setFont(fntMid);

        // JLabel phía bên trái
        lblHangThanhVien  = new JLabel("Hạng thành viên");
        lblPhanTramGiam = new JLabel("Phần trăm giảm");
        lblHinhThucThanhToan = new JLabel("Hình thức thanh toán");
        lblHangThanhVien.setFont(fntMid);
        lblPhanTramGiam.setFont(fntMid);
        lblHinhThucThanhToan.setFont(fntMid);
        lblHangThanhVien.setPreferredSize(lblHinhThucThanhToan.getPreferredSize());
        lblPhanTramGiam.setPreferredSize(lblHinhThucThanhToan.getPreferredSize());
        // JLabel phía bên phải
        lblTongTien = new JLabel("Tổng tiền");
        lblGiamGia = new JLabel("Giảm giá");
        lblTongThanhTien = new JLabel("Tổng thành tiền");
        lblTongThanhTien.setFont(fntMid);
        lblTongTien.setFont(fntMid);
        lblGiamGia.setFont(fntMid);
        lblTongTien.setPreferredSize(lblTongThanhTien.getPreferredSize());
        lblGiamGia.setPreferredSize(lblTongThanhTien.getPreferredSize());

        txtMaSanPham = new JTextField(40);
        txtMaSanPham.setMaximumSize(new Dimension(txtMaSanPham.getWidth(), 50));
        txtMaSanPham.setFont(fntMid);

        // JTextField phía bên phải
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

        // JTextField phía bên trái
        txtHangThanhVien = new JTextField(20);
        txtPhanTramGiam = new JTextField(20);
        txtHinhThucThanhToan = new JTextField(20);
        txtHangThanhVien.setFont(fntMid);
        txtPhanTramGiam.setFont(fntMid);
        txtHinhThucThanhToan.setFont(fntMid);
        txtHangThanhVien.setBorder(BorderFactory.createEmptyBorder());
        txtPhanTramGiam.setBorder(BorderFactory.createEmptyBorder());
        txtHinhThucThanhToan.setBorder(BorderFactory.createEmptyBorder());
        txtHangThanhVien.setEditable(false);
        txtPhanTramGiam.setEditable(false);
        txtHinhThucThanhToan.setEditable(false);
        txtHangThanhVien.setText("Không");
        txtPhanTramGiam.setText("0%");
        txtHinhThucThanhToan.setText("Thanh toán bằng tiền mặt");
        txtHangThanhVien.setHorizontalAlignment(SwingConstants.RIGHT);
        txtPhanTramGiam.setHorizontalAlignment(SwingConstants.RIGHT);
        txtHinhThucThanhToan.setHorizontalAlignment(SwingConstants.RIGHT);

        // Khởi tạo ImageIcon
        ImageIcon membership_icon = new ImageIcon("image/membership.png");
        ImageIcon pause_icon = new ImageIcon("image/pause.png");
        ImageIcon payment_icon = new ImageIcon("image/payment.png");
        ImageIcon bill_icon = new ImageIcon("image/bill.png");
        ImageIcon exit_icon = new ImageIcon("image/exit.png");
        ImageIcon invoice_icon = new ImageIcon("image/invoice.png");
        
        // Khởi tạo JButtons
        btnThemSanPham = new JButton("Thêm SP");
        btnXoaSanPham = new JButton("Xóa SP");
        btnTimSanPham = new JButton("Tìm mã SP");
        btnTheThanhVien = new JButton("Nhập mã thành viên");
        btnTamDungBan = new JButton("Tạm dừng bán");
        btnKieuThanhToan  = new JButton("Kiểu thanh toán");
        btnXemLaiHoaDon = new JButton("Xem lại hóa đơn");
        btnKetCa = new JButton("Kết ca");
        btnXuatHoaDon = new JButton("Xuất hóa đơn");
        btnTimTheoTenSanPham = new JButton("Tìm tên SP");

        // Thêm icon cho các JButton
        btnTheThanhVien.setIcon(new ImageIcon(membership_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnTamDungBan.setIcon(new ImageIcon(pause_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnKieuThanhToan.setIcon(new ImageIcon(payment_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnXemLaiHoaDon.setIcon(new ImageIcon(bill_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnKetCa.setIcon(new ImageIcon(exit_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));
        btnXuatHoaDon.setIcon(new ImageIcon(invoice_icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH)));


        // Thêm ActionListener cho buttons
        btnThemSanPham.addActionListener(this);
        btnXoaSanPham.addActionListener(this);
        btnTimSanPham.addActionListener(this);
        btnTheThanhVien.addActionListener(this);
        btnTamDungBan.addActionListener(this);
        btnKieuThanhToan.addActionListener(this);
        btnXemLaiHoaDon.addActionListener(this);
        btnKetCa.addActionListener(this);
        btnXuatHoaDon.addActionListener(this);
        btnTimTheoTenSanPham.addActionListener(this);

        // Set font cho các JButton
        btnThemSanPham.setFont(fntMid);
        btnXoaSanPham.setFont(fntMid);
        btnTimSanPham.setFont(fntMid);
        btnTheThanhVien.setFont(fntMid);
        btnTamDungBan.setFont(fntMid);
        btnKieuThanhToan.setFont(fntMid);
        btnXemLaiHoaDon.setFont(fntMid);
        btnKetCa.setFont(fntMid);
        btnXuatHoaDon.setFont(fntMid);
        btnTimTheoTenSanPham.setFont(fntMid);

        // Tắt focus vào title của JButton ( viền của chữ trong button )
        btnTheThanhVien.setFocusPainted(false);
        btnTamDungBan.setFocusPainted(false);
        btnKieuThanhToan.setFocusPainted(false);
        btnXemLaiHoaDon.setFocusPainted(false);
        btnKetCa.setFocusPainted(false);
        btnXuatHoaDon.setFocusPainted(false);
        btnXoaSanPham.setFocusPainted(false);
        btnTimSanPham.setFocusPainted(false);
        btnThemSanPham.setFocusPainted(false);
        btnTimTheoTenSanPham.setFocusPainted(false);

        // Set lại màu cho các JButton
        btnTheThanhVien.setBackground(Color.CYAN);
        btnTamDungBan.setBackground(Color.CYAN);
        btnKieuThanhToan.setBackground(Color.CYAN);
        btnXemLaiHoaDon.setBackground(Color.CYAN);
        btnKetCa.setBackground(Color.CYAN);
        btnXuatHoaDon.setBackground(Color.CYAN);
        btnXoaSanPham.setBackground(Color.RED);
        btnThemSanPham.setBackground(Color.GREEN);
        btnTimSanPham.setBackground(Color.CYAN);
        btnTimTheoTenSanPham.setBackground(Color.YELLOW);

        // Set size 1 số JButton
        setFixedSize(btnThemSanPham,150,50);
        setFixedSize(btnTimSanPham,150,50);
        setFixedSize(btnXoaSanPham,150,50);
        setFixedSize(btnTimTheoTenSanPham,150,50);
        setFixedSize(btnTheThanhVien,230,70);
        setFixedSize(btnTamDungBan,230,70);
        setFixedSize(btnKieuThanhToan,230,70);
        setFixedSize(btnXemLaiHoaDon,230,70);
        setFixedSize(btnKetCa,230,70);
        setFixedSize(btnXuatHoaDon,230,70);

        // Thêm MouseListener cho Table
        tblSanPhamHoaDon.addMouseListener(this);
        tblSanPhamKho.addMouseListener(this);

        // GridBagLayout 
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        // Khởi tạo JPanel chứa Jtable sản phẩm hóa đơn, tổng tiền
        JPanel pnlSpHoaDonTongTien = new JPanel(new BorderLayout());

        JPanel pnlThanhTienDuoiJTbaleSp = new JPanel(new BorderLayout());

        JPanel pnlRightRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlRightRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlRightRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pnlRightRow1.add(lblTongTien);
        pnlRightRow1.add(txtTongTien);
        pnlRightRow2.add(lblGiamGia);
        pnlRightRow2.add(txtGiamGia);
        pnlRightRow3.add(lblTongThanhTien);
        pnlRightRow3.add(txtTongThanhTien);

        Box boxThanhTien = Box.createVerticalBox();
        boxThanhTien.add(pnlRightRow1);
        boxThanhTien.add(pnlRightRow2);
        boxThanhTien.add(pnlRightRow3);

        JPanel pnlLeftRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlLeftRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlLeftRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pnlLeftRow1.add(lblHangThanhVien);
        pnlLeftRow1.add(txtHangThanhVien);
        pnlLeftRow2.add(lblPhanTramGiam);
        pnlLeftRow2.add(txtPhanTramGiam);
        pnlLeftRow3.add(lblHinhThucThanhToan);
        pnlLeftRow3.add(txtHinhThucThanhToan);

        Box boxThanhVien = Box.createVerticalBox();
        boxThanhVien.add(pnlLeftRow1);
        boxThanhVien.add(pnlLeftRow2);
        boxThanhVien.add(pnlLeftRow3);

        pnlThanhTienDuoiJTbaleSp.add(boxThanhTien,BorderLayout.EAST);
        pnlThanhTienDuoiJTbaleSp.add(boxThanhVien,BorderLayout.WEST);

      JScrollPane scrollPane = new JScrollPane(tblSanPhamHoaDon);
      scrollPane.setMinimumSize(new Dimension(500, tblSanPhamHoaDon.getRowHeight() * 10));
      scrollPane.setPreferredSize(new Dimension(500, tblSanPhamHoaDon.getRowHeight() * 15));
      pnlSpHoaDonTongTien.setBorder(BorderFactory.createTitledBorder("Hóa đơn"));
      pnlSpHoaDonTongTien.add(scrollPane,BorderLayout.CENTER);
      pnlSpHoaDonTongTien.add(pnlThanhTienDuoiJTbaleSp, BorderLayout.SOUTH);


        // Panel chứa full các button chức năng
        JPanel pnlButtons = new JPanel(new GridLayout(1,1));
        // Vì maximum size của button sẽ ko có tác dụng với FlowLayout

        Box boxButtonRightTop = Box.createVerticalBox();

        boxButtonRightTop.add(btnTheThanhVien);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnKieuThanhToan);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnTamDungBan);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnXemLaiHoaDon);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnKetCa);
        boxButtonRightTop.add(Box.createVerticalStrut(30));
        boxButtonRightTop.add(btnXuatHoaDon);

        JPanel pnlUserInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlUserInfo.add(lblMaNhanVien);
        pnlUserInfo.add(lblTenNhanVien);
        boxButtonRightTop.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        JPanel pnlWrapper = new JPanel();
        JPanel pnlTemp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTemp.add(boxButtonRightTop);
        pnlWrapper.add(pnlTemp);
        pnlButtons.add(pnlWrapper);



        // Panel chứa Table sản phẩm kho và button thêm xóa sửa tìm Sản phẩm

        JPanel pnlSanPhamHoaDonVaButtons = new JPanel(new BorderLayout());
        pnlSanPhamHoaDonVaButtons.setBackground(new Color(223, 220, 213));

        Box boxButton = Box.createVerticalBox();

        boxButton.add(btnThemSanPham);
        boxButton.add(Box.createVerticalStrut(15));
        boxButton.add(btnTimSanPham);
        boxButton.add(Box.createVerticalStrut(15));
        boxButton.add(btnXoaSanPham);
        boxButton.add(Box.createVerticalStrut(15));
        boxButton.add(btnTimTheoTenSanPham);
        boxButton.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        JPanel pnlJtableSpKhoContainer = new JPanel(new BorderLayout());
        JScrollPane scrollPane2 = new JScrollPane(tblSanPhamKho);
        pnlJtableSpKhoContainer.add(scrollPane2,BorderLayout.CENTER);

        JPanel pnlContainerOfTxtMaSanPham = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlContainerOfTxtMaSanPham.setBackground(new Color(223, 220, 213));
        pnlContainerOfTxtMaSanPham.setBorder(BorderFactory.createEmptyBorder(0,0, 10,0));
        pnlContainerOfTxtMaSanPham.add(lblMaSanPham);
        pnlContainerOfTxtMaSanPham.add(txtMaSanPham);

        pnlSanPhamHoaDonVaButtons.setBorder(BorderFactory.createTitledBorder("Sản phẩm kho"));
        pnlSanPhamHoaDonVaButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        pnlSanPhamHoaDonVaButtons.add(pnlContainerOfTxtMaSanPham, BorderLayout.NORTH);
        pnlSanPhamHoaDonVaButtons.add(pnlJtableSpKhoContainer, BorderLayout.CENTER);
        pnlSanPhamHoaDonVaButtons.add(boxButton, BorderLayout.EAST);


        // Panel (4x2)
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 4; // giup container chiem 3 cot (column)
        gbc1.gridheight = 2; // container chiem 2 dong (row)
        gbc1.weightx = 4.0; // default value se la 1,
        // weight x/y - muc do ma component co the mo rong khi resize frame
        gbc1.weighty = 4.0; //
        gbc1.fill = GridBagConstraints.BOTH;
        container.add(pnlSpHoaDonTongTien, gbc1);

        // Panel (1x3)
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 4;
        gbc2.gridy = 0;
        gbc2.gridwidth = 1;
        gbc2.gridheight = 3;
        gbc2.weightx = 1.0;
        gbc2.weighty = 5.0;
        gbc2.fill = GridBagConstraints.BOTH;
        container.add(pnlButtons, gbc2);

        // Panel (4x1)
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.gridwidth = 4;
        gbc3.gridheight = 1;
        gbc3.weightx = 4.0;
        gbc3.weighty = 1.0;
        gbc3.fill = GridBagConstraints.BOTH;
        container.add(pnlSanPhamHoaDonVaButtons, gbc3);

        reloadTongTienPanel();
        ConnectDB.getInstance().connect();
        loadData_sanPham();

        setMinimumSize(new Dimension(1600, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }



    /**
     * description : Phương thức Override actionPerformed - xử lí sự kiện nhấn các Button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == btnThemSanPham){
        	int rowSelected = tblSanPhamKho.getSelectedRow();
            if (rowSelected == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm từ table kho!");
                return;
            }
            try {
                Integer.parseInt(tblSanPhamKho.getValueAt(rowSelected,4).toString());
            }catch (Exception exception){
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "Sản phẩm chọn đã hết hàng!");
                return;
            }
            String maspthem = tblSanPhamKho.getValueAt(rowSelected, 0).toString();
            int soLuongKhoCuaSanPham = listSanPham.stream()
                    .filter(sp -> sp.getMaSanPham().equalsIgnoreCase(maspthem))
                    .map(SanPham::getSoLuongKho)
                    .findFirst()
                    .orElse(0);
            boolean daTonTai = false;

            for (int i = 0; i < tblSanPhamHoaDon.getRowCount(); i++) {
                if (tblSanPhamHoaDon.getValueAt(i, 0).toString().equalsIgnoreCase(maspthem)) {
                    // Tồn tại thì + 1
                    int soLuongCu = Integer.parseInt(tblSanPhamHoaDon.getValueAt(i, 3).toString());
                    if(soLuongCu + 1 > soLuongKhoCuaSanPham){
                        JOptionPane.showMessageDialog(this,"Không đủ hàng để thêm!");
                        return;
                    }
                    tblSanPhamHoaDon.setValueAt(soLuongCu + 1, i, 3);
                    daTonTai = true;
                    break; // out loop
                }
            }
            // Chuaw tồn tại thì thêm
            if (!daTonTai) {
                Object[] data = {
                    tblSanPhamKho.getValueAt(rowSelected, 0),
                    tblSanPhamKho.getValueAt(rowSelected, 1),
                    tblSanPhamKho.getValueAt(rowSelected, 2),
                    1,
                    tblSanPhamKho.getValueAt(rowSelected, 3)
                };
                dtmSanPhamHoaDon.addRow(data);
            }
            tblSanPhamHoaDon.scrollRectToVisible(tblSanPhamHoaDon.getCellRect(tblSanPhamHoaDon.getRowCount(), 0, true));
            updateHoaDon(rowSelected);
            updateSubTotal();
        }
        else if(o == btnTimSanPham){
            String masp = txtMaSanPham.getText();
            tblSanPhamKho.clearSelection();
            for(int i = 0; i< dtmSanPhamKho.getRowCount(); i++){
                if(masp.equalsIgnoreCase(dtmSanPhamKho.getValueAt(i,0).toString())){
                    tblSanPhamKho.setRowSelectionInterval(i,i);
                    tblSanPhamKho.scrollRectToVisible(tblSanPhamKho.getCellRect(i, 0, true)); // scroll đến chỗ selected row
                    return;
                }
            }
            timSanPhamTheoMa(true);
        }
        else if(o == btnTimTheoTenSanPham){
            timSanPhamTheoMa(false);
        }
        else if(o == btnXoaSanPham){
            int rowSelected = tblSanPhamHoaDon.getSelectedRow();
            if(rowSelected == -1){
                JOptionPane.showMessageDialog(this,"Vui lòng chọn sản phẩm từ table hóa đơn!");
                return;
            }
            String maspxoa = tblSanPhamHoaDon.getValueAt(rowSelected, 0).toString();
            SanPham spxoa = new SanPham(maspxoa);
            listSanPhamHoaDon.remove(spxoa);
            dtmSanPhamHoaDon.removeRow(rowSelected);
            updateSubTotal();
        }
        else if(o == btnTamDungBan){
            showLoginDialog(username);
        }
        else if(o == btnTheThanhVien){
        	Map.Entry<String, Integer> entry = nhapMaThanhVien().entrySet().iterator().next();
        	// map.entry là 1 interface đại diện cho 1 cặp giá trị key-value
        	 txtHangThanhVien.setText(entry.getKey());
        	 txtPhanTramGiam.setText(entry.getValue().toString() + "%");
        	updateSubTotal();
        }
        else if(o == btnKieuThanhToan){
            chonPhuongThucThanhToan();
        }
        else if(o == btnXuatHoaDon){
        	String tt = txtTongThanhTien.getText().toLowerCase().replace("vnd", "").replace(",","").trim();
        	double tongtien = Double.parseDouble(tt);
            double tienKhach = 0;
            if(txtHinhThucThanhToan.getText().equalsIgnoreCase("Thanh toán bằng tiền mặt")){
                tienKhach = new TinhTienDialog().showPaymentKeypad(this,tongtien);
                if(tienKhach>0) {
                    //update số lượng sản phẩm
                    SanPham_DAO sp_dao = new SanPham_DAO();
                    for (Map.Entry<SanPham, Integer> entry : listSanPhamHoaDon.entrySet()) {
                        SanPham key = entry.getKey();
                        Integer value = entry.getValue();
                        key.setSoLuongKho(key.getSoLuongKho()-value);
                        sp_dao.update(key);
                    }
                    //ghi hoa don len csdls
                    nhanvienbanhang = new NhanVien(username);
                    HoaDon hd = new HoaDon( khThanhVien, nhanvienbanhang, listSanPhamHoaDon, Date.valueOf(LocalDate.now()));
                    System.out.println(hd);
                    HoaDon_DAO hd_dao = new HoaDon_DAO();
                    hd_dao.create(hd);
                    hd_dao.createCTHoaDon(hd);
                    //tao hoa don
                    new HoaDonDialog(listSanPhamHoaDon,tienKhach,Integer.parseInt(txtPhanTramGiam.getText().replace("%", "").trim()), khThanhVien).hienThiHoaDon();
                    listSanPhamHoaDonCu = listSanPhamHoaDon;
                    tienKhachPre = tienKhach;
                    khThanhVienCu = khThanhVien;
                    phamTramGiam = Integer.parseInt(txtPhanTramGiam.getText().replace("%", "").trim());
                    //reset
                    khThanhVien = new KhachHang(null);
                    hd=null;

                    listSanPhamHoaDon = new HashMap<SanPham, Integer>();
                    for (int i = 0; i < tblSanPhamHoaDon.getRowCount(); i++) {
                        for (int j = 0; j < tblSanPhamHoaDon.getColumnCount(); j++) {
                            tblSanPhamHoaDon.setValueAt("", i, j);
                        }
                    }

                    dtmSanPhamHoaDon.setRowCount(0);
                    reloadTongTienPanel();
                    loadData_sanPham();
                }
                else {
                    JOptionPane.showMessageDialog(this,
                            "Đã hủy thanh toán!",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
            else {
                tienKhach = Double.parseDouble(txtTongThanhTien.getText().replace("VND", "").replace(",","").trim());
                //update số lượng sản phẩm
                SanPham_DAO sp_dao = new SanPham_DAO();
                for (Map.Entry<SanPham, Integer> entry : listSanPhamHoaDon.entrySet()) {
                    SanPham key = entry.getKey();
                    Integer value = entry.getValue();
                    key.setSoLuongKho(key.getSoLuongKho()-value);
                    sp_dao.update(key);
                }
                //ghi hoa don len csdls
                nhanvienbanhang = new NhanVien(username);
                HoaDon hd = new HoaDon( khThanhVien, nhanvienbanhang, listSanPhamHoaDon, Date.valueOf(LocalDate.now()));
                System.out.println(hd);
                HoaDon_DAO hd_dao = new HoaDon_DAO();
                hd_dao.create(hd);
                hd_dao.createCTHoaDon(hd);
                //tao hoa don
                new HoaDonDialog(listSanPhamHoaDon,tienKhach,Integer.parseInt(txtPhanTramGiam.getText().replace("%", "").trim()), khThanhVien ).hienThiHoaDon();
                listSanPhamHoaDonCu = listSanPhamHoaDon;
                khThanhVienCu = khThanhVien;
                tienKhachPre = tienKhach;
                phamTramGiam = Integer.parseInt(txtPhanTramGiam.getText().replace("%", "").trim());
                //reset
                khThanhVien = new KhachHang(null);
                hd=null;

                listSanPhamHoaDon = new HashMap<SanPham, Integer>();
                for (int i = 0; i < tblSanPhamHoaDon.getRowCount(); i++) {
                    for (int j = 0; j < tblSanPhamHoaDon.getColumnCount(); j++) {
                        tblSanPhamHoaDon.setValueAt("", i, j);
                    }
                }
                dtmSanPhamHoaDon.setRowCount(0);
                reloadTongTienPanel();
                loadData_sanPham();
            }

        }
        else if(o == btnKetCa){
            int option = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn kết thúc ca chứ?");
            if(option == JOptionPane.YES_OPTION){
                this.dispose();
                new DangNhapGUI();
            }
        }
        else if(o == btnXemLaiHoaDon) {
        	if(tienKhachPre == 0) {
        		JOptionPane.showMessageDialog(this, 
                        "Chưa có hóa đơn nào trong ca", 
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
        	else {
            	new HoaDonDialog(listSanPhamHoaDonCu,tienKhachPre,phamTramGiam,khThanhVienCu).hienThiHoaDon();
            }
        }
    }  	

    /**
     * Description : Phương thức cập nhật lại các Sản phẩm trong ArrayList hóa đơn tạm
     * @param rowSelected
     */
    private void updateHoaDon(int rowSelected) {
        String masp = tblSanPhamKho.getValueAt(rowSelected, 0).toString();
        int soLuong = 0;

        for (int i = 0; i < tblSanPhamHoaDon.getRowCount(); i++) {
            if (tblSanPhamHoaDon.getValueAt(i, 0).toString().equalsIgnoreCase(masp)) {
                soLuong = Integer.parseInt(tblSanPhamHoaDon.getValueAt(i, 3).toString());
                break;
            }
        }
        for (SanPham sp : listSanPham) {
            if (sp.getMaSanPham().equalsIgnoreCase(masp)) {
                listSanPhamHoaDon.put(sp, soLuong);
                break;
            }
        }
    }

    /**
     * Description : Phương thức cập nhật giá trị của các JTextField thành tiền, giảm giá và tổng thành tiền
     */
    private void updateSubTotal(){
        Double total = 0.0;
        Double soLuong = 0.0, donGia = 0.0,giamGia = 0.0;
        for(int i = 0; i < tblSanPhamHoaDon.getRowCount(); i++){
            soLuong = Double.parseDouble((tblSanPhamHoaDon.getValueAt(i,3)).toString());
            donGia = Double.parseDouble((tblSanPhamHoaDon.getValueAt(i,4)).toString());
            total += soLuong * donGia;
        }
        DecimalFormat moneyFormat = new DecimalFormat("#,### VND");
        
        txtTongTien.setText(moneyFormat.format(total) );
        giamGia = total/100 * Integer.parseInt(txtPhanTramGiam.getText().replace("%", "").trim());
        txtGiamGia.setText(moneyFormat.format(giamGia));
        txtTongThanhTien.setText(moneyFormat.format(total-giamGia));
    }

    /**
     * Description : Phương thức chỉnh  fixed size (size cố định cho JButton truyền vào function)
     * @param button JButton, width int, height int
     */
    private void setFixedSize(JButton button,int width, int height) { // su dung gridbaglayout ko có tác dụng set size
        // nên tạo function set size cho button
        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
    }

    /**
     * Description : Phương thức cập nhật list các sản phầm từ Database lên JTable chứa sản phẩm kho
     */
    public void loadData_sanPham(){
    	dtmSanPhamKho.setRowCount(0); // reset table
        SanPham_DAO spDao = new SanPham_DAO();
        listSanPham = spDao.getListSanPham();
        for (SanPham sanPham : listSanPham) {
            Object[] rowData = {
                    sanPham.getMaSanPham(),
                    sanPham.getTenSanPham(),
                    sanPham.getLoaiSanPham(),
                    sanPham.getDonGia(),
                    sanPham.getSoLuongKho()  == 0 ? "Hết hàng" : sanPham.getSoLuongKho()
            };
            dtmSanPhamKho.addRow(rowData);
        }
    }

    /**
     * Description : Phương thức hiển thị 1 JDialog ngăn user tương tác với cửa sổ khác, trừ khi login chính xác account hiện tại
     * @param username
     */
    public void showLoginDialog(String username) {
        // custom lai layout cho dialog
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Tên đăng nhập:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));

        JButton okButton = new JButton("OK");
        panel.add(okButton);

        // Tạo JOptionPane không có nút mặc định
        JOptionPane optionPane = new JOptionPane(
                panel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{}, // Không có nút nào mặc định
                null
        );

        // Custom lại dialog, dialog là dạng hộp thoại thông báo,xác nhận,....
        JDialog dialog = optionPane.createDialog("Đang tạm dừng bán - Vui lòng đăng nhập");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // nhấn nút X ko có j xảy ra - do nothing
        // Dùng dialog vì có thể thực hiện cái setDefaultCLoseOperation này, JPanel ko thể
        dialog.setModal(true);
        // SetModal là set dạng dialog giống modal của web,
        // Chặn tương tác với các cửa sổ khác cho đến khi modal bị đóng

        okButton.addActionListener(e -> { // add actionlistener cho button ok
            String user = usernameField.getText();
            String pass = passwordField.getText();

            TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
            TaiKhoan tkUserHienTai = tkdao.getTaiKhoan(username);
            if(tkUserHienTai == null){
                System.out.println("acacacac");
                return;
            }else{
                System.out.println(tkUserHienTai.getUsername());
                System.out.println(tkUserHienTai.toString());
                System.out.println(tkUserHienTai.getPassword());
            }
            if (username.equals(user) && tkUserHienTai.getPassword().equals(pass)) {
                dialog.dispose(); // chỉ dispose khi check đúng pass
            } else {
                JOptionPane.showMessageDialog(panel, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    /**
     * Description : Phương thức hiển thị 1 JDialog để nhân viên chọn phương thức thanh toán
     */
    public void chonPhuongThucThanhToan(){
        JComboBox<String> cboPaymentMethod = new JComboBox<String>();
        String[] paymentMethods = {"Thanh toán bằng tiền mặt", "Thanh toán bằng ngân hàng","Thanh toán bằng MOMO", "Thanh toán bằng VISA", "Thanh toán bằng Apple Pay"};
        for(String pm : paymentMethods){
            cboPaymentMethod.addItem(pm);
        }

        cboPaymentMethod.setMaximumSize(new Dimension(300,60));
        cboPaymentMethod.setMinimumSize(new Dimension(300,60));
        cboPaymentMethod.setFont(fntMid);

        JButton btnOk = new JButton("Xác nhận");
        btnOk.setFont(fntMid);
        btnOk.setMaximumSize(new Dimension(200,50));
        btnOk.setMinimumSize(new Dimension(200,50));
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);
        Box pnlPaymentCustomPanel = Box.createVerticalBox();
        pnlPaymentCustomPanel.add(Box.createVerticalStrut(10));
        pnlPaymentCustomPanel.add(cboPaymentMethod);
        pnlPaymentCustomPanel.add(Box.createVerticalStrut(50));
        pnlPaymentCustomPanel.add(btnOk);

        JOptionPane optionPane = new JOptionPane(
                pnlPaymentCustomPanel, // component hiển thị trong dialog
                JOptionPane.PLAIN_MESSAGE, // loại thông báo, PLAIN là ko có biểu tượng thông báo
                JOptionPane.DEFAULT_OPTION, // Kiểu lựa chọn mặc định, do tự custom lại nút nên set là DEFAULT
                new ImageIcon("image/payment.png"), // icon cho JDialog
                new Object[]{}, // mảng button
                null // button mặc định
        );
        JDialog dialog = optionPane.createDialog("Chọn hình thức thanh toán");
        btnOk.addActionListener(e -> {
        	String method = Objects.requireNonNull(cboPaymentMethod.getSelectedItem(),"Null object!").toString();
        	txtHinhThucThanhToan.setText(method);
        	dialog.dispose();
        });
        dialog.setVisible(true);
    }

    /**
     * Description : Hiển thị JDialog để nhập mã thành viên, kiểm tra mã thành viên nhập vào
     * @return : Map<String,Integer> thanhVien, bao gồm cặp key-value : (Hạng thành viên, Phần trăm giảm giá)
     */
    public Map<String,Integer> nhapMaThanhVien(){
    	Map<String, Integer> thanhVien = new HashMap<String, Integer>();
    	JTextField txtMaThanhVien = new JTextField(20);
        txtMaThanhVien.setFont(fntMid);
    	JLabel lblNhapMaKhachHang = new JLabel("Nhập mã khác hàng:");
        lblNhapMaKhachHang.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNhapMaKhachHang.setFont(fntMid);

        JPanel pnlLblNhapMaKhachHangContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlLblNhapMaKhachHangContainer.add(lblNhapMaKhachHang);
        JButton btnOk = new JButton("Xác nhận");
       
        btnOk.setFont(fntMid);
        btnOk.setMaximumSize(new Dimension(200,50));
        btnOk.setMinimumSize(new Dimension(200,50));
        btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box pnlPaymentCustomPanel = Box.createVerticalBox();
        pnlPaymentCustomPanel.add(Box.createVerticalStrut(10));
        pnlPaymentCustomPanel.add(pnlLblNhapMaKhachHangContainer);
        pnlPaymentCustomPanel.add(Box.createVerticalStrut(10));
        pnlPaymentCustomPanel.add(txtMaThanhVien);
        pnlPaymentCustomPanel.add(Box.createVerticalStrut(50));
        pnlPaymentCustomPanel.add(btnOk);

        JOptionPane optionPane = new JOptionPane(
                pnlPaymentCustomPanel, // component hiển thị trong dialog
                JOptionPane.PLAIN_MESSAGE, // loại thông báo, PLAIN là ko có biểu tượng thông báo
                JOptionPane.DEFAULT_OPTION, // Kiểu lựa chọn mặc định, do tự custom lại nút nên set là DEFAULT
                null, // icon cho JDialog
                new Object[]{}, // mảng button
                null // button mặc định
        );
        JDialog dialog = optionPane.createDialog("Nhập mã khách hàng");
        btnOk.addActionListener(e -> {
        	String makh = txtMaThanhVien.getText();
        	System.out.println(makh);

        	KhachHang_DAO khdao = new KhachHang_DAO();
        	khThanhVien = khdao.getKhachHang(makh);

        	if(khThanhVien.getMaKhachHang() !=null) {
        		JOptionPane.showMessageDialog(this, "Tìm thấy khác hàng!");
        		int phanTramGiam = switch (khThanhVien.getHangThanhVien()) {
                    case "Kim Cương" -> 10;
                    case "Vàng" -> 5;
                    default -> 0;
                };
                thanhVien.put(khThanhVien.getHangThanhVien(), phanTramGiam);
        	}else {
        		JOptionPane.showMessageDialog(this, "Không tìm thấy khác hàng!");
        	}
        	dialog.dispose();
        });
        dialog.setVisible(true);
        return thanhVien;
    }

    /**
     * Phương thức tìm tương đối sản phẩm theo mã và tên sản phẩm
     * @param loaiTim boolean
     */

    private void timSanPhamTheoMa(boolean loaiTim){ // true là tìm theo mã, false là tìm theo sản phẩm
        String keyword = txtMaSanPham.getText();
        ArrayList<SanPham> listSpTimDuoc = new ArrayList<>();
        ArrayList<SanPham> listSpConLai = new ArrayList<>();
        if(loaiTim){
            for(SanPham sp : listSanPham){
                if(sp.getMaSanPham().toLowerCase().contains(keyword.toLowerCase()))
                    listSpTimDuoc.add(sp);
                else
                    listSpConLai.add(sp);
            }
        }
        else{
            for(SanPham sp : listSanPham){
                if(sp.getTenSanPham().toLowerCase().contains(keyword.toLowerCase()))
                    listSpTimDuoc.add(sp);
                else
                    listSpConLai.add(sp);
            }
        }

        Object[] data = null;
        dtmSanPhamKho.setRowCount(0); // Reset toan bo table
        for (SanPham sp : listSpTimDuoc){
            data = new Object[]{sp.getMaSanPham(), sp.getTenSanPham(), sp.getLoaiSanPham(), sp.getDonGia(), sp.getSoLuongKho() == 0 ? "Hết hàng": sp.getSoLuongKho() };
            dtmSanPhamKho.addRow(data);
        }
        for (SanPham sp : listSpConLai){
            data = new Object[]{sp.getMaSanPham(), sp.getTenSanPham(), sp.getLoaiSanPham(), sp.getDonGia(), sp.getSoLuongKho() == 0 ? "Hết hàng": sp.getSoLuongKho() };
            dtmSanPhamKho.addRow(data);
        }
        tblSanPhamKho.scrollRectToVisible(tblSanPhamKho.getCellRect(0, 0, true));
    }

    /**
     * Description : Phương thức reset panel tổng tiền
     */
    private void reloadTongTienPanel(){
        txtHangThanhVien.setText("Không");
        txtPhanTramGiam.setText("0%");
        txtHinhThucThanhToan.setText("Thanh toán bằng tiền mặt");
        txtTongTien.setText("0 VND");
        txtGiamGia.setText("0 VND");
        txtTongThanhTien.setText("0 VND");
    }

    /**
     * Description : Phương thức Override mouseClicked - xử lí sự kiện click chuột trên JTable
     * @param e the event to be processed
     */
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

