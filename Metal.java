// Represents a metal item that can be compressed
public class Metal extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;

    // Constructor to initialize a metal item with weight
    public Metal(double itemWeight) {
        super("Metal", itemWeight);
    }

    // Method to compress the metal item
    @Override
    public void compress() {
        isCompressed = true;
    }

    // Method to check if the metal item is compressed
    @Override
    public boolean isCompressed() {
        return isCompressed;
    }
}