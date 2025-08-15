import java.util.List;
import java.util.Scanner;

public class ExamSeatingSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🎓 Welcome to Simple Exam Seating Arrangement System");
        System.out.println("=".repeat(55));

        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAllStudents();
                    break;
                case 2:
                    addNewStudent();
                    break;
                case 3:
                    viewAllHalls();
                    break;
                case 4:
                    generateSeatingArrangement();
                    break;
                case 5:
                    System.out.println("\n👋 Thank you for using the system!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void showMenu() {
        System.out.println("\n📋 Main Menu:");
        System.out.println("1. View All Students");
        System.out.println("2. Add New Student");
        System.out.println("3. View All Halls");
        System.out.println("4. Generate Seating Arrangement");
        System.out.println("5. Exit");
        System.out.print("\nEnter your choice (1-5): ");
    }

    private static void viewAllStudents() {
        System.out.println("\n👨‍🎓 All Students:");
        System.out.println("-".repeat(50));

        List<Student> students = DatabaseConnection.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in database.");
            return;
        }

        for (Student student : students) {
            System.out.printf("%-12s | %-20s | %s%n",
                student.getRollNumber(),
                student.getName(),
                student.getDepartment()
            );
        }
        System.out.println("\nTotal Students: " + students.size());
    }

    private static void addNewStudent() {
        System.out.println("\n➕ Add New Student:");
        System.out.println("-".repeat(30));

        System.out.print("Roll Number: ");
        String rollNumber = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Department (CSE/ECE/MECH/CIVIL): ");
        String department = scanner.nextLine().toUpperCase();

        Student student = new Student(0, rollNumber, name, department);

        if (DatabaseConnection.addStudent(student)) {
            System.out.println("✅ Student added successfully!");
        } else {
            System.out.println("❌ Failed to add student.");
        }
    }

    private static void viewAllHalls() {
        System.out.println("\n🏛️ All Examination Halls:");
        System.out.println("-".repeat(40));

        List<Hall> halls = DatabaseConnection.getAllHalls();

        if (halls.isEmpty()) {
            System.out.println("No halls found in database.");
            return;
        }

        for (Hall hall : halls) {
            System.out.printf("%-10s | Capacity: %d seats%n",
                hall.getHallName(),
                hall.getTotalSeats()
            );
        }
    }

    private static void generateSeatingArrangement() {
        System.out.println("\n🎯 Generating Seating Arrangement...");
        System.out.println("-".repeat(45));

        // Get students and halls from database
        List<Student> students = DatabaseConnection.getAllStudents();
        List<Hall> halls = DatabaseConnection.getAllHalls();

        if (students.isEmpty() || halls.isEmpty()) {
            System.out.println("❌ Cannot generate seating: No students or halls found.");
            return;
        }

        // Generate seating arrangement
        List<SeatingArrangement> arrangements = SeatingManager.generateSeating(students, halls);

        // Display the arrangement
        SeatingManager.displaySeatingArrangement(arrangements);

        // Save to database
        System.out.print("\nSave this arrangement to database? (y/n): ");
        String save = scanner.nextLine();

        if (save.equalsIgnoreCase("y")) {
            int saved = 0;
            for (SeatingArrangement arrangement : arrangements) {
                if (DatabaseConnection.saveSeatingArrangement(arrangement)) {
                    saved++;
                }
            }
            System.out.println("✅ Saved " + saved + " seating arrangements to database!");
        }
    }
}