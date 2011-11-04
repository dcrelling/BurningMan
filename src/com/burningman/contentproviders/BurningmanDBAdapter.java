package com.burningman.contentproviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BurningmanDBAdapter {

  public static final String DATABASE_NAME = "burningman";
  public static final int DATABASE_VERSION = 7;
  public static final String FAVORITES_TABLE_NAME = "favorites";
  public static final String REST_REQUEST_TABLE_NAME = "rest_request";
  
  public static final String CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesMetaData.TABLE_NAME + "("
  + FavoritesMetaData.FAVORITE_PRIMAY_ID + " INTEGER PRIMARY KEY," + FavoritesMetaData.FAVORITE_EXPRESSION_ID
  + " TEXT, " + FavoritesMetaData.FAVORITE_TYPE + " TEXT, " + FavoritesMetaData.FAVORITE_NAME + " TEXT, "
  + FavoritesMetaData.FAVORITE_CONTACT_EMAIL + " TEXT, " + FavoritesMetaData.FAVORITE_URL + " TEXT, "
  + FavoritesMetaData.FAVORITE_DESCRIPTION + " TEXT, " + FavoritesMetaData.CREATED_DATE + " TEXT)";
  
  public static final String CREATE_REST_REQUEST_TABLE = "CREATE TABLE " + RestRequestMetaData.TABLE_NAME + "("
  + RestRequestMetaData.REST_REQUEST_PRIMAY_ID + " INTEGER PRIMARY KEY," + RestRequestMetaData.REST_REQUEST_REQUEST_ID + " TEXT,"
  + RestRequestMetaData.REST_REQUEST_STATUS + " TEXT, " 
  + RestRequestMetaData.REST_REQUEST_TYPE + " TEXT, " + RestRequestMetaData.REST_REQUEST_VALUE + " TEXT, "
  + RestRequestMetaData.REST_REQUEST_EXPIRATION_DATE + " TEXT, " + RestRequestMetaData.CREATED_DATE + " TEXT)";

  private Context context;
  private SQLiteDatabase db;
  private BurningManDBHelper DBHelper;

  public BurningmanDBAdapter(Context ctx) {
    this.context = ctx;
    this.DBHelper = new BurningManDBHelper(this.context);
  }

  public static final class FavoritesMetaData {

    private FavoritesMetaData() {

    }

    public static final String TABLE_NAME = "favorites";
    public static final String FAVORITE_PRIMAY_ID = "id";
    public static final String FAVORITE_EXPRESSION_ID = "expression_id";
    public static final String FAVORITE_TYPE = "type";
    public static final String FAVORITE_NAME = "name";
    public static final String FAVORITE_DESCRIPTION = "description";
    public static final String FAVORITE_CONTACT_EMAIL = "contact_email";
    public static final String FAVORITE_URL = "url";
    public static final String CREATED_DATE = "created";

  }
  
  public static final class RestRequestMetaData {

    private RestRequestMetaData() {

    }

    public static final String TABLE_NAME = "rest_request";
    public static final String REST_REQUEST_PRIMAY_ID = "id";
    public static final String REST_REQUEST_REQUEST_ID = "request_id";
    public static final String REST_REQUEST_STATUS = "status";
    public static final String REST_REQUEST_TYPE = "type";
    public static final String REST_REQUEST_VALUE = "content_value";
    public static final String REST_REQUEST_EXPIRATION_DATE = "expiration_date";
    public static final String CREATED_DATE = "created";

  }

  private static class BurningManDBHelper extends SQLiteOpenHelper {

    public BurningManDBHelper(Context context) {
      super(context, BurningmanDBAdapter.DATABASE_NAME, null, BurningmanDBAdapter.DATABASE_VERSION);
      // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(BurningmanDBAdapter.CREATE_FAVORITES_TABLE);
      db.execSQL(BurningmanDBAdapter.CREATE_REST_REQUEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w("Example", "Upgrading database, this will drop tables and recreate.");
      db.execSQL("DROP TABLE IF EXISTS " + FavoritesMetaData.TABLE_NAME);
      db.execSQL("DROP TABLE IF EXISTS " + RestRequestMetaData.TABLE_NAME);
      onCreate(db);

    }

  }

  // ---opens the database---
  public void open() throws SQLException {
    db = DBHelper.getWritableDatabase();

  }

  // ---closes the database---
  public void close() {
    DBHelper.close();
  }

  // ---retrieves all the favorites---
  public Cursor getAllFavorites() {
    return db.query(BurningmanDBAdapter.FAVORITES_TABLE_NAME, new String[]{FavoritesMetaData.FAVORITE_PRIMAY_ID,
        FavoritesMetaData.FAVORITE_EXPRESSION_ID, FavoritesMetaData.FAVORITE_TYPE, FavoritesMetaData.FAVORITE_NAME,
        FavoritesMetaData.FAVORITE_CONTACT_EMAIL, FavoritesMetaData.FAVORITE_URL,
        FavoritesMetaData.FAVORITE_DESCRIPTION, FavoritesMetaData.CREATED_DATE}, null, null, null, null, null);
  }

  // ---retrieves a particular expression---
  public Cursor getFavorite(String expressionId) throws SQLException {
    Cursor mCursor = db.query(true, BurningmanDBAdapter.FAVORITES_TABLE_NAME,
        new String[]{FavoritesMetaData.FAVORITE_EXPRESSION_ID}, FavoritesMetaData.FAVORITE_EXPRESSION_ID + "="
            + expressionId, null, null, null, null, null);
    if (mCursor != null) {
      mCursor.moveToFirst();
    }
    return mCursor;
  }

  // ---insert a favorite into the database---
  public long insertFavorite(String id, String name, String type, String conatct_email, String url, String description) {
    ContentValues initialValues = new ContentValues();
    initialValues.put(FavoritesMetaData.FAVORITE_EXPRESSION_ID, id);
    initialValues.put(FavoritesMetaData.FAVORITE_TYPE, type);
    initialValues.put(FavoritesMetaData.FAVORITE_NAME, name);
    initialValues.put(FavoritesMetaData.FAVORITE_CONTACT_EMAIL, conatct_email);
    initialValues.put(FavoritesMetaData.FAVORITE_URL, url);
    initialValues.put(FavoritesMetaData.FAVORITE_DESCRIPTION, description);
    return db.insert(BurningmanDBAdapter.FAVORITES_TABLE_NAME, null, initialValues);

  }
  
//---retrieves all the favorites---
  public Cursor getRestRequests(String type) {
    return db.query(BurningmanDBAdapter.REST_REQUEST_TABLE_NAME, new String[]{RestRequestMetaData.REST_REQUEST_PRIMAY_ID,
        RestRequestMetaData.REST_REQUEST_REQUEST_ID, RestRequestMetaData.REST_REQUEST_STATUS, RestRequestMetaData.REST_REQUEST_TYPE,
        RestRequestMetaData.REST_REQUEST_VALUE, RestRequestMetaData.REST_REQUEST_EXPIRATION_DATE, RestRequestMetaData.CREATED_DATE}, RestRequestMetaData.REST_REQUEST_TYPE + "=" + "'" + type +"'", null, null, null, null);
  }
  
//---insert a rest request from burning man web service into the database---
  public long insertRestRequest(String id, String status, String type, String requestValue) {
    ContentValues initialValues = new ContentValues();
    initialValues.put(RestRequestMetaData.REST_REQUEST_REQUEST_ID, id);
    initialValues.put(RestRequestMetaData.REST_REQUEST_STATUS, status);
    initialValues.put(RestRequestMetaData.REST_REQUEST_TYPE, type);
    initialValues.put(RestRequestMetaData.REST_REQUEST_VALUE, requestValue);
    //initialValues.put(RestRequestMetaData.REST_REQUEST_EXPIRATION_DATE, url);
    //initialValues.put(RestRequestMetaData.CREATED_DATE, description);
    return db.insert(BurningmanDBAdapter.REST_REQUEST_TABLE_NAME, null, initialValues);

  }

}
