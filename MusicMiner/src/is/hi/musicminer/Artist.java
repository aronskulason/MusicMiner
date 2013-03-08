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

import is.hi.musicminer.MainActivity.GetFbInfoAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

// Contains information about the artist, including 
// major information from facebook.

public class Artist extends Item{

	// The artists profile picture
	private String pic;
	// The number of likes that the artist has gotten on facebook
	private String likes;
	// The about clause
	private String about;
	// The website clause
	private String website;
	// The facebook page url
	private String page_url;
	
	//Use: Artist a = new Artist(id, match)
	//Before: id is the artists page ID and match is its 
	//		  match percentage for the user.
	//After: a contains all major information about the user 
	//		 with user ID = userid 
	public Artist(String id, int match) {
		this.id = id;
		this.match = match;
		build();
	}
	
	@Override
	protected void build() {
		//GetFbInfoAsync g = new GetFbInfoAsync();
		//g.execute(this.id);
		this.name=this.id;
		
	}
	
	public String getPic() {
		return this.pic;
	}
	public String getLikes() {
		return this.likes;
	}
	public String getAbout() {
		return this.about;
	}
	public String getWebsite() {
		return this.website;
	}
	public String getPageUrl() {
		return this.page_url;
	}

	////////////////////////////////////////////
	// The following is to implement Parcelable
	
	public int describeContents() {
        return 0;
    }
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeInt(match);
        out.writeString(likes);
        out.writeString(name);
        out.writeString(pic);
        out.writeString(about);
        out.writeString(website);
        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    // get the values back
    private Artist(Parcel in) {
        id = in.readString();
        match = in.readInt();
        likes = in.readString();
        name = in.readString();
        pic = in.readString();
        about = in.readString();
        website = in.readString();
    }
    // end of parcelable code
    //////////////////////////////////
    
    //Get the info from facebook
    private class GetFbInfoAsync extends AsyncTask<String, JSONObject, Void> {

		protected Void doInBackground(String... params) {
			JSONObject artistInfo = FBInfoFetcher.getArtistInfo(params[0]);
			publishProgress(artistInfo);
			return null;
		}

		@Override
		protected void onProgressUpdate(JSONObject... values) {
			setArtistInfo(values[0]);			
		}
       		
    }

	public void setArtistInfo(JSONObject artistInfo) {
		try {
			JSONArray arr = artistInfo.getJSONArray("data");
			JSONObject obj = arr.getJSONObject(0);
			System.out.println("arg!: "+obj.toString());
			this.about = obj.getString("about");
			this.likes = obj.getString("fan_count");
			this.website = obj.getString("website");
			this.page_url = obj.getString("page_url");
			this.name = obj.getString("name");
			this.about = obj.getString("about");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}   
}
	