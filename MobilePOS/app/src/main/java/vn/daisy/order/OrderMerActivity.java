package vn.daisy.order;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import vn.daisy.mobilepos.R;
import vn.daisy.sdk.Common;
import vn.daisy.sdk.XMLCreator;
import vn.daisy.sdk.XMLParser;

public class OrderMerActivity extends AppCompatActivity {

    private TextView tv_barcode;
    private DataReceive dataReceive;
    private EditText et_quantity;
    private EditText et_discount;
    private TextView tv_about;
    private TextView tv_total;
    private CheckBox cb_discount;
    private TextView tv_id_mer;
    private ListView lv_list_item;



    private List<Merchandise> merchandises;
    private XMLParser xmlParser;
    private AlertDialog.Builder dialog;
    private ItemOrderAdapter itemOrderAdapter =null;
    private  Common comm;

    private String barcode;
    private float total = 0;
    private ArrayList<Merchandise> list_mercs = null;
    private Queue<OrderForm> orderForms_quQueue;
    private String transNum="1N1234";


    private String ACTION_CONTENT_NOTIFY = "android.intent.action.CONTENT_NOTIFY";
    private final String  TAG_ERROR_ENTER = "Error Enter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_merchandise_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        merchandises = new ArrayList<>();
        xmlParser = new XMLParser();
        merchandises = xmlParser.getInforMechandises("/sdcard/SKUXML.xml");
        comm = new Common(this);
        orderForms_quQueue = new LinkedList<>();
        /**************************config container*************************/
        tv_barcode = (TextView) findViewById(R.id.tv_id_barcode);
        et_quantity = (EditText)findViewById(R.id.et_id_quantity);
        et_discount = (EditText)findViewById(R.id.et_discount);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_id_mer = (TextView) findViewById(R.id.tv_id_mer);
        cb_discount = (CheckBox)findViewById(R.id.cb_type_discount);

