package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;


public class Database {
private DataSource dataSource;
private static Database database=null;
private static DB mongodb=null;	
public Database(String jndiname) throws SQLException {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" +jndiname);
        } catch (NamingException e) {
// Handle error that it’s not configured in JNDI.
        throw new SQLException(jndiname + " is missing in JNDI! : "+e.getMessage());
       }
   }

public Connection getConnection() throws SQLException {
	return dataSource.getConnection();
}

public static Connection getMySQLConnection() throws SQLException{
	try{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
		System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}catch(ClassNotFoundException e){
		throw new RuntimeException("aaaa", e);
	}
	
	if (DBStatic.mysql_pooling==false){
		
    return( DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host+"/"
	           +DBStatic.mysql_db, DBStatic.mysql_username,DBStatic.mysql_password));
 
	}else{
		
		if (database==null){
			
			database=new Database("jdbc/db");
		}
		return(database.getConnection());
	}
}


private static DB getMongo(){
	Mongo m;
	if (mongodb == null) {
		try {
			m = new Mongo(DBStatic.mongo_adress,DBStatic.mongo_port);
			mongodb = m.getDB(DBStatic.mysql_db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return mongodb;
}

public static DBCollection getMongoCollection(String coll) {
	return Database.getMongo().getCollection(coll);
}


}
