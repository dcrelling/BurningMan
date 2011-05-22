package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Home extends Activity {

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home_screen);
    // --- Art Button view---
    ImageButton artButton = (ImageButton) findViewById(R.id.ArtButton01);
    artButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.ArtList");
        startActivity(intent);
      }
    });

    // --- Event Button view---
    ImageButton eventButton = (ImageButton) findViewById(R.id.EventsButton02);
    eventButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.EventList");
        startActivity(intent);
      }
    });

    // --- Camp Button view---
    ImageButton campButton = (ImageButton) findViewById(R.id.CampButton03);
    campButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.CampList");
        startActivity(intent);
      }
    });

    // --- Favorite Button view---
    ImageButton favoritesButton = (ImageButton) findViewById(R.id.FavoritesButton04);
    favoritesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.FavoritesList");
        startActivity(intent);
      }
    });
  }

}
