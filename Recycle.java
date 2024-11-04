public class Recycle {
    private String itemType;
    private double size;
    private String condition;
    private double itemWeight;
    private boolean itemRecyclability;

    // Constructor
    public Recycle(String itemType, double size, String condition, double itemWeight, boolean itemRecyclability) {
        this.itemType = itemType;
        this.size = size;
        this.condition = condition;
        this.itemWeight = itemWeight;
        this.itemRecyclability = itemRecyclability;
    }

    // Getter and Setter methods
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public boolean isItemRecyclability() {
        return itemRecyclability;
    }

    public void setItemRecyclability(boolean itemRecyclability) {
        this.itemRecyclability = itemRecyclability;
    }

    // Method to create a list of Recycle objects
    public void createList(int number) {
        // Implementation for creating a list of recycle items
    }

    // Method to mark items as done
    public void done(boolean done) {
        // Implementation for marking items as done
    }
}
