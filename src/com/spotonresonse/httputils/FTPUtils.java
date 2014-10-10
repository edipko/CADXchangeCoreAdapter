package com.spotonresonse.httputils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;


import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
 
/**
 * A program demonstrates how to upload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPUtils {
 
	public static String ftpFetchFile(String _url) {
		URL url;
		System.out.println("Getting url: " + _url);
		String line = "";
		String lines = "";
		try {
			url = new URL(_url);
	        BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
	        while((line=in.readLine())!=null)
	            {
	            lines += line;
	            }
	        in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        return lines;
        
	}
    public static String ftpFetchFile(String server, int port, String file, String user, String pass) {
    	
        FTPClient ftpClient = new FTPClient();
        String xml = "";
        
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
           // ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
 
            OutputStream outputStream = new ByteArrayOutputStream();
            boolean success = ftpClient.retrieveFile(file, outputStream);
            if (success) {
            	xml = outputStream.toString();
            }
            outputStream.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return xml;
    }
}