import java.util.Random;

public abstract class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer experienceYears;
    private Double tiredness;
    private Integer itemsDone;
    private Integer errorsNum;

    public Employee(Integer employeeNumber, Double workingHours, String name) {
        this.employeeNumber = employeeNumber;
        this.workingHours = workingHours;
        this.name = name;
        this.experienceYears = 0;
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

    public abstract double calculateSortTime(Recyclableitem recyclable);
    public abstract boolean sort(Recyclableitem recyclable);
    public abstract double calculateDistributeTime(Recyclableitem item);
    public abstract void distributeItem(Recyclableitem item);
}

