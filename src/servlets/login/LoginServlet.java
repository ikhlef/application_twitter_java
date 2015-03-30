package servlets.login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.LoginServices;

public class LoginServlet extends HttpServlet {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		
		String login= request.getParameter("login");
		String mdp= request.getParameter("mdp");
		
		try {
		
			 json1= LoginServices.login( login, mdp); 
			//LoginServices.login( login, mdp);
				
		} catch (JSONException e) {
		    	e.printStackTrace();
		} 
		
out.print(json1);		
	}
}
