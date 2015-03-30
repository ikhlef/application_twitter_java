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

public class SearchServlet extends HttpServlet {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		String cle= request.getParameter("key");
		String query= request.getParameter("query");
		String friends= request.getParameter("friends");
		try{
        	json1= MongoServices.search(cle, query, friends);
        	out.println(json1);
        } catch (JSONException e) {
	    	e.printStackTrace();
	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
