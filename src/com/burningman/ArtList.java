package com.burningman;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.burningman.adapters.ExpressionListAdapter;
import com.burningman.services.DBServiceHelper;
import com.burningman.services.HttpServiceHelper;

public class ArtList extends ListActivity {

  private ArrayList<Parcelable> artList = null;
  private ExpressionListAdapter expressionListAdapter;
  private static final String ART_URL = "http://earth.burningman.com/api/0.1/2009/art/";
  private static final String TAG = "art";
  
 
    
    private  Handler myHandler = new Handler(){
      @Override
      public void handleMessage(Message msg) {
          if(msg.getData().getBoolean("success")){
        	artList = msg.getData().getParcelableArrayList("expressionList");
            displayArtList();
          }else{
            consumeRestService();
            getConvertRequestFromDB();
          }
          super.handleMessage(msg);
      }
  };       
    
    
  private void displayArtList(){
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, artList);
    setListAdapter(expressionListAdapter);
  }
  
  private void consumeRestService(){
    HttpServiceHelper helper =  new HttpServiceHelper();
    helper.consumeRestService(ArtList.ART_URL, this.getBaseContext());
  }
  
  

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getConvertRequestFromDB();
    

    
    
    /*
    String artRequestValue = getArtRequestFromDB();
    if(artRequestValue != null){
      convertToArtList(artRequestValue);
    }else{ 
      HttpServiceHelper helper =  new HttpServiceHelper();
      helper.consumeRestService(ArtList.ART_URL, this.getBaseContext());
    }
    */
    
    //HttpProvider httpProvider = new HttpProvider();
    //convertToArtList(httpProvider.getHttpContent(ART_URL, this));
    
    setContentView(R.layout.expressionlist);
    //expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, artList);
    //setListAdapter(expressionListAdapter);

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
/*
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
  /*
      }
      // A Simple JSONObject Value Pushing
      // json.put("sample key", "sample value");
      // Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
    } catch (JSONException e) {
      // TODO Auto-generated catch block
    }
  } */
  
  /*
  public String getArtRequestFromDB(){
    BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(this);
    dbAdapter.open();
    Cursor ArtListCursor = dbAdapter.getRestRequests(ArtList.TAG);
    if (ArtListCursor != null) {
      if (ArtListCursor.getCount() > 0) {
        ArtListCursor.moveToFirst();
        String artRequestValue = ArtListCursor.getString(ArtListCursor
            .getColumnIndex(RestRequestMetaData.REST_REQUEST_VALUE));
        dbAdapter.close();
        dbAdapter = null;
        return artRequestValue;
      }
    }
    dbAdapter.close();
    dbAdapter = null;
    return null;
  } */
  
  private void getConvertRequestFromDB(){
    DBServiceHelper dBQueryHelper = new DBServiceHelper();
    dBQueryHelper.registerCallBackHandler(myHandler);
    dBQueryHelper.executeOperation(ArtList.TAG, this.getBaseContext(), "getConvertRequest");
  }
  
}
