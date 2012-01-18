package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.burningman.adapters.ExpressionDetailAdapter;
import com.burningman.beans.Art;
import com.burningman.contentproviders.BurningmanDBAdapter;

public class ArtDetail extends Activity {

  private Art artItem;
  private static final String ITEM_ADDED = "Has Been Added To Your Favorites";
  private static final String ITEM_ALREDY_ADDED = "Is Already In Your Favorites";
  private static final String ERROR = "ERROR: A Error Was Encountered Could Not Add To Favorites";

  /** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    artItem = data.getParcelable("artItem");
    ExpressionDetailAdapter eda = new ExpressionDetailAdapter(this, artItem);
    setContentView(eda.mapToDetailView());

    // --- Favorite Button view---
    Button addFavoriteButton = (Button) findViewById(R.id.ButtonFavorite);
    addFavoriteButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(v.getContext());
        try {
          dbAdapter.open();
          if (dbAdapter.getFavorite(artItem.getId()).getCount() <= 0) {
            dbAdapter.insertFavorite(artItem.getId(), artItem.getName(), "art", artItem.getContact_email(),
                artItem.getUrl(), artItem.getDescription());
            Toast.makeText(getApplicationContext(), artItem.getName() + " " + ITEM_ADDED, Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getApplicationContext(), artItem.getName() + " " + ITEM_ALREDY_ADDED, Toast.LENGTH_SHORT)
            .show();
          }
        } catch (SQLException e) {
          Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
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
    
    //-- Map It Button View --
    Button mapItButton = (Button) findViewById(R.id.ButtonMap);
    mapItButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
        intent.putExtra("latitude", artItem.getLatitude());
        intent.putExtra("longitude", artItem.getLongitude());
        startActivity(intent);
      }
    });
    
  }
}
