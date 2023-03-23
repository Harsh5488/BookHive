import Hive.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginForm extends JFrame {
    private JPasswordField passwordtext;
    private JTextField usertext;
    private JButton btnsubmit;
    private JPanel LoginPanel;
    private JLabel statusmsg;
    private String username = "";
    private String password = "";

    public LoginForm(){
//        super(parent);
        setTitle("Login Form");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        setLogin();
        usertext.setText(username);
        passwordtext.setText(password);
        btnsubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usertext.getText();
                String password = String.valueOf(passwordtext.getPassword());
                if(getAuthorization(username, password)){
                    statusmsg.setText("Login Successful");
                    HomePage h = new HomePage();
                    dispose();
                }
                else{
                    statusmsg.setText("Login failed");
                }
            }
        });
        setVisible(true);
    }
    private boolean getAuthorization(String name, String pass){
        setLogin();
        if(username.equals(name) && password.equals(pass)){
            return true;
        }
        else{
            return false;
        }
    }
    private void setLogin(){
        try{
            LoginDAO l = new LoginDAO();
            Connection con = l.Connect();
            String query = "SELECT * FROM login;";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            res.next();
            username = res.getString(1);
            password = res.getString(2);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
