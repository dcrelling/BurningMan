package com.burningman.beans;

import android.os.Parcel;
import android.os.Parcelable;


public class Event extends Expression implements Parcelable {

  private String title;

  public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
    public Event createFromParcel(Parcel in) {
      return new Event(in);
    }

    public Event[] newArray(int size) {
      return new Event[size];
    }
  };

  public Event() {
    super();
  }

  private Event(Parcel in) {
    super();
    setId(in.readString());
    setName(in.readString());
    setDescription(in.readString());
    setContact_email(in.readString());
    setTitle(in.readString());
    setSlug(in.readString());
    setUrl(in.readString());
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
    dest.writeString(getTitle());
    dest.writeString(getSlug());
    dest.writeString(getUrl());

  }

}
