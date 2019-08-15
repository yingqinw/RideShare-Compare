<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String error = (String)request.getAttribute("loginError");
	if(error==null || error.trim().length()==0) {   
		error = "";
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="login.css" >
		<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600|Open+Sans:300,400,600" rel="stylesheet">    
		<title>Login Page</title>
		
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
		
		<div id="errorDiv"> 
			<h6 id="error" style="text-align: center; color: red; font-size: 100%; margin: 1% 0% 0% 0%; position: relative;"> <%=error %> </h6>
		</div>
		
		<div id="loginForm">
			<form action="Login" method="POST" >  <!-- onsubmit="return validate(this);"  --> 
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
				<div id="submitButton"> 
					<img src="${pageContext.request.contextPath}/images/loginKey.png"> 
					<input name="submit" type="submit" value="Login" /> 
					<br> 
				</div> 
			</form>
		</div>
		<div>
	
		</div>
		
<!-- 		<div id="hidden-login" style="visibility:hidden;">
			<form action="Login" method="GET">
				<input type="text" name="username-hid" id="username-hid" value="">
				<input type="password" name="password-hid" id="password-hid" value="" >
				<input type="submit" name="loginsubmit" id="loginsubmit">
			</form>
		</div> -->
		
		<footer> Created by Annie, Charlie, Eli and Steven </footer>
		
		<script type="text/javascript"> 
          
            // Function to check Whether both passwords is same or not. 
            /* function validate(form) { 
            	username = document.getElementById("usernameValue").value; 
                password = document.getElementById("passwordValue").value; 
  
                if(username.trim() == "") {
                	if(password == "") {
                		document.getElementById("error").innerHTML = "Please enter a username and a password to login. ";
                	}
                	else {
                		document.getElementById("error").innerHTML = "Please enter a username to login. ";
                	}
                	return false; 
                }
                // If password not entered 
                else if (password == "")  {
                	//alert ("Please enter Password"); 
                	document.getElementById("error").innerHTML = "Please enter a password to login. ";
                	return false; 
                }
                else{
                	
                	document.getElementById("username-hid").value = username;
                	document.getElementById("password-hid").value = password;
                	document.getElementById('loginsubmit').click();
                   // alert("Successful login! - Welcome to RideShare Compare!") 
                    return true; 
                } 
            }  */
        </script> 
	</body>
</html>