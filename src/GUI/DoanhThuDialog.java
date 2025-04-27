package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Entity.ThongKeDoanhThu;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.border.EmptyBorder;

public class DoanhThuDialog extends JDialog {
    
    private JLabel lblTieuDe;
    private JTable tblDoanhThu;
    private JScrollPane scrDoanhThu;
    private JPanel pnlContent;
    private final Font fntMid = new Font("Roboto", Font.PLAIN, 18);
    public DoanhThuDialog(JFrame parent,ArrayList<ThongKeDoanhThu> listThongKe) {
        super(parent, "Thống Kê Doanh Thu", true);
        initComponents();
        loadDuLieu(listThongKe);
        setVisible(true);
    }
    
    private void initComponents() {
        // Thiết lập layout cho JDialog
        setLayout(new BorderLayout());
        
        // Tạo panel chính với border làm viền bo tròn
        pnlContent = new JPanel(new BorderLayout(10, 20));
        pnlContent.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Tạo tiêu đề
        lblTieuDe = new JLabel("THỐNG KÊ DOANH THU");
        lblTieuDe.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTieuDe.setHorizontalAlignment(JLabel.CENTER);
        lblTieuDe.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Tạo model cho bảng
        String[] cols = {"Ngày" , "Số hóa đơn", "Số sp bán","Tổng doanh thu"};
        DefaultTableModel modelDoanhThu = new DefaultTableModel(cols, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tạo bảng doanh thu
        tblDoanhThu = new JTable(modelDoanhThu);
        tblDoanhThu.setRowHeight(30);
        tblDoanhThu.getTableHeader().setFont(fntMid);
        tblDoanhThu.setFont(fntMid);

        tblDoanhThu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Tạo JScrollPane để thêm thanh cuộn cho bảng
        scrDoanhThu = new JScrollPane(tblDoanhThu);
        
        // Thêm các thành phần vào panel chính
        pnlContent.add(lblTieuDe, BorderLayout.NORTH);
        pnlContent.add(scrDoanhThu, BorderLayout.CENTER);
        
        // Thêm panel vào dialog
        add(pnlContent);
        
        // Thiết lập kích thước và vị trí của dialog
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    // Phương thức để thêm dữ liệu mẫu vào bảng
    public void loadDuLieu(ArrayList<ThongKeDoanhThu> listThongKe) {
        DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
        model.setRowCount(0);
        
        for(ThongKeDoanhThu tk : listThongKe) {
        	String [] data = {tk.getNgay().toString(),Integer.toString(tk.getSoLuongHD()),Integer.toString(tk.getSoLuongSP()),Double.toString(tk.getTongDoanhThu())};
        	model.addRow(data);
        }
        
        
      
    }

//    public static void main(String[] args) {
//            DoanhThuDialog dialog = new DoanhThuDialog(null);
//            dialog.loadSampleData();
//            dialog.setVisible(true);
//
//    }
}