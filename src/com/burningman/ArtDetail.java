package com.burningman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.burningman.adapters.ExpressionDetailAdapter;
import com.burningman.beans.Art;
import com.burningman.contentproviders.BurningmanDBAdapter;

public class ArtDetail extends Activity {

  private Art artItem;

  /** Called when the activity is first created. */
  /** Test Of Git */
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    artItem = data.getParcelable("artItem");
    ExpressionDetailAdapter eda = new ExpressionDetailAdapter(this, artItem);
    setContentView(eda.mapToDetailView());

    // --- Event Button view---
    Button eventButton = (Button) findViewById(R.id.ButtonFavorite);
    eventButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(v.getContext());
        dbAdapter.open();
        if (dbAdapter.getFavorite(artItem.getId()).getCount() <= 0) {
          dbAdapter.insertFavorite(artItem.getId(), artItem.getName(), "art", artItem.getContact_email(), artItem
              .getUrl(), artItem.getDescription());
          Toast.makeText(getApplicationContext(), artItem.getName() + " Has Been Added To Your Favorites",
              Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getApplicationContext(), artItem.getName() + " Is Already In Your Favorites",
              Toast.LENGTH_SHORT).show();
        }
        dbAdapter.close();
        dbAdapter = null;
      }
    });

  }

}
