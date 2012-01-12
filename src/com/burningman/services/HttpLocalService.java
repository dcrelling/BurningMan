package com.burningman.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.burningman.exception.DBException;
import com.burningman.exception.HTTPException;

public class HttpLocalService extends IntentService {

  // Intent intent;
  private Messenger messenger;
  public static final String HTTP_SERVICE_RESULT_KEY = "Success";
  public static final boolean HTTP_SERVICE_SUCESSFULL = true;
  public static final boolean HTTP_SERVICE_FAILURE = false;
  public static final String HTTP_SERVICE_FAILURE_MSG_KEY = "HTTP_SERVICE_FAILURE_MSG_KEY";
  public static final String HTTP_SERVICE_FAILURE_MSG = "A HTTP Exception Occured. Failed To Retrive Content.";
  public static final String DB_FAILURE_MSG = "A Database Exception Occured. Failed To Insert Content.";

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
      data.putBoolean(HttpLocalService.HTTP_SERVICE_RESULT_KEY, HttpLocalService.HTTP_SERVICE_SUCESSFULL);
    } catch (HTTPException e) {
      data.putBoolean(HttpLocalService.HTTP_SERVICE_RESULT_KEY, HttpLocalService.HTTP_SERVICE_FAILURE);
      data.putString(HttpLocalService.HTTP_SERVICE_FAILURE_MSG_KEY, HttpLocalService.HTTP_SERVICE_FAILURE_MSG);
    } catch (DBException e) {
      data.putBoolean(HttpLocalService.HTTP_SERVICE_RESULT_KEY, HttpLocalService.HTTP_SERVICE_FAILURE);
      data.putString(HttpLocalService.HTTP_SERVICE_FAILURE_MSG_KEY, HttpLocalService.DB_FAILURE_MSG);
    }
    msg.setData(data);
    try {
      messenger.send(msg);
    } catch (RemoteException re) {
      // TODO: handle exception
    }

  }

}
