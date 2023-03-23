import Hive.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateDialog extends JDialog {

    private JButton btnUpdate;
    private JButton btnCancel;
    private JTextField textBId;
    private JTextField textBName;
    private JTextField textAuthor;
    private JTextField textQuantity;
    private JLabel labelBId;
    private JLabel labelBName;
    private JLabel labelAuthor;
    private JLabel labelQuantity;
    private JPanel panelUpdate;
    private JLabel statusmsg;
    public static int one_count=0;

    UpdateDialog(Book b){
        setTitle("Update Books");
        setContentPane(panelUpdate);
        setMinimumSize(new Dimension(400,200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        one_count++;

        textBId.setText(""+b.getBookid());
        textBName.setText(b.getBname());
        textAuthor.setText(b.getAuthor());
        textQuantity.setText(""+b.getQuantity());

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(
                            textBId.getText().equals("")
                                    || textBName.getText().equals("")
                                    || textAuthor.getText().equals("")
                                    || textQuantity.getText().equals("")
                    ){
                        statusmsg.setText("Enter ALL the field before adding");
                    }
                    else {
                        int initbookid = b.getBookid();
                        int bookid = Integer.parseInt(textBId.getText());
                        String name = textBName.getText();
                        String author = textAuthor.getText();
                        int quantity = Integer.parseInt(textQuantity.getText());
                        BookDAO bk = new BookDAO();
                        Connection conn = bk.Connect();
                        PreparedStatement pst = conn.prepareStatement("UPDATE books SET bookid=?, name=?, author=?, quantity=? WHERE bookid=?;");
                        pst.setInt(1, bookid);
                        pst.setString(2, name);
                        pst.setString(3, author);
                        pst.setInt(4, quantity);
                        pst.setInt(5,initbookid);
                        pst.executeUpdate();
                        ViewBooks.model.setRowCount(0);
                        BookDAO ba = new BookDAO();
                        Connection con = ba.Connect();
                        Statement stmt = con.createStatement();

                        ResultSet res = stmt.executeQuery("SELECT * FROM books");
                        ViewBooks.printTable(res);
                        stmt.close();
                        ba.Disconnect();

                        pst.close();
                        bk.Disconnect();
                        dispose();
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}
