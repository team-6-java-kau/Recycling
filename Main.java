import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Recyclableitem> items = Recyclableitem.createList(12);
        Scanner input = new Scanner(System.in);
        Integer experience_input = input.nextInt();
        Integer Timescalse = input.nextInt();


        input.close();

        Employee sorter = new Employee(1, 5.0, "Moha", experience_input);
        Employee distributor = new Employee(2, 5.0, "Spotty", 3); // Create a distribution employee

        Factory one = new Factory();
        one.setTimescale(Timescalse);
        one.manual(items, sorter, distributor); // Pass both employees


        System.out.println("Errors encountered: " + sorter.errorsNum);
    }
}
