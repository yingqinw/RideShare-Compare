package finalProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username + " hello there.");
		String nextpage = "/homePage.jsp";
		
		boolean user = SQL.login(username, password);
		System.out.println(user);
		
		if(user) {
			HttpSession session = request.getSession(false);
			session.setAttribute("username", username);
			
			String hello = (String)session.getAttribute("username");
			System.out.println(hello);
			
			String systemipaddress = ""; 
	        try
	        { 
	            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
	  
	            BufferedReader sc = 
	            new BufferedReader(new InputStreamReader(url_name.openStream())); 
	  
	            // reads system IPAddress 
	            systemipaddress = sc.readLine().trim(); 
	        } 
	        catch (Exception e) 
	        { 
	            systemipaddress = "Cannot Execute Properly"; 
	        } 
			CurrentLocationThread c = new CurrentLocationThread(systemipaddress);
			c.start();
			session.setAttribute("threadLatitude", c.latitude);
			session.setAttribute("threadLongitude", c.longitude);
			session.setAttribute("currentThread", c);
			ActiveThreads a = (ActiveThreads) session.getAttribute("server");
			if (a != null)
				a.addThread(c);
			
			Socket s = new Socket("localhost", 6789);
			
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextpage);
			dispatch.forward(request, response);
		}
		
		else {
			nextpage = "/login.jsp";
			request.setAttribute("loginError", "Incorrect username or password. Please try again! :)");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextpage);
			dispatch.forward(request, response);
		}
	}
}