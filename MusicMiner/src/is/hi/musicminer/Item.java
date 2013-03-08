/*****************************************************
 * 		Suggester									**
 * 													**
 * 		Authors: Aron Sk�lason						**
 * 				 Bjarni Gr�tar Ing�lfsson			**
 * 				 Sigur�ur �li �rnason				**
 * 				 Tinna Fr�mann J�kulsd�ttir			**
 * 													**
 *****************************************************/
package is.hi.musicminer;

import android.os.Parcel;
import android.os.Parcelable;

//This is a parent class for items that will be suggested to the user

public abstract class Item implements Parcelable {
	
	// The items facebook ID
	protected String id;
	// The items public name
	protected String name;
	//The match percentage computed for the item (0<=match<=100)
	protected int match;
	
	// For Parcelable
	private int mData;
	
	
	//Use: build();
	//After: The items name is set.
	protected abstract void build();
	
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}	
	public int getMatch() {
		return this.match;
	}
}
