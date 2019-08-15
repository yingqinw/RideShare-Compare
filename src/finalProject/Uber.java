package finalProject;

public class Uber {
	private String vehicleType;

    private int timeEstimate;

    private String priceEstimate;

    private double maxPrice;

    public Uber() {
        vehicleType = "";
        timeEstimate = 0;
        priceEstimate = "";
        maxPrice = 0;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public String getPriceEstimate() {
        return priceEstimate;
    }

    public void setPriceEstimate(String priceEstimate) {
        this.priceEstimate = priceEstimate;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        String asString = "Vechicle Type: " + vehicleType +
                "\nTime Estimate: " + timeEstimate +
                "\nPrice Estimate: " + priceEstimate;
        return asString;
    }

}
