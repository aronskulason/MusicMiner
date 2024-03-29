package is.hi.musicminer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UserActivity extends Activity {

	User user;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        
        Intent i = getIntent();
        user = (User) i.getParcelableExtra("user");
       
        ListFetcher.getArtistsFromUser(user.getId(), this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user, menu);
        return true;
    }
    public void build(String string){
    	JSONObject json = null;
		try {
			json = new JSONObject(string);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	LinearLayout layout = (LinearLayout) this.findViewById(R.id.layout);
    	
    	TreeMap<String, Integer> artists = new TreeMap<String, Integer>();
    	
		Iterator<String> keys = json.keys();
        while (keys.hasNext()){
        	String aid = (String)keys.next();
        	artists.put(aid, 0);
        }
		 
                
        ArtistListBuilder builder = new ArtistListBuilder(artists, this);
        layout.addView(builder.getListView());
        
        ProgressBar progress = (ProgressBar) findViewById(R.id.progress); 
        ((ViewManager) progress.getParent()).removeView(progress);
    }
    
}
