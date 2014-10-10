package com.spotonresponse.xchangecore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.apache.commons.codec.binary.Base64;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class UICDStools
{
  static Logger logger = Logger.getLogger(UICDStools.class);

  public UICDStools() {
    HostnameVerifier hv = new HostnameVerifier()
    {
      public boolean verify(String urlHostName, SSLSession session) {
        UICDStools.logger.info("Warning: URL Host: " + urlHostName + 
          " vs. " + session.getPeerHost());
        return true;
      }
    };
    HttpsURLConnection.setDefaultHostnameVerifier(hv);
  }

 
  public String sendRequest(Document doc, String siteaddress, String uicdsUser, String uicdsPass, int UICDStimeout)
  {
    HttpURLConnection httpConn = null;
    try {
      logger.info("Connecting to: " + siteaddress);
      //EncryptDecrypt ed = new EncryptDecrypt();
      //String Pass = ed.decryptPass(uicdsPass);
      String Pass = uicdsPass;
      String authString = uicdsUser + ":" + Pass;

      byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
      String authStringEnc = new String(authEncBytes);

      HttpURLConnection connection = null;

      String xml = XMLUtils.xmlToString(doc);

      byte[] b = xml.getBytes();

      URL url = new URL(siteaddress);
      connection = (HttpURLConnection)url.openConnection();
      httpConn = connection;
      connection.setConnectTimeout(UICDStimeout * 1000);
      connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
      httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
      httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
      httpConn.setRequestProperty("Accept", "*/*");
      httpConn.setRequestProperty("SOAPAction", "");
      httpConn.setRequestMethod("POST");
      httpConn.setDoOutput(true);
      httpConn.setDoInput(true);

      OutputStream out = httpConn.getOutputStream();
      out.write(b);
      out.close();

      InputStreamReader isr = new InputStreamReader(
        httpConn.getInputStream());
      BufferedReader in = new BufferedReader(isr);

      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null)
      {
        sb.append(line);
      }
      return sb.toString();
    } catch (SocketTimeoutException ste) {
      logger.error("Timeout connecting to UICDS");
      return null;
    } catch (ConnectException ce) {
      return null;
    } catch (Exception ex) {
      try {
        httpConn.getResponseCode();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      ex.printStackTrace();
    }return null;
  }
}