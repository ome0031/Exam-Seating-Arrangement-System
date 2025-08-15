import java.util.*;

public class SeatingManager {

    /**
     * Simple seating arrangement algorithm
     * Rule: Students from same department should not sit together
     */
    public static List<SeatingArrangement> generateSeating(List<Student> students, List<Hall> halls) {
        List<SeatingArrangement> arrangements = new ArrayList<>();

        // Separate students by department
        Map<String, List<Student>> departmentGroups = new HashMap<>();
        for (Student student : students) {
            departmentGroups.computeIfAbsent(student.getDepartment(), k -> new ArrayList<>()).add(student);
        }

        // Mix students from different departments
        List<Student> mixedStudents = mixStudentsByDepartment(departmentGroups);

        int studentIndex = 0;
        int arrangementId = 1;

        // Allocate seats hall by hall
        for (Hall hall : halls) {
            if (studentIndex >= mixedStudents.size()) break;

            int seatsInHall = hall.getTotalSeats();

            for (int seatNumber = 1; seatNumber <= seatsInHall && studentIndex < mixedStudents.size(); seatNumber++) {
                Student student = mixedStudents.get(studentIndex++);

                SeatingArrangement arrangement = new SeatingArrangement(
                    arrangementId++, student, hall, seatNumber
                );

                arrangements.add(arrangement);
            }
        }

        return arrangements;
    }

    /**
     * Mix students from different departments to avoid same department sitting together
     */
    private static List<Student> mixStudentsByDepartment(Map<String, List<Student>> departmentGroups) {
        List<Student> mixed = new ArrayList<>();

        // Get maximum number of students in any department
        int maxSize = departmentGroups.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        // Round-robin allocation from each department
        for (int i = 0; i < maxSize; i++) {
            for (List<Student> deptStudents : departmentGroups.values()) {
                if (i < deptStudents.size()) {
                    mixed.add(deptStudents.get(i));
                }
            }
        }

        return mixed;
    }

    /**
     * Display seating arrangement in a formatted way
     */
    public static void displaySeatingArrangement(List<SeatingArrangement> arrangements) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                SEATING ARRANGEMENT");
        System.out.println("=".repeat(60));

        // Group by hall
        Map<String, List<SeatingArrangement>> hallGroups = new HashMap<>();
        for (SeatingArrangement arrangement : arrangements) {
            String hallName = arrangement.getHall().getHallName();
            hallGroups.computeIfAbsent(hallName, k -> new ArrayList<>()).add(arrangement);
        }

        for (String hallName : hallGroups.keySet()) {
            System.out.println("\n🏛️  " + hallName + ":");
            System.out.println("-".repeat(40));

            List<SeatingArrangement> hallArrangements = hallGroups.get(hallName);
            hallArrangements.sort(Comparator.comparing(SeatingArrangement::getSeatNumber));

            for (SeatingArrangement arrangement : hallArrangements) {
                System.out.printf("Seat %2d: %-15s [%s - %s]%n",
                    arrangement.getSeatNumber(),
                    arrangement.getStudent().getName(),
                    arrangement.getStudent().getRollNumber(),
                    arrangement.getStudent().getDepartment()
                );
            }
        }
        System.out.println("\n" + "=".repeat(60));
    }
}