import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/simple_exam_seating";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    // Get database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Get all students from database
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("department")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }

        return students;
    }

    // Get all halls from database
    public static List<Hall> getAllHalls() {
        List<Hall> halls = new ArrayList<>();
        String query = "SELECT * FROM halls";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Hall hall = new Hall(
                    rs.getInt("id"),
                    rs.getString("hall_name"),
                    rs.getInt("total_seats")
                );
                halls.add(hall);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching halls: " + e.getMessage());
        }

        return halls;
    }

    // Add student to database
    public static boolean addStudent(Student student) {
        String query = "INSERT INTO students (roll_number, name, department) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getRollNumber());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // Save seating arrangement
    public static boolean saveSeatingArrangement(SeatingArrangement seating) {
        String query = "INSERT INTO seating (student_id, hall_id, seat_number) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, seating.getStudent().getId());
            pstmt.setInt(2, seating.getHall().getId());
            pstmt.setInt(3, seating.getSeatNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error saving seating: " + e.getMessage());
            return false;
        }
    }
}