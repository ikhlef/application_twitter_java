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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import services.LoginServices;

public class MongoServlet extends HttpServlet {
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JSONObject json1= new JSONObject();
     Mongo m = new Mongo("132.227.201.129",27130);
     DB db = m.getDB("blaszkiewicz_ikh");
     DBCollection coll = db.getCollection("moi");
     DBObject doc = new BasicDBObject();
     
     doc.put("nom", "mohamed");
     doc.put("adresse", "rue de panama 75016 panama");
     coll.insert(doc);
       DBCursor cur = coll.find();
     try{
       while(cur.hasNext()){
    	   DBObject doc1 = cur.next();
    	   json1.put("resultat de coll", doc1);
    	   out.println(json1.toString());
			//json1.put("hello", cur.next());
       }
       cur.close();
      m.close();
       } catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
	//	}
 catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
      
     // } catch (JSONException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	//}
		
		

//out.println(json1.toString());		
	}
}
