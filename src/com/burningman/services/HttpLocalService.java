package com.burningman.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.HttpProvider;

public class HttpLocalService extends IntentService {
  
  
  Intent intent;

  /** 
   * A constructor is required, and must call the super IntentService(String)
   * constructor with a name for the worker thread.
   */
  public HttpLocalService() {
      super("HttpLocalService");
  }
  
  @Override
  public void onCreate() {
    super.onCreate();
    intent = new Intent("com.burningman.test");  
  }

  /**
   * The IntentService calls this method from the default worker thread with
   * the intent that started the service. When this method returns, IntentService
   * stops the service, as appropriate.
   */
  @Override
  protected void onHandleIntent(Intent intent) {
    HttpProvider httpProvider = new HttpProvider();
    BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(this.getBaseContext());
          synchronized (this) {
              try {
                String result = httpProvider.getHttpContent(intent.getStringExtra("URL"), this);
                dbAdapter.open();
                dbAdapter.insertRestRequest("1234", "processed", "art", result);
                dbAdapter.close();
                dbAdapter = null;
                Intent broadcastIntent = new Intent();
                broadcastIntent.putExtra("msg1", "fist message to me yaba daba");
                broadcastIntent.setAction("com.burningman.test");
                getBaseContext().sendBroadcast(broadcastIntent);
              } catch (Exception e) {
              }
          }
  }

}
