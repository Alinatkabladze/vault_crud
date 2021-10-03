package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String login;
    private String password;

    public User(){ }

    public User(String login, String password){
        this.login=login;
        this.password=password;
    }

    public String getlogin() {
        return login;
    }

    public String getpassword() {
        return password;
    }
    public void setUserName(String userName) {
        this.login = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
