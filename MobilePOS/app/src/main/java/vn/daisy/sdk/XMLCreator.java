package vn.daisy.sdk;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import vn.daisy.order.Merchandise;
import vn.daisy.order.OrderForm;

/**
 * Created by trongkhanhhd on 4/9/16.
 * Create XMl file use DOM technical
 */
public class XMLCreator {
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    private final String TAG_XML_CREATOR_STATUS = "XML_PARSER_STATUS";
    // tag root
    private final String TAG_ROOT = "VFPData";
    // Tag header
    private final String TAG_HEADER_TRANS_NUM ="transnum";
    private final String TAG_HEADER_TRANS_DATE ="transdate";
    private final String TAG_HEADER_TRANS_TIME ="transtime";
    private final String TAG_HEADER_TRANS_ID ="transcode";
    private final String TAG_TOTAL = "totalall";
    private final String TAG_HEADER_EMPLOYEE_ID = "user_id";
    private final String TAG_HEADER_MACHINE_ID = "machine_id";
    // tag children
    private final String TAG_NODE = "mercinfo" ;
    private final String TAG_CHILD_MER_ID="sku_id";
    private final String TAG_CHILD_MER_QUANTITY="quantity";
    private final String TAG_CHILD_PRICE="price";
    private final String TAG_CHILD_DISCOUNT="discount";
    private final String TAG_CHILD_TOTAL="total";
    private final String TAG_STARUS = "status";

    public final int TAG_DISCOUNT_PER = 1;
    public final int TAG_DISCOUNT_VALUE = 2;

    public XMLCreator(){
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        }catch (Exception ex){
            Log.e(TAG_XML_CREATOR_STATUS, "XML Creater Document Builder Exception: " + ex.getMessage());
        }
    }
    public void createXMLFile(String sourceFileUri, OrderForm orderForm, int code){
        Element root = document.createElement(TAG_ROOT);
        document.appendChild(root);

        Element tran_num = document.createElement(TAG_HEADER_TRANS_NUM);
        tran_num.appendChild(document.createTextNode(orderForm.getTransNum()));
        root.appendChild(tran_num);
        /*************/
        Element date = document.createElement(TAG_HEADER_TRANS_DATE);
        date.appendChild(document.createTextNode(orderForm.getTransDate()));
        root.appendChild(date);

        Element time = document.createElement(TAG_HEADER_TRANS_TIME);
        time.appendChild(document.createTextNode(orderForm.getTransTime()));
        root.appendChild(time);

        Element trans_code = document.createElement(TAG_HEADER_TRANS_ID);
        trans_code.appendChild(document.createTextNode(orderForm.getTransCode()));
        root.appendChild(trans_code);

        Element user_id = document.createElement(TAG_HEADER_EMPLOYEE_ID);
         user_id.appendChild(document.createTextNode(orderForm.getEmployee().getId()));
        root.appendChild(user_id);

        Element machine_id= document.createElement(TAG_HEADER_MACHINE_ID);
        machine_id.appendChild(document.createTextNode(orderForm.getMachineCode()));
        root.appendChild(machine_id);

        Element status = document.createElement(TAG_STARUS);
        status.appendChild(document.createTextNode(String.valueOf(orderForm.getStatus())));
        root.appendChild(status);


        Element merchandise;
        for(Merchandise merc: orderForm.getMerchandises()){
            merchandise = document.createElement(TAG_NODE);
            root.appendChild(merchandise);

            Element sku_code = document.createElement(TAG_CHILD_MER_ID);
            sku_code.appendChild(document.createTextNode(merc.getMerchandiseID()));
            merchandise.appendChild(sku_code);

            Element quantity = document.createElement(TAG_CHILD_MER_QUANTITY);
            quantity.appendChild(document.createTextNode(String.valueOf(merc.getQuantity())));
            merchandise.appendChild(quantity);

            Element price  = document.createElement(TAG_CHILD_PRICE);
            price.appendChild(document.createTextNode(String.valueOf(merc.getPrice())));
            merchandise.appendChild(price);

            Element discount;
            switch (code){
                case TAG_DISCOUNT_PER : {
                    discount = document.createElement(TAG_CHILD_DISCOUNT);
                    discount.appendChild(document.createTextNode(String.valueOf(merc.getDiscount() +"%")));
                    merchandise.appendChild(discount);
                }break;
                case TAG_DISCOUNT_VALUE : {
                    discount = document.createElement(TAG_CHILD_DISCOUNT);
                    discount.appendChild(document.createTextNode(String.valueOf(merc.getDiscount())));
                    merchandise.appendChild(discount);
                }
                default:break;
            }

            Element total = document.createElement(TAG_CHILD_TOTAL);
            total.appendChild(document.createTextNode(String.valueOf(merc.getTotal(code))));
            merchandise.appendChild(total);
        }

            TransformerFactory tranFactory = TransformerFactory.newInstance();
        try {
            Transformer tran = tranFactory.newTransformer();
            DOMSource dom = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(sourceFileUri));
            tran.transform(dom,streamResult);
        }catch (TransformerException ex){
            Log.e(TAG_XML_CREATOR_STATUS, "XML Creater Tranformer Exception: " + ex.getMessage());
        }


    }
}
