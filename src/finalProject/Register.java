    
package finalProject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import finalProject.SQL;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("confirmPassword");
		
		//System.out.println(username);
		if(!password.equals(password2)) {
			request.setAttribute("registerError", "Passwords did not match.");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
			dispatch.forward(request, response);
		}
		else if(password.trim().equals("") || password2.trim().equals("") || username.trim().equals("")) {
			request.setAttribute("registerError", "Please don't use mass blankspace.");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
			dispatch.forward(request, response);
		}
		else if(SQL.userExist(username)) {
			request.setAttribute("registerError", "User already exists.");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
			dispatch.forward(request, response);
		}
		else {
			SQL.register(username, password);
			HttpSession session = request.getSession(false);
	  
	        // Find public IP address 
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
			
			session.setAttribute("username", username);
			session.setAttribute("threadLatitude", c.latitude);
			session.setAttribute("threadLongitude", c.longitude);
			session.setAttribute("currentThread", c);
			
			ActiveThreads a = (ActiveThreads) session.getAttribute("server");
			if (a != null)
				a.addThread(c);
			Socket s = new Socket("localhost", 6789);
			
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/homePage.jsp");
			dispatch.forward(request, response);
		}
	}
}
