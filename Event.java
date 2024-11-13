
public class Event {
    private String type;
    private Recyclableitem item;
    private double time;

    public Event(String type, Recyclableitem item, double time) {
        this.type = type;
        this.item = item;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public Recyclableitem getItem() {
        return item;
    }

    public double getTime() {
        return time;
    }
}