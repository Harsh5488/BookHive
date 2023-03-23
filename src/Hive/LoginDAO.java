package Hive;

import java.sql.Connection;
import java.sql.DriverManager;

public class LoginDAO {
    private String url = DBCredentials.url;
    private String user = DBCredentials.user;
    private String pass = DBCredentials.pass;
    Connection conn;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public Connection Connect(){
        try {
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void Disconnect(){
        try{
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
