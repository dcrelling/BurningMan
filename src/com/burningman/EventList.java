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

public class EventList extends ListActivity {

  private ArrayList<Parcelable> eventList = null;
  private ExpressionListAdapter expressionListAdapter;
  static final String EVENT_URL = "http://earth.burningman.com/api/0.1/2009/event/";
  private static final String TAG = "event";
  ProgressDialog dialog = null;

  private Handler myEventListDBHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.getData().getBoolean(DBLocalService.QUERY_RESULT_KEY)) {
        eventList = msg.getData().getParcelableArrayList(Expression.EXPRESSION_LIST_KEY);
        displayEventList();
      } else {
        consumeRestService();
      }
    }
  };

  private Handler myEventListHTTPHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.getData().getBoolean(HttpLocalService.HTTP_SERVICE_RESULT_KEY)) {
        getConvertRequestFromDB();
      }
    }
  };

  private void displayEventList() {
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, eventList);
    setListAdapter(expressionListAdapter);
    dialog.dismiss();
  }

  private void consumeRestService() {
    HttpServiceHelper httpServicehelper = new HttpServiceHelper();
    httpServicehelper.registerCallBackHandler(myEventListHTTPHandler);
    httpServicehelper.consumeRestService(EventList.EVENT_URL, EventList.TAG, this.getBaseContext());
  }

  private void getConvertRequestFromDB() {
    DBServiceHelper dBServiceHelper = new DBServiceHelper();
    dBServiceHelper.registerCallBackHandler(myEventListDBHandler);
    dBServiceHelper.executeOperation(EventList.TAG, this.getBaseContext(), DBServiceHelper.GET_CONV_REQUEST_DATA);
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dialog = ProgressDialog.show(this, "", 
        "Loading. Please wait...", true);
    getConvertRequestFromDB();
    setContentView(R.layout.expressionlist);
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
}
