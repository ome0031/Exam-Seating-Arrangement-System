public class Student {
    private int id;
    private String rollNumber;
    private String name;
    private String department;

    // Default constructor
    public Student() {}

    // Parameterized constructor
    public Student(String rollNumber, String name, String department) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.department = department;
    }

    // Full constructor
    public Student(int id, String rollNumber, String name, String department) {
        this.id = id;
        this.rollNumber = rollNumber;
        this.name = name;
        this.department = department;
    }

    // Getters
    public int getId() { return id; }
    public String getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", rollNumber='" + rollNumber + 
               "', name='" + name + "', department='" + department + "'}";
    }
}