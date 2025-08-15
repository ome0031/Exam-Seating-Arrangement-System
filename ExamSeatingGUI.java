import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExamSeatingGUI extends JFrame {
    private JTable studentTable;
    private JTextArea resultArea;
    private DefaultTableModel tableModel;

    public ExamSeatingGUI() {
        initializeGUI();
        loadStudentData();
    }

    private void initializeGUI() {
        setTitle("🎓 Exam Seating Arrangement System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Create main panels
        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Exam Seating Arrangement System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(34, 139, 34));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addStudentBtn = new JButton("➕ Add Student");
        JButton refreshBtn = new JButton("🔄 Refresh");
        JButton generateSeatingBtn = new JButton("🎯 Generate Seating");
        JButton clearResultsBtn = new JButton("🗑️ Clear Results");

        // Style buttons
        styleButton(addStudentBtn, new Color(34, 139, 34));
        styleButton(refreshBtn, new Color(30, 144, 255));
        styleButton(generateSeatingBtn, new Color(255, 140, 0));
        styleButton(clearResultsBtn, new Color(220, 20, 60));

        // Add action listeners
        addStudentBtn.addActionListener(e -> showAddStudentDialog());
        refreshBtn.addActionListener(e -> loadStudentData());
        generateSeatingBtn.addActionListener(e -> generateSeatingArrangement());
        clearResultsBtn.addActionListener(e -> resultArea.setText(""));

        buttonPanel.add(addStudentBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(generateSeatingBtn);
        buttonPanel.add(clearResultsBtn);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Students Database"));

        // Create table
        String[] columns = {"Roll Number", "Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.setSelectionBackground(new Color(173, 216, 230));

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Seating Arrangement Results"));

        resultArea = new JTextArea(12, 50);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(248, 248, 255));
        resultArea.setText("Click 'Generate Seating' to create seating arrangement...");

        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createRaisedBorderBorder());
        button.setPreferredSize(new Dimension(140, 35));
        button.setFocusPainted(false);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    private void loadStudentData() {
        // Clear existing data
        tableModel.setRowCount(0);

        try {
            List<Student> students = DatabaseConnection.getAllStudents();

            for (Student student : students) {
                Object[] row = {
                    student.getRollNumber(),
                    student.getName(),
                    student.getDepartment()
                };
                tableModel.addRow(row);
            }

            // Update status
            if (students.isEmpty()) {
                resultArea.setText("No students found in database.\nAdd some students to get started!");
            } else {
                resultArea.setText("Loaded " + students.size() + " students from database.\n" +
                                 "Click 'Generate Seating' to create arrangement.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading student data: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddStudentDialog() {
        JDialog dialog = new JDialog(this, "Add New Student", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Roll Number
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Roll Number:"), gbc);
        gbc.gridx = 1;
        JTextField rollField = new JTextField(15);
        dialog.add(rollField, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        dialog.add(nameField, gbc);

        // Department
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> deptCombo = new JComboBox<>(new String[]{"CSE", "ECE", "MECH", "CIVIL", "IT"});
        dialog.add(deptCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");

        addBtn.addActionListener(e -> {
            String rollNumber = rollField.getText().trim();
            String name = nameField.getText().trim();
            String department = (String) deptCombo.getSelectedItem();

            if (rollNumber.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(0, rollNumber, name, department);

            if (DatabaseConnection.addStudent(student)) {
                JOptionPane.showMessageDialog(dialog, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentData(); // Refresh table
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add student!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void generateSeatingArrangement() {
        try {
            resultArea.setText("Generating seating arrangement...\n\n");

            // Get data
            List<Student> students = DatabaseConnection.getAllStudents();
            List<Hall> halls = DatabaseConnection.getAllHalls();

            if (students.isEmpty()) {
                resultArea.setText("❌ No students found!\nPlease add some students first.");
                return;
            }

            if (halls.isEmpty()) {
                resultArea.setText("❌ No halls found!\nPlease check database setup.");
                return;
            }

            // Generate seating
            List<SeatingArrangement> arrangements = SeatingManager.generateSeating(students, halls);

            // Display results
            StringBuilder result = new StringBuilder();
            result.append("✅ SEATING ARRANGEMENT GENERATED\n");
            result.append("=".repeat(50)).append("\n\n");

            // Group by hall
            String currentHall = "";
            for (SeatingArrangement arrangement : arrangements) {
                if (!arrangement.getHall().getHallName().equals(currentHall)) {
                    currentHall = arrangement.getHall().getHallName();
                    result.append("\n🏛️  ").append(currentHall).append(":\n");
                    result.append("-".repeat(30)).append("\n");
                }

                result.append(String.format("Seat %2d: %-15s [%s - %s]\n",
                    arrangement.getSeatNumber(),
                    arrangement.getStudent().getName(),
                    arrangement.getStudent().getRollNumber(),
                    arrangement.getStudent().getDepartment()
                ));
            }

            result.append("\n").append("=".repeat(50)).append("\n");
            result.append("Total Students Allocated: ").append(arrangements.size());

            resultArea.setText(result.toString());

            // Ask if user wants to save
            int choice = JOptionPane.showConfirmDialog(this, 
                "Save this arrangement to database?", 
                "Save Arrangement", 
                JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                int saved = 0;
                for (SeatingArrangement arrangement : arrangements) {
                    if (DatabaseConnection.saveSeatingArrangement(arrangement)) {
                        saved++;
                    }
                }

                JOptionPane.showMessageDialog(this, 
                    "Saved " + saved + " arrangements to database!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            resultArea.setText("❌ Error generating seating: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new ExamSeatingGUI().setVisible(true);
        });
    }
}