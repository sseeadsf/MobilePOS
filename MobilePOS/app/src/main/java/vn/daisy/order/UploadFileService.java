package vn.daisy.order;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import vn.daisy.sdk.XMLCreator;

/**
 * Created by trongkhanhhd on 4/23/16.
 */
public class UploadFileService extends Service{
    private final String TAG_NULL_UPLOAD="NP_UPLOAD_SERVICE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }

    private void readQueue(Queue<OrderForm> orderForms) {
        List<Merchandise> merchandises = new ArrayList<>();
        String fileName = null;
        OrderForm orderForm ;
        XMLCreator xmlCreator = new XMLCreator();
        try {
            while (orderForms.isEmpty()) {
                orderForm = orderForms.poll();
                merchandises = orderForm.getMerchandises();
                fileName = String.valueOf(orderForm.getTransCode()+orderForm.getEmployee().getId() +".xml");
 
            }
        }catch (NullPointerException ex){
            Log.e(TAG_NULL_UPLOAD, "Queue is empty");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
