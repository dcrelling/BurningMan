package com.burningman.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Art extends Expression implements Parcelable {

  private String artist;

  public static final Parcelable.Creator<Art> CREATOR = new Parcelable.Creator<Art>() {
    public Art createFromParcel(Parcel in) {
      return new Art(in);
    }

    public Art[] newArray(int size) {
      return new Art[size];
    }
  };

  public Art() {
    super();
  }

  private Art(Parcel in) {
    super();
    setId(in.readString());
    setName(in.readString());
    setDescription(in.readString());
    setContact_email(in.readString());
    setArtist(in.readString());
    setUrl(in.readString());
    setLatitude(in.readString());
    setLongitude(in.readString());
  }

  public String getArtist() {
    return artist;
  }
  public void setArtist(String artist) {
    this.artist = artist;
  }

  @Override
  public int describeContents() {
    // TODO Auto-generated method stub
    return 0;
  }
  @Override
  public void writeToParcel(Parcel dest, int flags) {
    // TODO Auto-generated method stub
    dest.writeString(getId());
    dest.writeString(getName());
    dest.writeString(getDescription());
    dest.writeString(getContact_email());
    dest.writeString(getArtist());
    dest.writeString(getUrl());
    dest.writeString(getLatitude());
    dest.writeString(getLongitude());

  }

}
