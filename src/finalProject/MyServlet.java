package finalProject;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String startLat=request.getParameter("startLat");
		String startLong=request.getParameter("startLong");
		String endLat=request.getParameter("endLat");
		String endLong=request.getParameter("endLong");
		String startLocation=request.getParameter("startLocation");
		String endLocation=request.getParameter("endLocation");
		session.setAttribute("startLocation", startLocation);
		session.setAttribute("endLocation", endLocation);
		
		String nextPage ="/results.jsp";
		
		double myLat = Double.parseDouble(startLat);
		double myLong = Double.parseDouble(startLong);
		double destLat = Double.parseDouble(endLat);
		double destLong = Double.parseDouble(endLong);
		List<Uber> ubers = RideUtils.getAvailableUbers(myLat,myLong,destLat,destLong);
		Uber shortestuber = RideUtils.getShortestUber(ubers);
		Uber cheapestuber = RideUtils.getCheapestUber(ubers);
		
		session.setAttribute("ubers", ubers);
		session.setAttribute("shortestuber", shortestuber);
		session.setAttribute("cheapestuber", cheapestuber);
		
		
		List<Lyft> lyfts = RideUtils.getAvailableLyfts(myLat,myLong,destLat,destLong);
		Lyft shortestlyft = RideUtils.getShortestLyft(lyfts);
		Lyft cheapestlyft = RideUtils.getCheapestLyft(lyfts);
		session.setAttribute("lyfts", lyfts);
		session.setAttribute("shortestlyft", shortestlyft);
		session.setAttribute("cheapestlyft", cheapestlyft);
		
		RequestDispatcher dispatch =getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
