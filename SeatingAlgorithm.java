import java.util.*;

public class SeatingAlgorithm {

    // Main method to generate seating arrangement
    public static List<SeatingArrangement> generateSeating(List<Student> students, List<Hall> halls) {
        List<SeatingArrangement> arrangements = new ArrayList<>();

        // Step 1: Group students by department
        Map<String, List<Student>> departmentGroups = groupByDepartment(students);

        // Step 2: Mix students using round-robin
        List<Student> mixedStudents = mixStudentsByDepartment(departmentGroups);

        // Step 3: Allocate seats
        int studentIndex = 0;

        for (Hall hall : halls) {
            if (studentIndex >= mixedStudents.size()) break;

            for (int seatNum = 1; seatNum <= hall.getTotalSeats() && studentIndex < mixedStudents.size(); seatNum++) {
                Student student = mixedStudents.get(studentIndex++);
                SeatingArrangement arrangement = new SeatingArrangement(student, hall, seatNum);
                arrangements.add(arrangement);
            }
        }

        return arrangements;
    }

    // Group students by department (OOP principle: Single Responsibility)
    private static Map<String, List<Student>> groupByDepartment(List<Student> students) {
        Map<String, List<Student>> groups = new HashMap<>();

        for (Student student : students) {
            String dept = student.getDepartment();
            if (!groups.containsKey(dept)) {
                groups.put(dept, new ArrayList<>());
            }
            groups.get(dept).add(student);
        }

        return groups;
    }

    // Mix students using round-robin algorithm
    private static List<Student> mixStudentsByDepartment(Map<String, List<Student>> departmentGroups) {
        List<Student> mixed = new ArrayList<>();

        // Find maximum department size
        int maxSize = 0;
        for (List<Student> deptStudents : departmentGroups.values()) {
            maxSize = Math.max(maxSize, deptStudents.size());
        }

        // Round-robin selection
        for (int i = 0; i < maxSize; i++) {
            for (List<Student> deptStudents : departmentGroups.values()) {
                if (i < deptStudents.size()) {
                    mixed.add(deptStudents.get(i));
                }
            }
        }

        return mixed;
    }

    // Utility method to check if arrangement is valid
    public static boolean validateArrangement(List<SeatingArrangement> arrangements) {
        // Simple validation: check no adjacent same department students
        for (int i = 0; i < arrangements.size() - 1; i++) {
            SeatingArrangement current = arrangements.get(i);
            SeatingArrangement next = arrangements.get(i + 1);

            // If same hall and adjacent seats, check departments
            if (current.getHall().getId() == next.getHall().getId() && 
                Math.abs(current.getSeatNumber() - next.getSeatNumber()) == 1) {

                if (current.getStudent().getDepartment().equals(next.getStudent().getDepartment())) {
                    return false; // Same department students are adjacent
                }
            }
        }
        return true;
    }
}