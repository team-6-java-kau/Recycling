import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents a recyclable item with various properties and methods for sorting and distribution
public class Recyclableitem {
    private String itemType;
    private String itemType_sorter;
    private Boolean sortingError;
    private double itemWeight;
    private boolean done_sorting;
    private boolean done_distribute;
    private double time_to_sort;
    private double time_to_distribute;

    // Constructor to initialize a recyclable item with type and weight
    public Recyclableitem(String itemType, double itemWeight) {
        this.itemType = itemType;
        this.sortingError = false;
        this.itemWeight = itemWeight;
        this.setisdone_distribute(false);
        this.setisDone_sorting(false);
        this.setItemType_sorter(itemType);
        this.time_to_sort = 0.0;
    }

    // Copy constructor to create a copy of an existing recyclable item
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

    // Method to create a list of recyclable items with random properties
    public static List<Recyclableitem> createList(int number) {
        List<Recyclableitem> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            int type = random.nextInt(4);
            double itemWeight = 0.1 + (2.0 - 0.1) * random.nextDouble();

            switch (type) {
                case 0:
                    Plastic plastic = new Plastic(itemWeight);
                    if (random.nextBoolean()) {
                        plastic.compress();
                    }
                    items.add(plastic);
                    break;
                case 1:
                    Metal metal = new Metal(itemWeight * 4);
                    if (random.nextBoolean()) {
                        metal.compress();
                    }
                    items.add(metal);
                    break;
                case 2:
                    items.add(new Glass(itemWeight * 2));
                    break;
                case 3:
                    Paper paper = new Paper(itemWeight * 0.2);
                    if (random.nextBoolean()) {
                        paper.compress();
                    }
                    items.add(paper);
                    break;
            }
        }
        return items;
    }

    // Method to create a random item type as a string
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

