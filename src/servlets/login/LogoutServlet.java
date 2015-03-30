package servlets.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.LoginServices;

public class LogoutServlet extends HttpServlet {

	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		
		String logout= request.getParameter("logout");
		
		try {
			 json1= LoginServices.logout( logout); 
				
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
out.print(json1);		
	}
}
