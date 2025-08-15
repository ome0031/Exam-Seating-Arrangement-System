public class Hall {
    private int id;
    private String hallName;
    private int totalSeats;

    // Default constructor
    public Hall() {}

    // Parameterized constructor
    public Hall(String hallName, int totalSeats) {
        this.hallName = hallName;
        this.totalSeats = totalSeats;
    }

    // Full constructor
    public Hall(int id, String hallName, int totalSeats) {
        this.id = id;
        this.hallName = hallName;
        this.totalSeats = totalSeats;
    }

    // Getters
    public int getId() { return id; }
    public String getHallName() { return hallName; }
    public int getTotalSeats() { return totalSeats; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    @Override
    public String toString() {
        return "Hall{id=" + id + ", hallName='" + hallName + 
               "', totalSeats=" + totalSeats + "}";
    }
}