package services;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

import bdd.AuthentificationFriendsTools;
import bdd.AuthentificationSessionsTools;
import bdd.AuthentificationUsersTools;
import bdd.DBStatic;
import bdd.Database;
import bdd.MongoTools;

public class MongoServices {
    
	public static DBObject getTweetidobj( String idobj){
		DBObject ob = new BasicDBObject();
		DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
		ob.put("_id", new ObjectId(idobj));
		return coll.findOne(ob);
    }
// methode pour reccuperer le champ id d'un commentaire
	public static int getIdsessionCommentbyIdcom(DBObject objtweet,String idcom, int idsess){
		List<BasicDBObject> listecomments = new ArrayList<BasicDBObject>();
		listecomments = (List<BasicDBObject>) objtweet.get("listecomments");
		for(DBObject o : listecomments){
			Object s =  o.get("_id");
			int id = (Integer) o.get("id");
			if((s.equals(idcom)) && id==idsess){
				return id;
			}
		}
		return 0;
	}

	// methode pour creer un tweet
public static DBObject createTweetObject(int id, String text) throws SQLException{
	DBObject obj = new BasicDBObject();
	List <DBObject> listecomments = new ArrayList<DBObject>();
	List <Integer> listelikeur = new ArrayList<Integer>();
	String login = AuthentificationUsersTools.getLoginById(id); 	
	String name = AuthentificationUsersTools.getNameById(id);
	obj.put("login", login); obj.put("id",id);obj.put("name", name);obj.put("like",0);
	obj.put("date",new Date().getTime()); obj.put("text",text);obj.put("listecomments",listecomments);
	obj.put("listelikeur",listelikeur);
	return obj;
}
// methode pour creer un commentaire, pareil pour un tweet
public static DBObject createCommentObject(int id, String text) throws SQLException{
	DBObject obj = new BasicDBObject();
	String login = AuthentificationUsersTools.getLoginById(id); 	
	obj.put("_id", new ObjectId());obj.put("login", login);obj.put("id",id);
	obj.put("date",new Date().getTime()); obj.put("text",text); 
	return obj;
}

// methode pour ajouter un tweet
	public static JSONObject addTweet(String key, String text) throws JSONException, SQLException{
		DBObject obj = new BasicDBObject();JSONObject retour=new JSONObject();
	//	if (!LoginServices.verifier_session(key)) return(ServicesTools.error("deconnexion",300));
			try
			{  
			// recuperation de l'id de la session ouverte	
				int id=AuthentificationSessionsTools.getIdSourceByKey(key);	
				if ((id==0)) return(ServicesTools.error("session",0));
				obj=createTweetObject(id, text);
				MongoTools.plusTweet(id);
			// recuperation de la l collection  moi dans mongodb, 
				DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
				coll.insert(obj);
				return retour;
			}catch (Exception e) {
				return(ServicesTools.error("error",10000));
			}
  }	
// methode pour ajouter un commentaire dans un tweet
public static JSONObject addCommentaire(String key, String text, String idobj) throws JSONException, SQLException{
		JSONObject retour=new JSONObject(); DBObject obj_tweet =getTweetidobj(idobj);
		//if (!LoginServices.verifier_session(key)) return ServicesTools.error("error",300);
			try{  
				 // recuperation de l'id de la session ouverte	
				int id=AuthentificationSessionsTools.getIdSourceByKey(key);
				
				if ((id==0)) return(ServicesTools.error("error",0));
				// recuperation de la mongodb moi
			DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);	
			DBObject o =createCommentObject(id,text);
			
			coll.update(obj_tweet,new BasicDBObject("$addToSet", new BasicDBObject("listecomments",o)));	
			String login = o.get("login").toString();
			String idco= o.get("_id").toString();
			retour.put("idco", idco);
			retour.put("login", login);
			retour.put("date",o.get("date"));
			return retour;
			}catch (Exception e) {
				return(ServicesTools.error("error",10000));
			}
  }
 // fonction qui verifie si l id de l utilisateur fait parti de ceux qui ont deja liker un tweet 
 // lors du chargement de chaque tweet, il faut d abord verifier si le weet est deja liker par l utilisateur ou pas.
 // a fin depouvoir proposer la bonne icone , c a dire like or dislike tweet 
