<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String registerError = null;
	if (request.getAttribute("registerError") != null) {
		registerError = (String)request.getAttribute("registerError");
	}
	else {
		registerError = "";
	}
%>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="register.css" >
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600|Open+Sans:300,400,600" rel="stylesheet">    
		<title>Register Page</title>
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
			<a href="login.jsp" id="login">  LOGIN <img src="${pageContext.request.contextPath}/images/login.png"> </a> 	
			<a href="register.jsp" id="register"> REGISTER  <img src="${pageContext.request.contextPath}/images/register.png"></a> 
		</div>   

		<div id="icon">
			<img src="${pageContext.request.contextPath}/images/icon2.png"/>    
			<br>
		</div>

		<div id="errorDiv" > 
			<h6 id="error" style="text-align: center; color: red; font-size: 100%; margin: 1% 0% 0% 0%; position: relative;"><%=registerError %> </h6>
		</div>

		<div id="registerForm">
			<form action="Register" method="POST">
				<div id="username"> 
					<h3 > Username </h3>
					<input id="usernameValue" name="username" type="text" placeholder="Enter your username" />
					<br> 
				</div>
				<div id="password"> 
					<h3> Password </h3> 
					<input id="passwordValue" name="password" type="password" placeholder="Enter your password" />  
					<br>  
				</div> 
				<div id="confirmPassword">   
					<h3> Confirm Password </h3> 
					<input id="confirmPasswordValue" name="confirmPassword" type="password" placeholder="Please confirm your password" /> 
					<br>    
				</div>
				<div id="submitButton"> 
					<img src="${pageContext.request.contextPath}/images/registericon.png"> 
					<input name="submit" type="submit" value="Register" /> 
					<br> 
				</div> 
			</form>  
		</div>
		<footer> Created by Annie, Charlie, Eli and Steven </footer>

		<script type="text/javascript"> 
          
            // Function to check Whether both passwords is same or not. 
            function validate(form) { 
            	username = document.getElementById("usernameValue").value; 
                password1 = document.getElementById("passwordValue").value; 
                password2 = document.getElementById("confirmPasswordValue").value; 
  
                if(username.trim() == "") {
                	if(password1 == "") {
                		document.getElementById("error").innerHTML = "Please enter a username and a password to register. ";
                	}
                	else {
                		document.getElementById("error").innerHTML = "Please enter a username to register. ";
                	}
                	return false; 
                }
                // If password not entered 
                else if (password1 == "")  {
                	//alert ("Please enter Password"); 
                	document.getElementById("error").innerHTML = "Please enter a password to register. ";
                	return false; 
                }
                      
                // If confirm password not entered 
                else if (password2 == "") {
                	//alert ("Please enter confirm password"); 
                	document.getElementById("error").innerHTML = "Please enter confirm password to register. ";
                	return false; 
                }
                    
                // If Not same return False.     
                else if (password1 != password2) { 
                    //alert ("Password did not match: Please try again...") 
                    document.getElementById("error").innerHTML = "Passwords did not match - Please try again...";
                    return false; 
                } 
  
                // If same return True. 
                else{ 
                    alert("Password Match: Welcome to RideShare Compare!") 
                    return true; 
                } 
            } 
        </script> 
	</body>
</html>