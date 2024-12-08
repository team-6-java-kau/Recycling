// Represents a paper item that can be compressed
public class Paper extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;

    // Constructor to initialize a paper item with weight
    public Paper(double itemWeight) {
        super("Paper", itemWeight);
    }

    // Method to compress the paper item
    @Override
    public void compress() {
        isCompressed = true;
    }

    // Method to check if the paper item is compressed
    @Override
    public boolean isCompressed() {
        return isCompressed;
    }
}