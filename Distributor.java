
import java.util.Random;

public class Distributor extends Employee {
    public Distributor(Integer employeeNumber, Double workingHours, String name) {
        super(employeeNumber, workingHours, name);
    }

    @Override
    public double calculateSortTime(Recyclableitem recyclable) {
        throw new UnsupportedOperationException("Distributor does not implement sort");
    }

    @Override
    public boolean sort(Recyclableitem recyclable) {
        throw new UnsupportedOperationException("Distributor does not implement sort");
    }

    @Override
    public double calculateDistributeTime(Recyclableitem item) {
        Random random = new Random();
        double baseTime = 3.0; // Base time to distribute an item, more than sorting time

        double weightModifier = 1.0 + (item.getItemWeight() * 0.2); // Increase time based on weight
        double tirednessModifier = 1.0 + (getTiredness() * 0.05); // Increase time based on tiredness
        double randomFactor = 0.9 + random.nextDouble() * 0.1; // Random factor between 0.9 and 1.0 for less variability
        int compress = 0;
        if (item instanceof Compressible) {
            Compressible compressible = (Compressible) item;
            if (!compressible.isCompressed()) {
                compress = 1 + random.nextInt(3); // Random time between 1 and 3 seconds }
                }
        }
        double totalModifier = weightModifier * tirednessModifier * randomFactor;
        double distributeTime = baseTime * totalModifier + compress;
        distributeTime = Math.round(distributeTime * 10.0) / 10.0; // Format to one decimal place
        item.set_time_to_distribute(distributeTime);
        return distributeTime;
    }

    @Override
    public void distributeItem(Recyclableitem item) {
        double distributeTime = calculateDistributeTime(item);

        setWorkingHours(getWorkingHours() + distributeTime);
        setTiredness(getTiredness() + distributeTime * 0.3); // Increment tiredness
        incrementItemsDone();
    }
}