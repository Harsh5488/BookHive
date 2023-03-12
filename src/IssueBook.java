import Hive.BookDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class IssueBook extends JFrame {
    private JPanel panelIssue;
    private JPanel addField;
    private JPanel fieldId;
    private JLabel labelRName;
    private JTextField textRName;
    private JPanel fieldAuthor;
    private JLabel labelDOI;
    private JTextField textDOI;
    private JPanel fieldName;
    private JLabel labelBName;
    private JTextField textBName;
    private JPanel fieldBtn;
    private JButton btnIssueBook;
    private JButton btnClear;
    private JButton btnBack;
    private JLabel statusmsg;
    private JPanel panelTitle;
    private JLabel labelTitle;
    private JLabel labelRId;
    private JTextField textRId;
    private JComboBox cBoxBName;

    IssueBook(){
        setTitle("Issue Books");
        setContentPane(panelIssue);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try{
            BookDAO b = new BookDAO();
            Connection conn = b.Connect();
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM books");
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
                textBName.setText(null);
                textDOI.setText(null);
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

                }
            }
        });
        setVisible(true);
    }
}
