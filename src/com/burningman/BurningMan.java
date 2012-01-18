package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class BurningMan extends Activity {

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.start_screen);

    
    /*
    // --- Camp Button view---
    ImageButton launchButton = (ImageButton) findViewById(R.id.LaunchButton01);
    launchButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.Home");
        startActivity(intent);
      }
    }); */

  } 
  

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.layout.main_menu, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
    case R.id.art_menu:
        displayArtList();
        return true;
    case R.id.events_menu:
        displayEventList();
        return true;
    case R.id.camps_menu:
      displayCampList();
      return true;
    case R.id.favorites_menu:
      displayFavortiesList();
      return true;
    case R.id.map_menu:
      displayMap();
      return true;
    default:
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

  protected void displayFavortiesList() {
    Intent intent = new Intent("com.burningman.FavoritesList");
    startActivity(intent);
  }

  protected void displayMap() {
    Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
    startActivity(intent);
  }

}
