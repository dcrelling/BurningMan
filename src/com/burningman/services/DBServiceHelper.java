package com.burningman.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Messenger;

public class DBServiceHelper {

	private Handler myDBCallbackHandler;
	
	
	     public void executeOperation(String expressionType, Context context, String operation){
	    	 Intent dbServiceIntent = createDBServiceIntent(expressionType, context, operation);
	    	 context.startService(dbServiceIntent);
	     }
	     
	     public void registerCallBackHandler(Handler myHandler){
	    	 this.myDBCallbackHandler = myHandler;
	     }
	
	
		  private Intent createDBServiceIntent(String expressionType, Context context, String operation){
			    Intent dbServiceIntent = new Intent(context, DBLocalService.class);
			    dbServiceIntent.putExtra("handler", new Messenger(myDBCallbackHandler));
			    dbServiceIntent.putExtra("expressionType", expressionType);
			    dbServiceIntent.putExtra("operation", operation); 
			    return dbServiceIntent;
		  }
	

	
}
