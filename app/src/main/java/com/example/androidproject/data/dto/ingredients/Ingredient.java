package com.example.androidproject.data.dto.ingredients;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

	@SerializedName("strDescription")
	private String strDescription;

	@SerializedName("strIngredient")
	private String strIngredient;

	@SerializedName("strType")
	private Object strType;

	@SerializedName("idIngredient")
	private String idIngredient;

	public String getStrDescription(){
		return strDescription;
	}

	public String getStrIngredient(){
		return strIngredient;
	}

	public Object getStrType(){
		return strType;
	}

	public String getIdIngredient(){
		return idIngredient;
	}

	public Ingredient()
	{


	}
	protected Ingredient(Parcel in) {
		strDescription = in.readString();
		strIngredient = in.readString();
		idIngredient = in.readString();
		strType = in.readParcelable(Object.class.getClassLoader());
	}

	public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
		@Override
		public Ingredient createFromParcel(Parcel in) {
			return new Ingredient(in);
		}

		@Override
		public Ingredient[] newArray(int size) {
			return new Ingredient[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(strDescription);
		dest.writeString(strIngredient);
		dest.writeString(idIngredient);
		dest.writeParcelable((Parcelable) strType, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}