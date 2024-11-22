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
    public Recyclableitem(String itemType, double itemWeight) {
        this.itemType = itemType;
        this.sortingError = false;
        this.itemWeight = itemWeight;
        this.setisdone_distribute(false);
        this.setisDone_sorting(false);
        this.time_to_sort = 0.0;
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
    public static List<Recyclableitem> createList(int number) {
        List<Recyclableitem> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            int type = random.nextInt(4);
            double itemWeight = 0.1 + (2.0 - 0.1) * random.nextDouble();

            switch (type) {
                case 0:
                    items.add(new Plastic(itemWeight));
                    break;
                case 1:
                    items.add(new Metal(itemWeight * 4));
                    break;
                case 2:
                    items.add(new Glass(itemWeight * 2));
                    break;
                case 3:
                    items.add(new Paper(itemWeight * 0.2));
                    break;
            }
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

