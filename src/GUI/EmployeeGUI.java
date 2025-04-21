/*
 * @ (#) EmployeeGUI.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
 * @description:
 * @author:
 * @date:  4/21/2025
 * @version:    1.0
 */
public class EmployeeGUI extends JFrame {
    private JLabel lblTenNhanVien, lblMaNhanVien;
    private JTextField txtNhapMaSanPham;
    private JButton btnThemSanPham, btnXoaSanPham;
    private JTable tblSanPham;
    private DefaultTableModel defaultTableModel;

    public EmployeeGUI(){
        super();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel table = new JPanel();
        table.setBackground(Color.GREEN);

        JPanel table1 = new JPanel();
        table1.setBackground(Color.YELLOW);

        JPanel table2 = new JPanel();
        table2.setBackground(Color.RED);

        JPanel table3 = new JPanel();
        table3.setBackground(Color.BLUE);



        // Panel xanh lá (3x2)
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 3;
        gbc1.gridheight = 2;
        gbc1.weightx = 3.0;
        gbc1.weighty = 2.0;
        gbc1.fill = GridBagConstraints.BOTH;
        container.add(table, gbc1);

        // Panel vàng (1x2)
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 3;
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
        gbc3.gridwidth = 3;
        gbc3.gridheight = 1;
        gbc3.weightx = 3.0;
        gbc3.weighty = 1.0;
        gbc3.fill = GridBagConstraints.BOTH;
        container.add(table2, gbc3);

        // Panel xanh dương (1x1)
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 3;
        gbc4.gridy = 2;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        gbc4.weightx = 1.0;
        gbc4.weighty = 1.0;
        gbc4.fill = GridBagConstraints.BOTH;
        container.add(table3, gbc4);

    //kh co database làm cc gì hac v
        push lên đi xí a lấy về làm database sau
        ac va ac a c cacacacacacacaccaccacacacaccacc ac v

        

        setSize(1200,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EmployeeGUI();
    }
}
