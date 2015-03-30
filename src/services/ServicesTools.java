package services;

import org.json.JSONException;
import org.json.JSONObject;

public class ServicesTools {
	
	public static JSONObject accesok(){
		return new JSONObject();
	}
	
	public static JSONObject error(String a, int b) throws JSONException{
		return new JSONObject().put(a, b);
	}

}
