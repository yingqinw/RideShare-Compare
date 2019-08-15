<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="finalProject.SQL" %>
<%@ page import="finalProject.Rides" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.HashSet" %>
<%@page import="java.util.Iterator" %>
<!DOCTYPE html>

<%
String username = "";
String error = "";
ArrayList<String> friends = new ArrayList<String>();
Set<String> friendRequestSet = new HashSet<String>();
ArrayList<String> friendRequests = new ArrayList<String>();
ArrayList<Rides> rideHistory = new ArrayList<Rides>();

HttpSession sesh = request.getSession(false);
if(sesh.getAttribute("username") != null) {
	username = (String)sesh.getAttribute("username");
}
else if(username==null || username.trim().length()==0) {   
	username = "";
}
if(SQL.userExist(username)) {
	friends = SQL.getFriends(username);
	rideHistory = SQL.getRides(username);
	friendRequestSet = SQL.getFriendRequests(username);
	Iterator<String> itt = friendRequestSet.iterator();
	while(itt.hasNext()) {
		friendRequests.add(itt.next());
	}
	
	System.out.println(friendRequests.size());
}
if(sesh.getAttribute("error") != null) {
	error = (String)sesh.getAttribute("error");
}
else if(error == null || error.trim().length()==0) {   
	error = "";
}

%>

<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600|Open+Sans:300,400,600" rel="stylesheet">    
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="profile.css" >
		   
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
		<title>Profile Page</title>
		
		<script type="text/javascript"> 
			function addFriends() {
				if(document.getElementById("addFriendsUsername").style.display == 'none') {  
					document.getElementById("addFriendsUsername").style.display = "flex";
				}
				else {
					document.getElementById("addFriendsUsername").style.display = "none";
				}  
			} 
		</script>       
	</head>  
	<body>      
		<div id="background"> 
			 <img src="${pageContext.request.contextPath}/images/birds.jpg" /> 
		</div>
		<div id="topbar">
			<img src="${pageContext.request.contextPath}/images/top.png"/>   
		</div>
		          
		<div id="topleft">   
			<a href="homePage.jsp"> RideShare Compare </a>  
		</div>
		<div id="topright"> 
			<div class="dropdown">
			  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"> FRIEND REQUEST <span class="caret"></span></button>
			  <ul class="dropdown-menu">
				   <% for (int i = 0; i < friendRequests.size(); i++) { %>
					  <li style="margin-left: 2%;">
						<form action="acceptServlet" method="POST" style="display:inline-block; margin-bottom: 2%;">
						  <button id="frequest" name="frequest" value="<%=friendRequests.get(i) %>" style="font-weight: 400; color: white; background-color:#339933; border:none; border-radius: 5px;" type="submit"><%=friendRequests.get(i)%></button>
						</form>
						<form action="denyServlet" method="POST" style="display:inline-block; margin-bottom: 2%;"> 
						  <button id="denied" name="denied" value="<%=friendRequests.get(i) %>" style="font-weight: 400; color: white; background-color:#e60000; border:none; border-radius: 5px;" type="submit">Deny</button> 
						</form>
					  </li>
				   <% } %>          
			  </ul>
			</div>  
			<div class="dropdown">
			  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"> FRIEND LIST <span class="caret"></span></button>
			  <ul class="dropdown-menu">
			  <% for (int i = 0; i < friends.size(); i++) { %>
				<li>
					<form action="FriendProfileServlet" method="GET">
				  		<button id="friendUsername" name="friendUsername" type="submit" value="<%=friends.get(i)%>"> <%=friends.get(i) %> </button>
				  	</form> 
				</li> 
			  <% } %>  
			  </ul>
			</div>
			<button id="friends" onclick="addFriends()">  ADD FRIENDS <img src="${pageContext.request.contextPath}/images/friends.png"> </button> 	
			<a href="profile.jsp" id="profile">  PROFILE <img src="${pageContext.request.contextPath}/images/user.png"> </a> 	
			<a href="homePage.jsp" id="signout" onclick="LogOut()"> SIGNOUT  <img src="${pageContext.request.contextPath}/images/logout.png"></a> 
		</div>    
		
		<div id="addFriendsUsername">     
			<form action="SendFriend" method="POST" class="myForm" name="myForm" > 
				<input type="text" name="receiveUser" id="receiveUser" placeholder="Enter a username to send a friend Request!">
				<input id="submitButton" type="submit" value="Send Friend Request">
			</form>  
		</div>
		<div id="errorDiv"> 
			<h6 id="error" style="text-align: center; color: red; font-size: 110%; margin: 1% 0% 0% 30%; position: relative;"> <%=error%> </h6>
		</div>
		
		<div id="icon"> <img src="${pageContext.request.contextPath}/images/pastridesicon.png"> </div>
		
		<h3 id="tableTitle"> <%=username %>'s Past Rides </h3>
		<div id="tableContainer">             
			<div id="dynamicTable">        
				<table class="table table-striped table-bordered" id="resultTable">
					<thead>
						<tr id="tableHeading">  
						    <th>Date and Time</th>     
						    <th>Pick Up</th>
						    <th>Drop Off</th>
						    <th>Ride Type</th>   
					 	</tr>
				 	</thead>
				 	<tbody>     
				 		<%for(Rides ride: rideHistory) {%>
							<tr>
								<td><%= ride.getDate()%> </td>
								<td><%= ride.getStartLoc()%></td>
								<td><%= ride.getEndLoc()%></td>
								<td><%= ride.getCompany()%></td>  
							</tr>
						<%}%>  
					 </tbody>  
			 	</table>
			</div>
		</div>
		
		<footer> Created by Annie, Charlie, Eli and Steven </footer>
		
		<script>
			function LogOut() {
				var xhttp = new XMLHttpRequest();    
				xhttp.open("GET", "Logout", false);   //go to the logout servlet
				xhttp.send(); 
			}
		</script>
		
	</body>
</html>