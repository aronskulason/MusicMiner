package is.hi.musicminer;

import android.content.Context;
import android.content.Intent;

public class GetDataArtistsFromUser extends GetData{
	
	public GetDataArtistsFromUser(Context context) {

		super(context);
	}
	@Override
	protected void onProgressUpdate(String... string) {
		((UserActivity) context).build(string[0]);
	}

}
