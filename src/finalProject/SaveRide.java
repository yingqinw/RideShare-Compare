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

/**
 * Servlet implementation class SaveRide
 */
@WebServlet("/SaveRide")
public class SaveRide extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username=(String)session.getAttribute("username");
		List<Uber> ubers = (List<Uber>)session.getAttribute("ubers");
		List<Lyft> lyfts = (List<Lyft>)session.getAttribute("lyfts");
		String Searchname=request.getParameter("Searchname");
		String delims = "[|]";
		String[] tokens = Searchname.split(delims);
		String VehicleType = tokens[0];
		int company = Integer.parseInt(tokens[1]);
		String date = SQL.getTime();
		String startLocation = (String)session.getAttribute("startLocation");
		String endLocation = (String)session.getAttribute("endLocation");
		double price = 0;
		if(company==0) {
			for(Uber uber : ubers) {
				if(uber.getVehicleType().equals(VehicleType)) {
					price = uber.getMaxPrice();
					break;
				}
			}
		}
		if(company==1) {
			for(Lyft lyft : lyfts) {
				if(lyft.getVehicleType().equals(VehicleType)) {
					price = lyft.getMaxPrice();
					break;
				}
			}
		}
		Rides myRide = new Rides(VehicleType,date, startLocation, endLocation, price);
		SQL.saveRide(username, myRide);
		String nextPage ="/profile.jsp";
		RequestDispatcher dispatch =getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request,  response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
