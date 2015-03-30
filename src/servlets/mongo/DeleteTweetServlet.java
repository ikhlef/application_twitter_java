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

public class DeleteTweetServlet extends HttpServlet {
	
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		
		String key= request.getParameter("key");
		String idob= request.getParameter("id");
		
        try{
        	json1= MongoServices.deleteTweet(key, idob);
       	 
        	out.println(json1.toString());
        } catch (JSONException e) {
	    	e.printStackTrace();
	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
