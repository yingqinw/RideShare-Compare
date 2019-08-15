package finalProject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SQL {
	private static String SQLpassword = "root";
	public static boolean userExist (String username) { //returns a boolean for if a user with the username exists or not

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); //prepare statement is for user input, use statement if not
			ps.setString(1, username); 
			rs = ps.executeQuery(); 


			if (!rs.next()) {
				return false;
			}


		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (ps != null) { ps.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}

		return true;
	}

	//---------------------------------------------------------------------------------------------

	public static boolean login(String username, String password) { //queries the database to authenticate a user login
		boolean foundUser = false;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); 
			ps.setString(1, username); 
			rs = ps.executeQuery(); //gets the user


			if (!rs.next()) {
				return foundUser;
			}
			String pswd = rs.getString("password"); //checks the password
			if (pswd.equals(password)) {
				foundUser = true;
			}

		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (ps != null) { ps.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}

		return foundUser;
	}

	//----------------------------------------------------------------------------------------

	public static void register(String username, String password) { //registers the user to the database
		//ideally we will have used userExists to check before this whether a user of that username exists.
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			String sql = "INSERT INTO users (username,password) VALUES ('" + username + "','" + password + "')";
			ps = conn.prepareStatement(sql); //prepare statement is for user input, use statement if not
			ps.execute(); 

		}
		catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (ps != null) { ps.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}

	}

	//------------------------------------------------------------------------------------------

	public static void sendFriendRequest (String sendUser, String receiveUser) { //adds a friend request for "receiveUser" from "sendUser"
		//we will have used userExist before this to check if the user they want to friend is an actual user

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); 
			ps.setString(1, receiveUser); 
			rs = ps.executeQuery(); 
			System.out.println(receiveUser);

			rs.next();


			String fr1 = rs.getString("frequest1");
			String fr2 = rs.getString("frequest2");
			String fr3 = rs.getString("frequest3");
			String fr4 = rs.getString("frequest4");

			String sql = null;

			if (fr1 == null) {
				sql = "UPDATE users SET frequest1 = '" + sendUser + "' WHERE username = '" + receiveUser + "'";
			}
			else if (fr2 == null) {
				sql = "UPDATE users SET frequest2 = '" + sendUser + "' WHERE username = '" + receiveUser + "'";
			}
			else if (fr3 == null) {
				sql = "UPDATE users SET frequest3 = '" + sendUser + "' WHERE username = '" + receiveUser + "'";
			}
			else if (fr4 == null) {		
				sql = "UPDATE users SET frequest4 = '" + sendUser + "' WHERE username = '" + receiveUser + "'";
			}

			else {
				sql = "UPDATE users SET frequest1 = '" + fr2 + "', frequest2 = '" + fr3 + "', frequest3 = '" + fr4 + "', frequest4 = '" + sendUser + "' WHERE username = '" + receiveUser + "'";
				//a "cycle" kind of way of keeping track of oldest friends request.
			}
			System.out.println("here4");
			ps2 = conn.prepareStatement(sql);
			ps2.execute();


		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (ps != null) { ps.close(); }
				if (ps2 != null) { ps2.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}
	}

	//-----------------------------------------------------------------------------------------------

	public static void acceptFriendRequest (String sendUser, String receiveUser, boolean accept) { //if "receiveUser" has a friend request from "sendUser", then the friend request is removed and they become friends
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		//String sUser = sendUser;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);

			ps = conn.prepareStatement("SELECT * FROM users WHERE username=? OR username=?"); //will get receiving-user info
			ps.setString(1, receiveUser); 
			ps.setString(2, sendUser);
			rs = ps.executeQuery(); 

			rs.next();
			int firstID = rs.getInt("userID"); //gets IDs of the users to add to friends table

			rs.next();
			int secondID = rs.getInt("userID");
			

			if (accept) {
				String sqlFriend = "INSERT INTO friends (user1ID,user2ID) VALUES ('" + firstID + "', '" + secondID + "')";
				ps3 = conn.prepareStatement(sqlFriend); //adds them to the friends table
				ps3.execute();
			}
			
			ps4 = conn.prepareStatement("SELECT * FROM users WHERE username='" + receiveUser + "'");
			rs2 = ps4.executeQuery();
			
			rs2.next();
			
			String fr1 = rs2.getString("frequest1"); //gets friend request data so we can remove the friend request they accepted
			
			String fr2 = rs2.getString("frequest2");
			String fr3 = rs2.getString("frequest3");
			String fr4 = rs2.getString("frequest4");

			String sql = null;

			if (fr1.equals(sendUser)) {

				sql = "UPDATE users SET frequest1 = NULL WHERE username = '" + receiveUser + "'";
			}
			else if (fr2.equals(sendUser)) {

				sql = "UPDATE users SET frequest2 = NULL WHERE username = '" + receiveUser + "'";
			}
			else if (fr3.equals(sendUser)) {

				sql = "UPDATE users SET frequest3 = NULL WHERE username = '" + receiveUser + "'" ;
			}
			else if (fr4.equals(sendUser)) {

				sql = "UPDATE users SET frequest4 = NULL WHERE username = '" + receiveUser + "'";
			}

			ps2 = conn.prepareStatement(sql); //sets the old friend request to null
			ps2.execute();

			


		} catch (SQLException sqle) {
			System.out.println("sqle19: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (rs2 != null) { rs2.close(); }
				if (ps != null) { ps.close(); }
				if (ps2 != null) { ps2.close(); }
				if (ps3 != null) { ps3.close(); }
				if (ps4 != null) { ps4.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}
	}


	public static Set<String> getFriendRequests(String username) { //returns an arraylist that contains all the usernames of the friendrequests of the user
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Set<String> friends = new HashSet<String>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); 
			ps.setString(1, username); 
			rs = ps.executeQuery(); 

			rs.next();

			String fr1 = rs.getString("frequest1");
			String fr2 = rs.getString("frequest2");
			String fr3 = rs.getString("frequest3");
			String fr4 = rs.getString("frequest4");

			if (fr1 != null) {
				friends.add(fr1);
			}
			if (fr2 != null) {
				friends.add(fr2);
			}
			if (fr3 != null) {
				friends.add(fr3);
			}
			if (fr4 != null) {
				friends.add(fr4);
			}

		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (ps != null) { ps.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}
		
		return friends;
	}

	public static ArrayList<String> getFriends(String username) { //returns an arraylist of all the usernames of the friends of the user
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ArrayList<String> friends = new ArrayList<String>();

		ArrayList<Integer> IDs = new ArrayList<Integer>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); 
			ps.setString(1, username); 
			rs = ps.executeQuery(); 

			rs.next();

			int ID = rs.getInt("userID");

			ps2 = conn.prepareStatement("SELECT * FROM friends WHERE user1ID='"+ID+"' OR user2ID='"+ ID + "'"); 
			rs2 = ps2.executeQuery();


			while(rs2.next()) {

				int user1ID = rs2.getInt("user1ID");
				if (ID == user1ID) { //the other ID is saved in user2ID
					IDs.add(rs2.getInt("user2ID"));

				}
				else {
					IDs.add(rs2.getInt("user1ID"));
				}
			}

			ps3 = conn.prepareStatement("SELECT * FROM users");
			rs3 = ps3.executeQuery();

			while (rs3.next()) {
				int userID = rs3.getInt("userID");
				for (int i = 0; i < IDs.size(); i++) {
					if (IDs.get(i) == userID) {
						friends.add(rs3.getString("username"));
						break;
					}
				}

			}

		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (rs2 != null) { rs2.close(); }
				if (ps != null) { ps.close(); }
				if (ps2 != null) { ps2.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}
		
		return friends;
	}
	
	//------------------------------------------------------------------

	public static ArrayList<Rides> getRides(String username) {
		ArrayList<Rides> userRides = new ArrayList<Rides>();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); 
			ps.setString(1, username); 
			rs = ps.executeQuery(); //gets the user

			rs.next();
			int userID = rs.getInt("userID");

			ps2 = conn.prepareStatement("SELECT * FROM rides WHERE userID=" + userID);
			rs2 = ps2.executeQuery();

			while (rs2.next()) {
				//Rides newRide  = new Rides();
				String startLoc = rs2.getString("startLocation");
				String endLoc = rs2.getString("endLocation");
				String time = rs2.getString("ridetime");
				double price = rs2.getDouble("price");
				String company = rs2.getString("company"); // 0 if uber, 1 if lyft

				Rides newRide = new Rides(company, time, startLoc, endLoc, price);
				userRides.add(newRide);
			}

		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (rs2 != null) {rs2.close(); }
				if (ps != null) { ps.close(); }
				if (ps2 != null) {ps2.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}

		return userRides;
	}

	//----------------------------------------------------------------------

	public static void saveRide(String username, Rides myRide) {
		String company = myRide.getCompany();
		String startLoc = myRide.getStartLoc();
		String endLoc = myRide.getEndLoc();
		String date = myRide.getDate();
		double price = myRide.getPrice();

		//String sqlFriend = "INSERT INTO friends (user1ID,user2ID) VALUES ('" + firstID + "', '" + secondID + "')";

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		//ResultSet rs2 = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rideshare?user=root&password=" + SQLpassword);
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?"); 
			ps.setString(1, username); 
			rs = ps.executeQuery(); //gets the user

			rs.next();

			int userID = rs.getInt("userID");

			String insertStatement = "INSERT INTO rides (company, ridetime, startLocation, endLocation, price, userID) VALUES "
					+ "('" + company + "', '" + date + "', '" + startLoc + "', '" + endLoc + "', '" + price + "', '" + userID + "' )";

			ps2 = conn.prepareStatement(insertStatement);
			//rs2 = ps2.executeQuery();
			ps2.execute();

		} catch (SQLException sqle) {
			System.out.println("sqle1: " + sqle.getMessage());
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (ps != null) { ps.close(); }
				if (ps2 != null) {ps2.close(); }
				if (conn != null) { conn.close(); }
			} catch (SQLException sqle) {
				System.out.println("sqle2: " + sqle.getMessage());
			}
		}


	}

	//----------------------------------------------------------------------

	public static String getTime() { //use this on the servlet to get the current date and time as a String formatted correctly
		String time = null;
		LocalDateTime now = LocalDateTime.now();

		//now should be in the form yyyy-mm-ddThh:mm:ss.nsnsns
		DateTimeFormatter format = DateTimeFormatter.ofPattern("KK:mm  dd MMM uuuu");
		//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM uuuu  KK:mm");

		time = now.format(format);
		return time;
	}

}
