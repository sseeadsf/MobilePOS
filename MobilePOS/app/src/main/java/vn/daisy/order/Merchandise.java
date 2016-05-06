package vn.daisy.order;

/**
 * Created by trongkhanhhd on 4/7/16.
 */
public class Merchandise {
    private String merchandiseBarcode; // barcode of merchandise
    private String merchandiseShortName; // name merchandise
    private String merchandiseFullName;
    private String merchandiseID;// sku code of merchandise
    private int quantity; // qunatity order
    private float price; // prices of order
    private String piceUnit; // unit of merchandise
    private long discount;
    public final int TAG_DISCOUNT_PER = 1;
    public final int TAG_DISCOUNT_VALUE = 2;


    public String toString(){
        return merchandiseBarcode+"\t"+merchandiseShortName+"\t"+merchandiseFullName+"\t"+merchandiseID+"\t"+quantity+"\t"+price+"\t"+piceUnit+"\t"+discount;
    }
    public Merchandise(String merchandiseBarcode, String merchandiseShortName, String merchandiseFullName, String merchandiseID, int quantity, float price, String piceUnit, long discount) {
        this.merchandiseBarcode = merchandiseBarcode;
        this.merchandiseShortName = merchandiseShortName;
        this.merchandiseFullName = merchandiseFullName;
        this.merchandiseID = merchandiseID;
        this.quantity = quantity;
        this.price = price;
        this.piceUnit = piceUnit;
        this.discount = discount;
    }
    public Merchandise(){
        super();

    }

    public float getTotal(int code) {
        float total = 0;
        switch (code) {
            case TAG_DISCOUNT_PER: {
                total = quantity*price - quantity*price*(float)(discount);

            }
            break;
            case TAG_DISCOUNT_VALUE: {
                total = quantity*price - quantity*(float)discount;
            }
            break;
            default:
                break;

        }
        return total;

    }

    public String getMerchandiseBarcode() {
        return merchandiseBarcode;
    }

    public String getMerchandiseShortName() {
        return merchandiseShortName;
    }

    public String getMerchandiseFullName() {
        return merchandiseFullName;
    }

    public String getMerchandiseID() {
        return merchandiseID;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getPiceUnit() {
        return piceUnit;
    }

    public long getDiscount() {
        return discount;
    }

    public void setMerchandiseBarcode(String merchandiseBarcode) {
        this.merchandiseBarcode = merchandiseBarcode;
    }

    public void setMerchandiseShortName(String merchandiseShortName) {
        this.merchandiseShortName = merchandiseShortName;
    }

    public void setMerchandiseFullName(String merchandiseFullName) {
        this.merchandiseFullName = merchandiseFullName;
    }

    public void setMerchandiseID(String merchandiseID) {
        this.merchandiseID = merchandiseID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPiceUnit(String piceUnit) {
        this.piceUnit = piceUnit;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }



}
