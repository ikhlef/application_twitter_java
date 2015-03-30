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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import services.MongoServices;

public class AddCommentServlet extends HttpServlet {
	
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
		String key_com= request.getParameter("key");
		String text= request.getParameter("text");
		String idob= request.getParameter("idobj");
        try{
        	json1= MongoServices.addCommentaire( key_com, text,idob);
        	out.println(json1);
        } catch (JSONException e) {
	    	e.printStackTrace();
	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
