package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.burningman.adapters.ExpressionDetailAdapter;
import com.burningman.beans.Event;
import com.burningman.contentproviders.BurningmanDBAdapter;

public class EventDetail extends Activity {
  private Event eventItem;
  private static final String ITEM_ADDED = "Has Been Added To Your Favorites";
  private static final String ITEM_ALREDY_ADDED = "Is Already In Your Favorites";
  private static final String ERROR = "ERROR: A Error Was Encountered Could Not Add To Favorites";
  private static final String NO_LOCATION = "This item has no location information associated to it. Can't map it!";


  /** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    eventItem = data.getParcelable("eventItem");
    ExpressionDetailAdapter eda = new ExpressionDetailAdapter(this, eventItem);
    setContentView(eda.mapToDetailView());

    // --- Favorite Button view---
    Button addFavoriteButton = (Button) findViewById(R.id.ButtonFavorite);
    addFavoriteButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(v.getContext());
        try {
          dbAdapter.open();
          if (dbAdapter.getFavorite(eventItem.getId()).getCount() <= 0) {
            dbAdapter.insertFavorite(eventItem.getId(), eventItem.getTitle(), "event", eventItem.getContact_email(),
                eventItem.getUrl(), eventItem.getDescription(), eventItem.getLatitude(), eventItem.getLongitude());
            Toast.makeText(getApplicationContext(), eventItem.getTitle() + " " + ITEM_ADDED, Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getApplicationContext(), eventItem.getTitle() + " " + ITEM_ALREDY_ADDED, Toast.LENGTH_SHORT)
                .show();
          }
        } catch (SQLException e) {
          Log.v("EventDetail ", e.toString());
          Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          Log.v("EventDetail ", e.toString());
          Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show();
        }

        if (dbAdapter != null) {
          dbAdapter.close();
        }

        if (dbAdapter != null) {
          dbAdapter.close();
          dbAdapter = null;
        }
      }
    });

    // -- Map It Button View --
    Button mapItButton = (Button) findViewById(R.id.ButtonMap);
    mapItButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if(((eventItem.getLatitude() != null && !eventItem.getLatitude().equals("")) && (eventItem.getLongitude() != null && !eventItem.getLongitude().equals(""))) ){
          Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
          intent.putExtra("latitude", eventItem.getLatitude());
          intent.putExtra("longitude", eventItem.getLongitude());
          startActivity(intent);
        }else{
          Toast.makeText(getApplicationContext(), NO_LOCATION, Toast.LENGTH_SHORT).show();
        }
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
