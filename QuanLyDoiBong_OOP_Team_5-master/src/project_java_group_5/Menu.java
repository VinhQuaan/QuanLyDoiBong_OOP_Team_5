package project_java_group_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {
    public Menu() {
        initComponents();
    }

    public void initComponents() {
        try {
            // Set a modern look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Using BorderLayout for better arrangement
        setLayout(new BorderLayout(10, 10));

        JButton btnCauThu = new JButton("Footballer");
        JButton btnHuanLuyenVien = new JButton("Coach");
        JButton btnBack = new JButton("Back"); // Tạo nút "Back"

        // Customize buttons
        btnCauThu.setBackground(new Color(100, 100, 255)); 
        btnCauThu.setForeground(Color.WHITE);
        btnCauThu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewCauThu.view();
                    dispose();
                }
            });

        btnHuanLuyenVien.setBackground(new Color(255, 100, 100)); 
        btnHuanLuyenVien.setForeground(Color.WHITE);
        btnHuanLuyenVien.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewHuanLuyenVien.view();   
                    dispose();
                }
            });

        btnBack.setBackground(new Color(100, 100, 100)); // Đặt màu cho nút "Back"
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login.view(); // Hiển thị lại màn hình đăng nhập
                dispose(); // Đóng cửa sổ Menu
            }
        });

        // Add padding around buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Sửa lại GridLayout để chứa 3 nút
        buttonPanel.add(btnCauThu);
        buttonPanel.add(btnHuanLuyenVien);
        buttonPanel.add(btnBack); // Thêm nút "Back" vào panel
        buttonPanel.setBorder(new EmptyBorder(10, 30, 10, 30)); // Padding

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
