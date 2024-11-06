import java.util.List;

public class Factory {

    public void manual(List<Recyclableitem> recyclables, Employee employee) {
        for (Recyclableitem recyclable : recyclables) {
            boolean success = employee.sort(recyclable);
           

            //System.out.println(recyclable.get_time_to_finish());
            // Handle success or error based on the return value
            if (!success) {
                employee.incrementerrorsNum();
            }

        }
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }
}
