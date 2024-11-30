public class Metal extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;
    public Metal(double itemWeight) {
        super("Metal", itemWeight);
    }

    @Override
    public void compress() {
        // Implement the compress logic for Plastic
        this.isCompressed = true;
    }
}