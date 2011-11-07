package com.burningman.services;

import android.content.Context;

import com.burningman.contentproviders.BurningmanDBAdapter;
import com.burningman.contentproviders.HttpProvider;

public class HttpServiceProcessor {

	private BurningmanDBAdapter dbAdapter;
	private Context mContext;
	
	public HttpServiceProcessor(Context context){
		mContext = context;
		dbAdapter = new BurningmanDBAdapter(mContext);
	}
	
	public void doGetDBInsert(String httpResult){
		dbAdapter.open();
        dbAdapter.insertRestRequest("1234", "processed", "art", httpResult);
        dbAdapter.close();
        dbAdapter = null;
	}
	
	public void process(){
		
	}
	
	private void queryRestRequest(){
		
	}
	
	private void callHttpRestService(){
		HttpProvider httpProvider = new HttpProvider();
		//doGetDBInsert(httpProvider.getHttpContent(intent.getStringExtra("URL"), this));
		
	}
}
