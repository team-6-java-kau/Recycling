import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer experienceYears;
    Double tiredness;
    Integer itemsDone;
    Integer errorsNum;
    private static final String[] NAMES = {
        "Liam", "Emma", "Noah", "Olivia", "William", "Ava", 
        "James", "Isabella", "Salman", "Sophia"
    };

    public Employee(Integer employeeNumber, Double workingHours, String name, Integer experienceYears) {
        this.employeeNumber = employeeNumber;
        this.workingHours = workingHours;
        this.name = name;
        this.experienceYears = experienceYears;
        this.tiredness = 0.0;
        this.itemsDone = 0;
        this.errorsNum = 0;
    }
    

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Double getTiredness() {
        return tiredness;
    }

    public void setTiredness(Double tiredness) {
        this.tiredness = tiredness;
    }

    public Integer getItemsDone() {
        return itemsDone;
    }

    public void setItemsDone(Integer itemsDone) {
        this.itemsDone = itemsDone;
    }

    public void incrementItemsDone() {
        this.itemsDone++;
    }  
    public void incrementerrorsNum() {
        this.errorsNum++;
    }

    public double calculateSortTime(Recyclableitem recyclable) {
        Random random = new Random();
        double baseTime = 2.0; // Base time to sort an item

        double experienceModifier = 1.0 - (experienceYears * (random.nextDouble() * 0.03)); // Up to 3% faster per year
        double tirednessModifier = 1.0 + (tiredness * (random.nextDouble() * 0.1)); // Up to 10% slower per tiredness level

        double randomFactor = 0.8 + random.nextDouble() * 0.4; // Random factor between 0.8 and 1.2 for variability

        double totalModifier = experienceModifier * tirednessModifier * randomFactor;
        double sortTime = baseTime * totalModifier;
        recyclable.set_time_to_finish(sortTime);
        return sortTime;
    }

    public boolean sort(Recyclableitem recyclable) {
        Random random = new Random();
        double sortTime = calculateSortTime(recyclable);

        workingHours += sortTime;
        tiredness += sortTime * 0.1; // Increment tiredness

        double errorChance = Math.min(0.3, tiredness * (random.nextDouble() * 0.05)); // Up to 30% error chance depending on tiredness
        boolean hasError = random.nextDouble() < errorChance;

        recyclable.setisDone_sorting(true);

        incrementItemsDone();
        if (hasError) {
            recyclable.setsortingError(true);
        }

        return !hasError;
    }

    // Method to create a list of Employees with random attributes
    public static List<Employee> createEmployees(int number) {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            String name = NAMES[random.nextInt(NAMES.length)];
            int experienceYears = random.nextInt(10); // 0 to 10 years of experience
            double workingHours = random.nextDouble() * 8; // Random working hours between 0 to 8

            Employee employee = new Employee(i + 1, workingHours, name, experienceYears);
            employees.add(employee);
        }

        return employees;
    }

}

/* 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer experienceYears;
    Double tiredness;
    Integer itemsDone;
    Integer errorsNum;
    private static final String[] NAMES = {
        "Liam", "Emma", "Noah", "Olivia", "William", "Ava", 
        "James", "Isabella", "Salman", "Sophia"
    };

    public Employee(Integer employeeNumber, Double workingHours, String name, Integer experienceYears) {
        this.employeeNumber = employeeNumber;
        this.workingHours = workingHours;
        this.name = name;
        this.experienceYears = experienceYears;
        this.tiredness = 0.0;
        this.itemsDone = 0;
        this.errorsNum = 0;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Double getTiredness() {
        return tiredness;
    }

    public void setTiredness(Double tiredness) {
        this.tiredness = tiredness;
    }

    public Integer getItemsDone() {
        return itemsDone;
    }

    public void setItemsDone(Integer itemsDone) {
        this.itemsDone = itemsDone;
    }

    public void incrementItemsDone() {
        this.itemsDone++;
    }

    public void incrementerrorsNum() {
        this.errorsNum++;
    }

    public double calculateSortTime(Recyclableitem recyclable) {
        Random random = new Random();
        double baseTime = 2.0; // Base time to sort an item

        double experienceModifier = 1.0 - (experienceYears * (random.nextDouble() * 0.03)); // Up to 3% faster per year
        double tirednessModifier = 1.0 + (tiredness * (random.nextDouble() * 0.1)); // Up to 10% slower per tiredness level

        double randomFactor = 0.8 + random.nextDouble() * 0.4; // Random factor between 0.8 and 1.2 for variability

        double totalModifier = experienceModifier * tirednessModifier * randomFactor;
        double sortTime = baseTime * totalModifier;
        recyclable.set_time_to_finish(sortTime);
        return sortTime;
    }

    public boolean sort(Recyclableitem recyclable) {
        Random random = new Random();
        double sortTime = calculateSortTime(recyclable);

        workingHours += sortTime;
        tiredness += sortTime * 0.1; // Increment tiredness

        double errorChance = Math.min(0.3, tiredness * (random.nextDouble() * 0.05)); // Up to 30% error chance depending on tiredness
        boolean hasError = random.nextDouble() < errorChance;

        recyclable.setisDone_sorting(true);

        incrementItemsDone();
        if (hasError) {
            recyclable.setsortingError(true);
        }

        return !hasError;
    }

    // Method to create a list of Employees with random attributes
    public static List<Employee> createEmployees(int number) {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            String name = NAMES[random.nextInt(NAMES.length)];
            int experienceYears = random.nextInt(11); // 0 to 10 years of experience
            double workingHours = random.nextDouble() * 8; // Random working hours between 0 to 8

            Employee employee = new Employee(i + 1, workingHours, name, experienceYears);
            employees.add(employee);
        }

        return employees;
    }
}
*/