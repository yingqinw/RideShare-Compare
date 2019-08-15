<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="homePage.css" >
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600|Open+Sans:300,400,600" rel="stylesheet">    
		<title>Home Page</title>
	<%
		HttpSession sesh = request.getSession(false);
    	String username = null;
    	if(sesh.getAttribute("username") != null) {
    		username = (String)sesh.getAttribute("username");
    	}
    	else {
    		username = "";
    	}
    	if (sesh.getAttribute("threadLatitude") == null)
    		sesh.setAttribute("threadLatitude", 30.2672);
    	if (sesh.getAttribute("threadLongitude") == null)
    		sesh.setAttribute("threadLongitude", -97.7431);
    	
    	
    	
    %>
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
					<%=username %>
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

				<div id="icon">
					<img src="${pageContext.request.contextPath}/images/icon2.png"/> 
					<br>
				</div>

				<div id="errorDiv"> 
					<h6 id="error" style="text-align: center; color: red; font-size: 100%; margin: 1% 0% 0% 0%; position: relative;">  </h6>
				</div>

				<form action="MyServlet">
					<input id="startLat" name="startLat" value="" style="display:none">
					<input id="startLong" name="startLong"  value="" style="display:none">
					<input id="endLat" name="endLat" value="" style="display:none">
					<input id="endLong" name="endLong" value="" style="display:none">
					<input id="startLocation" name="startLocation" value="" style="display:none">
					<input id="endLocation" name="endLocation" value="" style="display:none">
					<input type="submit" id="loginSubmit" style="display:none">
				</form>
				
				<form>
					<input type="hidden" id="hiddenLat" name="hiddenLat" value=<%= sesh.getAttribute("threadLatitude")%>>
					<input type="hidden" id="hiddenLong" name="hiddenLong" value=<%= sesh.getAttribute("threadLongitude")%>>
				</form>

				<div id="searchForm">
					<form>
						<div id="initialDes"> 
							<h3 > Initial Location </h3>  
							<input id="icon_start" name="initialDes" type="text" value="" placeholder="Enter a location" />
							<img src="${pageContext.request.contextPath}/images/locationicon.png">  
							<br> 
						</div>
						<div id="finalDes">   
							<h3> Destination </h3> 
							<input id="icon_end" name="finalDes" type="text" value="" placeholder="Enter a location" />  
							<img src="${pageContext.request.contextPath}/images/locationicon.png">  
							<br> 
						</div>       
						<div id="clearButton">          
							<img src="${pageContext.request.contextPath}/images/delete.png">    
							<button id="clear"> Clear </button>
						</div>
						<div id="submitButton"> 
							<img src="${pageContext.request.contextPath}/images/magglass.png">    
							<input id="submit" name="submit" type="submit" value="Check Price" onclick=""/> 
							<br> 
						</div> 
					</form>
				</div>
				<div id="sectionWhite" >
					<div id="map"></div>     
				</div>
				<footer> Created by Annie, Charlie, Eli and Steven </footer>

		<script>
			function changeHeader() {
				if (username != null) {
					console.log("here");
					document.getElementById("profile").style.display = "block";
					document.getElementById("signout").style.display = "block";
					document.getElementById("login").style.display = "none";
					document.getElementById("register").style.display = "none";
				}
			}
	
		</script>

			<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
			<script type="text/javascript"  async>
			var startLat;
			var startLong;
			var endLong;
			var endLat;
			var markers = [];
			var overuse = false;
		
			// Initialize Firebase
		
			function clearOverlays() {
				for (var i = 0; i < markers.length; i++ ) {
					markers[i].setMap(null);
				}
				markers.length = 0;
				overuse = false;
			};
			
			function clearInput(){
				$("#icon_start").val("");
				$("#icon_end").val("");
			}
		
			function initMap() {
				var latitude = 30.2672; // YOUR LATITUDE VALUE
		        var longitude = -97.7431; // YOUR LONGITUDE VALUE
				var myLatLng = {lat: latitude, lng: longitude};
		
				console.log(myLatLng);
		
				var map = new google.maps.Map(document.getElementById("map"), {
					zoom: 14,
					center: myLatLng
				});
		        
				 var geocoder = new google.maps.Geocoder();
				  // If the navigator successfully loaded, then center the map and prefill start location
				  if (navigator.geolocation) {
				    navigator.geolocation.getCurrentPosition(function (position) {
				      var pos = {
			    		  lat: parseFloat(document.getElementById("hiddenLat").value),
					      lng: parseFloat(document.getElementById("hiddenLong").value)
				      };
				      map.setCenter(pos);
				      geocoder.geocode({
				        location: {
				        	lat: parseFloat(document.getElementById("hiddenLat").value),
					        lng: parseFloat(document.getElementById("hiddenLong").value)
				        }
				      }, function (results, status) {
				        if (status === 'OK') {
				          $("#icon_start").val(results[0].formatted_address);
				        }
				      });
				    }, function () {
				      console.log("Location Retrieval Failed");
				    });
				  }
				console.log(document.getElementById("hiddenLat").value);
				console.log(document.getElementById("hiddenLong").value);
				map.addListener('click', function(e) {
					if(!overuse) placeMarker(e.latLng, map);
				});
		
				function placeMarker(position, map) {
						var marker = new google.maps.Marker({
						position: position,
						map: map
					});
		
					map.panTo(position);
		
					markers.push(marker);
					console.log(markers);
		
					var markerStartLat = marker.getPosition().lat();
					var markerStartLong = marker.getPosition().lng();
					var geocoder = new google.maps.Geocoder();
					geocoder.geocode({
				        location: {
				          lat: markerStartLat,
				          lng: markerStartLong
				        }
				      }, function (results, status) {
				        if (status === 'OK') {
				        	if(markers.length==1){
				        		$("#icon_start").val(results[0].formatted_address);
				        	}
				        	if(markers.length==2){
				        		$("#icon_end").val(results[0].formatted_address);
				        	}
				        }
				      });
					if(markers.length==2){
						overuse = true;
					}
		
					console.log(marker.getPosition().lat());
					console.log(marker.getPosition().lng());
					
				}
			}
			
		
			$("#submit").on("click", function(){
		
				event.preventDefault();	
				var startPoint = $("#icon_start").val().trim();
				console.log(startPoint);
				var endPoint = $("#icon_end").val().trim();
				console.log(endPoint);
			
				$.when(
					$.ajax({
						url: "https://maps.googleapis.com/maps/api/geocode/json?address=" + startPoint + "&key=AIzaSyCVUpJOQ43zmN_hdo0fHcY8aGZ7WacfBzg",
						method: 'GET'
					}).done(function(response){
						console.log(response);
						startLat = response.results[0].geometry.location.lat
						console.log(startLat);
						document.getElementById("startLat").value = startLat;
						startLong = response.results[0].geometry.location.lng 
						console.log(startLong);
						document.getElementById("startLong").value = startLong;
					}),
			
				//This is the API call for the destination
				$.ajax({
					url: "https://maps.googleapis.com/maps/api/geocode/json?address=" + endPoint + "&key=AIzaSyCVUpJOQ43zmN_hdo0fHcY8aGZ7WacfBzg",
					method: 'GET'
				}).done(function(response){
					console.log(response);
					endLat = response.results[0].geometry.location.lat
					console.log(endLat);
					document.getElementById("endLat").value = endLat;
					
					endLong = response.results[0].geometry.location.lng 
					console.log(endLong);
					document.getElementById("endLong").value = endLong;
				})
				).then(function (){
					document.getElementById("startLocation").value = startPoint;
					document.getElementById("endLocation").value = endPoint;
					document.getElementById('loginSubmit').click();	
				});
					
			});
			
			$("#clear").on("click", function(){
				event.preventDefault();
				clearOverlays();
				clearInput();
			});
		</script>
		<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDMxH14Pu6mSQn82RA5BEI-gs5ObHMpiHY&libraries=places&callback=initMap">
		console.log("ahhhh");
		</script>
		<script>
			function LogOut() {
				var xhttp = new XMLHttpRequest();    
				xhttp.open("GET", "Logout", false);   //go to the logout servlet
				xhttp.send(); 
			}
		</script>
	</body>
</html>