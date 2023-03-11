import Hive.BookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;

public class ViewBooks extends JFrame {
    private JPanel bookView;
    private JTable bookTable;
    private JTextField textSearch;
    private JButton btnBack;
    private JLabel labelId;
    private JLabel labelName;
    private JLabel labelAuthor;
    private JLabel labelQuantity;
    private JLabel dLabelId;
    private JLabel dLabelName;
    private JLabel dLabelAuthor;
    private JLabel dLabelQuantity;
    private JButton btnReset;
    private JPanel panelPreview;
    private JPanel panelSearch;
    private JPanel panelBtn;
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

        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = bookTable.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel)bookTable.getModel();
                dLabelId.setText(model.getValueAt(i,1).toString());
                dLabelName.setText(model.getValueAt(i,2).toString());
                dLabelAuthor.setText(model.getValueAt(i,3).toString());
                dLabelQuantity.setText(model.getValueAt(i,4).toString());
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dLabelId.setText(null);
                dLabelName.setText(null);
                dLabelAuthor.setText(null);
                dLabelQuantity.setText(null);
                textSearch.setText(null);
            }
        });

        textSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String keyword = textSearch.getText();
                search(keyword);
            }
        });
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

    public void search(String str){
        model = (DefaultTableModel) bookTable.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        bookTable.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + str));
    }
}
