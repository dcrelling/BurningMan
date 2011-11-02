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

public class HttpProvider {

  public String getHttpContent(String url, Activity activity) {
    String page = null;
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
        e.printStackTrace();
      } finally {
        try {
          instream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      page = sb.toString();

      // page = EntityUtils.toString(response.getEntity());
      // System.out.println(page);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (page != null) {
      return page;
    } else {
      return "";
    }

  }
  
  public String getHttpContent(String url, Service service) {
    String page = null;
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
        e.printStackTrace();
      } finally {
        try {
          instream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      page = sb.toString();

      // page = EntityUtils.toString(response.getEntity());
      // System.out.println(page);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (page != null) {
      return page;
    } else {
      return "";
    }

  }
}
