package com.burningman;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.burningman.adapters.ExpressionListAdapter;
import com.burningman.beans.Art;
import com.burningman.beans.Expression;
import com.burningman.contentproviders.HttpProvider;
import com.burningman.services.HttpServiceHelper;

public class ArtList extends ListActivity {

  private ArrayList<Expression> artList = null;
  private ExpressionListAdapter expressionListAdapter;
  public static final String ART_URL = "http://earth.burningman.com/api/0.1/2009/art/";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    //Intent intent = new Intent(this, HttpLocalService.class);
    //intent.putExtra("URL", ART_URL);
    //this.startService(intent);
    
    HttpServiceHelper helper =  new HttpServiceHelper(getBaseContext());
    helper.startService();
   
    
    HttpProvider httpProvider = new HttpProvider();
    convertToArtList(httpProvider.getHttpContent(ART_URL, this));
    setContentView(R.layout.expressionlist);
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, artList);
    setListAdapter(expressionListAdapter);

    ListView artListView = getListView();
    artListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
        Intent intent = new Intent("com.burningman.ArtDetail");
        intent.putExtra("artItem", (Parcelable) parent.getAdapter().getItem(postion));
        startActivity(intent);
      }
    });

  }

  private void convertToArtList(String page) {
    artList = new ArrayList<Expression>();
    try {
      // A Simple JSONArray Creation
      JSONArray jsonArtArray = new JSONArray(page);
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
  }
  
}
