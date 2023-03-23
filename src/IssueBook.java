import Hive.BookDAO;
import Hive.IssueDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class IssueBook extends JFrame {
    private JPanel panelIssue;
    private JPanel panelAdd;
    private JPanel panelRName;
    private JLabel labelRName;
    private JTextField textRName;
    private JPanel panelDOI;
    private JLabel labelDOI;
    private JTextField textDOI;
    private JPanel panelBName;
    private JLabel labelBName;
    private JComboBox cBoxBName;
    private JPanel fieldBtn;
    private JButton btnIssueBook;
    private JButton btnClear;
    private JButton btnBack;
    private JLabel statusmsg;
    private JPanel panelTitle;
    private JLabel labelTitle;
    private JLabel labelRId;
    private JTextField textRId;
    private JPanel panelRId;
    private JCheckBox cbToday;
    private LocalDate date;

    IssueBook(){
        setTitle("Issue Books");
        setContentPane(panelIssue);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        cbToday.setSelected(true);
        textDOI.setEditable(false);
        date = LocalDate.now();

        textDOI.setText(date.toString());
        try{
            BookDAO b = new BookDAO();
            Connection conn = b.Connect();
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM books");
            cBoxBName.addItem("Select");

            while(res.next()){
                cBoxBName.addItem(res.getString(2));
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textRId.setText(null);
                textRName.setText(null);
                cBoxBName.setSelectedIndex(0);
//                textDOI.setText(null);
                statusmsg.setText("Fill the field and Hit \"Add Book\"");
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage h =  new HomePage();
                dispose();
            }
        });
        btnIssueBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(
                        textRId.getText().equals("")
                        || textRName.getText().equals("")
                        || cBoxBName.getSelectedItem().equals("Select")
                        || textDOI.getText().equals("")
                ){
                    statusmsg.setText("Enter ALL the field before adding");
                }
                else {
                    try{
                        IssueDAO ib = new IssueDAO();
                        Connection conn = ib.Connect();
                        String rid, rname, bname, date;
                        rid = textRId.getText();
                        rname = textRName.getText();
                        bname = cBoxBName.getSelectedItem().toString();
                        date = textDOI.getText();

                        PreparedStatement pst1 = conn.prepareStatement("SELECT Quantity from books WHERE name=?;");
                        pst1.setString(1,bname);
                        ResultSet res = pst1.executeQuery();
                        res.next();
                        if(res.getInt(1)==0){
                            statusmsg.setText("Book Currently NOT Available in Hive");
                            pst1.close();
                            ib.Disconnect();
                        }
                        else{
                            PreparedStatement pst2 = conn.prepareStatement("UPDATE books SET Quantity=Quantity-1 WHERE name=?;");
                            pst2.setString(1,bname);
                            pst2.executeUpdate();

                            PreparedStatement pst = conn.prepareStatement("INSERT INTO issue VALUES(?,?,?,?,?,?);");
                            pst.setString(1, rid);
                            pst.setString(2, rname);
                            pst.setString(3, bname);
                            pst.setString(4, date);
                            pst.setString(5, null);
                            pst.setString(6, "Pending");
                            int count = pst.executeUpdate();

                            statusmsg.setText(count + " row(s) affected. Book Issued Successfully");

                            pst2.close();
                            pst.close();
                            ib.Disconnect();

                            textRId.setText(null);
                            textRName.setText(null);
                            cBoxBName.setSelectedIndex(0);
                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        cbToday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbToday.isSelected()){
                    date = LocalDate.now();
                    textDOI.setText(date.toString());
                    textDOI.setEditable(false);
                }
                else{
                    textDOI.setEditable(true);
                }
            }
        });
        setVisible(true);
    }
}
