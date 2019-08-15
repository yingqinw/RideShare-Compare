package finalProject;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {
	 public static List<Uber> getAvailableUbers(String timeResponse, String priceResponse) {
	        List<Uber> ubers = new ArrayList<>();

	        try {
	            // get Json Arrays
	            JSONObject timeObject = new JSONObject(timeResponse);

	            JSONArray timeArray = timeObject.getJSONArray("times");
	            JSONArray priceArray = new JSONArray();
	            if(!priceResponse.equals("")) {
	                JSONObject priceObject = new JSONObject(priceResponse);
	                priceArray = priceObject.getJSONArray("prices");
	            }

	            // loop through time Array
	            for (int i = 0; i < timeArray.length(); ++i) {
	                JSONObject currentObject = timeArray.getJSONObject(i);
	                Uber currentUber = new Uber();
	                currentUber.setVehicleType(currentObject.getString("localized_display_name"));
	                String eta = Integer.toString(currentObject.getInt("estimate"));
	                if (eta != null && !eta.equals("null")) {
	                    Double timeEstimate = currentObject.getDouble("estimate");
	                    currentUber.setTimeEstimate((int) timeEstimate.doubleValue() / 60);
	                } else {
	                    currentUber.setTimeEstimate(-1);
	                }
	                ubers.add(currentUber);
	            }

	            // loop through priceArray
	            for (int i = 0; i < priceArray.length(); ++i) {
	                JSONObject currentObject = priceArray.getJSONObject(i);
	                String currentDisplayName = currentObject.getString("localized_display_name");
	                boolean foundMatch = false;
	                for (Uber uber : ubers) {
	                    if (uber.getVehicleType().equals(currentDisplayName)) {
	                        foundMatch = true;
	                        String price = currentObject.getString("estimate");
	                        if (price != null && !price.equals("null")) {
	                            String priceEstimate = currentObject.getString("estimate");
	                            uber.setPriceEstimate(priceEstimate);
	                        } else {
	                            uber.setPriceEstimate(null);
	                        }

	                        String high = Double.toString(currentObject.getDouble("high_estimate"));
	                        if (high != null && !high.equals("null")) {
	                            Double priceMax = currentObject.getDouble("high_estimate");
	                            uber.setMaxPrice(priceMax.doubleValue());
	                        } else {
	                            uber.setMaxPrice(-1);
	                        }
	                        break;
	                    }
	                }
	                if (!foundMatch) {
	                    Uber newUber = new Uber();
	                    newUber.setVehicleType(currentDisplayName);
	                    String price = currentObject.getString("estimate");
	                    if (price != null && !price.equals("null")) {
	                        String priceEstimate = currentObject.getString("estimate");
	                        newUber.setPriceEstimate(priceEstimate);
	                    } else {
	                        newUber.setPriceEstimate(null);
	                    }

	                    String high = currentObject.getString("high_estimate");
	                    if (high != null && !high.equals("null")) {
	                        Double priceMax = currentObject.getDouble("high_estimate");
	                        newUber.setMaxPrice(priceMax.doubleValue());
	                    } else {
	                        newUber.setMaxPrice(-1);
	                    }
	                    newUber.setTimeEstimate(-1);
	                }
	            }
	            for (Uber uber : ubers) {
	                if(uber.getPriceEstimate().equals("")) {
	                    uber.setPriceEstimate("N/A");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return ubers;
	    }


	    public static List<Lyft> getAvailableLyfts(String timeResponse, String priceResponse) {
	        List<Lyft> lyfts = new ArrayList<>();

	        try {
	            // get Json Arrays
	            JSONObject timeObject = new JSONObject(timeResponse);
	            JSONArray timeArray = timeObject.getJSONArray("eta_estimates");
	            JSONArray priceArray = new JSONArray();
	            if(!priceResponse.equals("")) {
	                JSONObject priceObject = new JSONObject(priceResponse);
	                priceArray = priceObject.getJSONArray("cost_estimates");
	            }

	            // loop through time Array
	            for (int i = 0; i < timeArray.length(); ++i) {
	                JSONObject currentObject = timeArray.getJSONObject(i);
	                Lyft currentLyft = new Lyft();
	                currentLyft.setVehicleType(currentObject.getString("display_name"));
	                String eta = Double.toString(currentObject.getDouble("eta_seconds"));
	                if(eta != null && !eta.equals("null")) {
	                    Double timeEstimate = currentObject.getDouble("eta_seconds");
	                    currentLyft.setTimeEstimate((int)timeEstimate.doubleValue()/60);
	                } else {
	                    currentLyft.setTimeEstimate(-1);
	                }
	                lyfts.add(currentLyft);
	            }

	            // loop through priceArray
	            for (int i = 0; i < priceArray.length(); ++i) {
	                JSONObject currentObject = priceArray.getJSONObject(i);
	                boolean foundMatch = false;
	                String currentDisplayName = currentObject.getString("display_name");
	                for (Lyft lyft: lyfts) {
	                    if (lyft.getVehicleType().equals(currentDisplayName)) {
	                        foundMatch = true;
	                        String objMin = Double.toString(currentObject.getDouble("estimated_cost_cents_min"));
		                    String objMax = Double.toString(currentObject.getDouble("estimated_cost_cents_max"));
	                        if(objMin != null && !objMin.equals("null") && objMax != null && !objMax.equals("null")) {
	                            Double priceEstimateMin = currentObject.getDouble("estimated_cost_cents_min");
	                            Double priceEstimateMax = currentObject.getDouble("estimated_cost_cents_max");
	                            lyft.setPriceEstimate("$" + priceEstimateMin/100 + "-" + priceEstimateMax/100);
	                            lyft.setMaxPrice(priceEstimateMax.doubleValue());
	                        } else {
	                            lyft.setPriceEstimate(null);
	                            lyft.setMaxPrice(-1);
	                        }
	                        break;
	                    }
	                }
	                if (!foundMatch) {
	                    Lyft newLyft = new Lyft();
	                    newLyft.setVehicleType(currentDisplayName);
	                    String objMin = currentObject.getString("estimated_cost_cents_min");
                        String objMax = currentObject.getString("estimated_cost_cents_max");
	                    if(objMin != null && !objMin.equals("null") && objMax != null && !objMax.equals("null")) {
	                        Double priceEstimateMin = currentObject.getDouble("estimated_cost_cents_min");
	                        Double priceEstimateMax = currentObject.getDouble("estimated_cost_cents_max");
	                        newLyft.setPriceEstimate("$" + priceEstimateMin/100 + "-" + priceEstimateMax/100);
	                        newLyft.setMaxPrice(priceEstimateMax.doubleValue());
	                    } else {
	                        newLyft.setPriceEstimate(null);
	                        newLyft.setMaxPrice(-1);
	                    }
	                    newLyft.setTimeEstimate(-1);
	                }
	            }
	            for (Lyft lyft : lyfts) {
	                if(lyft.getPriceEstimate().equals("")) {
	                    lyft.setPriceEstimate("N/A");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return lyfts;
	    }

}
