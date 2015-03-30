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

public class RemoveFriendServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		
		String key= request.getParameter("key");
		String idf=request.getParameter("id");
		int friend = Integer.parseInt(idf); 
		try {
			 json1= FriendsServices.removeFriends(key,friend);			 
				
		} catch (JSONException e) {
		    	e.printStackTrace();
		} 
		
out.println(json1);		
	}


}
