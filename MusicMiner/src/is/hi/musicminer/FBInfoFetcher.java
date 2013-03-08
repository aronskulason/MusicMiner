package is.hi.musicminer;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

// Holds methods to get information from facebook
public class FBInfoFetcher {
	
	private final static String TAG = "FBInfoFetcher";//
	private static String[] test = new String[2];
	
	public static JSONObject getUserLikes(){
		String fqlQuery1 = "SELECT page_id, created_time  FROM page_fan WHERE type=\"Musician/band\" AND uid = me() ";
		JSONObject response1 = executeFqlQuery(fqlQuery1);
		response1 = prepareJSON(response1);
		System.out.println("!!!: "+response1.toString());
		String fqlQuery2 = "SELECT uid FROM user WHERE uid=me()";
		JSONObject response2 = executeFqlQuery(fqlQuery2);
		//response2 = prepareJSON(response2);
		JSONObject result = new JSONObject();
		try {
			result.put("user",response2.get("data"));
			result.put("likes",response1.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static JSONObject getArtistInfo(String pid){
		System.out.println("BLA: "+pid);
	 	String fqlQuery1 = "SELECT name, about, fan_count, website, page_url, pic FROM page WHERE page_id="+pid;
	 	JSONObject response=null;
		response = executeFqlQuery(fqlQuery1);
		System.out.println("BLA: "+response.toString());
	 	return response;
	}
	
	/*public User[] getUserInfo(String[][] users){
		
	}*/
	
	private static JSONObject executeFqlQuery(String fqlQuery){
		Bundle params = new Bundle();
	    params.putString("q", fqlQuery);
	    Session session = Session.getActiveSession();
	    System.out.println("BBBB: "+session.getAccessToken().toString() );
	    Request request = new Request(session,"/fql",params,HttpMethod.GET); 
	    Response response = Request.executeAndWait(request);
	    System.out.println("Response: "+response);
        JSONObject jsonObject = response.getGraphObject().getInnerJSONObject();
	    return jsonObject;

	}
	
	private static JSONObject prepareJSON(JSONObject json){

        try {
        	Iterator<?> keys1 = json.keys();
			while( keys1.hasNext() ){
				String key = (String)keys1.next();
				JSONArray arr = json.getJSONArray(key);
				for(int i=0; i<arr.length(); i++){	
					System.out.println("asdf "+arr.get(i).toString());
					JSONObject obj = arr.getJSONObject(i);
					obj.put("page_id", obj.get("page_id").toString());
					arr.put(i, obj);
					System.out.println("aaaa"+obj.toString());
					//String newVal = arr.toJSONObject(names)
					//arr.put("page_id", ""+arr.get("page_id")+"")
				}
				json.put("data", arr);
			}
        } catch (JSONException e) {
        	e.printStackTrace();
		}
        return json;
	}
}
