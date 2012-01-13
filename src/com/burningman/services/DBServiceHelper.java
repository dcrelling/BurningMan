package com.burningman.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Messenger;

import com.burningman.beans.Expression;

public class DBServiceHelper {

  private Handler myDBCallbackHandler;
  public static final String GET_REQUEST_DATA = "GET_REQUEST_DATA";
  public static final String GET_CONV_REQUEST_DATA = "GET_CONV_REQUEST_DATA";
  public static final String TRUNCATE_REQUEST_DATA = "TRUNCATE_REQUEST_DATA";
  public static final String GET_FAVORITES = "GET_FAVORITES";
  public static final String DB_OPERATION_KEY = "DB_OPERATION";
  public static final String DB_HANDLER_KEY = "DB_HANDLER";

  public void executeOperation(String expressionType, Context context, String dbOperation) {
    Intent dbServiceIntent = createDBServiceIntent(expressionType, context, dbOperation);
    context.startService(dbServiceIntent);
  }

  public void registerCallBackHandler(Handler myHandler) {
    this.myDBCallbackHandler = myHandler;
  }

  private Intent createDBServiceIntent(String expressionType, Context context, String DBoperation) {
    Intent dbServiceIntent = new Intent(context, DBLocalService.class);
    dbServiceIntent.putExtra(DBServiceHelper.DB_HANDLER_KEY, new Messenger(myDBCallbackHandler));
    dbServiceIntent.putExtra(Expression.EXPRESSION_TYPE_KEY, expressionType);
    dbServiceIntent.putExtra(DBServiceHelper.DB_OPERATION_KEY, DBoperation);
    return dbServiceIntent;
  }

}
