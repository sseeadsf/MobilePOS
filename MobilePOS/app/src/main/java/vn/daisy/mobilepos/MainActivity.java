package vn.daisy.mobilepos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import vn.daisy.order.OrderMerActivity;
import vn.daisy.sdk.Common;
import vn.daisy.sdk.HttpHandler;

public class MainActivity extends AppCompatActivity {

    private HttpHandler httpHandler;
    public static final int progress_bar_type = 0;
    public static final String PATH_FILE_UML = "/sdcard/SKUXML.xml";

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnInventoryClick(View view){
        if(Common.checkFileExist(PATH_FILE_UML)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Override this file?");
            dialog.setMessage("Are you sure?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new DownloadFile().execute("http://192.168.1.121/SKUXML.xml");
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();

        }else{
            new DownloadFile().execute("http://192.168.1.121/SKUXML.xml");
        }
        Intent intent = new Intent(this, OrderMerActivity.class);
        startActivity(intent);
    }
    @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Download File Starting....");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setCancelable(true);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                return pDialog;
            default:
                return null;
        }
    }

    class DownloadFile extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissDialog(progress_bar_type);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pDialog.setProgress(Integer.parseInt(values[0]));
        }
        @Override
        protected String doInBackground(String... f_url) {
            int count = 0;
            try{
                URL url = new URL(f_url[0]);
                Log.i("du", f_url[0].toString());
                URLConnection urlCon = url.openConnection();
                urlCon.connect();
                int lOfFile = 0;
                Object obj = urlCon.getContent();
                Log.i("ducon", obj.toString());
                lOfFile= urlCon.getContentLength();
                Log.i("du", String.valueOf(lOfFile));
                InputStream inputStream = new BufferedInputStream(url.openStream(), 9000);
                OutputStream outputStream =  new FileOutputStream(PATH_FILE_UML);
                byte data[] = new byte[1024];
                long total = 0;
                while((count=inputStream.read(data)) !=-1)
                {
                    total += count;
                    publishProgress(""+(int)(total*100)/lOfFile);
                    outputStream.write(data,0,count);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();


            }catch (Exception ex){
                Log.i("du", ex.toString());
            }
            return null;
        }
    }



}
