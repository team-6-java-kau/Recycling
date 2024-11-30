public class Paper extends Recyclableitem implements Compressible {
    public Paper(double itemWeight) {
        super("Paper", itemWeight);
    }

    @Override
    public void compress() {
        // Implement the compress logic for Paper
        System.out.println("Compressing Paper");
    }
}