import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recyclableitem {
    private String itemType;
    private Boolean sortingError;
    private double itemWeight;
    private boolean done_sorting;
    private double time_to_finish;


    // Constructor
    public Recyclableitem(String itemType, double itemWeight) {
        this.itemType = itemType;
        this.sortingError = false;
        this.itemWeight = itemWeight;
        this.setisDone_sorting(false);
        this.time_to_finish = 0.0;
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
    public void set_time_to_finish(double time_to_finish) { this.time_to_finish = time_to_finish; }
    public double get_time_to_finish() { return time_to_finish; }


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
                    items.add(new Metal((itemWeight * 4)));
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
}