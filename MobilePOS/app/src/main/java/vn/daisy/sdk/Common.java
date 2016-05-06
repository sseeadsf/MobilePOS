package vn.daisy.sdk;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by trongkhanhhd on 4/9/16.
 */
public class Common {
    private Context context;

    public Common(){
        super();

    }
    public Common(Context context){
        this.context = context;
    }

    /**
     * Function: Get time now
     * @return
     */
    public static String getNowTime() {
        return getNowTime("HH:mm:ss");
    }

    /**
     *  Function: Get date now
     * @return
     */
    public static String getNowDate() {
        return getNowTime("yyyy/MM/dd");
    }

    public static String getNowTime(String format) {
        Calendar tmpCal = Calendar.getInstance();
        SimpleDateFormat tmpSDF = new SimpleDateFormat(format);
        return tmpSDF.format(tmpCal.getTime());
    }

    /**
     *  Function: Check Sdcard
     * @return
     */
    public static boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public String createFileName(String prefix, String suffixes){
        return prefix + "_" +suffixes;
    }

    /***
     * Function :Check Internet access
     * @return
     */
    public boolean isCheckNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.v("du", "connected!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void beep(){
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP,300);
    }

    public String getMACAddress(){
        String address = null;
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        address = info.getMacAddress();
        return address;
    }
}
