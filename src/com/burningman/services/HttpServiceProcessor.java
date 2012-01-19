package com.burningman.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;

import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.HttpProvider;
import com.burningman.exception.DBException;
import com.burningman.exception.HTTPException;

public class HttpServiceProcessor {

  private Context mContext;
  private Intent intent;
  private Service service;

  public HttpServiceProcessor(Context context, Intent intent, Service service) {
    this.mContext = context;
    this.intent = intent;
    this.service = service;
  }

  public void doDBInsert(String httpResult) throws HTTPException, DBException {
    if (httpResult != null) {
      BurningmanDBAdapter dbAdapter = new BurningmanDBAdapter(mContext);
      try {
        dbAdapter.open();
        dbAdapter.insertRestRequest("processed", intent.getStringExtra("EXPRESSION_TYPE"), httpResult);
        dbAdapter.close();
        dbAdapter = null;
      } catch (SQLException e) {
        Log.v("HTTPService Processor ", e.toString());
        throw new DBException(e.toString());
      } 
    } else {
      throw new HTTPException("httpResult is null");
    }

  }

  public void process() throws HTTPException, DBException {
    callHttpRestService();
  }

  private void callHttpRestService() throws HTTPException, DBException {
    HttpProvider httpProvider = new HttpProvider();
    doDBInsert(httpProvider.getHttpContent(intent.getStringExtra("URL"), service));

  }
}
