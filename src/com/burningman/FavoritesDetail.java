package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
}
