package vn.daisy.mobilepos;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import vn.daisy.order.Merchandise;
import vn.daisy.sdk.HttpHandler;

public class MyService extends Service {
    private int count=0;
    private Merchandise  merchandises;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String a, b, c, d;
        int e;
        float f;
        String g;
        long h;
        Scanner scan = null;
        HttpHandler http = new HttpHandler();
        try {
            scan = new Scanner(new File("Query" + count + ".xml"));
        while (connectInternet(getApplicationContext())) {
                    a = scan.next();
                    b = scan.next();
                    c = scan.next();
                    d = scan.next();
                    e = scan.nextInt();
                    f = scan.nextFloat();
                    g = scan.next();
                    h = scan.nextLong();
                    merchandises = new Merchandise(a, b, c, d, e, f, g, h);
                    http.sendFile("Query"+count+".xml");
                    count++;
            }
        } catch (FileNotFoundException e1) {
            onDestroy();
        }
        return START_STICKY;
    }
    private boolean connectInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
