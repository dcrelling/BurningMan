package com.burningman.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;



public class HttpServiceHelper {
  
  //Context mContext = null;
  
  public HttpServiceHelper(){
   // mContext = context;
  }
  
  private BroadcastReceiver myReceiver = new BroadcastReceiver() {
              
            @Override
             public void onReceive(Context context, Intent intent) {
              Toast.makeText(context, intent.getStringExtra("msg1"),
                  Toast.LENGTH_LONG).show();
             }
         }; 

  
  public void consumeRestService(String url, Context context){
   context.registerReceiver(myReceiver, createHttpBroadcastFilter());
   context.startService(createHttpServiceIntent(url, context));
 
  }
  
  private IntentFilter createHttpBroadcastFilter(){
    IntentFilter broadcastFilter = new IntentFilter();
    broadcastFilter.addAction("com.burningman.test");
    return broadcastFilter;
  }
  
  private Intent createHttpServiceIntent(String url, Context context){
    Intent httpServiceIntent = new Intent(context, HttpLocalService.class);
    httpServiceIntent.putExtra("URL", url);
    return httpServiceIntent;
  }
  
}
