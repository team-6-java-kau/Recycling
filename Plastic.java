public class Plastic extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;
    public Plastic(double itemWeight) {
        super("Plastic", itemWeight);
    }

    @Override
    public void compress() {
        // Implement the compress logic for Plastic
        this.isCompressed = true;
    }
}