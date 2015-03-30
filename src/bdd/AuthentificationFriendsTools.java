package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthentificationFriendsTools {
	
	public static void insertFriends(int id_source, int id_friend) throws SQLException{
		try{
			Connection con= Database.getMySQLConnection();
			PreparedStatement state = con.prepareStatement("insert into friends (source,destination,date_creation) values(?,?,now());");
			state.setInt(1, id_source);
			state.setInt(2, id_friend);
			state.executeUpdate();
			state.close(); 
			con.close();
			
			}catch(Exception e){
				System.out.println("problem de connexion");
				throw new SQLException();
			}
		
	}
	public static boolean existFriend(int id_source,int id_friend) throws Exception{
		boolean a =false;
	try{
		java.sql.Connection con=  Database.getMySQLConnection();	
		PreparedStatement state = con.prepareStatement("select * from friends where source = ? && destination=?;");
		state.setInt(1, id_source);
		state.setInt(2, id_friend);
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
	public static void deleteFriends(int id_source, int id_friend) throws SQLException{
		try{
			Connection con= Database.getMySQLConnection();
			PreparedStatement state = con.prepareStatement("delete from friends where source=? && destination=?;");
			state.setInt(1, id_source);
			state.setInt(2, id_friend);
			state.executeUpdate();
			state.close(); 
			con.close();
			}catch(Exception e){
				throw new SQLException();
			}
	}

// renvoie la liste de amis , la liste des abonnés, ceux que je suis
	public static ArrayList<Integer> listeAbonnements(int id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = Database.getMySQLConnection();
			PreparedStatement statement = connection.prepareStatement("select * from friends where source=?;");
			statement.setInt(1, id);
			ResultSet resultat = statement.executeQuery();
			while (resultat.next()) {
				list.add(resultat.getInt("destination"));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// renvoie la liste de amis , la liste des abonnés, ceux qui me suivent
		public static ArrayList<Integer> listeAbonnes(int id) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			try {
				Connection connection = Database.getMySQLConnection();
				PreparedStatement statement = connection.prepareStatement("select * from friends where destination=?;");
				statement.setInt(1, id);
				ResultSet resultat = statement.executeQuery();
				while (resultat.next()) {
					list.add(resultat.getInt("source"));
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
	
		// renvoie la liste de utilsiateurs a suggerér , 
				public static ArrayList<Integer> listeSuggerer(int id) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					try {
						Connection connection = Database.getMySQLConnection();
						// la requete de selection qui permer de renvoyer les nuplets que je ne suis pas
						PreparedStatement statement = connection.prepareStatement("select id from users where id!=? and "
															+ "not exists (select destination from friends where source=? and destination=users.id);");
						statement.setInt(1, id);
						statement.setInt(2, id);
						
						ResultSet resultat = statement.executeQuery();
						while (resultat.next()) {
							list.add(resultat.getInt("id"));
						}
						statement.close();
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return list;
				}
				
 	public static void plusAbonnements(int id){
 		 AuthentificationUsersTools.plus(id,"abonnements");
 	}
   public static void plusAbonnes(int id){
	   AuthentificationUsersTools.plus(id,"abonnes");
   }

	public static void moinsAbonnements(int id){
		 AuthentificationUsersTools.moins(id,"abonnements");
 	}
   public static void moinsAbonnes(int id){
	   AuthentificationUsersTools.moins(id,"abonnes");
   }
  
}