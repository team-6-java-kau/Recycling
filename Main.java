import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Recyclableitem> items = Recyclableitem.createList(30);
        Employee muhammed = new Employee(1, 5.0, "moha", 0);

        Factory one =new Factory();
        one.manual(items, muhammed);
        if (items != null && !items.isEmpty()) {
            System.out.println("List created successfully with " + items.size() + " items.");
            for (Recyclableitem item : items) {
                System.out.println(item.getItemType() + " - " +  item.getsortingError() + " - " + item.get_time_to_finish());            }
        } else {
            System.out.println("Failed to create list.");
        }
       
        
        System.out.println(muhammed.errorsNum);

    }
}
