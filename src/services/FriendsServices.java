package services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.AuthentificationFriendsTools;
import bdd.AuthentificationSessionsTools;
import bdd.AuthentificationUsersTools;

public class FriendsServices {
	// ajouter un ami, c'est a dire pouvoir suivre une personne
	public static JSONObject addFriends(String key, int id_friend) throws JSONException{	
		if ((key==null) || (id_friend==0))return(ServicesTools.error("Wrong Arguments",0));
			try
			{
			//Verifie que l id_friend existe dans la table users sinon ERROR 4
			boolean id_user=AuthentificationUsersTools.userExistsById(id_friend);
			if (!id_user) return(ServicesTools.error("Unknown id "+id_friend,4));
			//Récupère l’id_source de l’utilisateur
			int id_source=AuthentificationSessionsTools.getIdSourceByKey(key);
			boolean a =AuthentificationFriendsTools.existFriend(id_source,id_friend);
			if(a) return ServicesTools.error("le couple existe "+ id_friend,4); 
			JSONObject retour=new JSONObject();
			//Insère un nouveau ami(e) dans la table friends
			AuthentificationFriendsTools.insertFriends(id_source,id_friend);
			AuthentificationFriendsTools.plusAbonnements(id_source);
			AuthentificationFriendsTools.plusAbonnes(id_friend);
			try{
				retour.put("id_source", id_source);
				retour.put("id_friend",id_friend);
				return(retour);
			}catch(JSONException e){
				return(ServicesTools.error("JSON Problem ajout friend "+e.getMessage(),100));
			}
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return(ServicesTools.error("Problem..."+e.getMessage(),10000));
			
			}
	}
// supression d'un ami que je suis
	public static JSONObject removeFriends(String key, int id_friend) throws JSONException{	
		if ((key==null) || (id_friend==0))return(ServicesTools.error("Wrong Arguments",0));
			try
			{
			//Verifie que l id_friend existe dans la table users sinon ERROR 4
			boolean id_user=AuthentificationUsersTools.userExistsById(id_friend);
			if (!id_user) return(ServicesTools.error("Unknown id "+id_friend,4));
			//Récupère l’id_source de l’utilisateur
			int id_source=AuthentificationSessionsTools.getIdSourceByKey(key);
			boolean a =AuthentificationFriendsTools.existFriend(id_source,id_friend);
			if(!a) return ServicesTools.error("nexist"+ id_friend,4); 
			JSONObject retour=new JSONObject();
			//Insère un nouveau ami(e) dans la table friends
			AuthentificationFriendsTools.deleteFriends(id_source,id_friend);
			AuthentificationFriendsTools.moinsAbonnements(id_source);
			AuthentificationFriendsTools.moinsAbonnes(id_friend);
			try{
				retour.put("id_source", id_source);
				retour.put("id_friend",id_friend);
				return(retour);
			}catch(JSONException e){
				return(ServicesTools.error("error",100));
			}
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return(ServicesTools.error("Problem..."+e.getMessage(),10000));
			
			}
	}
// renvoie la liste des amis au quels je suis abonnés, c'est a dire ceux que je suis. sur tweeter c'est Abonnements.	
	public static JSONObject listeFriendsAbonnements(String key) throws JSONException{	
		List<JSONObject>listejs= new ArrayList<JSONObject>();
		if (key==null)return(ServicesTools.error("error",0));
			try
			{
			//Récupère l’id_source de l’utilisateur
			int id_source=AuthentificationSessionsTools.getIdSourceByKey(key);
			JSONObject retour=new JSONObject();
			retour.put("id", id_source);
			//Insère un nouveau ami(e) dans la table friends
			ArrayList<Integer> liste= AuthentificationFriendsTools.listeAbonnements(id_source);
			for(Integer a : liste){
				JSONObject usr=new JSONObject();
				String login = AuthentificationUsersTools.getLoginById(a);
				usr.put("id", a);
				usr.put("login",login);
				usr.put("contact", true);
				listejs.add(usr);
			}
			try{
				retour.put("users", listejs);
				return(retour);
			}catch(JSONException e){
				return(ServicesTools.error("error",100));
			}
			}catch (Exception e) {
				return(ServicesTools.error("error",10000));
			}
	}


	// renvoie la liste des amis qui se sont abonnés a moi, c'est a dire ceux qui me suivent. sur tweeter c'est Abonnés.	
		public static JSONObject listeFriendsAbonnes(String key) throws JSONException{	
			List<String>listejs= new ArrayList<String>();
			if (key==null)return(ServicesTools.error("Wrong Arguments",0));
				try
				{
				//Récupère l’id_source de l’utilisateur
				int id_source=AuthentificationSessionsTools.getIdSourceByKey(key);
				JSONObject retour=new JSONObject();
				retour.put("id", id_source);
				//Insère un nouveau ami(e) dans la table friends
				ArrayList<Integer> liste= AuthentificationFriendsTools.listeAbonnes(id_source);
				for(Integer a : liste){
					String login = AuthentificationUsersTools.getLoginById(a);
					listejs.add(login);
				}
				try{
					retour.put("users", listejs);						
					return(retour);
				}catch(JSONException e){
					return(ServicesTools.error("error",100));
				}
				}catch (Exception e) {
					return(ServicesTools.error("error",10000));	
				}
		}

		// renvoie la liste des utilisateur en suggestion , proposer une liste d'ami . sur tweeter c'est Suggestions.	
				public static JSONObject listeSuggerer(String key) throws JSONException{	
					List<JSONObject>listejs= new ArrayList<JSONObject>();
					if (key==null)return(ServicesTools.error("error",0));
						try
						{
						//Récupère l’id_source de l’utilisateur
						int id_source=AuthentificationSessionsTools.getIdSourceByKey(key);
						JSONObject retour=new JSONObject();
						retour.put("id", id_source);
						//Insère un nouveau ami(e) dans la table friends
						ArrayList<Integer> liste= AuthentificationFriendsTools.listeSuggerer(id_source);
						for(Integer a : liste){
							JSONObject usr=new JSONObject();
							String login = AuthentificationUsersTools.getLoginById(a);
							usr.put("id", a);
							usr.put("login", login);
							usr.put("contact", false);
							listejs.add(usr);
						}
								try{
							retour.put("users", listejs);
							return(retour);
						}catch(JSONException e){
							return(ServicesTools.error("error",100));
						}
						}catch (Exception e) {
							return(ServicesTools.error("error",10000));	
						}
				}
	
	
	
}
