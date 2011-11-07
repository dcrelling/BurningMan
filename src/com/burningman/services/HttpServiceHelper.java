package com.burningman.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;



public class HttpServiceHelper {
  
  
  private BroadcastReceiver myHttpServiceReceiver = new BroadcastReceiver() {
              
            @Override
             public void onReceive(Context context, Intent intent) {
              Toast.makeText(context, intent.getStringExtra("msg1"),
                  Toast.LENGTH_LONG).show();
             }
         }; 

  
  public void consumeRestService(String url, Context context){
   context.registerReceiver(myHttpServiceReceiver, createHttpBroadcastFilter());
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
