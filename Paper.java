public class Paper extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;
    public Paper(double itemWeight) {
        super("Paper", itemWeight);
    }

    @Override
    public void compress() {
        // Implement the compress logic for Plastic
        this.isCompressed = true;
    }
}