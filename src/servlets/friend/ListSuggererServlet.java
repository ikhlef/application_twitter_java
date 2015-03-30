package servlets.friend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.FriendsServices;

public class ListSuggererServlet extends HttpServlet {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		
		String key= request.getParameter("key");
		try {
			 json1= FriendsServices.listeSuggerer(key);			 
				
		} catch (JSONException e) {
		    	e.printStackTrace();
		} 
		
out.println(json1);		
	}
	
}
