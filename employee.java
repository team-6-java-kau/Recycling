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
        // Base time for sorting a simple recyclable
        double baseTime = 2.0;
    
        // Apply modifiers based on experience and tiredness
        double experienceModifier = 1.0 - (experienceYears * 0.05); // Up to 20% faster per year
        double tirednessModifier = 1.0 + (workingHours * 0.01); // Up to 10% slower per hour of work
    
        // Apply modifiers based on recyclable complexity (optional)
        // double complexityModifier = recyclable.getComplexity(); // Example
    
        // Combine modifiers
        
        double totalModifier = experienceModifier * tirednessModifier /* * complexityModifier */;
        double sort_time = baseTime * totalModifier;
        recyclable.set_time_to_finish(sort_time);
        return baseTime * totalModifier;
    }

    public boolean sort(Recyclableitem recyclable) {
        double sortTime = calculateSortTime(recyclable);

        // Simulate working hours and tiredness accumulation
        workingHours += sortTime;
        tiredness += sortTime * 0.2; // Increase tiredness based on sort time

        // Simulate potential errors based on tiredness (optional)
        double errorChance = tiredness * 0.05; // Up to 5% error chance per unit of tiredness
        boolean hasError = Math.random() < errorChance;

        // Update recyclable status
        recyclable.setisDone_sorting(true);

        // Increment items done and return success flag
        incrementItemsDone();
        if (hasError == true){
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


