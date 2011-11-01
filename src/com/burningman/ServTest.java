package com.burningman;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.burningman.services.HttpLocalServiceBinding;
import com.burningman.services.HttpLocalServiceBinding.LocalBinder;

public class ServTest extends Activity {

  HttpLocalServiceBinding mService;
  boolean mBound = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
  }

  @Override
  protected void onStart() {
      super.onStart();
      // Bind to LocalService
      Intent intent = new Intent(this, HttpLocalServiceBinding.class);
      bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  protected void onStop() {
      super.onStop();
      // Unbind from the service
      if (mBound) {
          unbindService(mConnection);
          mBound = false;
      }
  }

  /** Called when a button is clicked (the button in the layout file attaches to
    * this method with the android:onClick attribute) */
  public void doWork() {
      
          int num = mService.getRandomNumber();
          Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
     
  }

  /** Defines callbacks for service binding, passed to bindService() */
  private ServiceConnection mConnection = new ServiceConnection() {

      @Override
      public void onServiceConnected(ComponentName className,
              IBinder service) {
          // We've bound to LocalService, cast the IBinder and get LocalService instance
          LocalBinder binder = (LocalBinder) service;
          mService = binder.getService();
          mBound = true;
      }

      @Override
      public void onServiceDisconnected(ComponentName arg0) {
          mBound = false;
      }
  };

 
}
