import java.util.Random;

public class Sorter extends Employee {
    public Sorter(Integer employeeNumber, Double workingHours, String name) {
        super(employeeNumber, workingHours, name);
    }

    public void setExperienceYears(int experienceYears) {
        super.setExperienceYears(experienceYears);
    }

    @Override
    public double calculateSortTime(Recyclableitem recyclable) {
        Random random = new Random();
        double baseTime = 5.0; // Base time to sort an item

        // Calculate experience modifier
        double experienceModifier = 1.0 - (getExperienceYears() * (random.nextDouble() * 0.1)); 
        // Calculate tiredness modifier
        double tirednessModifier = 1.0 + (getTiredness() * (random.nextDouble() * 0.1)); 
        // Random factor for variability
        double randomFactor = 0.8 + random.nextDouble() * 0.4; 

        // Calculate total modifier
        double totalModifier = experienceModifier * tirednessModifier * randomFactor;
        double sortTime = baseTime * totalModifier;

        // Ensure sort time is not less than 1.0
        if (sortTime < 1.0) { 
            sortTime = Math.max(sortTime, 1.0); 
            sortTime += 0.1 + (random.nextDouble() * 0.8); 
        }

        recyclable.set_time_to_sort(sortTime);
        return sortTime;
    }

    @Override
    public boolean sort(Recyclableitem recyclable) {
        Random random = new Random();
        double sortTime = calculateSortTime(recyclable);

        // Update working hours and tiredness
        setWorkingHours(getWorkingHours() + sortTime);
        setTiredness(getTiredness() + sortTime * 0.1); 

        // Calculate error chance
        double errorChance = Math.min(0.3, (getTiredness() * 0.05) - (getExperienceYears() * 0.01)); 
        boolean hasError = random.nextDouble() < errorChance;
        sortTime = Math.round(sortTime * 10.0) / 10.0;

        if (hasError) {
            recyclable.setsortingError(true);
        }

        return !hasError;
    }

    @Override
    public double calculateDistributeTime(Recyclableitem item) {
        throw new UnsupportedOperationException("Sorter does not implement distributeItem");
    }

    @Override
    public void distributeItem(Recyclableitem item) {
        throw new UnsupportedOperationException("Sorter does not implement distributeItem");
    }
}