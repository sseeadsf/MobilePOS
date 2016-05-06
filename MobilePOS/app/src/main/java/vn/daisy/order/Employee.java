package vn.daisy.order;

/**
 * Created by trongkhanhhd on 4/9/16.
 */
public class Employee {
    private String id;
    private String name;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private String position;
    private String telephone;
    private LoginInfo loginInfo;

    public Employee(String id,String name, String fullName, String dateOfBirth, String address, String position, String telephone, LoginInfo loginInfo) {
        this.id  = id;
        this.name = name;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.position = position;
        this.telephone = telephone;
        this.loginInfo = loginInfo;
    }
    public Employee(){
        super();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getPosition() {
        return position;
    }

    public String getTelephone() {
        return telephone;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