// pour cela il fait proposer une servlet qui permet d effectuer la liaison et les test necessaire
public static int verifierliketweet (String key, String idobj ){
	  int a = 0;
	DBObject ob=getTweetidobj(idobj);
	// recuperation de l'id de la session ouverte	
	 int id=AuthentificationSessionsTools.getIdSourceByKey(key);	
		DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);	
		int []tab={id};
		DBObject obb=new BasicDBObject("$in",tab);
		//DBObject obbb=new BasicDBObject("listelikeur",obb);
		ob.put("listelikeur", obb);
		DBObject o=coll.findOne(ob);
		if(o!=null){
			a=1;
		}
	 return a;
 }

//methode pour aimer un tweet	
public static JSONObject likeTweet(String key,String idobj) throws JSONException, SQLException{
	JSONObject retour=new JSONObject(); DBObject obj_tweet =getTweetidobj(idobj);
	if (!LoginServices.verifier_session(key)) return(ServicesTools.error("error",300));
		try
		{   // recuperation de l'id de la session ouverte	
			int id=AuthentificationSessionsTools.getIdSourceByKey(key);	
			if ((id==0)) return(ServicesTools.error("error",0));
	
			// recuperation de la mongodb moi
			DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);	
		// recuperer l  objet a liker, verifier si l id de l utilisateur est dans la liste listelikeur
			coll.update(obj_tweet,new BasicDBObject("$push", new BasicDBObject("listelikeur",id)));	
			DBObject obj_tweett =getTweetidobj(idobj);
			coll.update(obj_tweett,new BasicDBObject("$inc", new BasicDBObject("like",1)));	
		 return retour;
		}catch (Exception e) {
			return(ServicesTools.error("error",10000));
		}
}

//methode pour aimer un tweet	
public static JSONObject delikeTweet(String key,String idobj) throws JSONException, SQLException{
	JSONObject retour=new JSONObject(); DBObject obj_tweet =getTweetidobj(idobj);
	if (!LoginServices.verifier_session(key)) return(ServicesTools.error("error",300));
		try
		{   // recuperation de l'id de la session ouverte	
			int id=AuthentificationSessionsTools.getIdSourceByKey(key);	
			if ((id==0)) return(ServicesTools.error("error",0));
		    // recuperation de la mongodb moi
		DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
	
		coll.update(obj_tweet,new BasicDBObject("$pull", new BasicDBObject("listelikeur",id)));	
		DBObject obj_tweett =getTweetidobj(idobj);
		coll.update(obj_tweett,new BasicDBObject("$inc", new BasicDBObject("like",-1)));					
		 return retour;
		}catch (Exception e) {
			return(ServicesTools.error("Problem..."+e.getMessage(),10000));
		}
//db.people.update( {_id: 0}, {$pull: {loisirs: "cocooning"}})
}

	// methode pour supprimer un tweet
public static JSONObject deleteTweet(String key, String idobj) throws JSONException, SQLException{
	JSONObject retour=new JSONObject();
	if (!LoginServices.verifier_session(key)) return(ServicesTools.error("deconnexion",300));
	DBObject obj=getTweetidobj(idobj);	
	int idob= (Integer) obj.get("id");
		try
		{  
		// recuperation de l'id de la session ouverte	
			int id=AuthentificationSessionsTools.getIdSourceByKey(key);	
			if (id!=idob) return(ServicesTools.error("propriete",301));
		// recuperation de la mongodb moi
			DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
			coll.remove(obj);
			MongoTools.moinsTweet(id);
			return retour;
		}catch (Exception e) {
			//System.out.println(e.getMessage());
			return(ServicesTools.error("Problem...",10000));
		}
}

