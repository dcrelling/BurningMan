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

public class CampList extends ListActivity {

  private ArrayList<Parcelable> campList = null;
  private ExpressionListAdapter expressionListAdapter;
  static final String CAMP_URL = "http://earth.burningman.com/api/0.1/2009/camp/";
  private static final String TAG = "camp";
  ProgressDialog dialog = null;

  private Handler myCampListDBHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED)))) {
        campList = msg.getData().getParcelableArrayList(Expression.EXPRESSION_LIST_KEY);
        displayCampList();
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

  private Handler myCampListHTTPHandler = new Handler() {
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

  private void displayCampList() {
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, campList);
    setListAdapter(expressionListAdapter);
    dialog.dismiss();
  }

  private void consumeRestService() {
    HttpServiceHelper httpServicehelper = new HttpServiceHelper();
    httpServicehelper.registerCallBackHandler(myCampListHTTPHandler);
    httpServicehelper.consumeRestService(CampList.CAMP_URL, CampList.TAG, this.getBaseContext());
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
    getConvertRequestFromDB();
    setContentView(R.layout.expressionlist);
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

  private void getConvertRequestFromDB() {
    DBServiceHelper dBServiceHelper = new DBServiceHelper();
    dBServiceHelper.registerCallBackHandler(myCampListDBHandler);
    dBServiceHelper.executeOperation(CampList.TAG, this.getBaseContext(), DBServiceHelper.GET_CONV_REQUEST_DATA);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.main_menu, menu);
    MenuItem campMenuItem = menu.findItem(R.id.camps_menu);
    campMenuItem.setVisible(false);
    campMenuItem.setEnabled(false);
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
      case R.id.events_menu :
        displayEventList();
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

  protected void displayHome() {
    Intent intent = new Intent("com.burningman.BurningMan");
    startActivity(intent);
  }

  protected void displayEventList() {
    Intent intent = new Intent("com.burningman.EventList");
    startActivity(intent);
  }

  protected void displayArtList() {
    Intent intent = new Intent("com.burningman.ArtList");
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
