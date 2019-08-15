<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="finalProject.Uber, finalProject.Lyft, java.util.List"%>
<!DOCTYPE html>

<%
List<Uber> ubers = (List<Uber>)session.getAttribute("ubers");
List<Lyft> lyfts = (List<Lyft>)session.getAttribute("lyfts");
Uber shortestuber =(Uber)session.getAttribute("shortestuber");
Uber cheapestuber =(Uber)session.getAttribute("cheapestuber");
Lyft shortestlyft =(Lyft)session.getAttribute("shortestlyft");
Lyft cheapestlyft =(Lyft)session.getAttribute("cheapestlyft");

String startLocation = (String)session.getAttribute("startLocation");
String endLocation = (String)session.getAttribute("endLocation");

HttpSession sesh = request.getSession(false);
String username = null;
if(sesh.getAttribute("username") != null) {
	username = (String)sesh.getAttribute("username");
}
else {
	username = "";
}

%>

<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600|Open+Sans:300,400,600" rel="stylesheet">  
		<link rel="stylesheet" type="text/css" href="results.css" >
		<title>Results Page</title>
	</head>  
	
	<body>
		<div id="outerMost"> 
			<div id="webpageBackground">
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
				 	<%if(!username.equals("")) { %>
						<a href="profile.jsp" id="profile"> PROFILE <img src="${pageContext.request.contextPath}/images/user.png"> </a> 	
						<a href="homePage.jsp" id="signout" onclick="LogOut()"> SIGNOUT <img src="${pageContext.request.contextPath}/images/logout.png"> </a> 
					<%} %>
					<%if(username.equals("")) { %>
						<a href="login.jsp" id="login"> LOGIN <img src="${pageContext.request.contextPath}/images/login.png"> </a> 	
						<a href="register.jsp" id="register"> REGISTER <img src="${pageContext.request.contextPath}/images/register.png"></a> 
					<%} %>
				</div>        
				
				<div id="searchForm">   
					<div id="googleMap">
						<input id="mapIcon" type="image" src="${pageContext.request.contextPath}/images/map.png" onclick="mapOn()"/> 
					</div> 
					<div id="initialDes"> 
						<h3> Initial Location: </h3> <h4> <%=startLocation %> </h4>
						<input type="text" id="startLocation" value="<%=startLocation %>" style="display: none">
						<br> 
					</div>
					<div id="finalDes"> 
						<h3> Destination: </h3> <h4> <%=endLocation %> </h4>  
						<input type="text" id="endLocation" value="<%=endLocation %>" style="display: none"> 
						<br> 
					</div>  
				</div>     
				<div id="uberLyftIcons">   
					<div id="uberIcon">
						<img src="${pageContext.request.contextPath}/images/uber.png">    
					</div>
					
					<div id="lyftIcon">
						<img src="${pageContext.request.contextPath}/images/lyft.png">    
					</div>
				</div>
				<div id="tableContainer">    
					<div id="dynamicUberTable">         
						<table>  
							<thead>
								<tr class="tableHeading">  
								    <th>Vehicle Type</th>
								    <th>Waiting Time</th>
								    <th>Price Estimate</th> 
							 	</tr>
						 	</thead>       
						 	<tbody>
							 	<%for(Uber uber : ubers) {%>
								<tr>
								<%if(username.equals("")){ %>
									<td><%= uber.getVehicleType()%></td>
								<%} %>
								<%if(!username.equals("")){ %>
									<td><form action="SaveRide" method="GET"><button class="Searchname" name="Searchname" type="submit" value="<%= uber.getVehicleType()%>|0"><%= uber.getVehicleType()%></button></form></td>
								<%} %>
									<td><%= uber.getTimeEstimate()%> Minutes</td>
									<td><%= uber.getPriceEstimate()%></td>
								</tr>
							<%}%>  
						 	</tbody>      
						 </table>
						 <div class="cheapest">	
							 <h6>Cheapest Price: <%=cheapestuber.getVehicleType()%> </h6><br>
							 <h6>Shortest Waiting Time: <%=shortestuber.getVehicleType()%> </h6>
						 </div>  
					</div>

					<div id="dynamicLyftTable"> 
						<table>
							<thead>
								<tr class="tableHeading">  
								    <th>Vehicle Type</th>
								    <th>Waiting Time</th>
								    <th>Price Estimate</th>
							 	</tr>
							 </thead>
							 <tbody>
								 <%for(Lyft lyft : lyfts) {%>
								<tr>
								<%if(username.equals("")){ %>
									<td><%= lyft.getVehicleType()%></td>
								<%} %>
								<%if(!username.equals("")){ %>
									<td><form action="SaveRide" method="GET"><button class="Searchname" name="Searchname" type="submit" value="<%= lyft.getVehicleType()%>|1"><%= lyft.getVehicleType()%></button></form></td>
								<%} %>
									<td><%= lyft.getTimeEstimate()%> Minutes</td>
									<td><%= lyft.getPriceEstimate()%></td>
								</tr>
							<%}%>
							 </tbody>
						 </table>
						 <div class="cheapest">	
							 <h6> Cheapest Price: <%=cheapestlyft.getVehicleType()%> </h6> <br>
							 <h6> Shortest Waiting Time: <%=shortestlyft.getVehicleType()%> </h6>
						 </div>
					</div>
				</div>
				
				<footer> Created by Annie, Charlie, Eli and Steven </footer>
			</div>
			
			<div id="mapContainer" >
				<div id="map" onclick="mapOff();"> </div>
			</div>
		</div>
		
		<script>
		function saveRide() {
			
		}
		</script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script type="text/javascript"  async>
		function initMap() {
		    map = new google.maps.Map(document.getElementById('map'), {
		      center: { lat: 37.7749, lng: -122.4194 },
		      zoom: 10
		    });
		    var directions = new google.maps.DirectionsService();
		    var renderer = new google.maps.DirectionsRenderer();
		    // Get the to and from addresses
		    var address = document.getElementById('startLocation').value;
		    var destination = document.getElementById('endLocation').value;
		    // Draw the directions
		    directions.route({
		      origin: address,
		      destination: destination,
		      travelMode: 'DRIVING'
		    }, function (result, status) {
		      if (status == 'OK') {
		        renderer.setMap(map);
		        renderer.setDirections(result);
		      }
		    });
		  }
		
		function mapOff() {
			document.getElementById("mapContainer").style.display = "none";
			document.getElementById("webpageBackground").style.opacity = "1";
			document.getElementById("webpageBackground").style.filter= "brightness(100%)";
		}
		
		function mapOn() {
			initMap();
			document.getElementById("mapContainer").style.display = "inline-flex";
			document.getElementById("mapContainer").style.justifyContent = "center";
			document.getElementById("webpageBackground").style.filter= "brightness(30%)";
		}
		</script>
		<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAaODN8BfiLaVS7586DIEwayLiCIuVBWzw&callback=initMap"> </script>
		<script>
				function LogOut() {
					var xhttp = new XMLHttpRequest();    
					xhttp.open("GET", "Logout", false);   //go to the logout servlet
					xhttp.send(); 
				}
		</script>
	</body>
</html>