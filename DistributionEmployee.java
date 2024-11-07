public class DistributionEmployee extends Employee {
    public DistributionEmployee(Integer employeeNumber, Double workingHours, String name, Integer experienceYears) {
        super(employeeNumber, workingHours, name, experienceYears);
    }

    public void distributeItem(Recyclableitem item) {
        if (item.getsortingError()) {
            System.out.println(getName() + " cannot distribute " + item.getItemType() + " due to sorting error.");
            return;
        }

        switch (item.getItemType()) {
            case "Plastic":
                System.out.println(getName() + " is distributing " + item.getItemType() + " to the Plastic path.");
                break;
            case "Metal":
                System.out.println(getName() + " is distributing " + item.getItemType() + " to the Metal path.");
                break;
            case "Glass":
                System.out.println(getName() + " is distributing " + item.getItemType() + " to the Glass path.");
                break;
            case "Paper":
                System.out.println(getName() + " is distributing " + item.getItemType() + " to the Paper path.");
                break;
            default:
                System.out.println(getName() + " encountered an unknown item type: " + item.getItemType());
                break;
        }
    }
}