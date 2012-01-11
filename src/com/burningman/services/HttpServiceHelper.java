package com.burningman.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Messenger;

public class HttpServiceHelper {

  /*
   * private BroadcastReceiver myHttpServiceReceiver = new BroadcastReceiver() {
   * 
   * @Override public void onReceive(Context context, Intent intent) { Toast.makeText(context,
   * intent.getStringExtra("msg1"), Toast.LENGTH_LONG).show(); } };
   */

  public static final String HTTP_HANDLER_KEY = "HTTP_HANDLER";
  private Handler myHTTPServiceCallbackHandler;

  public void consumeRestService(String url, String expressionType, Context context) {
    // context.registerReceiver(myHttpServiceReceiver, createHttpBroadcastFilter());
    context.startService(createHttpServiceIntent(url, expressionType, context));
  }

  /*
   * private IntentFilter createHttpBroadcastFilter(){ IntentFilter broadcastFilter = new IntentFilter();
   * broadcastFilter.addAction("com.burningman.test"); return broadcastFilter; }
   */

  private Intent createHttpServiceIntent(String url, String expressionType, Context context) {
    Intent httpServiceIntent = new Intent(context, HttpLocalService.class);
    httpServiceIntent.putExtra(HttpServiceHelper.HTTP_HANDLER_KEY, new Messenger(myHTTPServiceCallbackHandler));
    httpServiceIntent.putExtra("URL", url);
    httpServiceIntent.putExtra("EXPRESSION_TYPE", expressionType);
    return httpServiceIntent;
  }

  public void registerCallBackHandler(Handler myHandler) {
    this.myHTTPServiceCallbackHandler = myHandler;
  }

}