// methode pour supprimer un commentaire dans un tweet 
public static JSONObject deleteComment(String key, String idtweet, String idcom) throws JSONException, SQLException{
	JSONObject retour=new JSONObject(); 
	if (!LoginServices.verifier_session(key)) return(ServicesTools.error("error",300));
	DBObject objtweet=getTweetidobj(idtweet);	 
	DBObject objcom = new BasicDBObject();
	DBObject ob = new BasicDBObject();
	ob.put("_id", new ObjectId(idcom));
	objcom.put("listecomments",ob);
	DBObject objtmp = new BasicDBObject("$pull",objcom);
	try
		{  		// recuperation de l'id de la session ouverte	
          int idsess=AuthentificationSessionsTools.getIdSourceByKey(key);	
        int id = getIdsessionCommentbyIdcom( objtweet, idcom, idsess);
          	if (idsess!=id) return(ServicesTools.error("error",301));
		// recuperation de la mongodb moi
			DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
		coll.update(objtweet, objtmp);
		 return retour;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return(ServicesTools.error("Problem..."+e.getMessage(),10000));
		}
	
}
//fonction search permet a effectuer une recherche on utilisant les expressions regulieres comme a perl sauf que c fait en java	
  public static JSONObject search(String token, String query, String friends ) throws  JSONException, SQLException{
	  DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
	 //  if (!LoginServices.verifier_session(token)) return(ServicesTools.error("error",300)); 
	   DBObject ob;
	   int etat=0;
		int requeteur=AuthentificationSessionsTools.getIdSourceByKey(token);	
		
	   if(Integer.parseInt(friends)==0){
	   ob = QueryBuilder.start().or(
	                    QueryBuilder.start("login").is(query).get(),
	                    QueryBuilder.start("text").regex(Pattern.compile(query)).get(),
	                    QueryBuilder.start("date").regex(Pattern.compile(query)).get()).get();
	}else{
		 ob = QueryBuilder.start().or(QueryBuilder.start("login").is(query).get()).get();
	}
      DBCursor cursor = coll.find(ob).sort(new BasicDBObject("$natural", -1));
      JSONObject obr = new JSONObject();
      if(cursor.count() == 0) {
    	return ServicesTools.error("error", 600);
      } else {
        while(cursor.hasNext()) {
         	DBObject o= cursor.next();
        	DBObject recherche = new BasicDBObject();   // objet rechercher, ou trouver
        	recherche.put("id", o.get("_id"));  //id du tweet
        	recherche.put("text", o.get("text")); // le text du tweet
        	recherche.put("like", o.get("like"));  // like du tweet
        	 DBObject auteur = new BasicDBObject();   // construction de l auteur du tweet
			// l auteur de tweet
			auteur.put("login", o.get("login"));          // le login de l auteur a afficher
			//auteur.put("name", o.get("name"));   
			auteur.put("id", o.get("id"));              // l id de l auteur
			auteur.put("contact", "false");             
			recherche.put("auteur", auteur);              
			recherche.put("date", o.get("date"));        // la date du tweet
			recherche.put("score", 0);                  // le scrore le nbhre de fois ou le tweet a etai proposé lors de la recherche 
			recherche.put("listcomm", o.get("listecomments"));                  // le scrore le nbhre de fois ou le tweet a etai proposé lors de la recherche 
			etat=verifierliketweet (token, (String) o.get("_id").toString());
			recherche.put("etat", etat);
       	
			obr.accumulate("resultats", recherche); // insertion de l objet recherche dans le tableau resultats 
        }
        obr.put("search","");   //mot cle de la recherche, par defaut vaut vide
        obr.put("contact_only",1);
        obr.put("auteur",requeteur); // id du proprio de la recherche
        obr.put("date", new Date().getTime());
      }     	
       cursor.close();      
      return obr;   	
  }

