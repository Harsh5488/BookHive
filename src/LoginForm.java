import Hive.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JPasswordField passwordtext;
    private JTextField usertext;
    private JButton btnsubmit;
    private JPanel LoginPanel;
    private JLabel statusmsg;

    public LoginForm(){
//        super(parent);
        setTitle("Login Form");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
    private boolean getAuthorization(String username, String password){
//        BookDAO bd = new BookDAO();
        return true;
//        if(username.equals(bd.getUser()) && password.equals(bd.getPass())){
//            return true;
//        }
//        else{
//            return false;
//        }
    }
}
