import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/exam_seating";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // Get database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Get all students
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
            System.out.println("Error getting students: " + e.getMessage());
        }

        return students;
    }

    // Add new student
    public static boolean addStudent(Student student) {
        String sql = "INSERT INTO students (roll_number, name, department) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getRollNumber());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // Get all halls
    public static List<Hall> getAllHalls() {
        List<Hall> halls = new ArrayList<>();
        String sql = "SELECT * FROM halls";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Hall hall = new Hall(
                    rs.getInt("id"),
                    rs.getString("hall_name"),
                    rs.getInt("total_seats")
                );
                halls.add(hall);
            }
        } catch (SQLException e) {
            System.out.println("Error getting halls: " + e.getMessage());
        }

        return halls;
    }

    // Save seating arrangement
    public static boolean saveSeatingArrangement(SeatingArrangement seating) {
        String sql = "INSERT INTO seating (student_id, hall_id, seat_number) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seating.getStudent().getId());
            pstmt.setInt(2, seating.getHall().getId());
            pstmt.setInt(3, seating.getSeatNumber());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error saving seating: " + e.getMessage());
            return false;
        }
    }

    // Clear all seating arrangements
    public static boolean clearSeatingArrangements() {
        String sql = "DELETE FROM seating";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
            System.out.println("Error clearing seating: " + e.getMessage());
            return false;
        }
    }
}