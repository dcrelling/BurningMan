package com.burningman;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.burningman.adapters.ExpressionListAdapter;
import com.burningman.beans.Art;
import com.burningman.beans.Camp;
import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.BurningmanDBAdapter.FavoritesMetaData;

public class FavoritesList extends ListActivity {

  private ArrayList<Parcelable> favoritesList = null;
  private ExpressionListAdapter expressionListAdapter;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.expressionlist);
    BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(this);
    dbAdapter.open();
    Cursor favoritesCursor = dbAdapter.getAllFavorites();
    if (favoritesCursor != null) {
      if (favoritesCursor.getCount() > 0) {
        Art art = null;
        Camp camp = null;
        favoritesList = new ArrayList<Parcelable>();
        favoritesCursor.moveToFirst();
        while (favoritesCursor.isAfterLast() == false) {
          String expressionType = favoritesCursor.getString(favoritesCursor
              .getColumnIndex(FavoritesMetaData.FAVORITE_TYPE));
          if (expressionType.equalsIgnoreCase("art")) {
            art = new Art();
            art.setDescription(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_DESCRIPTION)));
            art.setId(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_EXPRESSION_ID)));
            art.setName(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_NAME)));
            art.setContact_email(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_CONTACT_EMAIL)));
            art.setUrl(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_URL)));
            favoritesList.add(art);
          } else if (expressionType.equalsIgnoreCase("camp")) {
            camp = new Camp();
            camp.setId(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_EXPRESSION_ID)));
            camp.setName(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_NAME)));
            camp.setDescription(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_DESCRIPTION)));
            camp.setContact_email(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_CONTACT_EMAIL)));
            camp.setUrl(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_URL)));
            favoritesList.add(camp);
          } else if (expressionType.equalsIgnoreCase("event")) {
            // to do
          }
          favoritesCursor.moveToNext();
        }
      } else {
        Toast.makeText(getApplicationContext(), "You Have No Favorites", Toast.LENGTH_SHORT).show();
      }

    } else {
      Toast.makeText(getApplicationContext(), "You Have No Favorites", Toast.LENGTH_SHORT).show();
    }

    expressionListAdapter = new ExpressionListAdapter(this, R.layout.listrow, favoritesList);
    setListAdapter(expressionListAdapter);
    ListView favoritesListView = getListView();
    favoritesListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
        Intent intent = new Intent("com.burningman.FavoritesDetail");
        intent.putExtra("favoritesItem", (Parcelable) parent.getAdapter().getItem(postion));
        startActivity(intent);
      }
    });

  }

}