//fonction searchMyTweets permet une recherche de mes tweets pour affichage sur mon espace moi., ou cherchere tweet 	
  public static JSONObject searchMyTweets(String token, String id_tweet ) throws  Exception{
	  DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
	   //if (!LoginServices.verifier_session(token)) return(ServicesTools.error("error",999999000)); 
	// recuperation de l'id de la session ouverte	
		int requeteur=AuthentificationSessionsTools.getIdSourceByKey(token);	
		// contact permet d 'indiquer si l utilisateur fait partie des utilisauers deja connectes , vaut true, false sinon, par defaut vaut false
		boolean contact = false;
		int etat=0;
		int id = Integer.parseInt(id_tweet);
		contact = AuthentificationFriendsTools.existFriend(requeteur, id);
		DBObject ob = QueryBuilder.start("id").is(id).get();
      DBCursor cursor = coll.find(ob).sort(new BasicDBObject("$natural", -1));
      JSONObject obr = new JSONObject();
      if(cursor.count() == 0) {
    	return ServicesTools.error("error", 601);
      } else {
        while(cursor.hasNext()) {
        	DBObject o= cursor.next();
        	DBObject recherche = new BasicDBObject();   // objet rechercher, ou trouver
        	recherche.put("id", o.get("_id"));  //id du tweet
        	recherche.put("text", o.get("text")); // le text du tweet
        	recherche.put("like", o.get("like"));  // like du tweet
			DBObject auteur = new BasicDBObject();   // construction de l auteur du tweet
			// l auteur de tweet
			auteur.put("login", o.get("login"));          // le login de l auteur a afficher
			//auteur.put("name", o.get("name"));   
			auteur.put("id", o.get("id"));              // l id de l auteur
			auteur.put("contact", contact);             
			recherche.put("auteur", auteur);              
			recherche.put("date", o.get("date"));        // la date du tweet
			recherche.put("score", 0); 
			recherche.put("listcomm", o.get("listecomments"));
			etat=verifierliketweet (token, (String) o.get("_id").toString());
			recherche.put("etat", etat);
       	
			// le scrore le nbhre de fois ou le tweet a etai proposé lors de la recherche 
			obr.accumulate("resultats", recherche); // insertion de l objet recherche dans le tableau resultats 
        }
        obr.put("search","");   //mot cle de la recherche, par defaut vaut vide
        obr.put("contact_only",1);
        obr.put("auteur",requeteur); // id du proprio de la recherche
        obr.put("date", new Date().getTime());
      }     	
       cursor.close();      
      return obr;   	
  }
  
//fonction searchMyTweets permet une recherche de mes tweets et ceux de mes amis pour affichage a l'accueil.	
  public static JSONObject searchTweetsAccueil(String token) throws  NumberFormatException, Exception{
	  DBCollection coll = Database.getMongoCollection(DBStatic.mongocolle);
	  // if (!LoginServices.verifier_session(token)) return(ServicesTools.error("error",333333333)); 
	// recuperation de l'id de la session ouverte	
		int id=AuthentificationSessionsTools.getIdSourceByKey(token);
		int etat=0;
		ArrayList<Integer> liste= AuthentificationFriendsTools.listeAbonnements(id);
        //construire la liste des id la ou effectué la recherche by id car chaque tweet contient un champs id
           BasicDBList values = new BasicDBList();
			values.add(id);
		for(int b : liste){	
			values.add(b);
        }
		//l'objet requete
		 DBObject ob = QueryBuilder.start("id").in(values).get();
		DBCursor cursor = coll.find(ob).sort(new BasicDBObject("$natural", -1)); 
		 JSONObject obr = new JSONObject();
		
		 if(cursor.count() == 0) {
    	return ServicesTools.error("error", 601);
      } else {
    	  while(cursor.hasNext()) {
          	DBObject o= cursor.next();
          	DBObject recherche = new BasicDBObject();   // objet tweet rechercher, ou trouver
          	recherche.put("id", o.get("_id"));
          	recherche.put("text", o.get("text"));
          	recherche.put("like", o.get("like"));  // like du tweet
          	
          	DBObject auteur = new BasicDBObject();
  			// l auteur de tweet
  			auteur.put("login", o.get("login"));
  			auteur.put("id", o.get("id"));
  			auteur.put("contact", true);
  			recherche.put("auteur", auteur);
  			recherche.put("date", o.get("date"));
  			recherche.put("score", 0);
  			recherche.put("listcomm", o.get("listecomments"));
  			 etat=verifierliketweet (token, (String) o.get("_id").toString());
        	 recherche.put("etat", etat);
        	
  			obr.accumulate("resultats", recherche); // insertion de l objet recherche dans le tableau resultats 
          }
          obr.put("search","");   //mot cle de la recherche, par defaut vaut vide
          obr.put("contact_only",0);
          obr.put("auteur",""); // id du proprio de la recherche
          obr.put("date", new Date().getTime());
        
      }     	
       cursor.close();      
      return obr;   	
  }
}