package com.burningman.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.burningman.exception.DBException;
import com.burningman.exception.HTTPException;

public class HttpLocalService extends IntentService {

  // Intent intent;
  private Messenger messenger;
  public static final String HTTP_SERVICE_SUCESSFULL = "HTTP_SERVICE_SUCESSFULL";
  public static final String HTTP_SERVICE_FAILURE = "HTTP_SERVICE_FAILURE";


  /**
   * A constructor is required, and must call the super IntentService(String) constructor with a name for the worker
   * thread.
   */
  public HttpLocalService() {
    super("HttpLocalService");
  }

  /*
   * @Override public void onCreate() { super.onCreate(); intent = new Intent("com.burningman.test"); }
   */

  /**
   * The IntentService calls this method from the default worker thread with the intent that started the service. When
   * this method returns, IntentService stops the service, as appropriate.
   */
  @Override
  protected void onHandleIntent(Intent intent) {
    messenger = (Messenger) intent.getExtras().get(HttpServiceHelper.HTTP_HANDLER_KEY);
    Message msg = Message.obtain();
    Bundle data = new Bundle();
    HttpServiceProcessor httpServiceProcessor = new HttpServiceProcessor(this.getBaseContext(), intent, this);
    /*
     * synchronized (this) { try { httpServiceProcessor.process(); Intent broadcastIntent = new Intent();
     * broadcastIntent.putExtra("msg1", "fist message to me yaba daba");
     * broadcastIntent.setAction("com.burningman.test"); getBaseContext().sendBroadcast(broadcastIntent); } catch
     * (Exception e) { } }
     */
    try {
      httpServiceProcessor.process();
      data.putBoolean(HttpLocalService.HTTP_SERVICE_SUCESSFULL, true);
    } catch (HTTPException e) {
      Log.v("HTTPLocalService ", e.toString());
      data.putBoolean(HttpLocalService.HTTP_SERVICE_FAILURE, true);
    } catch (DBException e) {
      Log.v("HTTPLocalService ", e.toString());
      data.putBoolean(HttpLocalService.HTTP_SERVICE_FAILURE, true);
    }
    msg.setData(data);
    try {
      messenger.send(msg);
    } catch (RemoteException re) {
      Log.v("HTTPLocalService ", re.toString());
    }

  }

}
