package vn.daisy.order;

/**
 * Created by trongkhanhhd on 4/9/16.
 */
public class LoginInfo {
    private String userName;
    private String password;
    public LoginInfo(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public LoginInfo(){
        super();
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
