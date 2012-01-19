package com.burningman.converters;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcelable;

import com.burningman.beans.Art;
import com.burningman.beans.Camp;
import com.burningman.beans.Event;

public class RequestConverter {

  public ArrayList<Parcelable> convertRequest(String request, String expressionType) {
    ArrayList<Parcelable> list = new ArrayList<Parcelable>();
    if (expressionType.equalsIgnoreCase("art")) {
      list = convertToArtList(request);
    } else if (expressionType.equalsIgnoreCase("camp")) {
      list = convertToCampList(request);
    } else if (expressionType.equalsIgnoreCase("event")) {
      list = convertToEventList(request);
    }
    return list;
  }

  private ArrayList<Parcelable> convertToArtList(String request) {
    ArrayList<Parcelable> artList = new ArrayList<Parcelable>();
    try {
      // A Simple JSONArray Creation
      JSONArray jsonArtArray = new JSONArray(request);
      // A Simple JSONObject Parsing
      Art art = null;
      JSONObject locationPointObject = null;
      for (int i = 0; i < jsonArtArray.length(); i++) {
        art = new Art();
        JSONObject jsonArtObject = (JSONObject) jsonArtArray.get(i);
        art.setArtist(jsonArtObject.optString("artist"));
        art.setContact_email(jsonArtObject.optString("contact_email"));
        art.setDescription(jsonArtObject.optString("description"));
        art.setId(jsonArtObject.optString("id"));
        art.setName(jsonArtObject.optString("name"));
        art.setUrl(jsonArtObject.optString("url"));
        String locationPoint = jsonArtObject.optString("location_point");
        if (!(locationPoint.equalsIgnoreCase("") || locationPoint.equalsIgnoreCase("null"))) {
          locationPointObject = new JSONObject(locationPoint);
          if (locationPointObject != null) {
            JSONArray coordinatesArray = locationPointObject.optJSONArray("coordinates");
            if (coordinatesArray != null) {
              art.setLongitude(coordinatesArray.getString(0));
              art.setLatitude(coordinatesArray.getString(1));
            }
          }
        }
        artList.add(art);
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
    }
    return artList;
  }

  private ArrayList<Parcelable> convertToCampList(String request) {
    ArrayList<Parcelable> campList = new ArrayList<Parcelable>();
    try {
      // A Simple JSONArray Creation
      JSONArray jsonCampArray = new JSONArray(request);
      // A Simple JSONObject Parsing
      Camp camp = null;
      for (int i = 0; i < jsonCampArray.length(); i++) {
        camp = new Camp();
        JSONObject jsonCampObject = (JSONObject) jsonCampArray.get(i);
        camp.setId(jsonCampObject.optString("id"));
        camp.setName(jsonCampObject.optString("name"));
        camp.setDescription(jsonCampObject.optString("description"));
        camp.setContact_email(jsonCampObject.optString("contact_email"));
        camp.setUrl(jsonCampObject.optString("url"));
        campList.add(camp);
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
    }
    return campList;
  }

  private ArrayList<Parcelable> convertToEventList(String request) {
    ArrayList<Parcelable> eventList = new ArrayList<Parcelable>();
    try {
      // A Simple JSONArray Creation
      JSONArray jsonEventArray = new JSONArray(request);
      // A Simple JSONObject Parsing
      Event event = null;
      for (int i = 0; i < jsonEventArray.length(); i++) {
        event = new Event();
        JSONObject jsonEventObject = (JSONObject) jsonEventArray.get(i);
        event.setTitle(jsonEventObject.optString("title"));
        event.setDescription(jsonEventObject.optString("description"));
        event.setId(jsonEventObject.optString("id"));
        event.setUrl(jsonEventObject.optString("url"));
        eventList.add(event);
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
    }
    return eventList;
  }
}
