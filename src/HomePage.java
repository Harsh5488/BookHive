import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JLabel statusmsg;
    private JPanel welcome;
    private JPanel content;
    private JPanel footer;
    private JButton ViewBooks;
    private JButton deleteBook;
    private JButton updateBook;
    private JButton addBook;
    private JButton issueBook;
    private JButton exitbtn;

    HomePage(){
        setTitle("Home Page");
        setContentPane(welcome);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);




        ViewBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBooks v = new ViewBooks();
                dispose();
            }
        });

        addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBooks ab = new addBooks();
                dispose();
            }
        });

        exitbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm l = new LoginForm();
                dispose();
            }
        });
        setVisible(true);
    }
}
