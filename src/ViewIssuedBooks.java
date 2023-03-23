import Hive.BookDAO;
import Hive.IssueDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class ViewIssuedBooks extends JFrame {
    private JPanel content;
    private JPanel panelDBContent;
    private JTable tableIssue;
    private JPanel panelRId;
    private JPanel panelRName;
    private JPanel panelBName;
    private JPanel panelstatus;
    private JPanel panelDOI;
    private JPanel panelDOS;
    private JLabel statusmsg;
    private JPanel panelStatusMsg;
    private JLabel labelRId;
    private JLabel labelRName;
    private JLabel labelBName;
    private JLabel labelStatus;
    private JLabel labelDOI;
    private JLabel labelDOS;
    private JLabel dLabelRId;
    private JLabel dLabelRName;
    private JLabel dLabelBName;
    private JLabel dLabelStatus;
    private JLabel dLabelDOI;
    private JLabel dLabelDOS;
    private JPanel panelSearch;
    private JTextField textSearch;
    private JPanel panelBtn;
    private JButton btnBack;
    private JButton btnReset;
    private JButton btnSubmit;
    private JLabel labelSearch;
    private static DefaultTableModel model;
    TableRowSorter<DefaultTableModel> trs;

    ViewIssuedBooks(){
        setTitle("Issued Books Data");
        setContentPane(content);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        try{
            IssueDAO b = new IssueDAO();
            Connection conn = b.Connect();
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM issue");
            ResultSetMetaData rsmd = res.getMetaData();

            model = (DefaultTableModel) tableIssue.getModel();

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

        tableIssue.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (textSearch.getText().equals("")) {
                    //nhi hua
                    int i = tableIssue.getSelectedRow();
                    dLabelRId.setText(model.getValueAt(i, 1).toString());
                    dLabelRName.setText(model.getValueAt(i, 2).toString());
                    dLabelBName.setText(model.getValueAt(i, 3).toString());
                    dLabelDOI.setText(model.getValueAt(i, 4).toString());
                    if (model.getValueAt(i, 5) == null) {
                        dLabelDOS.setText("----");
                    } else {
                        dLabelDOS.setText(model.getValueAt(i, 5).toString());
                    }
                    dLabelStatus.setText(model.getValueAt(i, 6).toString());
                }
                else {
                    //hua h
                    model = (DefaultTableModel) tableIssue.getModel();
                    trs = new TableRowSorter<>(model);
                    tableIssue.setRowSorter(trs);
                    trs.setRowFilter(RowFilter.regexFilter("(?i)" + textSearch.getText()));
                    model = (DefaultTableModel) tableIssue.getModel();
                    int i = tableIssue.getSelectedRow()+1;
                    dLabelRId.setText(model.getValueAt(i, 1).toString());
                    dLabelRName.setText(model.getValueAt(i, 2).toString());
                    dLabelBName.setText(model.getValueAt(i, 3).toString());
                    dLabelDOI.setText(model.getValueAt(i, 4).toString());
                    if (model.getValueAt(i, 5) == null) {
                        dLabelDOS.setText("----");
                    } else {
                        dLabelDOS.setText(model.getValueAt(i, 5).toString());
                    }
                    dLabelStatus.setText(model.getValueAt(i, 6).toString());
                }
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage h = new HomePage();
                dispose();
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dLabelRId.setText("----");
                dLabelRName.setText("----");
                dLabelBName.setText("----");
                dLabelStatus.setText("----");
                dLabelDOI.setText("----");
                dLabelDOS.setText("----");
                textSearch.setText(null);
                tableIssue.getSelectionModel().clearSelection();
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
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dLabelRId.getText().equals("----")){
                    statusmsg.setText("Select a book for submission");
                }
                else{
                    try{

                        int ans = JOptionPane.showOptionDialog(content,"Are you sure to Submit this Book?","Confirm Submission",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Yes","No"},0);
                        if(ans==0){
                            LocalDate date = LocalDate.now();
                            IssueDAO ib = new IssueDAO();
                            Connection conn = ib.Connect();
                            String query = "UPDATE issue SET status=?, dos=? WHERE book_name=? AND reader_id=?;";
                            PreparedStatement pst = conn.prepareStatement(query);
                            pst.setString(1,"Submitted");
                            pst.setString(2,date.toString());
                            pst.setString(3, dLabelBName.getText());
                            pst.setString(4, dLabelRId.getText());
                            int count = pst.executeUpdate();

                            BookDAO bd = new BookDAO();
                            Connection conn1 = bd.Connect();
                            PreparedStatement pst2 = conn1.prepareStatement("UPDATE books SET Quantity=Quantity+1 WHERE name=?;");
                            pst2.setString(1,dLabelBName.getText());
                            pst2.executeUpdate();

                            statusmsg.setText(count + " row(s) affected - Book Submitted to Hive");

                            pst.close();
                            ib.Disconnect();

                            pst2.close();
                            bd.Disconnect();

                            ViewIssuedBooks.model.setRowCount(0);
                            IssueDAO ba = new IssueDAO();
                            Connection con = ba.Connect();
                            Statement stmt = con.createStatement();

                            ResultSet res = stmt.executeQuery("SELECT * FROM issue");
                            ViewIssuedBooks.printTable(res);
                            stmt.close();
                            ba.Disconnect();

                        }
                        else{
                            statusmsg.setText("Submission Canceled");
                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        setVisible(true);
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                statusmsg.setText("Issued Book's Database");
            }
        };
        btnSubmit.addMouseListener(listener);
        btnBack.addMouseListener(listener);
        btnReset.addMouseListener(listener);
        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Reset the selection");
            }
        });
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Back to Home Page");
            }
        });
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Submit the selected book");
            }
        });
    }
    public static void printTable(ResultSet res){
        try{
            String rid,rname,bname,doi,dos,status;
            int i=0;
            while(res.next()){
                i++;
                rid=res.getString(1);
                rname=res.getString(2);
                bname=res.getString(3);
                doi=res.getString(4);
                dos=res.getString(5);
                status=res.getString(6);
                String[] row = {i+"",rid,rname,bname,doi,dos,status};
                model.addRow(row);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void search(String str){
        model = (DefaultTableModel) tableIssue.getModel();
        trs = new TableRowSorter<>(model);
        tableIssue.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + str));

    }
}
