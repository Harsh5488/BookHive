import Hive.BookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewBooks extends JFrame {
    private JPanel bookView;
    private JTable bookTable;
    private JButton btnSearch;
    private JButton btnBack;
    private JTextField textId;
    private JTextField textName;
    private JTextField textAuthor;
    private JTextField textQuantity;
    private JPanel panelID;
    private JPanel panelName;
    private JPanel panelAuthor;
    private JPanel panelQuantity;
    private JLabel labelID;
    private JLabel labelName;
    private JLabel labelAuthor;
    private JLabel labelQuantity;
    private JButton btnClear;
    private static DefaultTableModel model;

    ViewBooks(){
        setTitle("Books Available");
        setContentPane(bookView);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try{
            BookDAO b = new BookDAO();
            Connection conn = b.Connect();
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM books");
            ResultSetMetaData rsmd = res.getMetaData();

            model = (DefaultTableModel)bookTable.getModel();

            int cols = rsmd.getColumnCount();
            cols++;
            String[] colnames = new String[cols];
            colnames[0] = "S. No.";
            for(int i=1;i<cols;i++){
                colnames[i]= rsmd.getColumnName(i);
            }
            model.setColumnIdentifiers(colnames);
            printTable(res);

            b.Disconnect();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage h = new HomePage();
                dispose();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int bookid = Integer.parseInt(textId.getText());
                String name = textName.getText();
                String author = textAuthor.getText();
                int quantity = Integer.parseInt(textQuantity.getText());
                BookDAO b = new BookDAO();
                Connection conn = b.Connect();

                model.setRowCount(0);

            }
        });

        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = bookTable.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel)bookTable.getModel();
                textId.setText(model.getValueAt(i,1).toString());
                textName.setText(model.getValueAt(i,2).toString());
                textAuthor.setText(model.getValueAt(i,3).toString());
                textQuantity.setText(model.getValueAt(i,4).toString());
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textId.setText(null);
                textName.setText(null);
                textAuthor.setText(null);
                textQuantity.setText(null);
            }
        });

        KeyAdapter listener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.getSource()==textId){
                    try {
                        model.setRowCount(0);
                        int id = Integer.parseInt(textId.getText());
                        BookDAO b = new BookDAO();
                        Connection conn = b.Connect();
                        String query = "select * from books where cast(bookid as char) like '%?%';";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setInt(1,id);
                        ResultSet res = stmt.executeQuery();
                        printTable(res);
                        b.Disconnect();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        };
        textId.addKeyListener(listener);
        textQuantity.addKeyListener(listener);
        textName.addKeyListener(listener);
        textAuthor.addKeyListener(listener);
        setVisible(true);
    }
    public static void printTable(ResultSet res){
        try{
            String stdid,name,author,quantity;
            int i=0;
            while(res.next()){
                i++;
                stdid=res.getString(1);
                name=res.getString(2);
                author=res.getString(3);
                quantity=res.getString(4);
                String[] row = {i+"",stdid, name, author, quantity};
                model.addRow(row);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
