import Hive.LoginDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateLogin extends JFrame{
    private JPanel panelUpLogin;
    private JButton btnUpdate;
    private JButton btnBack;
    private JLabel labelUpdateLogin;
    private JLabel labelNewUser;
    private JLabel labelOldPass;
    private JLabel labelNewPass;
    private JTextField textNewUser;
    private JTextField textOldPass;
    private JTextField textNewPass;
    private JPanel panelLabel;
    private JPanel panelText;
    private JPanel panelBtn;
    private JPanel panelTitle;
    private JPanel panelField;
    private JLabel statusmsg;
    private JLabel labelOldUser;
    private JTextField textOldUser;
    private JButton btnClear;

    UpdateLogin(){
        setTitle("Update Login");
        setContentPane(panelUpLogin);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                HomePage h = new HomePage();
                dispose();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (
                            textOldUser.getText().equals("")
                                    || textNewUser.getText().equals("")
                                    || textOldPass.getText().equals("")
                                    || textNewUser.getText().equals("")
                    ) {
                        statusmsg.setText("Enter ALL the field before adding");
                    } else {
                        int ans = JOptionPane.showConfirmDialog(panelUpLogin,"Are you sure to update?","Confirm Update",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(ans==JOptionPane.YES_OPTION){
                            //fire query for update in the database
                            LoginDAO l = new LoginDAO();
                            Connection con = l.Connect();
                            Statement stmt = con.createStatement();
                            ResultSet res = stmt.executeQuery("SELECT * FROM login;");
                            res.next();
                            String prevUser = res.getString(1);
                            String prevPass = res.getString(2);
                            if (prevUser.equals(textOldUser.getText()) && prevPass.equals(textOldPass.getText())) {
                                String newUser = textNewUser.getText();
                                String newPass = textNewPass.getText();
                                PreparedStatement pst = con.prepareStatement("UPDATE login SET username=?, password=? WHERE username=? AND password=?;");
                                pst.setString(1,newUser);
                                pst.setString(2,newPass);
                                pst.setString(3,prevUser);
                                pst.setString(4,prevPass);
                                pst.executeUpdate();
                                statusmsg.setForeground(Color.BLACK);
                                statusmsg.setText("Login Updated Successfully!");
                                textOldUser.setText(null);
                                textNewUser.setText(null);
                                textOldPass.setText(null);
                                textNewPass.setText(null);
                            }
                            else{
                                statusmsg.setText("Authentication failed!");
                                statusmsg.setForeground(Color.red);
                            }
                        }
                        else{
                            statusmsg.setForeground(Color.BLACK);
                            statusmsg.setText("Update Canceled");
                        }
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textOldUser.setText(null);
                textNewUser.setText(null);
                textOldPass.setText(null);
                textNewPass.setText(null);
            }
        });
        setVisible(true);
    }
}
