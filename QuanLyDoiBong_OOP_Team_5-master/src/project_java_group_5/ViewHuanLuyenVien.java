package project_java_group_5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewHuanLuyenVien extends JFrame {
    
    //Setup
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtNationality, txtBirthDate, txtExperience;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnSave, btnLoad, btnIn4, btnBack;         
    private JPanel btnPanel, panel;
    
    public ViewHuanLuyenVien() {
        setTitle("Coach");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Name", "Nationality", "Date of Birth", "Role", "Years of Experience"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form setup
        panel = new JPanel(new GridLayout(0, 2));
        
        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);
        
        panel.add(new JLabel("Nationality:"));
        txtNationality = new JTextField();
        panel.add(txtNationality);
        
        panel.add(new JLabel("Date of Birth:"));
        txtBirthDate = new JTextField();
        panel.add(txtBirthDate);

        String[] roles = {"", "Head Coach", "Assistant Coach", "Goalkeeper Coach", "Fitness Coach", "Technical Director", "Medical Staff"};
        cbRole = new JComboBox<>(roles);
        panel.add(new JLabel("Role:"));
        panel.add(cbRole);
        
        panel.add(new JLabel("Years of Experience:"));
        txtExperience = new JTextField();
        panel.add(txtExperience);

        // Buttons setup
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load Data");
        btnIn4 = new JButton("Salary Details");
        btnBack = new JButton("Back");   
        
        btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnSave);
        btnPanel.add(btnLoad);
        btnPanel.add(btnIn4);
        btnPanel.add(btnBack); 

        add(panel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);

        // Button functionalities
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.addCoach(txtName, txtNationality, txtBirthDate, txtExperience, cbRole, model);
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.updateCoach(table, txtName, txtNationality, txtBirthDate, txtExperience, cbRole, model);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.deleteCoach(table, model);
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.saveData(model);
            }
        });
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.loadData(table);
            }
        });        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                if (Login.phanQuyen() == 2) {
                    Login.view();
                    Login.setFlag(9);
                } else {
                    new Menu().setVisible(true); // Call the main menu display method
                }
            }
        });
        
        btnIn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerHuanLuyenVien.in4(table, model);
            }
        });
    }                          
    
    public static void view() {
        EventQueue.invokeLater(() -> {
            try {
                ViewHuanLuyenVien frame = new ViewHuanLuyenVien();
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
}
