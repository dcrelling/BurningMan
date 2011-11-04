package com.burningman.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;



public class HttpServiceHelper {
  
  Context mContext;
  
  public HttpServiceHelper(Context context){
    mContext = context;
  }
  
  
  private BroadcastReceiver myReceiver = new BroadcastReceiver() {
              
            @Override
             public void onReceive(Context context, Intent intent) {
              Toast.makeText(context, intent.getStringExtra("msg1"),
                  Toast.LENGTH_LONG).show();
             }
         }; 
  
 
  public void startService(){
    IntentFilter filter = new IntentFilter();
    filter.addAction("com.burningman.test");
    mContext.registerReceiver(myReceiver, filter);
    Intent intent = new Intent(mContext, HttpLocalService.class);
    intent.putExtra("URL", "http://earth.burningman.com/api/0.1/2009/art/");
    mContext.startService(intent);
    
    
  }
         
}
