public class Plastic extends Recyclableitem implements Compressible {
    private boolean isCompressed = false;
    public Plastic(double itemWeight) {
        super("Plastic", itemWeight);
    }
    @Override
    public void compress() {
        isCompressed = true;
    }


    @Override
    public boolean isCompressed() {
        return isCompressed;
    }
}