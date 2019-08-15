package finalProject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class CurrentLocationThread extends Thread {
	private String url;
	public double latitude;
	public double longitude;
	
	public CurrentLocationThread(String ip) {
		this.url = "http://api.ipstack.com/" + ip + "?access_key=8affc49a09d973969ec334aabe2385a6";
		StringBuilder result = new StringBuilder();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
		         result.append(line);
		    }
			rd.close();
			
			String jsonString = result.toString();
			JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
			
			latitude = Double.parseDouble(jobj.get("latitude").toString());
			longitude = Double.parseDouble(jobj.get("longitude").toString());
			System.out.println(latitude);
			System.out.println(longitude);
		}
		catch(Exception e) {
		}
	}
	
	public void run() {
		StringBuilder result = new StringBuilder();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
		         result.append(line);
		    }
			rd.close();
			
			String jsonString = result.toString();
			JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
			
			latitude = Double.parseDouble(jobj.get("latitude").toString());
			longitude = Double.parseDouble(jobj.get("longitude").toString());
		}
		catch(Exception e) {
		}
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		CurrentLocationThread t = new CurrentLocationThread("128.125.148.116");
//		t.start();
//	}
}