package com.spotonresonse.httputils;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;



public class HttpServices {

	public static String postXML(String xml, String url, UserAuthenticationObject ua) {
		DefaultHttpClient client = new DefaultHttpClient();
		String result = "";
		try {
			client.getCredentialsProvider().setCredentials(
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), 
                    new UsernamePasswordCredentials(ua.getUsername(), ua.getPassword()));
			HttpPost post = new HttpPost(url);
			HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}

	public static String postParameters(Map<String,Object> params, String urlstring) {
		String response = "";
		try {
			URL url = new URL(urlstring);
	System.out.println("posting to: " + urlstring);		
			StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        response = IOUtils.toString(conn.getInputStream(), "UTF-8");
	        		
	        //Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        //for (int c; (c = in.read()) >= 0; System.out.print((char)c));
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}

	
}
