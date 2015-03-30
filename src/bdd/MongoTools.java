package bdd;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoTools {
	
	public static DBObject findOneById(DBCollection coll, String id) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBObject dbObj = coll.findOne(query);		
		return dbObj;
	}

	public static DBCursor findById(DBCollection coll, String id) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBCursor dbCursor = coll.find(query);
		return dbCursor;
	}
	
	/*
   public static List<DBObject> getListComment	(DBCollection coll,String id){
	   
	   return (List<DBObject>)(MongoTools.findOneById(coll, id)).get("listcomment").toString();
	   
   }
*/

	public static void plusTweet(int id){
		 AuthentificationUsersTools.plus(id,"tweets");
	}
  

	public static void moinsTweet(int id){
		 AuthentificationUsersTools.moins(id,"tweets");
	}
  


}
