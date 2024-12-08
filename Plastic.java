// Represents a plastic item that can be compressed
public class Plastic extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;

    // Constructor to initialize a plastic item with weight
    public Plastic(double itemWeight) {
        super("Plastic", itemWeight);
    }

    // Method to compress the plastic item
    @Override
    public void compress() {
        isCompressed = true;
    }

    // Method to check if the plastic item is compressed
    @Override
    public boolean isCompressed() {
        return isCompressed;
    }
}