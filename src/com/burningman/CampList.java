package com.burningman;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.burningman.adapters.ExpressionListAdapter;
import com.burningman.beans.Camp;
import com.burningman.beans.Expression;
import com.burningman.contentproviders.HttpProvider;

public class CampList extends ListActivity {

  private ArrayList<Expression> campList = null;
  private ExpressionListAdapter expressionListAdapter;
  static final String EVENT_URL = "http://earth.burningman.com/api/0.1/2009/camp/";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    HttpProvider httpProvider = new HttpProvider();
    convertToCampList(httpProvider.getHttpContent(EVENT_URL, this));
    setContentView(R.layout.expressionlist);
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, campList);
    setListAdapter(expressionListAdapter);

    ListView campListView = getListView();
    campListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
        Intent intent = new Intent("com.burningman.CampDetail");
        intent.putExtra("campItem", (Parcelable) parent.getAdapter().getItem(postion));
        startActivity(intent);
      }
    });

  }

  private void convertToCampList(String page) {
    campList = new ArrayList<Expression>();
    try {
      // A Simple JSONArray Creation
      JSONArray jsonCampArray = new JSONArray(page);
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
