package com.burningman;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.os.Bundle;


public class OpenStreetMapActivity extends Activity {

  /** Called when the activity is first created. */
  private MapController mapController;
  private MapView mapView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Bundle data = getIntent().getExtras();
      String latitude = data.getString("latitude");
      String longitude = data.getString("longitude");
      setContentView(R.layout.map);
      mapView = (MapView) findViewById(R.id.map);
      mapView.setTileSource(TileSourceFactory.MAPNIK);
      mapView.setBuiltInZoomControls(true);
      mapController = mapView.getController();
      mapController.setZoom(MapController.MAXIMUM_ZOOMLEVEL);
      GeoPoint point = new GeoPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));
      mapController.setCenter(point);
  }
  protected boolean isRouteDisplayed() {
      // TODO Auto-generated method stub
      return false;
  }


  
}
