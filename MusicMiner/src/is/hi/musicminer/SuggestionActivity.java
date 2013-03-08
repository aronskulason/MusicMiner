/*****************************************************
 * 		Suggester									**
 * 													**
 * 		Authors: Aron Skúlason						**
 * 				 Bjarni Grétar Ingólfsson			**
 * 				 Sigurður Óli Árnason				**
 * 				 Tinna Frímann Jökulsdóttir			**
 * 													**
 *****************************************************/

package is.hi.musicminer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

// Main suggestion area

public class SuggestionActivity extends Activity {
	
	ArtistListBuilder abuilder;
	UserListBuilder ubuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        //TODO: Setja inn rétt userid.
        ListFetcher.getSuggestions("1580993246", this);
       

     	
    }
    
    
    public void build(String string){
    	JSONObject json = null;
		try {
			json = new JSONObject(string);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		System.out.println(json.toString());
    	
    	// Artist button
        Button btn_artists = (Button) findViewById(R.id.btn_artists);
    	btn_artists.setEnabled(false);
    	
    	btn_artists.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			switchToArtists();
    		}
    	});
    	
    	// Users button
    	Button btn_users = (Button) findViewById(R.id.btn_users);
    	btn_users.setEnabled(true);

     	btn_users.setOnClickListener(new View.OnClickListener() {

     		public void onClick(View v) {
     			switchToUsers();
     		}
     	});
     	
     	// Build the artists list
     	
        LinearLayout layout_artists = (LinearLayout) this.findViewById(R.id.layout_artists);      
        
        TreeMap<String, Integer> artists = new TreeMap<String, Integer>();
        JSONObject ajson;
		try {
			ajson = json.getJSONObject("artists");	
			Iterator<String> keys = ajson.keys();
	        while (keys.hasNext()){
	        	String aid = (String)keys.next();
	        	String match = ajson.getString(aid);
	        	artists.put(aid, Integer.parseInt(match));
	       }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        
        this.abuilder = new ArtistListBuilder(artists, this);
        
        
        //Build the users list
        LinearLayout layout_users = (LinearLayout) this.findViewById(R.id.layout_users);
        
        TreeMap<String, Integer> users = new TreeMap<String, Integer>();
        JSONObject ujson;
		try {
			ujson = json.getJSONObject("users");	
			Iterator<String> keys = ujson.keys();
	        while (keys.hasNext()){
	        	String uid = (String)keys.next();
	        	String match = ujson.getString(uid);
	        	users.put(uid, Integer.parseInt(match));
	       }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
                
        this.ubuilder = new UserListBuilder(users, this);
        
        // Build the lists
        layout_users.addView(ubuilder.getListView());
        layout_artists.addView(abuilder.getListView());
        
        // Hide the users layout        
        layout_users.setVisibility(View.INVISIBLE);
        layout_users.setVisibility(View.GONE);
        
        //Hide the progress bar
        ProgressBar progress = (ProgressBar) findViewById(R.id.progress); 
        ((ViewManager) progress.getParent()).removeView(progress);
        
    }

    private void switchToArtists() {
    	Log.d("SuggestionActivity","switchToArtists");
    	((Button) findViewById(R.id.btn_users)).setEnabled(true);
    	((Button) findViewById(R.id.btn_artists)).setEnabled(false);
    	
    	((LinearLayout) findViewById(R.id.layout_artists)).setVisibility(View.VISIBLE);
    	((LinearLayout) findViewById(R.id.layout_users)).setVisibility(View.INVISIBLE);
    	((LinearLayout) findViewById(R.id.layout_users)).setVisibility(View.GONE);
    	
    }
    
    private void switchToUsers() {
    	Log.d("SuggestionActivity","switchToUsers");
    	((Button) findViewById(R.id.btn_users)).setEnabled(false);
    	((Button) findViewById(R.id.btn_artists)).setEnabled(true);
    	
    	((LinearLayout) findViewById(R.id.layout_artists)).setVisibility(View.INVISIBLE);
    	((LinearLayout) findViewById(R.id.layout_artists)).setVisibility(View.GONE);
    	((LinearLayout) findViewById(R.id.layout_users)).setVisibility(View.VISIBLE);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_suggestion, menu);
        return true;
    }
}
