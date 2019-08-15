package finalProject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesh = request.getSession(false);
		sesh.setAttribute("username", null);
		sesh.setAttribute("threadLatitude", null);
		sesh.setAttribute("threadLongitude", null);
		ActiveThreads a = (ActiveThreads) sesh.getAttribute("server");
		if (a != null)
			a.removeThread((CurrentLocationThread)sesh.getAttribute("currentThread"));
		
		sesh.setAttribute("currentThread", null);
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/homePage.jsp");
		dispatch.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
