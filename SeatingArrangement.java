public class SeatingArrangement {
    private int id;
    private Student student;
    private Hall hall;
    private int seatNumber;

    // Default constructor
    public SeatingArrangement() {}

    // Parameterized constructor
    public SeatingArrangement(Student student, Hall hall, int seatNumber) {
        this.student = student;
        this.hall = hall;
        this.seatNumber = seatNumber;
    }

    // Full constructor
    public SeatingArrangement(int id, Student student, Hall hall, int seatNumber) {
        this.id = id;
        this.student = student;
        this.hall = hall;
        this.seatNumber = seatNumber;
    }

    // Getters
    public int getId() { return id; }
    public Student getStudent() { return student; }
    public Hall getHall() { return hall; }
    public int getSeatNumber() { return seatNumber; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setStudent(Student student) { this.student = student; }
    public void setHall(Hall hall) { this.hall = hall; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    @Override
    public String toString() {
        return "SeatingArrangement{id=" + id + 
               ", student=" + student.getName() + 
               ", hall=" + hall.getHallName() + 
               ", seatNumber=" + seatNumber + "}";
    }
}