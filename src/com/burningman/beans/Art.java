package com.burningman.beans;

import android.os.Parcel;
import android.os.Parcelable;


public class Art extends Expression implements Parcelable {

  private String circular_street;
  private String artist;
  private String time_address;
  private String location_point;
  private String location_poly;
  private String location_string;

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
    setCircular_street(in.readString());
    setSlug(in.readString());
    setUrl(in.readString());
  }

  public String getCircular_street() {
    return circular_street;
  }
  public void setCircular_street(String circular_street) {
    this.circular_street = circular_street;
  }
  public String getArtist() {
    return artist;
  }
  public void setArtist(String artist) {
    this.artist = artist;
  }
  public String getTime_address() {
    return time_address;
  }
  public void setTime_address(String time_address) {
    this.time_address = time_address;
  }
  public String getLocation_point() {
    return location_point;
  }
  public void setLocation_point(String location_point) {
    this.location_point = location_point;
  }
  public String getLocation_poly() {
    return location_poly;
  }
  public void setLocation_poly(String location_poly) {
    this.location_poly = location_poly;
  }
  public String getLocation_string() {
    return location_string;
  }
  public void setLocation_string(String location_string) {
    this.location_string = location_string;
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
    dest.writeString(getCircular_street());
    dest.writeString(getSlug());
    dest.writeString(getUrl());

  }

}
