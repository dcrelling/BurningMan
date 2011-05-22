package com.burningman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.burningman.adapters.ExpressionDetailAdapter;

public class FavoritesDetail extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    ExpressionDetailAdapter eda = new ExpressionDetailAdapter(this, data.getParcelable("favoritesItem"));
    setContentView(eda.mapToDetailView());
    findViewById(R.id.ButtonFavorite).setVisibility(View.INVISIBLE);

  }
}
