package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.burningman.adapters.ExpressionDetailAdapter;
import com.burningman.beans.Camp;
import com.burningman.contentproviders.BurningmanDBAdapter;

public class CampDetail extends Activity {

  private Camp campItem;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    campItem = data.getParcelable("campItem");
    ExpressionDetailAdapter eda = new ExpressionDetailAdapter(this, campItem);
    setContentView(eda.mapToDetailView());

    // --- Event Button view---
    Button addFavoriteButton = (Button) findViewById(R.id.ButtonFavorite);
    addFavoriteButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(v.getContext());
        dbAdapter.open();
        if (dbAdapter.getFavorite(campItem.getId()).getCount() <= 0) {
          dbAdapter.insertFavorite(campItem.getId(), campItem.getName(), "camp", campItem.getContact_email(), campItem
              .getUrl(), campItem.getDescription());
          Toast.makeText(getApplicationContext(), campItem.getName() + " Has Been Added To Your Favorites",
              Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplicationContext(), campItem.getName() + " Is Already In Your Favorites",
              Toast.LENGTH_SHORT).show();
        }
        dbAdapter.close();
        dbAdapter = null;
      }
    });
    
    //-- Map It Button View --
    Button mapItButton = (Button) findViewById(R.id.ButtonMap);
    mapItButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
        intent.putExtra("latitude", campItem.getLatitude());
        intent.putExtra("longitude", campItem.getLongitude());
        startActivity(intent);
      }
    });

  }

}
