package com.burningman.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.BurningmanDBAdapter.RestRequestMetaData;

public class DBQueryLocalService extends IntentService {
  
 private Messenger messenger; 
  /** 
   * A constructor is required, and must call the super IntentService(String)
   * constructor with a name for the worker thread.
   */
  public DBQueryLocalService() {
      super("DBQueryLocalService");
  }
 

  @Override
  protected void onHandleIntent(Intent intent) {
    String type = intent.getStringExtra("type");
    messenger = (Messenger) intent.getExtras().get("handler"); 
    Message msg = Message.obtain(); 
    Bundle data = new Bundle();
    BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(this);
    dbAdapter.open();
    Cursor ArtListCursor = dbAdapter.getRestRequests(type);
    if (ArtListCursor != null) {
      if (ArtListCursor.getCount() > 0) {
        ArtListCursor.moveToFirst();
        String artRequestValue = ArtListCursor.getString(ArtListCursor
            .getColumnIndex(RestRequestMetaData.REST_REQUEST_VALUE));
        data.putBoolean("success", true);
        data.putString("resultValue", artRequestValue); 
        msg.setData(data);
        try {
          messenger.send(msg); 
        } catch (Exception e) {
          dbAdapter.close();
          dbAdapter = null;
        }
      }else{
        data.putBoolean("success", false);
      }
    }else{
      data.putBoolean("success", false);
    }
    dbAdapter.close();
    dbAdapter = null;
  }

}
