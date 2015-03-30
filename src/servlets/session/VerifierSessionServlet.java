package servlets.session;

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

public class VerifierSessionServlet extends HttpServlet {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		String token= request.getParameter("key");
		
		try {
			out.println("voila je test la verifie");
			 boolean a= LoginServices.verifier_session(token); 
			json1.put("a", a); 
		} catch (JSONException e) {
		    	e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
out.println(json1.toString());		
	}

}
