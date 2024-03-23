package project_java_group_5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControllerCauThu {

    public static void filterByPosition(DefaultTableModel originalModel, String position) {
        // Tạo một DefaultTableModel mới để chứa kết quả lọc
        DefaultTableModel filteredModel = new DefaultTableModel();
        // Định nghĩa tiêu đề cột cho bảng mới
        String[] columnNames = { "Name", "Nationality", "Gender", "Date of Birth", "Date of Joining", "Position",
                "Matches", "Goals", "Agreed Salary", "Points from Last 5 Matches" };

        filteredModel.setColumnIdentifiers(columnNames);

        // Duyệt qua dữ liệu gốc và lọc theo vị trí
        for (int i = 0; i < originalModel.getRowCount(); i++) {
            String playerPosition = (String) originalModel.getValueAt(i, 5); // Giả sử vị trí được lưu ở cột thứ 6
            if (playerPosition.equals(position)) {
                // Nếu vị trí khớp, thêm dòng vào model mới
                Object[] row = new Object[originalModel.getColumnCount()];
                for (int j = 0; j < originalModel.getColumnCount(); j++) {
                    row[j] = originalModel.getValueAt(i, j);
                }
                filteredModel.addRow(row);
            }
        }

        // Tạo JTable mới với model đã lọc
        JTable filteredTable = new JTable(filteredModel);

        // Tạo JFrame hoặc JDialog mới để hiển thị bảng
        JDialog dialog = new JDialog();
        dialog.setTitle("Filter Results: " + position);
        dialog.setSize(800, 400); // Đặt kích thước phù hợp
        dialog.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình

        // Thêm JTable vào JScrollPane và thêm JScrollPane vào dialog
        JScrollPane scrollPane = new JScrollPane(filteredTable);
        dialog.add(scrollPane);

        // Hiển thị dialog
        dialog.setVisible(true);
    }

    public static void sortTableByColumn(DefaultTableModel model, int column, boolean ascending) {
        ArrayList<Object[]> rowDataList = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] row = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j);
            }
            rowDataList.add(row);
        }

        // Kiểm tra xem cột có phải là dữ liệu số không để áp dụng bộ so sánh phù hợp
        if (column == 6 || column == 7 || column == 8) { // Giả sử cột 6, 7, 8 là số trận, số bàn thắng, và lương
            rowDataList.sort((o1, o2) -> {
                Double val1 = Double.parseDouble(o1[column].toString());
                Double val2 = Double.parseDouble(o2[column].toString());
                return ascending ? val1.compareTo(val2) : val2.compareTo(val1);
            });
        } else {
            // Sắp xếp theo thứ tự từ điển cho các cột khác
            rowDataList.sort((o1, o2) -> {
                if (ascending) {
                    return o1[column].toString().compareToIgnoreCase(o2[column].toString());
                } else {
                    return o2[column].toString().compareToIgnoreCase(o1[column].toString());
                }
            });
        }

        // Cập nhật lại model với dữ liệu đã được sắp xếp
        model.setRowCount(0);
        for (Object[] row : rowDataList) {
            model.addRow(row);
        }
    }

    public static void saveTableModelToFile(DefaultTableModel model, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    writer.write(model.getValueAt(row, col).toString());
                    if (col < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }

                writer.write("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing data to file: " + e.getMessage());
        }
    }

    public static String chuanhoa(String s) {
        String[] arr = s.split("/");
        if (arr.length != 3)
            return "00/00/0000";
        String ans = String.format("%02d", Integer.parseInt(arr[0])) + "/";
        ans += String.format("%02d", Integer.parseInt(arr[1])) + "/" + arr[2];
        return ans;
    }

    public static boolean isRealNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(str);
            if (x >= 0) {
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

    public static boolean isStringInFormat(String input) {
        // Sửa đổi biểu thức chính quy để khớp với một số có một hoặc hai chữ số
        if (!input.matches("(\\d{1,2})-(\\d{1,2})-(\\d{1,2})-(\\d{1,2})-(\\d{1,2})")) {
            return false;
        }

        // Tách chuỗi thành mảng các phần tử dựa trên dấu "-"
        String[] parts = input.split("-");

        // Kiểm tra từng phần tử để đảm bảo chúng là số nguyên từ 1 đến 10
        for (String part : parts) {
            try {
                int number = Integer.parseInt(part);
                if (number < 1 || number > 10) {
                    // Nếu số nằm ngoài khoảng 1 đến 10, trả về false
                    return false;
                }
            } catch (NumberFormatException e) {
                // Nếu không thể chuyển đổi thành số, trả về false
                return false;
            }
        }

        // Nếu tất cả kiểm tra đều hợp lệ, trả về true
        return true;
    }

    public static String getYearFromString(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date = sdf.parse(dateString);
            // Tạo một đối tượng SimpleDateFormat mới để chỉ định định dạng của kết quả mong
            // muốn
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            return yearFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "The date format is invalid.";
        }
    }

    public static Object[][] loadData(Object[][] data) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Data.csv"));
            String line = br.readLine();
            int rowCount = 0;

            // Count the number of lines in the file to determine the array size
            while (line != null) {
                rowCount++;
                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader("Data.csv"));

            // Initialize the data array with the determined size
            data = new Object[rowCount][10];

            int rowIndex = 0;

            // Read data from the file and populate the array
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                for (int i = 0; i < value.length; i++) {
                    data[rowIndex][i] = value[i];
                }
                rowIndex++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public static void in4(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy thông tin 
            String ten = model.getValueAt(selectedRow, 0).toString();
            String nuoc = model.getValueAt(selectedRow, 1).toString();
            String ns = model.getValueAt(selectedRow, 3).toString();
            String join = model.getValueAt(selectedRow, 4).toString(); 
            String pos = model.getValueAt(selectedRow, 5).toString();
            String luongThoaThuanStr = model.getValueAt(selectedRow, 8).toString();
            String soTranThamGiaStr = model.getValueAt(selectedRow, 6).toString();
            String soBanThangStr = model.getValueAt(selectedRow, 7).toString();

            // Chuyển đổi sang kiểu dữ liệu số
            
            Integer luongThoaThuan = Integer.parseInt(luongThoaThuanStr);
            int soLuotTranThamGia = Integer.parseInt(soTranThamGiaStr);
            int banThang = Integer.parseInt(soBanThangStr);

            // Tính toán lương và thưởng            
           
            Integer thamNienn = Integer.parseInt(ControllerCauThu.getYearFromString(join, "d/M/yyyy"));

            CauThu x = new CauThu(ten, nuoc, ns, thamNienn, pos, soLuotTranThamGia, banThang, luongThoaThuan);

            // Hiển thị thông tin
            JOptionPane.showMessageDialog(null, "Salary: " + x.tinhLuong() + "\nBonus: " +  x.tinhThuong(),
                    "Salary and Bonus Details", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "Please select at least one player.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);

        }
    }

    public static void them5tran(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String latestMatchScore = JOptionPane.showInputDialog("Enter the latest match score of the player:");
            List<String> arr = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                arr.add("" + i);
            }
            if (!arr.contains(latestMatchScore)) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid score format for the latest match",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get the current row's latest match scores
            String currentLastFiveMatchesScores = (String) model.getValueAt(selectedRow, 9);
            String[] lastFiveMatchesArray = currentLastFiveMatchesScores.split("-");
            LinkedList<String> lastFiveMatchesList = new LinkedList<>(Arrays.asList(lastFiveMatchesArray));

            // Add the latest match score to the end of the list
            lastFiveMatchesList.addLast(latestMatchScore);

            // Remove the first element if the list has more than 5 elements
            if (lastFiveMatchesList.size() > 5) {
                lastFiveMatchesList.removeFirst();
            }

            // Join the elements back into a string separated by "-"
            String updatedLastFiveMatchesScores = String.join("-", lastFiveMatchesList);

            // Update the model with the updated last five matches scores
            model.setValueAt(updatedLastFiveMatchesScores, selectedRow, 9);

            // Save the updated data to the file
            ControllerCauThu.saveTableModelToFile(model, "Data.csv");

            JOptionPane.showMessageDialog(null, "Successfully updated the new score!");
        } else {
            JOptionPane.showMessageDialog(null, "Please select at least one player");
        }

    }

    public static void thaydoi(JTable table, DefaultTableModel model, DateValidator validator) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Menu lựa chọn các trường để chỉnh sửa
            final JCheckBox nameCheck = new JCheckBox("Name");
            final JCheckBox nationalityCheck = new JCheckBox("Nationality");
            final JCheckBox genderCheck = new JCheckBox("Gender");
            final JCheckBox birthDateCheck = new JCheckBox("Date of Birth");
            final JCheckBox joinDateCheck = new JCheckBox("Date of Joining");
            final JCheckBox positionCheck = new JCheckBox("Playing Position");
            final JCheckBox matchCheck = new JCheckBox("Number of Matches");
            final JCheckBox goalCheck = new JCheckBox("Number of Goals");
            final JCheckBox salaryCheck = new JCheckBox("Agreed Salary");
            final JCheckBox lastFiveMatchesCheck = new JCheckBox("Points from Last 5 Matches");


            Object[] options = {
                    nameCheck, nationalityCheck, genderCheck,
                    birthDateCheck, joinDateCheck, positionCheck,
                    matchCheck, goalCheck, salaryCheck, lastFiveMatchesCheck
            };

            int option = JOptionPane.showConfirmDialog(null, options, "Choose data fields to edit",
                    JOptionPane.OK_CANCEL_OPTION);


            if (option == JOptionPane.OK_OPTION) {
                // Tạo form chỉnh sửa dựa trên lựa chọn
                ArrayList<Object> fields = new ArrayList<>();
                if (nameCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Name:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 0))));
                }
                if (nationalityCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Nationality:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 1))));
                }
                if (genderCheck.isSelected()) {
                    JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
                    genderComboBox.setSelectedItem(table.getModel().getValueAt(selectedRow, 2));
                    fields.addAll(Arrays.asList("Gender:", genderComboBox));
                }
                if (birthDateCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Date of Birth:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 3))));
                }
                if (joinDateCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Date of Joining:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 4))));
                }
                if (positionCheck.isSelected()) {
                    JComboBox<String> positionComboBox = new JComboBox<>(
                            new String[] { "Forward", "Midfielder","Defender", "Goalkeeper" });
                    positionComboBox.setSelectedItem(table.getModel().getValueAt(selectedRow, 5));
                    fields.addAll(Arrays.asList("Playing Position:", positionComboBox));
                }
                if (matchCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Number of Matches:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 6))));
                }
                if (goalCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Number of Goals:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 7))));
                }
                if (salaryCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Agreed Salary:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 8))));
                }
                if (lastFiveMatchesCheck.isSelected()) {
                    fields.addAll(Arrays.asList("Points from Last 5 Matches:",
                            new JTextField((String) table.getModel().getValueAt(selectedRow, 9))));
                }


                // Hiển thị form chỉnh sửa
                if (!fields.isEmpty()) {
                    Object[] fieldsArray = fields.toArray();
                    boolean Check = false;
                    while (!Check) {
                        Check = true;
                        int result = JOptionPane.showConfirmDialog(null, fieldsArray, "Edit Player Information",
                                JOptionPane.OK_CANCEL_OPTION);

                        if (result == JOptionPane.OK_OPTION) {
                            int fieldIndex = 1; // Index for accessing the JTextField values in fieldsArray
                            // Cập nhật dữ liệu mới vào model dựa trên nhập liệu
                            if (nameCheck.isSelected()) {
                                JTextField name = (JTextField) fieldsArray[fieldIndex];
                                String namee = (String) name.getText();
                                if (ControllerCauThu.containsNumber(namee)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a name without special characters or numbers",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }

                                String name2 = ControllerCauThu.normalizeName(namee);
                                if (Check) {
                                    model.setValueAt(name2, selectedRow, 0);
                                }
                                fieldIndex += 2;
                            }
                            // Cập nhật các trường khác dựa vào lựa chọn
                            if (nationalityCheck.isSelected()) {
                                JTextField quoctich = (JTextField) fieldsArray[fieldIndex];
                                String quocc = (String) quoctich.getText();
                                if (ControllerCauThu.containsNumber(quocc)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter nationality data without special characters or numbers",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow,
                                            1);
                                }
                                fieldIndex += 2;
                            }
                            if (genderCheck.isSelected()) {
                                model.setValueAt(((JComboBox) fieldsArray[fieldIndex]).getSelectedItem(),
                                        selectedRow,
                                        2);
                                fieldIndex += 2;
                            }
                            if (birthDateCheck.isSelected()) {
                                JTextField Date = (JTextField) fieldsArray[fieldIndex];
                                String birthDate = ControllerCauThu.chuanhoa((String) Date.getText());
                                if (!validator.isValid(birthDate)) {
                                    JOptionPane.showMessageDialog(null, "Invalid date format", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(birthDate, selectedRow, 3);
                                }
                                fieldIndex += 2;
                            }
                            if (joinDateCheck.isSelected()) {
                                JTextField Date = (JTextField) fieldsArray[fieldIndex];
                                String joinDate = ControllerCauThu.chuanhoa((String) Date.getText());
                                if (!validator.isValid(joinDate)) {
                                    JOptionPane.showMessageDialog(null, "Invalid date format", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(joinDate, selectedRow, 4);
                                }
                                fieldIndex += 2;
                            }
                            if (positionCheck.isSelected()) {

                                model.setValueAt(((JComboBox) fieldsArray[fieldIndex]).getSelectedItem(),
                                        selectedRow,
                                        5);
                                fieldIndex += 2;
                            }
                            if (matchCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String sotrann = check.getText();
                                if (!ControllerCauThu.isRealNumber(sotrann)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid real number for the number of matches",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow,
                                            6);
                                }
                                fieldIndex += 2;
                            }
                            if (goalCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String banthang = check.getText();
                                if (!ControllerCauThu.isRealNumber(banthang)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid real number for the number of goals",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow,
                                            7);
                                }
                                fieldIndex += 2;
                            }
                            if (salaryCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String luongg = check.getText();
                                if (!ControllerCauThu.isRealNumber(luongg)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid real number for the salary data",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow,
                                            8);
                                }
                                fieldIndex += 2;
                            }
                            if (lastFiveMatchesCheck.isSelected()) {
                                JTextField check = (JTextField) fieldsArray[fieldIndex];
                                String namtran = check.getText();
                                if (!ControllerCauThu.isStringInFormat(namtran)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please enter a valid score format for the last 5 matches",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    Check = false;
                                }
                                if (Check) {
                                    model.setValueAt(((JTextField) fieldsArray[fieldIndex]).getText(), selectedRow,
                                            9);
                                }
                                // Không cần tăng fieldIndex ở đây nếu đây là trường cuối cùng
                            }
                            if (Check) {
                                JOptionPane.showMessageDialog(null, "Player information has been updated.");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select at least one field to edit.");
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please select a player for editing.");
        }
    }

    public static void them(DefaultTableModel model, Object[] fields, JTextField nameField, JTextField nationalityField,
            JComboBox<String> positionComboBox, JComboBox<String> genderField, JComboBox<String> joinDayComboBox,
            JComboBox<String> joinMonthComboBox, JComboBox<String> joinYearComboBox, JTextField matchField,
            JTextField goalField, JTextField salaryField, JComboBox<String> dayComboBox,
            JComboBox<String> monthComboBox, JComboBox<String> yearComboBox, DateValidator validator,
            JTextField lastFiveMatchesField) {
        boolean Check = false;
        while (!Check) {
            Check = true;
            int result = JOptionPane.showConfirmDialog(null, fields, "Enter information for the new player.",
                    JOptionPane.OK_CANCEL_OPTION);

            // Validate and add the new row to the model
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Convert relevant fields to appropriate types
                    String name1 = nameField.getText();
                    String nationality1 = nationalityField.getText();

                    if (ControllerCauThu.containsNumber(name1) || ControllerCauThu.containsNumber(nationality1)) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter the player's name and nationality without special characters or numbers.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        Check = false;
                    }
                    String name = ControllerCauThu.normalizeName(name1);
                    String nationality = ControllerCauThu.normalizeName(nationality1);

                    String joinD = (String) joinDayComboBox.getSelectedItem() + "/"
                            + (String) joinMonthComboBox.getSelectedItem() + "/"
                            + (String) joinYearComboBox.getSelectedItem();

                    // Integer sotran = Integer.parseInt(matchField.getText());
                    // Integer soBanThang = Integer.parseInt(goalField.getText());
                    // Integer Luong = Integer.parseInt(salaryField.getText());
                    String sotran = matchField.getText();
                    String soBanThang = goalField.getText();
                    String Luong = salaryField.getText();

                    // Get the selected position from JComboBox
                    String gender = (String) genderField.getSelectedItem();
                    String selectedPosition = (String) positionComboBox.getSelectedItem();
                    String birth = (String) dayComboBox.getSelectedItem() + "/"
                            + (String) monthComboBox.getSelectedItem() + "/"
                            + (String) yearComboBox.getSelectedItem();

                    // Xu li ngoai le ngay thang nam
                    if (!validator.isValid(joinD) || !validator.isValid(birth)) {
                        JOptionPane.showMessageDialog(null, "The date is invalid.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        Check = false;
                    }
                    // Xu li ngoai le chuoi nhap vao khong phai so
                    if (name.isEmpty() || nationality.isEmpty() || sotran.isEmpty() || soBanThang.isEmpty()
                            || Luong.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter complete data.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        Check = false;
                    }
                    if (!ControllerCauThu.isRealNumber(sotran) || !ControllerCauThu.isRealNumber(soBanThang)
                            || !ControllerCauThu.isRealNumber(Luong)) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter valid numerical data for matches played, goals scored, and salary.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        Check = false;
                    }

                    String lastFiveMatchesScores = lastFiveMatchesField.getText();
                    if (!ControllerCauThu.isStringInFormat(lastFiveMatchesScores)) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter valid scores for the player's last 5 matches.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        Check = false;
                    }
                    if (Check == true) {
                        // Add the new row to the model
                        model.addRow(
                                new Object[] { name, nationality, gender, birth, joinD, selectedPosition, sotran,
                                        soBanThang, Luong, lastFiveMatchesScores });
                        ControllerCauThu.saveTableModelToFile(model, "Data.csv");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "The number of matches played, goals scored, and salary must be integers. Please re-enter.", "Data entry error.",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null,
                            "The data entered is incorrect. Please do not leave the required fields blank.",
                            "Data entry error.", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
