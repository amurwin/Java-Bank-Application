import java.util.*;
import java.lang.*;

public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return username + password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}