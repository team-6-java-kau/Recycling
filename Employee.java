public abstract class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer experienceYears;
    private Double tiredness;
    private Integer itemsDone;
    private Integer errorsNum;

    // Constructor to initialize an employee
    public Employee(Integer employeeNumber, Double workingHours, String name) {
        this.employeeNumber = employeeNumber;
        this.workingHours = workingHours;
        this.name = name;
        this.experienceYears = 0;
        this.tiredness = 0.0;
        this.itemsDone = 0;
        this.errorsNum = 0;
    }

    // Getter and setter methods for employeeNumber
    public Integer getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(Integer employeeNumber) { this.employeeNumber = employeeNumber; }

    // Getter and setter methods for workingHours
    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }

    // Getter and setter methods for name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Getter and setter methods for experienceYears
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { 
        this.experienceYears = experienceYears; 
        System.out.println("Experience years updated to: " + experienceYears + "----" + getTiredness()); 
    }

    // Getter and setter methods for tiredness
    public Double getTiredness() { return tiredness; }
    public void setTiredness(Double tiredness) { this.tiredness = tiredness; }

    // Getter and setter methods for itemsDone
    public Integer getItemsDone() { return itemsDone; }
    public void setItemsDone(Integer itemsDone) { this.itemsDone = itemsDone; }

    // Increment the number of items done by the employee
    public void incrementItemsDone() { this.itemsDone++; }

    // Increment the number of errors made by the employee
    public void incrementerrorsNum() { this.errorsNum++; }

    // Abstract methods to be implemented by subclasses
    public abstract double calculateSortTime(Recyclableitem recyclable);
    public abstract boolean sort(Recyclableitem recyclable);
    public abstract double calculateDistributeTime(Recyclableitem item);
    public abstract void distributeItem(Recyclableitem item);
}

