
import java.util.Random;

public class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer experienceYears;
    Double tiredness;
    Integer itemsDone;
    Integer errorsNum;
  

    public Employee(Integer employeeNumber, Double workingHours, String name, Integer experienceYears) {
        this.employeeNumber = employeeNumber;
        this.workingHours = workingHours;
        this.name = name;
        this.experienceYears = experienceYears;
        this.tiredness = 0.0;
        this.itemsDone = 0;
        this.errorsNum = 0;
    }

    public Integer getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(Integer employeeNumber) { this.employeeNumber = employeeNumber; }
    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public Double getTiredness() { return tiredness; }
    public void setTiredness(Double tiredness) { this.tiredness = tiredness; }
    public Integer getItemsDone() { return itemsDone; }
    public void setItemsDone(Integer itemsDone) { this.itemsDone = itemsDone; }
    
    public void incrementItemsDone() { this.itemsDone++; }
    public void incrementerrorsNum() { this.errorsNum++; }

    public double calculateSortTime(Recyclableitem recyclable) {
        Random random = new Random();
        double baseTime = 5.0; // Base time to sort an item

        double experienceModifier = 1.0 - (experienceYears * (random.nextDouble() * 0.1)); // Up to 10% faster per year
        double tirednessModifier = 1.0 + (tiredness * (random.nextDouble() * 0.1)); // Up to 10% slower per tiredness level

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

    public boolean sort(Recyclableitem recyclable) {
        Random random = new Random();
        double sortTime = calculateSortTime(recyclable);

        workingHours += sortTime;
        tiredness += sortTime * 0.1; // Increment tiredness

        double errorChance = Math.min(0.3, (tiredness * 0.05) - (experienceYears * 0.01)); // Error depends on tiredness and experience
        boolean hasError = random.nextDouble() < errorChance;
        sortTime = Math.round(sortTime * 10.0) / 10.0;
        recyclable.setisDone_sorting(true);

        incrementItemsDone();
        if (hasError) {
            recyclable.setsortingError(true);
        }

        return !hasError;
    }
    public double calculateDistributeTime(Recyclableitem item) {
        Random random = new Random();
        double baseTime = 3.0; // Base time to distribute an item, more than sorting time

        double weightModifier = 1.0 + (item.getItemWeight() * 0.2); // Increase time based on weight
        double tirednessModifier = 1.0 + (tiredness * 0.05); // Increase time based on tiredness
        double randomFactor = 0.9 + random.nextDouble() * 0.1; // Random factor between 0.9 and 1.0 for less variability

        double totalModifier = weightModifier * tirednessModifier * randomFactor;
        double distributeTime = baseTime * totalModifier;
        distributeTime = Math.round(distributeTime * 10.0) / 10.0; // Format to one decimal place
        item.set_time_to_distribute(distributeTime);
        return distributeTime;
    }

    public void distributeItem(Recyclableitem item) {
        double distributeTime = calculateDistributeTime(item);
        System.out.println(getName() + " is distributing " + item.getItemType() + " to the appropriate path. It will take " + distributeTime + " seconds.");

        workingHours += distributeTime;
        tiredness += distributeTime * 0.3; // Increment tiredness
        incrementItemsDone();

        switch (item.getItemType()) {
            case "Plastic":
                System.out.println(getName() + " distributed the Plastic path.");                
                break;
            case "Metal":
                System.out.println(getName() + " distributed the Metal path.");                
                break;
            case "Glass":
                System.out.println(getName() + " distributed the Glass path.");                
                break;
            case "Paper":
                System.out.println(getName() + " distributed the Paper path.");                
                break;
            default:
                System.out.println(getName() + " encountered an unknown item type: " + item.getItemType());
                break;
        }
     }
    }
  