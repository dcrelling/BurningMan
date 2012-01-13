package com.burningman.contentproviders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.app.Service;

import com.burningman.application.ApplicationEx;
import com.burningman.exception.HTTPException;

public class HttpProvider {

  public String getHttpContent(String url, Activity activity) throws HTTPException {
    String httpContent = null;
    try {
      ApplicationEx app = (ApplicationEx) activity.getApplication();
      HttpClient client = app.getHttpClient();
      HttpGet request = new HttpGet();
      request.setURI(new URI(url));
      HttpResponse response = client.execute(request);

      InputStreamReader instream = new InputStreamReader(response.getEntity().getContent());
      BufferedReader reader = new BufferedReader(instream);
      StringBuffer sb = new StringBuffer();
      String line = "";
      try {
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }
      } catch (IOException e) {
        throw new HTTPException(e.toString());
      } finally {
        try {
          instream.close();
        } catch (IOException e) {
          throw new HTTPException(e.toString());
        }
      }
      httpContent = sb.toString();

      // page = EntityUtils.toString(response.getEntity());
      // System.out.println(page);
    } catch (IOException e) {
      throw new HTTPException(e.toString());
    }catch (ParseException e) {
      throw new HTTPException(e.toString());
    } catch (Exception e) {
      throw new HTTPException(e.toString());
    }
    if (httpContent != null) {
      return httpContent;
    } else {
      return null;
    }

  }
  
  public String getHttpContent(String url, Service service) throws HTTPException {
    String httpContent = null;
    try {
      ApplicationEx app = (ApplicationEx) service.getApplication();
      HttpClient client = app.getHttpClient();
      HttpGet request = new HttpGet();
      request.setURI(new URI(url));
      HttpResponse response = client.execute(request);

      InputStreamReader instream = new InputStreamReader(response.getEntity().getContent());
      BufferedReader reader = new BufferedReader(instream);
      StringBuffer sb = new StringBuffer();
      String line = "";
      try {
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }
      } catch (IOException e) {
        throw new HTTPException(e.toString());
      } finally {
        try {
          instream.close();
        } catch (IOException e) {
          throw new HTTPException(e.toString());
        }
      }
      httpContent = sb.toString();

      // page = EntityUtils.toString(response.getEntity());
      // System.out.println(page);
    } catch (IOException e) {
      throw new HTTPException(e.toString());
    } catch (ParseException e) {
      throw new HTTPException(e.toString());
    } catch (Exception e) {
      throw new HTTPException(e.toString());
    }
    if (httpContent != null) {
      return httpContent;
    } else {
      return null;
    }

  }
  
}
