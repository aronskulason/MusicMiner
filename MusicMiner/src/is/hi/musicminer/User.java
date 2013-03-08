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

import android.os.Parcel;
import android.os.Parcelable;

//Contains information about the user, including 
//major information from facebook.

public class User extends Item {

	public User(String id, int match) {
		this.id = id;
		this.match = match;
		build();
	}

	@Override
	protected void build() {
		System.out.println("ID: "+this.id);
		this.name = this.id;
	}
	
	// The following is to implement Parcelable
	
	public int describeContents() {
        return 0;
    }
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeInt(match);
        out.writeString(name);
        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // get the values back
    private User(Parcel in) {
        id = in.readString();
        match = in.readInt();
        name = in.readString();
    }

}
