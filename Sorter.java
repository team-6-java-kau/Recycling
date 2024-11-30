
import java.util.Random;

public class Sorter extends Employee {
    public Sorter(Integer employeeNumber, Double workingHours, String name, Integer experienceYears) {
        super(employeeNumber, workingHours, name, experienceYears);
    }

    @Override
    public double calculateSortTime(Recyclableitem recyclable) {
        Random random = new Random();
        double baseTime = 5.0; // Base time to sort an item

        double experienceModifier = 1.0 - (getExperienceYears() * (random.nextDouble() * 0.1)); // Up to 10% faster per year
        double tirednessModifier = 1.0 + (getTiredness() * (random.nextDouble() * 0.1)); // Up to 10% slower per tiredness level

        double randomFactor = 0.8 + random.nextDouble() * 0.4; // Random factor between 0.8 and 1.2 for variability

        double totalModifier = experienceModifier * tirednessModifier * randomFactor;
        double sortTime = baseTime * totalModifier;
        if (sortTime < 1.0) { 
            sortTime = Math.max(sortTime, 1.0); // Ensure minimum time is 1 second
            sortTime += 0.1 + (random.nextDouble() * 0.8); // Add random number between 0.1 and 0.9
        }
        recyclable.set_time_to_sort(sortTime);
        return sortTime;
    }

    @Override
    public boolean sort(Recyclableitem recyclable) {
        Random random = new Random();
        double sortTime = calculateSortTime(recyclable);

        setWorkingHours(getWorkingHours() + sortTime);
        setTiredness(getTiredness() + sortTime * 0.1); // Increment tiredness

        double errorChance = Math.min(0.3, (getTiredness() * 0.05) - (getExperienceYears() * 0.01)); // Error depends on tiredness and experience
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