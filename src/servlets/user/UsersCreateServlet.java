package servlets.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import services.UsersServices;

public class UsersCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		
		String login= request.getParameter("login");
		String mdp= request.getParameter("mdp");
		String nom = request.getParameter("nom");
		String prenom= request.getParameter("prenom");
		String email = request.getParameter("email");
		
		try {
			 json1= UsersServices.createUSer(login,mdp, nom, prenom, email); 
			
		} catch (JSONException e) {
		    	e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
		}	
		
		
out.print(json1);		
	}
}
