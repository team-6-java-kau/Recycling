import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recyclableitem {
    private String itemType;
    private double size;
    private String condition;
    private double itemWeight;
    private boolean done_sorting;


    // Constructor
    public Recyclableitem(String itemType, double size, String condition, double itemWeight, boolean done_sorting) {
        this.itemType = itemType;
        this.size = size;
        this.condition = condition;
        this.itemWeight = itemWeight;
        this.done_sorting = done_sorting;
    }

    // Getter and Setter methods
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public double getItemWeight() { return itemWeight; }
    public void setItemWeight(double itemWeight) { this.itemWeight = itemWeight; }
    public boolean isDone_sorting() { return done_sorting; }
    public void setisDone_sorting(boolean done_sorting) { this.done_sorting = done_sorting; }

    // Method to create a list of Recycle objects
    public static List<Recyclableitem> createList(int number) {
        List<Recyclableitem> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            int type = random.nextInt(4);
            double size = 0.5 + (5.0 - 0.5) * random.nextDouble();
            String condition = random.nextBoolean() ? "Good" : "Damaged";
            double itemWeight = 0.1 + (2.0 - 0.1) * random.nextDouble();
            boolean itemRecyclability = random.nextBoolean();

            switch (type) {
                case 0:
                    items.add(new Plastic(size, condition, itemWeight, itemRecyclability));
                    break;
                case 1:
                    items.add(new Metal(size, condition, itemWeight, itemRecyclability));
                    break;
                case 2:
                    items.add(new Glass(size, condition, itemWeight, itemRecyclability));
                    break;
                case 3:
                    items.add(new Paper(size, condition, itemWeight, itemRecyclability));
                    break;
            }
        }
        return items;
    }
}
