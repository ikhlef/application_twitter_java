package test;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.json.JSONException;

import services.LoginServices;

public class Test {

	public static void main(String[] args) throws JSONException, SQLException {
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		 java.util.Date now = new java.util.Date();
		 
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 try {
			Date table = dateFormat.parse("2014-02-21 16:52:46");
			System.out.println("voila la date de la table"+ table);
			System.out.println("voila la date de la table converti en long"+ table.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//long d = now.getTime().("2014-02-21 16:52:46");
		  // System.out.println("Value of java.util.Date : " + d);
	        System.out.println("Value of java.util.Date : " + now.getTime());
	      
	        //converting java.util.Date to java.sql.Date in Java
	        java.sql.Date sqlDate = new java.sql.Date(now.getTime());
	        System.out.println("Converted value of java.sql.Date : " + sqlDate.getTime());
	      
	        //converting java.sql.Date to java.util.Date back
	        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
	        System.out.println("Converted value of java.util.Date : " + utilDate.getTime());
 
	        String [] tab= {"nbre tweets", "ami suivi", "ami suive me"};
	        String chaine = Arrays.toString(tab).replaceAll("[\\[\\]]", "");
	        System.out.println(chaine);
	        System.out.println(new Date());
	        System.out.println(new Date().getTime());
	        Date d = new Date();
			/*
			 * Fabrication du commentaire 
			 * */
			DateFormat date_comment = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			date_comment.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

			String di = date_comment.format(d);
			 System.out.println("foufa"+di);
		       
	}

}
