/*
 * @ (#) TinhTienDialog.java   1.0     4/27/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */

package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class TinhTienDialog {
    private JDialog dialog;
    private JTextField amountField;
    private double enteredAmount = 0.0;
    private boolean confirmed = false;
    
    /**
     * Hiển thị bàn phím số và ô nhập để nhân viên bán hàng nhập số tiền khách đưa
     * @param parentFrame Frame cha để hiển thị dialog
     * @param totalAmount Tổng số tiền cần thanh toán
     * @return Số tiền khách đưa nếu xác nhận, -1 nếu hủy
     */
    public double showPaymentKeypad(JFrame parentFrame, double totalAmount) {
        // Tạo dialog
        dialog = new JDialog(parentFrame, "Nhập số tiền khách đưa", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(parentFrame);
        
        // Panel hiển thị thông tin
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        
        JLabel totalLabel = new JLabel("Tổng tiền cần thanh toán:");
        JLabel totalValueLabel = new JLabel(currencyFormat.format(totalAmount));
        totalValueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JLabel amountLabel = new JLabel("Số tiền khách đưa:");
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.BOLD, 18));
        amountField.setHorizontalAlignment(SwingConstants.RIGHT);
        amountField.setEditable(false);
        
        infoPanel.add(totalLabel);
        infoPanel.add(totalValueLabel);
        infoPanel.add(amountLabel);
        infoPanel.add(amountField);
        
        // Panel bàn phím số
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        keypadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Tạo các nút số và chức năng
        String[] buttonLabels = {
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "C", "0", "⌫"
        };
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String currentText = amountField.getText().replace(".", "");
                    
                    if (label.equals("C")) {
                        // Xóa hết
                        amountField.setText("");
                        enteredAmount = 0.0;
                    } else if (label.equals("⌫")) {
                        // Xóa ký tự cuối
                        if (currentText.length() > 0) {
                            currentText = currentText.substring(0, currentText.length() - 1);
                            try {
                                enteredAmount = currentText.isEmpty() ? 0.0 : Double.parseDouble(currentText);
                                formatAmountDisplay(enteredAmount);
                            } catch (NumberFormatException ex) {
                                amountField.setText("");
                                enteredAmount = 0.0;
                            }
                        }
                    } else {
                        // Thêm số
                        currentText += label;
                        try {
                            enteredAmount = Double.parseDouble(currentText);
                            formatAmountDisplay(enteredAmount);
                        } catch (NumberFormatException ex) {
                            // Xử lý lỗi nếu cần
                        }
                    }
                }
            });
            
            keypadPanel.add(button);
        }
        
        // Panel nút xác nhận/hủy
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Thêm các nút tiền nhanh
        JButton btn50k = createQuickAmountButton("50.000", 50000);
        JButton btn100k = createQuickAmountButton("100.000", 100000);
        JButton btn200k = createQuickAmountButton("200.000", 200000);
        JButton btn500k = createQuickAmountButton("500.000", 500000);
        
        JPanel quickAmountPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        quickAmountPanel.setBorder(BorderFactory.createTitledBorder("Tiền mặt thông dụng"));
        quickAmountPanel.add(btn50k);
        quickAmountPanel.add(btn100k);
        quickAmountPanel.add(btn200k);
        quickAmountPanel.add(btn500k);
        
        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enteredAmount >= totalAmount) {
                    confirmed = true;
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, 
                        "Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dialog.dispose();
            }
        });
        
        buttonPanel.add(new JLabel()); // Spacer
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        
        // Thêm các panel vào dialog
        dialog.add(infoPanel, BorderLayout.NORTH);
        dialog.add(keypadPanel, BorderLayout.CENTER);
        dialog.add(quickAmountPanel, BorderLayout.EAST);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Hiển thị dialog và đợi người dùng đóng
        dialog.setVisible(true);
        
        // Trả về kết quả
        return confirmed ? enteredAmount : -1;
    }
    
    private JButton createQuickAmountButton(String label, final double amount) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enteredAmount = amount;
                formatAmountDisplay(enteredAmount);
            }
        });
        
        return button;
    }
    
    private void formatAmountDisplay(double amount) {
        // Định dạng số nguyên không có phần thập phân
        long intAmount = (long) amount;
        String formattedAmount = String.format("%,d", intAmount).replace(",", ".");
        amountField.setText(formattedAmount);
    }
}