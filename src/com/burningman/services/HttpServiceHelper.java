package com.burningman.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;



public class HttpServiceHelper {
  
  Context mContext;
  IntentFilter httpServiceBroadcastfilter;
  
  public HttpServiceHelper(Context context){
    mContext = context;
  }
  
  
  private BroadcastReceiver myHttpServiceReceiver = new BroadcastReceiver() {
              
            @Override
             public void onReceive(Context context, Intent intent) {
              Toast.makeText(context, intent.getStringExtra("msg1"),
                  Toast.LENGTH_LONG).show();
             }
         }; 
  
 
  public void startService(){
	httpServiceBroadcastfilter = new IntentFilter();
    httpServiceBroadcastfilter.addAction("com.burningman.test");
    mContext.registerReceiver(myHttpServiceReceiver, httpServiceBroadcastfilter);
    Intent httpServiceintent = new Intent(mContext, HttpLocalService.class);
    httpServiceintent.putExtra("URL", "http://earth.burningman.com/api/0.1/2009/art/");
    mContext.startService(httpServiceintent);
  }
         
}
