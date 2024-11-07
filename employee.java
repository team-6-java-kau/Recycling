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
        double baseTime = 2.0;
        double experienceModifier = Math.abs(1.0 - (experienceYears * 0.05)); // Up to 20% faster per year
        double tirednessModifier = 1.0 + (workingHours * 0.01); // Up to 10% slower per hour of work

        double totalModifier = experienceModifier * tirednessModifier;
        double sort_time = baseTime * totalModifier;
        recyclable.set_time_to_finish(sort_time);
        return baseTime * totalModifier;
    }

    public boolean sort(Recyclableitem recyclable) {
        double sortTime = calculateSortTime(recyclable);
        workingHours += sortTime;
        tiredness += sortTime * 0.2;

        double errorChance = tiredness * 0.05; 
        boolean hasError = Math.random() < errorChance;

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