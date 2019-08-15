package finalProject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FriendProfileServlet
 */
@WebServlet("/FriendProfileServlet")
public class FriendProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String friendUsername = request.getParameter("friendUsername");
		
		HttpSession sesh = request.getSession(false);
		sesh.setAttribute("friendUsername", friendUsername);
		
		String hello = (String)sesh.getAttribute("friendUsername");
		System.out.println(hello);
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/friendProfile.jsp");
		dispatch.forward(request, response);
	}

}
