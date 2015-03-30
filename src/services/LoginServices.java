package services;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.AuthentificationSessionsTools;
import bdd.AuthentificationUsersTools;

public class LoginServices {

	private static long d;


	// methode d'autentification 
		public static JSONObject login(String login, String password) throws JSONException{
			
			if ((login==null) || (password==null))
				return(ServicesTools.error("Wrong Arguments",0));
				try
				{
				//Verifie que l�utilisateur existe sinon ERROR 1
				boolean is_user=AuthentificationUsersTools.userExists(login);
				if (!is_user) return(ServicesTools.error("error",1));
				//Verifie que le password et l�utilisateur sont OK sinon ERROR 2
				boolean password_ok=AuthentificationUsersTools.checkPassword(login,password);
				if (!password_ok) return(ServicesTools.error("error ",2));
				//R�cup�re l�id de l�utilisateur
				int id_user=AuthentificationUsersTools.getIdUser(login);
				JSONObject retour=new JSONObject();
				//Ins�re une nouvelle session dans la base de donn�es
				String key=AuthentificationSessionsTools.insertSession(id_user,false);
				try{
					retour.put("id", id_user);
					retour.put("login", login);
					retour.put("key",key);
					return(retour);
				}catch(JSONException e){
					return(ServicesTools.error("JSON Problem "+e.getMessage(),100));
				}
									
				}catch (Exception e) {
					System.out.println(e.getMessage());
					return(ServicesTools.error("Problem...",10000));
				
				}
		}
		
		
		public static boolean verifier_session(String key) throws JSONException, SQLException {
			boolean a= false;
			if(key==null)	return a;// ServicesTools.error("problem token : "+key, 1);
			  if(!AuthentificationSessionsTools.session_Existe(key)) return a;// ServicesTools.error("problem session not exist : ",550); 
			  String date = AuthentificationSessionsTools.get_Date_Creation(key);
			  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			  try {
				Date table = dateFormat.parse(date);		
				   Date d = new Date();
				 
				   if(d.getTime() - table.getTime()<600000){ // 600000milisec=10min
						//mise a jour de la date de creation avec une requete update sur la table session
						AuthentificationSessionsTools.session_update(key);
						a=true;
						return a;
					}else{
						AuthentificationSessionsTools.supprimerSession(key);
						return a;// ServicesTools.error("desol� deconnexion", 345);
					}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
			 return a;// ServicesTools.accesok();
			}
		
		
		public static JSONObject logout(String key) throws JSONException, SQLException {
		
		if(key==null)	return ServicesTools.error("problem token : "+key, 1);
		  if(!AuthentificationSessionsTools.session_Existe(key)) return ServicesTools.error("problem session not exist : ",550); 
		//  if(!AuthentificationSessionsTools.session_Active(key)) return ServicesTools.error("problem sessionnon active : ",551);
		  AuthentificationSessionsTools.supprimerSession(key);
		  
		 return ServicesTools.accesok();
		}
}
