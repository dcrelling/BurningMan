package com.burningman.services;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;

import com.burningman.beans.Expression;
import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.BurningmanDBAdapter.RestRequestMetaData;
import com.burningman.converters.RequestConverter;

public class DBLocalService extends IntentService {
  
 private Messenger messenger; 
  /** 
   * A constructor is required, and must call the super IntentService(String)
   * constructor with a name for the worker thread.
   */
  public DBLocalService() {
      super("DBLocalService");
  }
 

  @Override
  protected void onHandleIntent(Intent intent) {
	messenger = (Messenger) intent.getExtras().get("handler");  
    String operation = intent.getStringExtra("operation");
    if(operation.equals("getConvertRequest") ){
    	getConvertRequestData(intent.getStringExtra("expressionType"));
    }
  }
  
  private void getConvertRequestData(String expressionType){
		Message msg = Message.obtain();
		Bundle data = new Bundle();
		BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(this);
		dbAdapter.open();
		Cursor dBCursor = dbAdapter.getRestRequests(expressionType);
		if (dBCursor != null) {
			if (dBCursor.getCount() > 0) {
				dBCursor.moveToFirst();
				data.putBoolean("success", true);
				RequestConverter requestConverter = new RequestConverter();
				ArrayList<Parcelable> expressionList = requestConverter.convertRequest(dBCursor
						.getString(dBCursor
								.getColumnIndex(RestRequestMetaData.REST_REQUEST_VALUE)), expressionType);
				data.putParcelableArrayList("expressionList", expressionList);
				msg.setData(data);
				try {
					messenger.send(msg);
				} catch (Exception e) {
					dBCursor.close();
					dbAdapter.close();
					dbAdapter = null;
				}
			} else {
				data.putBoolean("success", false);
			}
		} else {
			data.putBoolean("success", false);
		}
		dBCursor.close();
		dbAdapter.close();
		dbAdapter = null;
  }
  
  private void getFavoritesData(){
	  
  }
  
  private void truncateRequestTable(){
	  
  }

}
