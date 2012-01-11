package com.burningman.converters;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcelable;

import com.burningman.beans.Art;
import com.burningman.beans.Camp;

public class RequestConverter {


	public ArrayList<Parcelable> convertRequest(String request, String expressionType){
	  ArrayList<Parcelable> list = new ArrayList<Parcelable>();
	  if(expressionType.equalsIgnoreCase("art")){
	    list =  convertToArtList(request);
	  }else if(expressionType.equalsIgnoreCase("camp")){
	    list =  convertToCampList(request);
	  }
    return list;
	}

	private ArrayList<Parcelable> convertToArtList(String request){
		ArrayList<Parcelable> artList = new ArrayList<Parcelable>();
	    try {
	      // A Simple JSONArray Creation
	      JSONArray jsonArtArray = new JSONArray(request);
	      // A Simple JSONObject Parsing
	      Art art = null;
	      for (int i = 0; i < jsonArtArray.length(); i++) {
	        art = new Art();
	        JSONObject jsonArtObject = (JSONObject) jsonArtArray.get(i);
	        art.setArtist(jsonArtObject.optString("artist"));
	        art.setCircular_street(jsonArtObject.optString("circular_street"));
	        art.setContact_email(jsonArtObject.optString("contact_email"));
	        art.setDescription(jsonArtObject.optString("description"));
	        art.setId(jsonArtObject.optString("id"));
	        art.setName(jsonArtObject.optString("name"));
	        art.setSlug(jsonArtObject.optString("slug"));
	        art.setUrl(jsonArtObject.optString("url"));
	        artList.add(art);
	    
	        /*
	         * JSONArray nameArray = json_art_object.names(); JSONArray valArray =json_art_object.toJSONArray(nameArray);
	         * for(int j=0; j<valArray.length(); j++) {
	         * Log.i("Praeda","<jsonname"+j+">\n"+nameArray.getString(j)+"\n</jsonname"+j+">\n"
	         * +"<jsonvalue"+j+">\n"+valArray.getString(j)+"\n</jsonvalue"+j+">"); }
	         */
	      }
	      // A Simple JSONObject Value Pushing
	      // json.put("sample key", "sample value");
	      // Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
	    } catch (JSONException e) {
	      // TODO Auto-generated catch block
	    }
	    return artList;
	}

	//private ArrayList<Parcelable> convertToEventList(){

	//}

	private ArrayList<Parcelable> convertToCampList(String request){
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
}
