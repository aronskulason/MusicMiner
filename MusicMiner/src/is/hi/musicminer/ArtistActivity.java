package is.hi.musicminer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

// Show info about one artist
public class ArtistActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        
        Intent i = getIntent();
        Artist artist = (Artist) i.getParcelableExtra("artist");
        
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(artist.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_artist, menu);
        return true;
    }
}
