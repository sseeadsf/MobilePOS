package vn.daisy.order;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.daisy.mobilepos.R;

/**
 * Created by trongkhanhhd on 4/10/16.
 */
public class ItemOrderAdapter extends ArrayAdapter<Merchandise>{
    Activity context;
    int layoutId;
    ArrayList<Merchandise> merc;

    public ItemOrderAdapter(Activity context, int layoutId, ArrayList<Merchandise> merc) {
        super(context, layoutId, merc);
        this.context = context;
        this.layoutId = layoutId;
        this.merc = merc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.order_item_layout, null);
        if(merc.size() >0 && position >=0){
            final TextView tv_name_item = (TextView)convertView.findViewById(R.id.tv_name_item);
            Merchandise merchandise = merc.get(position);
            tv_name_item.setText(merchandise.getMerchandiseFullName());
            final TextView tv_id_item = (TextView)convertView.findViewById(R.id.tv_id_item);
            tv_id_item.setText(merchandise.getMerchandiseBarcode());
            return  convertView;
        }



        return super.getView(position, convertView, parent);





    }
}
