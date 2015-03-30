package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthentificationUsersTools {

	// verifier l'existance d'un user dans la la table users, renvoie true, false sinon
	public static boolean userExists(String login)throws SQLException  {
		boolean a =false;
	try{
		java.sql.Connection con=  Database.getMySQLConnection();	
		PreparedStatement state = con.prepareStatement("select * from users where login = ?;");
		state.setString(1, login);
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			a=true;
		}else{
			a=false;
		}
		state.close(); con.close();
	}catch(Exception e){
		System.out.println(e.getMessage());
		throw e;
		
	}
		return a;
	} 
	// verifier l'existance d'un user qui a pour id id dans la la table users , renvoie true, false sinon
	
	public static boolean userExistsById(int id)throws SQLException  {
		boolean a =false;
	try{
		java.sql.Connection con=  Database.getMySQLConnection();	
		PreparedStatement state = con.prepareStatement("select * from users where id = ?;");
		state.setInt(1, id);
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			a=true;
		}else{
			a=false;
		}
		state.close(); con.close();
	}catch(Exception e){
		throw e;
		
	}
		return a;
	} 
	// create un new utilisateur + insertion dans la table des utilisateurs : users
	public static void usercreate(String log, String mdp,String nom, String prenom ,String  email) throws SQLException{
		try{
		java.sql.Connection con= Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("insert into users (login, password,nom, prenom, email, date_inscription,tweets, abonnements, abonnes) "
				+ "values(?,?,?,?,? , now(),0,0,0);");
		state.setString(1, log);
		state.setString(2, mdp);
		state.setString(3, nom);
		state.setString(4, prenom);
		state.setString(5, email);
		state.executeUpdate();
		state.close(); 
		con.close();
		
		}catch(Exception e){
			//System.out.println("problem de connexion");
			throw new SQLException();
		}		
	}
	
	// verification de l'existance du couple login + password dans la table des utilisateurs : users, renvoie true, false sinon
	public static boolean checkPassword(String login, String password){
		boolean a =false;
		try{
			java.sql.Connection con=  Database.getMySQLConnection();	
			PreparedStatement state = con.prepareStatement("select * from users where login = ? && password = ?;");
			state.setString(1, login);
			state.setString(2, password);
			
			ResultSet resultat = state.executeQuery();
			if(resultat.next()){
				a=true;
			}else{
				a=false;
			}
			state.close(); con.close();
		}catch(Exception e){
			e.toString();
		}
			return a;
	}
	
	// renvoie l 'id de l'utilisateur user qui a pour login login
	public static int getIdUser(String login) throws SQLException{
		int id = 0;
		java.sql.Connection con=  Database.getMySQLConnection();	
		PreparedStatement state = con.prepareStatement("select id from users where login = ?;");
		state.setString(1, login);		
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			id= resultat.getInt("id");
		}
		state.close(); con.close();
	
		return id;
	}

	// renvoie le nom d'un utilisateur , prend en parametre un id
public static String getNameById(int id) throws SQLException{
	
	String nom="";
	Connection con=  Database.getMySQLConnection();	
	PreparedStatement state = con.prepareStatement("select nom from users where id = ?;");
	state.setInt(1, id);		
	ResultSet resultat = state.executeQuery();
	if(resultat.next()){
		nom= resultat.getString(1);
	}
	state.close(); con.close();
	return nom;
}

// renvoie le prenom d'un utilisateur , prend en parametre un id
public static String getPrenomById(int id) throws SQLException{

String prenom="";
Connection con=  Database.getMySQLConnection();	
PreparedStatement state = con.prepareStatement("select prenom from users where id = ?;");
state.setInt(1, id);		
ResultSet resultat = state.executeQuery();
if(resultat.next()){
	prenom= resultat.getString(1);
}
state.close(); con.close();
return prenom;
}

//renvoie l'email d'un utilisateur , prend en parametre un id
public static String getEmailById(int id) throws SQLException{

String email="";
Connection con=  Database.getMySQLConnection();	
PreparedStatement state = con.prepareStatement("select email from users where id = ?;");
state.setInt(1, id);		
ResultSet resultat = state.executeQuery();
if(resultat.next()){
	email= resultat.getString(1);
}
state.close(); con.close();
return email;
}


// renvoie le nom d'un utilisateur , prend en parametre un id
	public static String getLoginById(int id) throws SQLException{

		String log="";
		Connection con=  Database.getMySQLConnection();	
		PreparedStatement state = con.prepareStatement("select login from users where id = ?;");
		state.setInt(1, id);		
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			log= resultat.getString(1);
		}
		state.close(); con.close();
		return log;
}
	
	public static ArrayList<Integer> seeUserStatus(int id) throws SQLException{
		ArrayList<Integer> list=new ArrayList<Integer>();
		Connection con=  Database.getMySQLConnection();	
		PreparedStatement state = con.prepareStatement("select tweets, abonnements, abonnes from users where id = ?;");
		state.setInt(1, id);		
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			list.add(resultat.getInt(1));
			list.add(resultat.getInt(2));
			list.add(resultat.getInt(3));
		}
		state.close(); con.close();
		
		return list ;
		
	}
	public static void userdelete(String login, String password){
		try{
			Connection con= Database.getMySQLConnection();
			PreparedStatement state = con.prepareStatement("delete from users where login = ? and password = ?;");
			state.setString(1, login);
			state.setString(1, password);
			state.executeUpdate();
			state.close(); 
			con.close();
			}catch(Exception e){
			}
	}
	

	
	public static void plus(int id,String c){
		try {
				Connection con = Database.getMySQLConnection();
				PreparedStatement state = con.prepareStatement("update users set " + c + "=" + c + "+1 where id=?;");
				state.setLong(1, id);
				state.executeUpdate();
				state.close();
				con.close();
		}catch(Exception e){
		}
	 }

	 public static void moins(int id, String c){
	    try {
		Connection con = Database.getMySQLConnection();
		 PreparedStatement state= con.prepareStatement("update users set " +c+"=" +c+ "-1 where id=?;");
		state.setLong(1, id);
		state.executeUpdate();
		state.close();
		con.close();
		}catch(Exception e){
		}
	}
	
	public static void plusTweets(int id){
 		plus(id,"tweets");
 	}
   public static void plusAbonnes(int id){
	   moins(id,"tweets");
   }

}
