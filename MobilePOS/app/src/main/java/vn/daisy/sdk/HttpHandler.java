package vn.daisy.sdk;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by trongkhanhhd on 4/9/16.
 */
public class HttpHandler {
    private URLConnection urlConnection;
    private final String TAG_HTTP_STATUS = "HTTP_STATUS";

    public HttpHandler(){

    }


    public void httpDownloadFile(String sourceFileUrl, String fileTarget){
        try {
            URL url = new URL(sourceFileUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int lengOfFile;
            int count = 0;
            Object obj = urlConnection.getContent();
            lengOfFile = urlConnection.getContentLength();
            Log.i(TAG_HTTP_STATUS, "Leng file: " + sourceFileUrl +" " + String.valueOf(lengOfFile));
            InputStream inputStream = new BufferedInputStream(url.openStream(), 9999);
            OutputStream outputStream  = new FileOutputStream(fileTarget);
            byte buffer[] = new byte[1024];
            while((count = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, count);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            Log.i(TAG_HTTP_STATUS, "Download file successfully");

        }catch (MalformedURLException ex){
            Log.e(TAG_HTTP_STATUS, "Can not request URL: "+ sourceFileUrl +" " + "Exception: "+ ex.getMessage());
        }catch (IOException e){
            Log.e(TAG_HTTP_STATUS, "Can not open URL: "+ sourceFileUrl +" " + "Exception: "+ e.getMessage());
        }
    }




}
