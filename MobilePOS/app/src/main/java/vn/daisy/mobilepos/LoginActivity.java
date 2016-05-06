package vn.daisy.mobilepos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import vn.daisy.order.Employee;
import vn.daisy.order.LoginInfo;
import vn.daisy.order.OrderMerActivity;

public class LoginActivity extends AppCompatActivity {
    private XmlPullParserFactory fc;
    private XmlPullParser parser;
    LinkedList<Employee> employee = new LinkedList<Employee>();
    public void btn_Login(View v) throws XmlPullParserException, IOException { //Login in here
        int status = 0;
        String u = null;
        String p = null;
        EditText user = (EditText) findViewById(R.id.user);
        EditText pass = (EditText) findViewById(R.id.pass);
        for (int i = 0; i < employee.size(); i++) {
            u = employee.get(i).getLoginInfo().getUserName(); //user name of each employee
            p = employee.get(i).getLoginInfo().getPassword(); //password of each employee
            if (user.getText().toString().equals(u) && pass.getText().toString().equals(p)) {
                Intent k = new Intent(getApplicationContext(), OrderMerActivity.class);
                startActivity(k);
                status = 1;
            }
        }
        if (status == 0) { //if login fail
            AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);
            b.setTitle("Alert");
            b.setMessage("Some thing went wrong");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    private void readXml() throws XmlPullParserException, FileNotFoundException { // read file xml
        fc = XmlPullParserFactory.newInstance();
        parser = fc.newPullParser();
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String xmlfile = sdcard+"/USERSXML.xml";
        FileInputStream fIn = new FileInputStream(xmlfile);
        parser.setInput(fIn, null);
    }
    private void Info() throws IOException, XmlPullParserException {
        Employee emp = null;
        int eventType = -1;
        String nodeName = null;
        String id = "";
        String name = "";
        String fullName = "";
        String dateOfBirth = "";
        String address = "";
        String position = "";
        String telephone = "";
        LoginInfo loginInfo = null; // Information about login of employee
        String user = null;
        String pass = null;

        while(eventType!= XmlPullParser.END_DOCUMENT){
            eventType = parser.next();
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    nodeName = parser.getName();
                    if (nodeName.equals("id")){
                        id = parser.nextText();
                    }
                    if (nodeName.equals("name")){
                        name = parser.nextText();
                    }
                    if(nodeName.equals("fullName")){
                        fullName = parser.nextText();
                    }
                    if(nodeName.equals("dateOfBorth")){
                        dateOfBirth = parser.nextText();
                    }
                    if(nodeName.equals("address")) {
                        address = parser.nextText();
                    }
                    if(nodeName.equals("position")) {
                        position = parser.nextText();
                    }
                    if(nodeName.equals("telephone")) {
                        telephone = parser.nextText();
                    }
                    if(nodeName.equals("userName")) {
                        user = parser.nextText();
                    }
                    if(nodeName.equals("password")) {
                        pass = parser.nextText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    nodeName = parser.getName(); //Read complete about an employee
                    if(nodeName.equals("users")){
                        loginInfo = new LoginInfo(user, pass);
                        emp = new Employee(id, name, fullName, dateOfBirth, address, position, telephone,loginInfo );  // create user
                        employee.add(emp);
                    }
            }
        }
    }
}
