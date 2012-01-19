package com.burningman;

import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.util.constants.MapViewConstants;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class OpenStreetMapActivity extends Activity implements MapViewConstants {

  // ===========================================================
  // Constants
  // ===========================================================

  private static final int MENU_ZOOMIN_ID = Menu.FIRST;
  private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

  // ===========================================================
  // Fields
  // ===========================================================

  private MapView myMapView;
  private MapController mapController;
  private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
  private ResourceProxy mResourceProxy;

  // ===========================================================
  // Constructors
  // ===========================================================
  /** Called when the activity is first created. */
  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle data = getIntent().getExtras();
    String latitude = data.getString("latitude");
    String longitude = data.getString("longitude");
    setContentView(R.layout.map);
    mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
    myMapView = (MapView) findViewById(R.id.map);
    myMapView.setTileSource(TileSourceFactory.MAPNIK);
    myMapView.setBuiltInZoomControls(true);
    mapController = myMapView.getController();
    mapController.setZoom(MapController.MAXIMUM_ZOOMLEVEL);
    GeoPoint point = new GeoPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));

    /* Itemized Overlay */
    {
      /* Create a static ItemizedOverlay showing a some Markers on some cities. */
      final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
      items.add(new OverlayItem("Hannover", "SampleDescription", point));

      /* OnTapListener for the Markers, shows a simple Toast. */
      this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
          new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
        @Override
        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
          Toast.makeText(OpenStreetMapActivity.this,
              "Item '" + item.mTitle + "' (index=" + index + ") got single tapped up", Toast.LENGTH_LONG).show();
          return true; // We 'handled' this event.
        }

        @Override
        public boolean onItemLongPress(final int index, final OverlayItem item) {

          return false;
        }
      }, mResourceProxy);
      this.myMapView.getOverlays().add(this.mMyLocationOverlay);
    }

    /* MiniMap */
    {
      MinimapOverlay miniMapOverlay = new MinimapOverlay(this, myMapView.getTileRequestCompleteHandler());
      this.myMapView.getOverlays().add(miniMapOverlay);
    }
    mapController.setCenter(point);

  }

  // ===========================================================
  // Getter & Setter
  // ===========================================================

  // ===========================================================
  // Methods from SuperClass/Interfaces
  // ===========================================================

  @Override
  public boolean onCreateOptionsMenu(final Menu pMenu) {
    pMenu.add(0, MENU_ZOOMIN_ID, Menu.NONE, "ZoomIn");
    pMenu.add(0, MENU_ZOOMOUT_ID, Menu.NONE, "ZoomOut");

    return true;
  }

  @Override
  public boolean onMenuItemSelected(final int featureId, final MenuItem item) {
    switch (item.getItemId()) {
      case MENU_ZOOMIN_ID :
        this.myMapView.getController().zoomIn();
        return true;

      case MENU_ZOOMOUT_ID :
        this.myMapView.getController().zoomOut();
        return true;
    }
    return false;
  }

  // ===========================================================
  // Methods
  // ===========================================================

  // ===========================================================
  // Inner and Anonymous Classes
  // ===========================================================

}
