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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.burningman.adapters.ExpressionListAdapter;
import com.burningman.beans.Expression;
import com.burningman.services.DBLocalService;
import com.burningman.services.DBServiceHelper;

public class FavoritesList extends ListActivity {

  private ArrayList<Parcelable> favoritesList = null;
  private ExpressionListAdapter expressionListAdapter;
  private static final String TAG = "favorites";
  ProgressDialog dialog = null;

  private Handler myFavoritesListDBHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED)))) {
        favoritesList = msg.getData().getParcelableArrayList(Expression.EXPRESSION_LIST_KEY);
        displayFavoritesList();
      } else if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED)))) {
        dialog.dismiss();
        Context context = getApplicationContext();
        CharSequence text = "No Favorites Found";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
      } else if (((msg.getData().getBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND)) && (msg.getData()
          .getBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED)))) {
        dialog.dismiss();
        Context context = getApplicationContext();
        CharSequence text = "Database Error: Data could not be retrieved from database";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
      }
    }
  };

  private void displayFavoritesList() {
    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, favoritesList);
    setListAdapter(expressionListAdapter);
    dialog.dismiss();
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
    getFavoritesFromDB();
    setContentView(R.layout.expressionlist);
    ListView favoritesListView = getListView();
    favoritesListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
        Intent intent = new Intent("com.burningman.FavoritesDetail");
        Expression expressionItem = (Expression) parent.getAdapter().getItem(postion);
        Parcelable favoriteItem = (Parcelable) parent.getAdapter().getItem(postion);
        intent.putExtra("favoritesItem", favoriteItem);
        intent.putExtra("latitude", expressionItem.getLatitude());
        intent.putExtra("longitude", expressionItem.getLongitude());
        startActivity(intent);
      }
    });

  }

  private void getFavoritesFromDB() {
    DBServiceHelper dBServiceHelper = new DBServiceHelper();
    dBServiceHelper.registerCallBackHandler(myFavoritesListDBHandler);
    dBServiceHelper.executeOperation(FavoritesList.TAG, this.getBaseContext(), DBServiceHelper.GET_FAVORITES);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.main_menu, menu);
    MenuItem favoritesMenuItem = menu.findItem(R.id.favorites_menu);
    favoritesMenuItem.setVisible(false);
    favoritesMenuItem.setEnabled(false);
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
      case R.id.camps_menu :
        displayCampList();
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

  protected void displayEventList() {
    Intent intent = new Intent("com.burningman.EventList");
    startActivity(intent);
  }

  protected void displayCampList() {
    Intent intent = new Intent("com.burningman.CampList");
    startActivity(intent);
  }

  protected void displayHome() {
    Intent intent = new Intent("com.burningman.BurningMan");
    startActivity(intent);
  }

  protected void displayMap() {
    Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
    intent.putExtra("latitude", "40.78231");
    intent.putExtra("longitude", "-119.21282");
    startActivity(intent);
  }

}
