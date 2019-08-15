<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="finalProject.SQL" %>
<%@ page import="finalProject.Rides" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>

<%
String friendUsername = "";
ArrayList<Rides> rideHistory = new ArrayList<Rides>();

HttpSession sesh = request.getSession(false);
if(sesh.getAttribute("friendUsername") != null) {
	friendUsername = (String)sesh.getAttribute("friendUsername");
}
if(friendUsername==null || friendUsername.trim().length()==0) {   
	friendUsername = "";
}
if(SQL.userExist(friendUsername)) {
	System.out.println("hello");
	rideHistory = SQL.getRides(friendUsername);
}

%>

<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600|Open+Sans:300,400,600" rel="stylesheet">    
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="friendProfile.css" >
		   
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
		<title><%=friendUsername %>'s Profile Page</title>
		
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
		
		<div id="icon"> <img src="${pageContext.request.contextPath}/images/pastridesicon.png"> </div>
		
		<h3 id="tableTitle"> <%=friendUsername %>'s Past Rides </h3>
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
		
	</body>
</html>