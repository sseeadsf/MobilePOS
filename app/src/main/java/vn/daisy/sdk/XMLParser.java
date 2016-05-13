package vn.daisy.sdk;

import android.support.annotation.Nullable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.daisy.order.Merchandise;

/**
 * Created by trongkhanhhd on 4/8/16.
 * Parser Xml file use SAX technical
 */
public class XMLParser {
    /* XML TAG*/
    private final String TAG_ROOT = "VFPData";
    private final String TAG_NODE = "mercinfo" ;
    private final String TAG_CHILD_PRICE="rtprice";
    private final String TAG_CHILD_MER_ID="sku_id";
    private final String TAG_CHILD_MER_FULL_NAME="full_name";
    private final String TAG_CHILD_MER_SHORT_NAME="short_name";
    private final String TAG_CHILD_BARCODE="barcode";
    private final String TAG_CHILD_PICE_UNIT = "piceunit";
    /* XML TAG status handle*/
    private final String TAG_XML_PARSER_STATUS = "XML_PARSER_STATUS";

    private XmlPullParserFactory xmlPullParserFactory;
    private XmlPullParser xmlPullParser;
    public XMLParser() {
        try{
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParser = xmlPullParserFactory.newPullParser();

        }catch (XmlPullParserException ex){
            Log.e(TAG_XML_PARSER_STATUS , "XML Parser Exception: "+ ex.getMessage() );

        }
    }

    /**
     * Function: Get all information children node
     * @param sourceXmlFileUri
     * @return
     */
    @Nullable
    public List<Merchandise> getInforMechandises(String sourceXmlFileUri){

        List<Merchandise> merchandises = new ArrayList<>();
        Merchandise merchandise =null;
        String nodeName = null;
        int eventType = -1;
        String sku_code = null;
        String barcode = null;
        String short_name = null;
        String full_name = null;
        String pice_unit = null;
        float price = 0 ;

        try {

            FileInputStream fileInputStream = new FileInputStream(sourceXmlFileUri);

            xmlPullParser.setInput(fileInputStream, "UTF-8");

            while (eventType != XmlPullParser.END_DOCUMENT){
                eventType = xmlPullParser.next();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                    {
                        nodeName = xmlPullParser.getName();
                        if(nodeName.equals(TAG_CHILD_BARCODE))
                            barcode = xmlPullParser.nextText();
                        else if(nodeName.equals(TAG_CHILD_MER_ID))
                            sku_code= xmlPullParser.nextText();
                        else if(nodeName.equals(TAG_CHILD_MER_SHORT_NAME))
                            short_name = xmlPullParser.nextText();
                        else if(nodeName.equals(TAG_CHILD_MER_FULL_NAME))
                             full_name = xmlPullParser.nextText();
                        else if(nodeName.equals(TAG_CHILD_PICE_UNIT))
                            pice_unit = xmlPullParser.nextText();
                        else if(nodeName.equals(TAG_CHILD_PRICE))
                            price = Float.parseFloat(xmlPullParser.nextText());
                    }
                    break;
                    case XmlPullParser.END_TAG:
                    {
                        nodeName = xmlPullParser.getName();
                        if (nodeName.equals(TAG_NODE)){
                            merchandise = new Merchandise(barcode, short_name,full_name, sku_code, 0, price, pice_unit, 0);
                            merchandises.add(merchandise);
                        }
                    }
                    break;
                    default:break;
                }
            }

        }catch (IOException ex){
            Log.e(TAG_XML_PARSER_STATUS, "Read XML File Exception: "+ ex.getMessage());

        }catch (XmlPullParserException e){
            Log.e(TAG_XML_PARSER_STATUS , "XML Parser Exception: "+ e.getMessage() );
        }
        return  merchandises;
    }

}
