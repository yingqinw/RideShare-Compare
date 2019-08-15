package finalProject;

public class Rides {
	private String company; 
	private String date;
	private String startLoc;
	private String endLoc;
	//private double startLat;
	//private double startLong;
	//private double endLat;
	//private double endLong;
	private double price;

	public Rides() {

	}

	public Rides(String company, String date, String startLoc, String endLoc , double price) { //this will be used on the results page when the user clicks on a class
		this.company = company;
		this.date = date;
		this.price = price;
		this.startLoc = startLoc;
		this.endLoc = endLoc;
	}


	public String getCompany() {
		return company;
	}

	public String getDate() {
		return date;
	}

	public String getStartLoc() {
		return startLoc;
	}

	public String getEndLoc() {
		return endLoc;
	}

	public double getPrice() {
		return price;
	}
}
