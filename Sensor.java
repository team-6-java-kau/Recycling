public class Sensor extends Employee {
    private Integer sensorNumber;
    private Double workingHours;
    private Integer itemsDone;
    private Integer errorsNum;

    public Sensor(Integer employeeNumber, Double workingHours, String name) {
        super(employeeNumber, workingHours, name);
        this.itemsDone = 0;
        this.errorsNum = 0;
    }

    // Getters and setters
    public Integer getSensorNumber() { return sensorNumber; }
    public void setSensorNumber(Integer sensorNumber) { this.sensorNumber = sensorNumber; }
    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }
    public Integer getItemsDone() { return itemsDone; }
    public void setItemsDone(Integer itemsDone) { this.itemsDone = itemsDone; }
    public Integer getErrorsNum() { return errorsNum; }
    public void setErrorsNum(Integer errorsNum) { this.errorsNum = errorsNum; }

    // Increment methods
    public void incrementItemsDone() { this.itemsDone++; }
    public void incrementErrorsNum() { this.errorsNum++; }

    @Override
    public double calculateSortTime(Recyclableitem recyclable) {
        double sortTime = 0.1; // Fixed sort time for sensors
        recyclable.set_time_to_sort(sortTime);
        return sortTime;
    }

    @Override
    public boolean sort(Recyclableitem recyclable) {
        double sortTime = calculateSortTime(recyclable);
        setWorkingHours(sortTime);
        incrementItemsDone();
        return true; // Sensors do not produce errors
    }

    @Override
    public double calculateDistributeTime(Recyclableitem item) {
        double distributeTime = 0.1; // Fixed distribute time for sensors
        item.set_time_to_distribute(distributeTime);
        return distributeTime;
    }

    @Override
    public void distributeItem(Recyclableitem item) {
        double distributeTime = calculateDistributeTime(item);
        setWorkingHours(distributeTime);
        incrementItemsDone();
    }
}
