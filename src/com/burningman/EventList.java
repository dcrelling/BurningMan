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
import com.burningman.beans.Event;
import com.burningman.contentproviders.HttpProvider;

public class EventList extends ListActivity {

  private ArrayList<Expression> eventList = null;
  private ExpressionListAdapter expressionListAdapter;
  static final String EVENT_URL = "http://earth.burningman.com/api/0.1/2009/event/";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    HttpProvider httpProvider = new HttpProvider();
    convertToEventList(httpProvider.getHttpContent(EVENT_URL, this));
    setContentView(R.layout.expressionlist);
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, eventList);
    setListAdapter(expressionListAdapter);
    ListView eventListView = getListView();
    eventListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
        Intent intent = new Intent("com.burningman.EventDetail");
        intent.putExtra("eventItem", (Parcelable) parent.getAdapter().getItem(postion));
        startActivity(intent);
      }
    });

  }

  private void convertToEventList(String page) {
    eventList = new ArrayList<Expression>();
    try {
      // A Simple JSONArray Creation
      JSONArray jsonEventArray = new JSONArray(page);
      // A Simple JSONObject Parsing
      for (int i = 0; i < jsonEventArray.length(); i++) {
        Event event = new Event();
        JSONObject jsonEventObject = (JSONObject) jsonEventArray.get(i);
        event.setTitle(jsonEventObject.optString("title"));
        event.setDescription(jsonEventObject.optString("description"));
        event.setId(jsonEventObject.optString("id"));
        event.setUrl(jsonEventObject.optString("url"));

        eventList.add(event);
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
