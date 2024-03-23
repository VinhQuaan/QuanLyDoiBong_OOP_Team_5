package project_java_group_5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControllerHuanLuyenVien {
    public static boolean isRealNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(str);
            if (x > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean containsNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true; // Tìm thấy số, trả về true
            }
        }
        // Kiểm tra ký tự đặc biệt, cho phép dấu cách
        String specialCharactersPattern = "[^a-zA-Z0-9\\s]";
        boolean containsSpecialCharacter = str.matches(".*" + specialCharactersPattern + ".*");

        return containsSpecialCharacter; // Trả về true nếu có ký tự đặc biệt, nghĩa là chuỗi không hợp lệ
    }

    public static String normalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        String[] words = name.trim().split("\\s+"); // Tách chuỗi dựa trên một hoặc nhiều dấu cách
        StringBuilder normalized = new StringBuilder();

        for (String word : words) {
            String normalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            normalized.append(normalizedWord).append(" ");
        }

        return normalized.toString().trim(); // Loại bỏ dấu cách thừa ở cuối chuỗi trước khi trả về
    }

    public static String chuanhoa(String s) {
        String[] arr = s.split("/");
        if (arr.length != 3)
            return "00/00/0000";
        String ans = String.format("%02d", Integer.parseInt(arr[0])) + "/";
        ans += String.format("%02d", Integer.parseInt(arr[1])) + "/" + arr[2];
        return ans;
    }

    public static boolean hasHeadCoach(int excludeRowIndex, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (i == excludeRowIndex) {
                continue; // Bỏ qua dòng hiện tại
            }
            String role = model.getValueAt(i, 3).toString(); // Giả sử cột 3 chứa thông tin về vai trò
            if ("Head Coach".equals(role)) {
                return true; // Đã tìm thấy HLV trưởng trong danh sách
            }
        }
        return false; // Không tìm thấy HLV trưởng
    }

    public static void in4(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Only update fields if the corresponding text field is not empty.

            String name = model.getValueAt(selectedRow, 0).toString();
            String nationality = model.getValueAt(selectedRow, 1).toString();
            String birthDate = model.getValueAt(selectedRow, 2).toString();
            String role = model.getValueAt(selectedRow, 3).toString();
            String experience = model.getValueAt(selectedRow, 4).toString();

            Integer thamnien = Integer.parseInt(experience);

            HuanLuyenVien x = new HuanLuyenVien(name, nationality, birthDate, thamnien, role);

            // Hiển thị thông tin
            JOptionPane.showMessageDialog(null, "Salary: " + x.tinhLuong() + "\nBonus: " + x.tinhThuong(),
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a coach to update.", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void addCoach(JTextField txtName, JTextField txtNationality, JTextField txtBirthDate,
            JTextField txtExperience, JComboBox<String> cbRole, DefaultTableModel model) {
        
        //Lay du lieu
        String name = txtName.getText().trim();
        String nationality = txtNationality.getText().trim();
        String birthDate = txtBirthDate.getText().trim();
        String Role = cbRole.getSelectedItem().toString();
        String experience = txtExperience.getText().trim();
        
        // Validate the input data
        DateValidator validator = new DateValidator("dd/MM/uuuu");       
        
        if (name.isEmpty() || nationality.isEmpty() || birthDate.isEmpty() || Role.isEmpty()
                || experience.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);

        }
        
        if (ControllerHuanLuyenVien.containsNumber(name) || ControllerHuanLuyenVien.containsNumber(nationality)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid name or nationality", "Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        
        //Không cần loại trừ hàng nào nên sẽ để là -1
        if ("Head Coach".equals(Role) && ControllerHuanLuyenVien.hasHeadCoach(-1, model)) {
            JOptionPane.showMessageDialog(null, "Head Coach already exists, cannot add another one.", "Error",
                    JOptionPane.ERROR_MESSAGE);

            return; // Dừng thực hiện nếu đã có HLV trưởng
        }

        if (!validator.isValid(ControllerHuanLuyenVien.chuanhoa(birthDate))) {
            JOptionPane.showMessageDialog(null, "Invalid date", "Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        if (!ControllerHuanLuyenVien.isRealNumber(experience)) {
            JOptionPane.showMessageDialog(null, "Invalid years of experience", "Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        // Add data to the table model
        model.addRow(new Object[] { ControllerHuanLuyenVien.normalizeName(name),
                ControllerHuanLuyenVien.normalizeName(nationality), ControllerHuanLuyenVien.chuanhoa(birthDate),
                Role, experience });

        // Clear the input fields after adding
        txtName.setText("");
        txtNationality.setText("");
        txtBirthDate.setText("");
        txtExperience.setText("");
    }

    public static void updateCoach(JTable table, JTextField txtName, JTextField txtNationality, JTextField txtBirthDate,
            JTextField txtExperience, JComboBox<String> cbRole, DefaultTableModel model) {
        
        DateValidator validator = new DateValidator("dd/MM/uuuu");
        
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Only update fields if the corresponding text field is not empty.
           
            String name = txtName.getText().trim();
            String nationality = txtNationality.getText().trim();
            String birthDate = txtBirthDate.getText().trim();
            String Role = cbRole.getSelectedItem().toString();
            String experience = txtExperience.getText().trim();

            if (name.isEmpty() && nationality.isEmpty() && birthDate.isEmpty() && Role.isEmpty()
                    && experience.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter at least 1 data", "Error",
                    JOptionPane.ERROR_MESSAGE);

                return;
            }
            
            int check = 0;
            if (!Role.isEmpty()) {
                boolean existingHeadCoach = ControllerHuanLuyenVien.hasHeadCoach(selectedRow, model); // Truyền chỉ số
                // dòng hiện tại
                String currentRole = selectedRow >= 0 ? model.getValueAt(selectedRow, 3).toString() : "";

                // Kiểm tra nếu vai trò mới là HLV trưởng và đã có HLV trưởng khác trong danh sách
                if ("Head Coach".equals(Role) && existingHeadCoach && !"Head Coach".equals(currentRole)) {
                    JOptionPane.showMessageDialog(null, "A Head Coach already exists, cannot update to Head Coach.", "Error",
                            JOptionPane.ERROR_MESSAGE);

                    check = 1;
                    return; // Dừng thực hiện nếu cố gắng cập nhật vai trò thành HLV trưởng khi đã có một
                    // HLV trưởng
                }
                model.setValueAt(Role, selectedRow, 3);
            }
            if(check == 1) return;
            
            if (!name.isEmpty()) {
                if (ControllerHuanLuyenVien.containsNumber(name)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                    return;
                }
                model.setValueAt(ControllerHuanLuyenVien.normalizeName(name), selectedRow, 0);
            }

            if (!nationality.isEmpty()) {
                if (ControllerHuanLuyenVien.containsNumber(nationality)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid nationality", "Error",
                            JOptionPane.ERROR_MESSAGE);

                    return;
                }
                model.setValueAt(ControllerHuanLuyenVien.normalizeName(nationality), selectedRow, 1);
            }                       
            
            if (!birthDate.isEmpty()) {

                if (!validator.isValid(ControllerHuanLuyenVien.chuanhoa(birthDate))) {
                    JOptionPane.showMessageDialog(null, "Invalid date", "Error",
                            JOptionPane.ERROR_MESSAGE);

                    return;
                }
                model.setValueAt(ControllerHuanLuyenVien.chuanhoa(birthDate), selectedRow, 2);
            }           
            
            if (!experience.isEmpty()) {
                if (!ControllerHuanLuyenVien.isRealNumber(experience)) {
                    JOptionPane.showMessageDialog(null, "Invalid years of experience", "Error",
                            JOptionPane.ERROR_MESSAGE);

                    return;
                }
                model.setValueAt(experience, selectedRow, 4);
            }

            JOptionPane.showMessageDialog(null, "Coach information updated successfully.", "Information",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "Please select a coach to update.", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void deleteCoach(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // Remove the selected row from the model
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Coach information deleted successfully.", "Information",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            // No row is selected
            JOptionPane.showMessageDialog(null, "Please select a coach to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void saveData(DefaultTableModel model) {
        File file = new File("coach.csv");
        try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    bw.write(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) { // Avoid adding comma at the end of the line
                        bw.write(",");
                    }
                }
                bw.newLine(); // Move to the next line after writing all columns of the current row
            }
            JOptionPane.showMessageDialog(null, "Data successfully saved to " + file.getAbsolutePath(),
                    "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void loadData(JTable table) {
        try (BufferedReader br = new BufferedReader(new FileReader("coach.csv"))) {
            String line;
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing data
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
            JOptionPane.showMessageDialog(null, "Data successfully displayed!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
