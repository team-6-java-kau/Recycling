import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Recyclableitem> items = Recyclableitem.createList(30);
        Scanner input = new Scanner(System.in);
        Integer experience_input = input.nextInt();
        input.close();

        Employee muhammed = new Employee(1, 5.0, "Moha", experience_input);
        Employee distributor = new Employee(2, 5.0, "Sara", 3); // Create a distribution employee

        Factory one = new Factory();
        one.manual(items, muhammed, distributor); // Pass both employees

        if (items != null && !items.isEmpty()) {
            System.out.println("List created successfully with " + items.size() + " items.");
            for (Recyclableitem item : items) {
                System.out.println(item.getItemType() + " - " +  item.getsortingError() + " - " + item.get_time_to_sort() + " - " + item.getItemWeight());
            }
        } else {
            System.out.println("Failed to create list.");
        }

        System.out.println("Errors encountered: " + muhammed.errorsNum);
    }
}