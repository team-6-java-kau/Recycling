public class Metal extends Recyclableitem implements Compressible {
    public Metal(double itemWeight) {
        super("Metal", itemWeight);
    }

    @Override
    public void compress() {
        // Implement the compress logic for Metal
        System.out.println("Compressing Metal");
    }
}