        /*****************Set text ************/
        tv_barcode.setText(null);
        et_quantity.setText(null);
        et_discount.setText(null);
        tv_about.setText(null);
        tv_total.setText(null);
        /****************************************/
        lv_list_item = (ListView)findViewById(R.id.lv_list_mer);
        list_mercs = new ArrayList<Merchandise>();
        itemOrderAdapter = new ItemOrderAdapter(this, R.layout.order_item_layout, list_mercs);
        lv_list_item.setAdapter(itemOrderAdapter);
        lv_list_item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    public void btnDeleteClick(View view){
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete Items");
        dialog.setMessage("Are you sure?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = lv_list_item.getChildCount()-1; i>=0; i--){
                    View view_ = lv_list_item.getChildAt(i);
                    CheckBox checkBox = (CheckBox)view_.findViewById(R.id.cb_check_item);
                    if(checkBox.isChecked()){
                        list_mercs.remove(i);
                    }
                }
                itemOrderAdapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ;
            }
        });
        dialog.show();

    }
    public void btnClearClick(View view){
        et_quantity.setText(null);
        et_discount.setText(null);
    }

    public void actions1()
    {

        if(new Common().isCheckNetworkAvailable()){


        }else{
            dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Warning");
            dialog.setMessage("Internet is not available");
            dialog.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.setNegativeButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    OrderForm orderForm = new OrderForm();
                    orderForm.setTransCode(new Common().createBillCode());
                    orderForm.setTransNum(transNum);
                    orderForm.setMachineCode(new Common().getMACAddress());
                    orderForm.setTransDate(new Common().getNowDate());
                    orderForm.setTransTime(new Common().getNowTime());
                    orderForm.setStatus('N');
                    orderForm.setMerchandises(list_mercs);
                    orderForms_quQueue.add(orderForm);

                }
            });


        }
    }

    public void btnNextClick(View view){
        Employee employee = new Employee();
        employee.setId("1N5822");
        XMLCreator xmlCreator = new XMLCreator();
        OrderForm orderForm = new OrderForm();
        orderForm.setTransCode(new Common().createBillCode());
        orderForm.setTransNum(transNum);
        orderForm.setMachineCode("AAAAA");
        orderForm.setTransDate(new Common().getNowDate());
        orderForm.setTransTime(new Common().getNowTime());
        orderForm.setStatus('N');
        orderForm.setMerchandises(list_mercs);
        orderForm.setEmployee(employee);
        xmlCreator.createXMLFile("/sdcard/khanh.xml", orderForm, 2);
        Toast.makeText(OrderMerActivity.this, "Save complete", Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, OrderMerActivity.class);
        startActivity(intent);
    }


    public void btnAddClick(View view){
        String mer_id = null;
        String full_name = null;
        String short_name = null;
        String pice_unit = null;
        float price = 0;
        int quantity = 0;
        long discount = 0;
        float about = 0;
        int temp;
        boolean status = false;
        Merchandise merchandise= null;
        try {
            if (!barcode.matches("")) {
                try {
                    Log.e(TAG_ERROR_ENTER, "Barcode: " + String.valueOf(barcode));
                    quantity = Integer.parseInt(et_quantity.getText().toString());
                    Log.e(TAG_ERROR_ENTER, "Quantity: " + String.valueOf(quantity));
                    discount = Long.parseLong(et_discount.getText().toString());
                    Log.e(TAG_ERROR_ENTER, "Discount " + String.valueOf(discount));
                    for(Merchandise merc : merchandises){
                        if(merc.getMerchandiseBarcode().matches(barcode)){
                            price = merc.getPrice();
                            mer_id = merc.getMerchandiseID();
                            full_name = merc.getMerchandiseFullName();
                            short_name = merc.getMerchandiseShortName();
                            pice_unit = merc.getPiceUnit();
                            status = true;
                        }
                    }
                    if(status){
                        status = false;
                        for(Merchandise merc : list_mercs ){
                            if(merc.getMerchandiseBarcode().matches(barcode)){
                                temp = quantity + merc.getQuantity();
                                merc.setQuantity(temp);
                                status = true;
                            }
                        }
                        if(status){
                            if(cb_discount.isChecked()){
                                about = price * quantity - discount*quantity;
                            }else {
                                about = price * quantity - ((float)discount /(float) 100) * quantity * price;
                            }
                            total +=about;
                            itemOrderAdapter.notifyDataSetChanged();

                        }else
                        {
                            merchandise = new Merchandise();
                            if(cb_discount.isChecked()){
                                about = price * quantity - discount*quantity;
                            }else {
                                about = price * quantity - ((float)discount /(float) 100) * quantity * price;
                            }
                            total +=about;

                            merchandise.setMerchandiseBarcode(barcode);
                            merchandise.setMerchandiseID(mer_id);
                            merchandise.setMerchandiseFullName(full_name);
                            merchandise.setMerchandiseShortName(short_name);
                            merchandise.setPrice(price);
                            merchandise.setQuantity(quantity);
                            merchandise.setDiscount(discount);
                            merchandise.setPiceUnit(pice_unit);
                            list_mercs.add(merchandise);
                            itemOrderAdapter.notifyDataSetChanged();
                        }

                        tv_id_mer.setText(short_name);
                        tv_about.setText(String.valueOf(about));
                        tv_total.setText(String.valueOf(total));

                        new Common().beep();
                    }else{
                        dialog = new AlertDialog.Builder(this);
                        dialog.setTitle("Warning");
                        dialog.setMessage("Data is not available, Not found " + barcode +" in database");
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                } catch (NumberFormatException ne) {
                    Log.e(TAG_ERROR_ENTER, ne.getMessage().toString());
                    dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Warning");
                    dialog.setMessage("Discount or Quantity is not available, Please enter information");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        }catch (IllegalStateException ex) {
            Log.e(TAG_ERROR_ENTER, ex.getMessage().toString());
            dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Warning");
            dialog.setMessage("Quantity is not available, Please enter information");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();

        }catch (NullPointerException e) {
            dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Warning");
            dialog.setMessage("Data is not available, Please enter information");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceive();
    }

    /*
     * Function: Read information from Barcode Reader
     *
     */
    class DataReceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            tv_barcode.setText(null);
            et_quantity.setText("");
            et_discount.setText("");
            String content, result;
            Bundle bundle  = new Bundle();
            bundle = intent.getExtras();
            content = bundle.getString("CONTENT").trim();
            result = bundle.getString("RESULT");

            if(!content.matches("")) {
                tv_barcode.setText(content);
                barcode = content;
                et_quantity.setText("");
                et_discount.setText("");
            }
        }
    }

    /*
     * Function:  This fuction is register broadcast Receiver
     */
    private void registerReceive(){
        if(dataReceive !=null)
            return;
        dataReceive = new DataReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONTENT_NOTIFY);
        registerReceiver(dataReceive, intentFilter);
    }

    /**
     * Function: This function is unregister broadcast Receiver
     */

    private void unregisterReceiver() {
        if (dataReceive != null)
            unregisterReceiver(dataReceive);
    }


}
