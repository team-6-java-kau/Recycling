public class Employee {
    private Integer employeeNumber;
    private Double workingHours;
    private String name;
    private Integer age;
    private Integer experienceYears;
    private Double tiredness;
    private String role;
    private Integer itemsDone;

    public Employee(Integer employeeNumber, Double workingHours, String name, Integer age,
                    Integer experienceYears, Double tiredness, String role, Integer itemsDone) {
        this.employeeNumber = employeeNumber;
        this.workingHours = workingHours;
        this.name = name;
        this.age = age;
        this.experienceYears = experienceYears;
        this.tiredness = tiredness;
        this.role = role;
        this.itemsDone = itemsDone;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getItemsDone() {
        return itemsDone;
    }

    public void setItemsDone(Integer itemsDone) {
        this.itemsDone = itemsDone;
    }
}