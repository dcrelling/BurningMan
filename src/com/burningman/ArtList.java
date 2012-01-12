package com.burningman;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
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
import com.burningman.beans.Expression;
import com.burningman.services.DBLocalService;
import com.burningman.services.DBServiceHelper;
import com.burningman.services.HttpLocalService;
import com.burningman.services.HttpServiceHelper;

public class ArtList extends ListActivity {

  private ArrayList<Parcelable> artList = null;
  private ExpressionListAdapter expressionListAdapter;
  private static final String ART_URL = "http://earth.burningman.com/api/0.1/2009/art/";
  private static final String TAG = "art";
  ProgressDialog dialog = null;
  
 
    
    private  Handler myArtListDBHandler = new Handler(){
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);  
        if(msg.getData().getBoolean(DBLocalService.QUERY_RESULT_KEY)){
          artList = msg.getData().getParcelableArrayList(Expression.EXPRESSION_LIST_KEY);
          displayArtList();
        }else{
          consumeRestService();
        } 
      }
  };       
  
  private  Handler myArtListHTTPHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);  
        if(msg.getData().getBoolean(HttpLocalService.HTTP_SERVICE_RESULT_KEY)){
            getConvertRequestFromDB();
          }
        }  
  };       
    
    
  private void displayArtList(){
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, artList);
    setListAdapter(expressionListAdapter);
    dialog.dismiss();
  }
  
  private void consumeRestService(){
    HttpServiceHelper httpServicehelper =  new HttpServiceHelper();
    httpServicehelper.registerCallBackHandler(myArtListHTTPHandler);
    httpServicehelper.consumeRestService(ArtList.ART_URL, ArtList.TAG, this.getBaseContext());
  }
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dialog = ProgressDialog.show(this, "", 
        "Loading. Please wait...", true);
    getConvertRequestFromDB();
    setContentView(R.layout.expressionlist);
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

  private void getConvertRequestFromDB(){
    DBServiceHelper dBServiceHelper = new DBServiceHelper();
    dBServiceHelper.registerCallBackHandler(myArtListDBHandler);
    dBServiceHelper.executeOperation(ArtList.TAG, this.getBaseContext(), DBServiceHelper.GET_CONV_REQUEST_DATA);
  }
  
}
