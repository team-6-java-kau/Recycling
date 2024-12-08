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

        // Calculate weight modifier
        double weightModifier = 1.0 + (item.getItemWeight() * 0.2); 
        // Calculate tiredness modifier
        double tirednessModifier = 1.0 + (getTiredness() * 0.05); 
        // Random factor for less variability
        double randomFactor = 0.9 + random.nextDouble() * 0.1; 
        int compress = 0;
        if (item instanceof Compressible) {
            Compressible compressible = (Compressible) item;
            if (!compressible.isCompressed()) {
                compress = 1 + random.nextInt(3); // Random time between 1 and 3 seconds
            }
        }
        // Calculate total modifier
        double totalModifier = weightModifier * tirednessModifier * randomFactor;
        double distributeTime = baseTime * totalModifier + compress;
        distributeTime = Math.round(distributeTime * 10.0) / 10.0; // Format to one decimal place
        item.set_time_to_distribute(distributeTime);
        return distributeTime;
    }

    @Override
    public void distributeItem(Recyclableitem item) {
        double distributeTime = calculateDistributeTime(item);

        // Update working hours and tiredness
        setWorkingHours(getWorkingHours() + distributeTime);
        setTiredness(getTiredness() + distributeTime * 0.3); 
        incrementItemsDone();
    }
}