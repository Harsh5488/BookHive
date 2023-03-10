import Hive.BookDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class addBooks extends JFrame{
    private JTextField textId;
    private JTextField textName;
    private JTextField textAuthor;
    private JTextField textQuantity;
    private JButton addBookButton;
    private JButton backButton;
    private JPanel add;
    private JPanel addField;
    private JPanel fieldId;
    private JPanel fieldName;
    private JPanel fieldAuthor;
    private JPanel fieldQuantity;
    private JPanel fieldBtn;
    private JLabel bookId;
    private JLabel bookName;
    private JLabel bookAuthor;
    private JLabel bookQuantity;
    private JLabel statusmsg;
    private JButton btnClear;

    addBooks(){
        setTitle("Add Books");
        setContentPane(add);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage h =  new HomePage();
                dispose();
            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int bookid = Integer.parseInt(textId.getText());
                    String name = textName.getText();
                    String author = textAuthor.getText();
                    int quantity = Integer.parseInt(textQuantity.getText());
                    BookDAO b = new BookDAO();
                    Connection conn = b.Connect();
                    PreparedStatement pst = conn.prepareStatement("INSERT INTO  books VALUES(?,?,?,?);");
                    pst.setInt(1,bookid);
                    pst.setString(2,name);
                    pst.setString(3,author);
                    pst.setInt(4,quantity);
                    int count = pst.executeUpdate();
                    statusmsg.setText(count + " row(s) affected - Book Added to Hive");
                    textId.setText(null);
                    textName.setText(null);
                    textAuthor.setText(null);
                    textQuantity.setText(null);
                    b.Disconnect();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        setVisible(true);
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textId.setText(null);
                textName.setText(null);
                textAuthor.setText(null);
                textQuantity.setText(null);
            }
        });
    }
}
