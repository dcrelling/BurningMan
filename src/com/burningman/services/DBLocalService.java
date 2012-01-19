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
import android.util.Log;

import com.burningman.AppStatus;
import com.burningman.beans.Art;
import com.burningman.beans.Camp;
import com.burningman.beans.Expression;
import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.BurningmanDBAdapter.FavoritesMetaData;
import com.burningman.contentproviders.BurningmanDBAdapter.RestRequestMetaData;
import com.burningman.converters.RequestConverter;
import com.burningman.exception.DBException;

public class DBLocalService extends IntentService {

  private Messenger messenger;
  public static final String QUERY_RESULTS_FOUND = "QUERY_RESULTS_FOUND";
  public static final String QUERY_RESULTS_NOT_FOUND = "QUERY_RESULTS_NOT_FOUND";
  public static final String QUERY_ERROR_ENCOUNTERED = "QUERY_ERROR_ENCOUNTERED";
  public static final String QUERY_ERROR_NOT_ENCOUNTERED = "QUERY_ERROR_NOT_ENCOUNTERED";
  private BurningmanDBAdapter dbAdapter = null;
  private Cursor dBCursor = null;
  /**
   * A constructor is required, and must call the super IntentService(String) constructor with a name for the worker
   * thread.
   */
  public DBLocalService() {
    super("DBLocalService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    messenger = (Messenger) intent.getExtras().get(DBServiceHelper.DB_HANDLER_KEY);
    String dbOperation = intent.getStringExtra(DBServiceHelper.DB_OPERATION_KEY);
    if (dbOperation.equals(DBServiceHelper.GET_CONV_REQUEST_DATA)) {
      getConvertRequestData(intent.getStringExtra(Expression.EXPRESSION_TYPE_KEY));
    }else if(dbOperation.equals(DBServiceHelper.GET_FAVORITES)){
      getFavoritesData();
    }
  }

  private void getConvertRequestData(String expressionType) {
    Message msg = Message.obtain();
    Bundle data = new Bundle();
    dbAdapter = new BurningmanDBAdapter(this);
    if (AppStatus.getInstance(this).isOnline(this)) {
      // if there is a network connection try to get non expired from db if nothing found use 
      // BM rest service
      try {
        dBCursor = getUnexpiredRestRequestData(expressionType);
      } catch (SQLException e) {
        Log.v("DBLocalService ", e.toString());
        data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
      } catch (DBException e) {
        Log.v("DBLocalService ", e.toString());
        data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
      }
      
    }else{
      //if there is not a a network connection then just get the latest created rest request
      try {
        dBCursor = getExpiredRestRequestData(expressionType);
      } catch (SQLException e) {
        Log.v("DBLocalService ", e.toString());
        data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
      } catch (DBException e) {
        Log.v("DBLocalService ", e.toString());
        data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
      }
    }
      
      if (dBCursor != null) {
        if (dBCursor.getCount() > 0) {
          try {
            dBCursor.moveToFirst();
            data.putBoolean(DBLocalService.QUERY_RESULTS_FOUND, true);
            data.putBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED, true);
            ArrayList<Parcelable> expressionList = convertRequestData(dBCursor, expressionType);
            data.putParcelableArrayList(Expression.EXPRESSION_LIST_KEY, expressionList);
          } catch (Exception e) {
            // add error handle
            Log.v("DBLocalService ", e.toString());
            data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
            data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
          }
        } else {
          data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
          data.putBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED, true);
        }
      } else {
        data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
      }

      if (dBCursor != null) {
        dBCursor.close();
      }
      if (dbAdapter != null) {
        dbAdapter.close();
        dbAdapter = null;
      }
      msg.setData(data);
      try {
        messenger.send(msg);
      } catch (RemoteException re) {
        Log.v("DBLocalService ", re.toString());
      }
  }

  private void getFavoritesData() {
    Message msg = Message.obtain();
    Bundle data = new Bundle();
    dbAdapter = new BurningmanDBAdapter(this);
    dbAdapter.open();
    Cursor favoritesCursor = dbAdapter.getAllFavorites();
    if (favoritesCursor != null) {
      if (favoritesCursor.getCount() > 0) {
        Art art = null;
        Camp camp = null;
        ArrayList<Parcelable> favoritesList = new ArrayList<Parcelable>();
        favoritesCursor.moveToFirst();
        while (favoritesCursor.isAfterLast() == false) {
          String expressionType = favoritesCursor.getString(favoritesCursor
              .getColumnIndex(FavoritesMetaData.FAVORITE_TYPE));
          if (expressionType.equalsIgnoreCase("art")) {
            art = new Art();
            art.setDescription(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_DESCRIPTION)));
            art.setId(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_EXPRESSION_ID)));
            art.setName(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_NAME)));
            art.setContact_email(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_CONTACT_EMAIL)));
            art.setUrl(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_URL)));
            favoritesList.add(art);
          } else if (expressionType.equalsIgnoreCase("camp")) {
            camp = new Camp();
            camp.setId(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_EXPRESSION_ID)));
            camp.setName(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_NAME)));
            camp.setDescription(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_DESCRIPTION)));
            camp.setContact_email(favoritesCursor.getString(favoritesCursor
                .getColumnIndex(FavoritesMetaData.FAVORITE_CONTACT_EMAIL)));
            camp.setUrl(favoritesCursor.getString(favoritesCursor.getColumnIndex(FavoritesMetaData.FAVORITE_URL)));
            favoritesList.add(camp);
          } else if (expressionType.equalsIgnoreCase("event")) {
            // to do
          }
          favoritesCursor.moveToNext();
        }
        data.putBoolean(DBLocalService.QUERY_RESULTS_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED, true);
        data.putParcelableArrayList(Expression.EXPRESSION_LIST_KEY, favoritesList);
      } else {
        //no error but no favorites added
        data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
        data.putBoolean(DBLocalService.QUERY_ERROR_NOT_ENCOUNTERED, true);
      }

    } else {
      //error
      data.putBoolean(DBLocalService.QUERY_RESULTS_NOT_FOUND, true);
      data.putBoolean(DBLocalService.QUERY_ERROR_ENCOUNTERED, true);
    }
    if (favoritesCursor != null) {
      favoritesCursor.close();
    }
    if (dbAdapter != null) {
      dbAdapter.close();
      dbAdapter = null;
    }
    msg.setData(data);
    try {
      messenger.send(msg);
    } catch (RemoteException re) {
      Log.v("DBLocalService ", re.toString());
    }  
  }

  private void truncateRequestTable() {

  }

  private Cursor getUnexpiredRestRequestData(String expressionType) throws DBException {
    try {
      dbAdapter.open();
      return dbAdapter.getUnexpiredRestRequests(expressionType);
    } catch (SQLException e) {
      Log.v("DBLocalService ", e.toString());
      dbAdapter.close();
      throw new DBException(e.toString());
    }
  }
  
  private Cursor getExpiredRestRequestData(String expressionType) throws DBException {
    try {
      dbAdapter.open();
      return dbAdapter.getExpiredRestRequests(expressionType);
    } catch (SQLException e) {
      Log.v("DBLocalService ", e.toString());
      dbAdapter.close();
      throw new DBException(e.toString());
    }
  }

  private ArrayList<Parcelable> convertRequestData(Cursor dBCursor, String expressionType) {
    RequestConverter requestConverter = new RequestConverter();
    ArrayList<Parcelable> expressionList = requestConverter.convertRequest(
        dBCursor.getString(dBCursor.getColumnIndex(RestRequestMetaData.REST_REQUEST_VALUE)), expressionType);
    return expressionList;
  }

}
