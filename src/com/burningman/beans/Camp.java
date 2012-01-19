package com.burningman.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Camp extends Expression implements Parcelable {

  public Camp() {
    super();
  }

  public Camp(Parcel in) {
    super();
    setId(in.readString());
    setName(in.readString());
    setDescription(in.readString());
    setContact_email(in.readString());
    setUrl(in.readString());
  }

  public static final Parcelable.Creator<Camp> CREATOR = new Parcelable.Creator<Camp>() {
    public Camp createFromParcel(Parcel in) {
      return new Camp(in);
    }

    public Camp[] newArray(int size) {
      return new Camp[size];
    }
  };

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
    dest.writeString(getUrl());
  }

}
