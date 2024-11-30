public class Plastic extends Recyclableitem implements Compressible {
    public Plastic(double itemWeight) {
        super("Plastic", itemWeight);
    }

    @Override
    public void compress() {
        // Implement the compress logic for Plastic
        System.out.println("Compressing Plastic");
    }
}