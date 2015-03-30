package services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.AuthentificationSessionsTools;
import bdd.AuthentificationUsersTools;

public class UsersServices {
	
	public static JSONObject createUSer(String login, String password , String nom, String prenom, String email ) throws SQLException, JSONException{
		if(login==null || password==null || nom==null || prenom==null || email==null){
			return ServicesTools.error("probleme de paramettre ", 2);
		}
		if(AuthentificationUsersTools.userExists(login)){
			return ServicesTools.error("existelogin",3);
		}else{
			AuthentificationUsersTools.usercreate(login, password,nom, prenom,email);	
		return ServicesTools.accesok();
		}
	}
	//methode pour supprimer un utilisateur
   public static JSONObject deleteUSer(String login, String password)  {
	   AuthentificationUsersTools.userdelete(login, password);
	   return ServicesTools.accesok();
	   }
   // renvoie le statut de l'utilisateur, nbre de tweets, nbre d abonnements et le nbre d'abonn�s.
   public static JSONObject seeUSerstatus(String cle) throws JSONException, SQLException  {
	   if(cle==null)  return(ServicesTools.error("Wrong Arguments",0));
	   //R�cup�re l�id_source de l�utilisateur
		int id_source=AuthentificationSessionsTools.getIdSourceByKey(cle);
		//recuperation du statut de l'utilisateur dans la table users
		ArrayList<Integer> liste = new ArrayList<Integer>();
		liste =AuthentificationUsersTools.seeUserStatus(id_source);
		JSONObject js = new JSONObject();
		js.put("id",id_source);
		js.put("Tweets", liste.get(0));
		js.put("Abonnements", liste.get(1));
		js.put("Abonnes", liste.get(2));
		
	   return js;
	   }
}
