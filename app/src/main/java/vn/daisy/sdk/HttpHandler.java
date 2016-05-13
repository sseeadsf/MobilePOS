package vn.daisy.sdk;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
    public void sendFile(String file){
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        String pathToFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+file;
        String urlServer = "http://";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String serverResponseMessage;

        int bytesRead, byteAvailable, buffSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        int serverResponseCode;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(pathToFile));
            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            //Allow Input and Output
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Set HTTP method to POST
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens+boundary+lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""+pathToFile+"\""+lineEnd);
            outputStream.writeBytes(lineEnd);

            byteAvailable = fileInputStream.available();
            buffSize = Math.min(byteAvailable, maxBufferSize);
            buffer = new byte[buffSize];

            //read file
            bytesRead = fileInputStream.read(buffer, 0, buffSize);
            while(bytesRead != 0){
                outputStream.write(buffer, 0, buffSize);
                byteAvailable = fileInputStream.available();
                buffSize = Math.min(byteAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, buffSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

            //Responses from server (code and message)
            serverResponseCode = connection.getResponseCode();
            serverResponseMessage = connection.getResponseMessage();

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
