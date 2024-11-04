public class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer experienceYears;
    private Double tiredness;
    private Integer itemsDone;

    public Employee(Integer employeeNumber, Double workingHours, String name,
                    Integer experienceYears) {
        this.employeeNumber = employeeNumber;
        this.workingHours = 0.0;
        this.name = name;
        this.experienceYears = experienceYears;
        this.tiredness = 0.0;
        this.itemsDone = 0;
    }


    public void sort() {
        
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
}