package vn.daisy.order;

import java.util.List;

/**
 * Created by trongkhanhhd on 4/7/16.
 * Function: this class contain object and method relate transaction
 */
public class OrderForm {
    private String transCode; // Id of Transaction
    private String transDate; // date request order form
    private String transTime;
    private String transNum;
    private List<Merchandise> merchandises; //list merchandise request
    private Employee employee; // employee execute
    private String machineCode;
    private String status;

    public OrderForm(String transCode, String transDate, String transTime, String transNum, List<Merchandise> merchandises, Employee employee, String machineCode, String status) {
        this.transCode = transCode;
        this.transDate = transDate;
        this.transTime = transTime;
        this.transNum = transNum;
        this.merchandises = merchandises;
        this.employee = employee;
        this.machineCode = machineCode;
        this.status = status;
    }
    public OrderForm(){
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public String getTransDate() {
        return transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public String getTransNum() {
        return transNum;
    }

    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public long getTotalOrder(int code) {
        long total=0;
        for(Merchandise merchandise:merchandises)
        {
            total +=merchandise.getTotal(code);
        }
        return total;
    }
}
