package com.burningman.services;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;

import com.burningman.beans.Expression;
import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.BurningmanDBAdapter.RestRequestMetaData;
import com.burningman.converters.RequestConverter;
import com.burningman.exception.DBException;

public class DBLocalService extends IntentService {
  
 private Messenger messenger; 
 public static final String QUERY_RESULT_KEY = "Success";
 public static final boolean QUERY_SUCESSFULL = true;
 public static final boolean QUERY_FAILURE = false;
  /** 
   * A constructor is required, and must call the super IntentService(String)
   * constructor with a name for the worker thread.
   */
  public DBLocalService() {
      super("DBLocalService");
  }
 

  @Override
  protected void onHandleIntent(Intent intent) {
	messenger = (Messenger) intent.getExtras().get(DBServiceHelper.DB_HANDLER_KEY);  
    String dbOperation = intent.getStringExtra(DBServiceHelper.DB_OPERATION_KEY);
    if(dbOperation.equals(DBServiceHelper.GET_CONV_REQUEST_DATA) ){
    	getConvertRequestData(intent.getStringExtra(Expression.EXPRESSION_TYPE_KEY));
    }
  }
  
  private void getConvertRequestData(String expressionType){
		Message msg = Message.obtain();
		Bundle data = new Bundle();
		BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(this);
		Cursor dBCursor = null;
		try {
		  dbAdapter.open();
	    dBCursor = getRequestData(expressionType);
    } catch (SQLException e) {
      data.putBoolean(DBLocalService.QUERY_RESULT_KEY, DBLocalService.QUERY_FAILURE);
      dBCursor.close();
      dbAdapter.close();
      dbAdapter = null;
      msg.setData(data);
      try {
        messenger.send(msg);
      } catch (RemoteException re) {
        // TODO: handle exception
      } 
    }catch (DBException e){
      data.putBoolean(DBLocalService.QUERY_RESULT_KEY, DBLocalService.QUERY_FAILURE);
      dBCursor.close();
      dbAdapter.close();
      dbAdapter = null;
      msg.setData(data);
      try {
        messenger.send(msg);
      } catch (RemoteException re) {
        // TODO: handle exception
      } 
    }
		if (dBCursor != null) {
			if (dBCursor.getCount() > 0) {
			  try {
				dBCursor.moveToFirst();
				data.putBoolean(DBLocalService.QUERY_RESULT_KEY, DBLocalService.QUERY_SUCESSFULL);
				ArrayList<Parcelable> expressionList = convertRequestData(dBCursor, expressionType);
				data.putParcelableArrayList(Expression.EXPRESSION_LIST_KEY, expressionList);
				} catch (Exception e) {
					//add error handle
				  dBCursor.close();
		     dbAdapter.close();
		     dbAdapter = null;
		      data.putBoolean(DBLocalService.QUERY_RESULT_KEY, DBLocalService.QUERY_FAILURE);
		      try {
		        messenger.send(msg);
		      } catch (RemoteException re) {
		        // TODO: handle exception
		      } 
				}
			} else {
				data.putBoolean(DBLocalService.QUERY_RESULT_KEY, DBLocalService.QUERY_FAILURE);
			}
		} else {
			data.putBoolean(DBLocalService.QUERY_RESULT_KEY, DBLocalService.QUERY_FAILURE);
		}
		  dBCursor.close();
	    dbAdapter.close();
	    dbAdapter = null;
	    msg.setData(data);
	    try {
	      messenger.send(msg);
      } catch (RemoteException re) {
        // TODO: handle exception
      } 
  }
  
  private void getFavoritesData(){
	  
  }
  
  private void truncateRequestTable(){
	  
  }
  
  private Cursor getRequestData(String expressionType) throws DBException {
    BurningmanDBAdapter dbAdapter = null;
    Cursor dBCursor = null;
    try {
      dbAdapter = new BurningmanDBAdapter(this);
      dbAdapter.open();
      dBCursor = dbAdapter.getRestRequests(expressionType);
    } catch (SQLException e) {
      dBCursor.close();
      dbAdapter.close();
      dbAdapter = null;
      throw new DBException(e.toString());
    }
    return dBCursor;
  }
  
  private ArrayList<Parcelable> convertRequestData(Cursor dBCursor, String expressionType){
    RequestConverter requestConverter = new RequestConverter();
    ArrayList<Parcelable> expressionList = requestConverter.convertRequest(dBCursor
        .getString(dBCursor
            .getColumnIndex(RestRequestMetaData.REST_REQUEST_VALUE)), expressionType);
    return expressionList;
  }

}
