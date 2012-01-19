package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.burningman.adapters.ExpressionDetailAdapter;
import com.burningman.beans.Camp;
import com.burningman.contentproviders.BurningmanDBAdapter;

public class CampDetail extends Activity {

  private Camp campItem;
  private static final String ITEM_ADDED = "Has Been Added To Your Favorites";
  private static final String ITEM_ALREDY_ADDED = "Is Already In Your Favorites";
  private static final String ERROR = "ERROR: A Error Was Encountered Could Not Add To Favorites";
  private static final String NO_LOCATION = "This item has no location information associated to it. Can't map it!";

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
        try {
          dbAdapter.open();
          if (dbAdapter.getFavorite(campItem.getId()).getCount() <= 0) {
            dbAdapter.insertFavorite(campItem.getId(), campItem.getName(), "camp", campItem.getContact_email(),
                campItem.getUrl(), campItem.getDescription(), campItem.getLatitude(), campItem.getLongitude());
            Toast.makeText(getApplicationContext(), campItem.getName() + " " + ITEM_ADDED, Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getApplicationContext(), campItem.getName() + " " + ITEM_ALREDY_ADDED, Toast.LENGTH_SHORT)
            .show();
          }
        } catch (SQLException e) {
          Log.v("CampDetail ", e.toString());
          Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          Log.v("CampDetail ", e.toString());
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
        if(((campItem.getLatitude() != null && !campItem.getLatitude().equals("")) && (campItem.getLongitude() != null && !campItem.getLongitude().equals(""))) ){
          Intent intent = new Intent("com.burningman.OpenStreetMapActivity");
          intent.putExtra("latitude", campItem.getLatitude());
          intent.putExtra("longitude", campItem.getLongitude());
          startActivity(intent);
        }else{
          Toast.makeText(getApplicationContext(), NO_LOCATION, Toast.LENGTH_SHORT).show();
        }
      }
    });

  }

}
