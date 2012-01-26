package com.burningman;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
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
      if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED)))) {
        eventList = msg.getData().getParcelableArrayList(Expression.EXPRESSION_LIST_KEY);
        displayEventList();
      } else if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED)))) {
        consumeRestService();
      } else if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED)))) {
        dialog.dismiss();
        Context context = getApplicationContext();
        CharSequence text = "Database Error: Data could not be retrieved";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
      }
    }
  };

  private Handler myEventListHTTPHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.getData().getBoolean(HttpLocalService.HTTP_SERVICE_SUCESSFULL)) {
        getConvertRequestFromDB();
      } else if (msg.getData().getBoolean(HttpLocalService.HTTP_SERVICE_FAILURE)) {
        dialog.dismiss();
        Context context = getApplicationContext();
        CharSequence text = "HTTP Error: Data could not be retrieved from service";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
    dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.main_menu, menu);
    MenuItem eventsMenuItem = menu.findItem(R.id.events_menu);
    eventsMenuItem.setVisible(false);
    eventsMenuItem.setEnabled(false);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.home_menu :
        displayHome();
        return true;
      case R.id.art_menu :
        displayArtList();
        return true;
      case R.id.camps_menu :
        displayCampList();
        return true;
      case R.id.favorites_menu :
        displayFavortiesList();
        return true;
      case R.id.map_menu :
        displayMap();
        return true;
      default :
        return super.onOptionsItemSelected(item);
    }

  }

  protected void displayArtList() {
    Intent intent = new Intent("com.burningman.ArtList");
    startActivity(intent);
  }

  protected void displayHome() {
    Intent intent = new Intent("com.burningman.BurningMan");
    startActivity(intent);
  }

  protected void displayCampList() {
    Intent intent = new Intent("com.burningman.CampList");
    startActivity(intent);
  }

  protected void displayFavortiesList() {
    Intent intent = new Intent("com.burningman.FavoritesList");
    startActivity(intent);
  }

  protected void displayMap() {
    Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
    intent.putExtra("latitude", "40.78231");
    intent.putExtra("longitude", "-119.21282");
    startActivity(intent);
  }
}
