package servlets.mongo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.MongoServices;

public class LikedislikeverifierServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		String cle= request.getParameter("key");
		String idob= request.getParameter("ido");
		try{
        	int d= MongoServices.verifierliketweet(cle, idob);
        	json1.put("love", d);
        	out.println(json1);
        } catch (JSONException e) {
	    	e.printStackTrace();
	}
}


}
