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
 * Servlet implementation class SendFriend
 */
@WebServlet("/SendFriend")
public class SendFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendFriend() {
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
		HttpSession sesh = request.getSession(false);
		String sendUser = (String)sesh.getAttribute("username");
		String receiveUser = request.getParameter("receiveUser");
		
		if (SQL.userExist(sendUser) && SQL.userExist(receiveUser)) {
			SQL.sendFriendRequest(sendUser, receiveUser);
			sesh.setAttribute("error", null);
			//add request.setAttribute("RequestSuccess", "Request Sent!")
		}
		else {
			sesh.setAttribute("error", "User does not exist. Please try again.");
		}
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/profile.jsp");
		dispatch.forward(request, response);
	}

}
