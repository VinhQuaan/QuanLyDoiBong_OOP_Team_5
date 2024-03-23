package project_java_group_5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewCauThu {  
    
    //Setup
    private static Object[][] data, data0; 
    private static DateValidator validator;     
    private static JFrame frame;
    private static JTable table;
    private static DefaultTableModel model;
    private static JScrollPane scrollPane;
    private static JPanel buttonPanel;
    private static JButton addButton, changeButton, deleteButton, in4, sortMenuButton, addLatestMatchScoreButton, backButton, saveButton, filterMenuButton;    
    private static JTextField nameField ,nationalityField, matchField, goalField, salaryField, lastFiveMatchesField;
    private static JComboBox<String> genderField, dayComboBox, monthComboBox, yearComboBox, joinDayComboBox, joinMonthComboBox, joinYearComboBox, positionComboBox;
    
    public static void view() {     
        data0 = null;
        validator = new DateValidator("dd/MM/uuuu"); 
        
        data = ControllerCauThu.loadData(data0);        
       
        frame = new JFrame("Footballer");

        String[] col = { "Name", "Nationality", "Gender", "Date of Birth", "Date of Joining", "Position",
                "Matches", "Goals", "Agreed Salary", "Points from Last 5 Matches" };

        model = new DefaultTableModel(data, col);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        // Use FlowLayout for the button panel with horizontal spacing
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        
        //Button
        addButton = new JButton("Add");
        changeButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");        
        backButton = new JButton("Back");
        addLatestMatchScoreButton = new JButton("Add Latest Match Score");
        in4 = new JButton("Salary and Bonus Details");
        sortMenuButton = new JButton("Sort");
        filterMenuButton = new JButton("Filter");  
      
        
        buttonPanel.add(addButton);
        buttonPanel.add(changeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(filterMenuButton);        
        buttonPanel.add(sortMenuButton);                               
        buttonPanel.add(addLatestMatchScoreButton);
        buttonPanel.add(in4);
        buttonPanel.add(backButton);
        
        addButton.addActionListener(e -> {
            
            nameField = new JTextField();
            nationalityField = new JTextField();
            genderField = new JComboBox<>(new String[] { "Male", "Female", "Other" });

            String[] days = new String[31];
            for (int i = 1; i <= 9; i++) {
                days[i - 1] = "0" + String.valueOf(i); 
            }
            for (int i = 10; i <= 31; i++) {
                days[i - 1] = String.valueOf(i); 
            }
            dayComboBox = new JComboBox<>(days);

            monthComboBox = new JComboBox<>(
                    new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });

            String[] years = new String[61];
            for (int i = 0; i < 61; i++) {
                years[i] = String.valueOf(2024 - i); 
            }
            yearComboBox = new JComboBox<>(years);

            joinDayComboBox = new JComboBox<>(days);
            joinMonthComboBox = new JComboBox<>(
                    new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
            joinYearComboBox = new JComboBox<>(years);

            positionComboBox = new JComboBox<>(new String[] { "Forward", "Midfielder","Defender" , "Goalkeeper" });
            matchField = new JTextField();
            goalField = new JTextField();
            salaryField = new JTextField();
            lastFiveMatchesField = new JTextField();

            Object[] fields = {
                    "Name:", nameField,
                    "Nationality:", nationalityField,
                    "Gender:", genderField,
                    "Date of Birth:", dayComboBox,
                    "Month of Birth: ", monthComboBox,
                    "Year of Birth: ", yearComboBox,
                    "Date of Joining:", joinDayComboBox,
                    "Month of Joining:", joinMonthComboBox,
                    "Year of Joining:", joinYearComboBox,
                    "Playing Position", positionComboBox,
                    "Number of Matches:", matchField,
                    "Number of Goals:", goalField,
                    "Agreed Salary:", salaryField,
                    "Points from Last 5 Matches:", lastFiveMatchesField, };

            ControllerCauThu.them(model, fields, nameField, nationalityField, positionComboBox, genderField, joinDayComboBox, joinMonthComboBox, joinYearComboBox, matchField, goalField, salaryField, dayComboBox, monthComboBox, yearComboBox, validator, lastFiveMatchesField);
        });

        changeButton.addActionListener(e -> {
            ControllerCauThu.thaydoi(table, model, validator);
        });                     

        saveButton.addActionListener(e -> {
            ControllerCauThu.saveTableModelToFile(model, "Data.csv");
            JOptionPane.showMessageDialog(null, "Data saved successfully!");

        });

        deleteButton.addActionListener(e -> {
            
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                ControllerCauThu.saveTableModelToFile(model, "Data.csv");
            }
        });        

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current window
            
            if(Login.phanQuyen() == 1){
                Login.view();
                Login.setFlag(9);
            }
            else{
                new Menu().setVisible(true); // Call the main menu display method
            }                                  
        });       
        

        addLatestMatchScoreButton.addActionListener(e -> {
            ControllerCauThu.them5tran(table, model);
        });       
        
        in4.addActionListener(e -> {
            ControllerCauThu.in4(table, model);
        });                        

        // Tạo JPopupMenu cho các lựa chọn sắp xếp
        JPopupMenu sortMenu = new JPopupMenu();

        // Thêm các mục lựa chọn sắp xếp
        JMenuItem sortByGoals = new JMenuItem("Sort by Goals");
        JMenuItem sortBySalary = new JMenuItem("Sort by Salary");
        JMenuItem sortByMatches = new JMenuItem("Sort by Number of Matches Played");

        // Thêm các mục vào menu
        sortMenu.add(sortByGoals);
        sortMenu.add(sortBySalary);
        sortMenu.add(sortByMatches);

        // Xử lý sự kiện cho từng mục menu
        sortByGoals.addActionListener(e -> ControllerCauThu.sortTableByColumn(model, 7, false)); // Số bàn thắng ở cột thứ 8 (index 7)
        sortBySalary.addActionListener(e -> ControllerCauThu.sortTableByColumn(model, 8, false)); // Lương ở cột thứ 9 (index 8)
        sortByMatches.addActionListener(e -> ControllerCauThu.sortTableByColumn(model, 6, false)); // Số trận ở cột thứ 7 (index 6)

        // Hiển thị menu khi nhấp vào nút
        sortMenuButton.addActionListener(e -> sortMenu.show(sortMenuButton, 0, sortMenuButton.getHeight()));                             
        
        // Tạo JPopupMenu cho các lựa chọn lọc theo vị trí
        JPopupMenu filterMenu = new JPopupMenu();
        
        // Thêm các mục lựa chọn sắp xếp
        JMenuItem filterForwards = new JMenuItem("Forward");
        JMenuItem filterMidfielders = new JMenuItem("Midfielder");
        JMenuItem filterDefenders = new JMenuItem("Defender");
        JMenuItem goalkeeper = new JMenuItem("Goalkeeper");
        
        //Thêm các mục vào menu
        filterMenu.add(filterForwards);
        filterMenu.add(filterMidfielders);
        filterMenu.add(filterDefenders);
        filterMenu.add(goalkeeper);
        
        //Hiển thị Menu khi bấm nút
        filterMenuButton.addActionListener(e -> filterMenu.show(filterMenuButton, 0, filterMenuButton.getHeight()));
        
        //Xử lí sự kiện
        filterForwards.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Forward"));
        filterMidfielders.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Midfielder"));
        filterDefenders.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Defender")); 
        goalkeeper.addActionListener(e -> ControllerCauThu.filterByPosition(model, "Goalkeeper"));    
        
        // Frame display code not shown
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(1200, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);              
    }
}
