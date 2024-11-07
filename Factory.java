import java.util.List;

public class Factory {
    public void manual(List<Recyclableitem> recyclables, Employee sortingEmployee, DistributionEmployee distributionEmployee) {
        for (Recyclableitem recyclable : recyclables) {
            boolean success = sortingEmployee.sort(recyclable);
            if (!success) {
                sortingEmployee.incrementerrorsNum();
            } else {
                // Distribute the item after sorting
                distributionEmployee.distributeItem(recyclable);
            }
        }
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }
}