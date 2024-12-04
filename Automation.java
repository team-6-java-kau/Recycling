
public class Automation {
    private Integer sensorNumber;
    private Double workingHours;
    private Integer itemsDone;
    private Integer errorsNum;

    public Automation(Integer sensorNumber, Double workingHours) {
        this.sensorNumber = sensorNumber;
        this.workingHours = workingHours;
        this.itemsDone = 0;
        this.errorsNum = 0;
    }

    public Integer getSensorNumber() { return sensorNumber; }
    public void setSensorNumber(Integer sensorNumber) { this.sensorNumber = sensorNumber; }
    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }
    public Integer getItemsDone() { return itemsDone; }
    public void setItemsDone(Integer itemsDone) { this.itemsDone = itemsDone; }
    public Integer getErrorsNum() { return errorsNum; }
    public void setErrorsNum(Integer errorsNum) { this.errorsNum = errorsNum; }

    public void incrementItemsDone() { this.itemsDone++; }
    public void incrementErrorsNum() { this.errorsNum++; }

    public double calculateSortTime(Recyclableitem recyclable) {
        double sortTime = 0.0;
        recyclable.set_time_to_sort(sortTime);
        return sortTime;
    }

    public void sort(Recyclableitem recyclable) {
        double sortTime = calculateSortTime(recyclable);
        setWorkingHours(getWorkingHours() + sortTime);
        incrementItemsDone();
    }

    public double calculateDistributeTime(Recyclableitem item) {
        double baseTime = 0.0; 
        double distributeTime = baseTime;
        item.set_time_to_distribute(distributeTime);
        return distributeTime;
    }

    public void distributeItem(Recyclableitem item) {
        double distributeTime = calculateDistributeTime(item);
        setWorkingHours(getWorkingHours() + distributeTime);
        incrementItemsDone();
    }
}
