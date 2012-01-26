package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.burningman.adapters.ExpressionDetailAdapter;

public class FavoritesDetail extends Activity {

  private String latitude = "";
  private String longitude = "";
  private static final String NO_LOCATION = "This item has no location information associated to it. Can't map it!";

 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    latitude = data.getString("latitude");
    longitude = data.getString("longitude");
    ExpressionDetailAdapter eda = new ExpressionDetailAdapter(this, data.getParcelable("favoritesItem"));
    setContentView(eda.mapToDetailView());
    findViewById(R.id.ButtonFavorite).setVisibility(View.INVISIBLE);

    //-- Map It Button View --
    Button mapItButton = (Button) findViewById(R.id.ButtonMap);
    mapItButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if(((latitude != null && !latitude.equals("")) && (longitude != null && !longitude.equals(""))) ){
        Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
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
