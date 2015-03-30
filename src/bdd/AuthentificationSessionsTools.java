package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class AuthentificationSessionsTools {
	
	private static String cle="";
	
	//Fabrique statique pour récupérer un (pseudo aléatoire) UUID de type 4.
	//Le UUID est généré en utilisant un pseudo cryptage fort générateur de nombres aléatoires.
	public static String genererCle(){	
		return UUID.randomUUID().toString().replace("-", "");
	}
	
// renvoie une clé de 32 caracteres, insert une session dans la table sessions	
public static String insertSession(int id_user,boolean bool) throws SQLException{
	try{
	cle = genererCle(); 
	Connection con= Database.getMySQLConnection();
	PreparedStatement state = con.prepareStatement("insert into sessions (token, id_user) values(?,?);");
	state.setString(1, cle);
	state.setInt(2, id_user);
	state.executeUpdate();
	state.close(); 
	con.close();
	
	}catch(Exception e){
	}
		return cle;
	}

// renvoie true si la session est activee, false sinon
/*public static boolean session_Active(String cle)throws SQLException{
	boolean a=false;
	try {
		Connection con = Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("select * from sessions where date_fin=0 && token=?;");
		state.setString(1, cle);
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			a = true;
		}
		state.close();
		con.close();
	} catch (Exception e) {
			}
	return a;
	
}
*/
//renvoie true si la session existe, false sinon
public static boolean session_Existe(String cle)throws SQLException{
	boolean a=false;
	try {
		Connection con = Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("select * from sessions where token=?;");
		state.setString(1, cle);
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			a = true;
		}
		state.close();
		con.close();
	} catch (Exception e) {
		System.out.println(e.getMessage());
			}
	return a;
	
}
public static String get_Date_Creation(String key){
	String date="";
	try {
		Connection con = Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("select * from sessions where token=?;");
		state.setString(1, key);
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			date=resultat.getString(3);
		}
		state.close();
		con.close();
	} catch (Exception e) {
		System.out.println(e.getMessage());
			}
	
	return date;
} 

// methode pour mettre a jour une session , c'est a dire mettre a jour la derniere date d'utilisation de la session
public static void session_update(String key){
	try{
		Connection con= Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("update sessions set date_creation = NOW() where token=?;");
		state.setString(1, key);
		state.executeUpdate();
		state.close(); 
		con.close();
		}catch(Exception e){
		}
	
}
// methode pour recuperer id_source a base de la clé session, afin de pouvoir l'inserer dans la table friends
public static int getIdSourceByKey(String key){
	int id=0;
	try{
		Connection con= Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("Select * from sessions where token=?;");
		state.setString(1, key);
		ResultSet resultat = state.executeQuery();
		if(resultat.next()){
			id=resultat.getInt("id_user");
		}
		state.close(); 
		con.close();
		}catch(Exception e){
		}
	return id;
}

// methode pour desactiver une session, j ai fais le choix de mettre a 1 , la valeur de date_fin, prend en paramettre la clé

 public static void desactiver_Session (String cle)throws SQLException{
		try{
		Connection con= Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("update sessions set date_fin=1 where token=?;");
		state.setString(1, cle);
		state.executeUpdate();
		state.close(); 
		con.close();
		
		}catch(Exception e){
		}
	}

//methode pour activer une session, j ai fais le choix de mettre a 0 , la valeur de date_fin, prend en paramettre la clé

public static void activer_Session (String cle)throws SQLException{
		try{
		Connection con= Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("update sessions set date_fin=0 where token=?;");
		state.setString(1, cle);
		state.executeUpdate();
		state.close(); 
		con.close();
		}catch(Exception e){
		}
	}
 
 
//methode pour supprimer une session
 public static void supprimerSession (String cle)throws SQLException{
		try{
		Connection con= Database.getMySQLConnection();
		PreparedStatement state = con.prepareStatement("delete from sessions where token=?;");
		state.setString(1, cle);
		state.executeUpdate();
		state.close(); 
		con.close();
		
		}catch(Exception e){
		}
	}
}
