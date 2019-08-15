<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "finalProject.ActiveThreads"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%

ActiveThreads at = new ActiveThreads(6789);
at.start();
HttpSession sesh = request.getSession(false);
sesh.setAttribute("server", at);

RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/homePage.jsp");
dispatch.forward(request, response);

%>

</body>
</html>