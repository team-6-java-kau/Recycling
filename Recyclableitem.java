import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recyclableitem {
    private String itemType;
    private String itemType_sorter;
    private Boolean sortingError;
    private double itemWeight;
    private boolean done_sorting;
    private boolean done_distribute;
    private double time_to_sort;
    private double time_to_distribute;

    // Constructor
    public Recyclableitem(String itemType) {
        this.itemType = itemType;
        this.sortingError = false;
        this.itemWeight = getFixedWeight(itemType); // Assign fixed weight
        this.setisdone_distribute(false);
        this.setisDone_sorting(false);
        this.setItemType_sorter(itemType);
        this.time_to_sort = 0.0;
    }
    public Recyclableitem(Recyclableitem other) {
        this.itemType = other.itemType;
        this.itemType_sorter = other.itemType_sorter;
        this.sortingError = other.sortingError;
        this.itemWeight = other.itemWeight;
        this.done_sorting = other.done_sorting;
        this.done_distribute = other.done_distribute;
        this.time_to_sort = other.time_to_sort;
        this.time_to_distribute = other.time_to_distribute;
    }

    // New constructor to accept both itemType and itemWeight
    public Recyclableitem(String itemType, double itemWeight) {
        this.itemType = itemType;
        this.sortingError = false;
        this.itemWeight = itemWeight;
        this.setisdone_distribute(false);
        this.setisDone_sorting(false);
        this.setItemType_sorter(itemType);
        this.time_to_sort = 0.0;
    }

    private double getFixedWeight(String itemType) {
        switch (itemType) {
            case "Metal":
                return 1.0; // Fixed weight for Metal
            case "Plastic":
                return 0.5; // Fixed weight for Plastic
            case "Glass":
                return 2.0; // Fixed weight for Glass
            case "Paper":
                return 0.3; // Fixed weight for Paper
            default:
                return 0.0; // Default weight
        }
    }

    // Getter and Setter methods
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public boolean getsortingError() { return sortingError; }
    public void setsortingError(boolean sortingError) { this.sortingError = sortingError; }
    public double getItemWeight() { return itemWeight; }
    public void setItemWeight(double itemWeight) { this.itemWeight = itemWeight; }
    public boolean isDone_sorting() { return done_sorting; }
    public void setisDone_sorting(boolean done_sorting) { this.done_sorting = done_sorting; }
    public boolean isdone_distribute() { return done_distribute; }
    public void setisdone_distribute(boolean done_distribute) { this.done_distribute = done_distribute; }
    public void set_time_to_sort(double time_to_sort) { this.time_to_sort = time_to_sort; }
    public double get_time_to_sort() { return time_to_sort; }
    public void set_time_to_distribute(double time_to_distribute) { this.time_to_distribute = time_to_distribute; }
    public double get_time_to_distribute() { return time_to_distribute; }
    public String getItemType_sorter() { return itemType_sorter; }
    public void setItemType_sorter(String itemType_sorter) { this.itemType_sorter = itemType_sorter; }
    // Method to create a list of Recycle objects
    public static List<Recyclableitem> createList(int numObjects) {
        List<Recyclableitem> items = new ArrayList<>();
        String[] types = {"Metal", "Plastic", "Glass", "Paper"};
        for (int i = 0; i < numObjects; i++) {
            String type = types[i % types.length];
            items.add(new Recyclableitem(type));
        }
        return items;
    }

    public static String createRandomItem() {
        Random random = new Random();
        int type = random.nextInt(4);

        switch (type) {
            case 0:
                return "Plastic";
            case 1:
                return "Metal";
            case 2:
                return "Glass";
            case 3:
                return "Paper";
            default:
                return null;
        }
    }
}

