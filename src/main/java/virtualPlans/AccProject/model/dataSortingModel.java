package virtualPlans.AccProject.model;

public class dataSortingModel {
    private String companyName;
    private String planName;
    private String priceWithDollar; // Store price as a String with $
    private double price; // Store numerical price for sorting
    private String billingCycle;
    private int maxUsers;
    private String features;

    // Constructor
    public dataSortingModel(String companyName, String planName, String priceWithDollar, double price, String billingCycle, int maxUsers, String features) {
        this.companyName = companyName;
        this.planName = planName;
        this.priceWithDollar = priceWithDollar;
        this.price = price;
        this.billingCycle = billingCycle;
        this.maxUsers = maxUsers;
        this.features = features;
    }

    // Getters and setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPriceWithDollar() {
        return priceWithDollar;
    }

    public void setPriceWithDollar(String priceWithDollar) {
        this.priceWithDollar = priceWithDollar;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
