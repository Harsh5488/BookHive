import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private JLabel statusmsg;
    private JPanel welcome;
    private JPanel content;
    private JPanel footer;
    private JButton btnViewBooks;
    private JButton btnaddBook;
    private JButton btnDelUpBook;
    private JButton btnIssueBook;
    private JButton btnexit;
    private JButton btnViewIssuedBooks;

    HomePage(){
        setTitle("Home Page");
        setContentPane(welcome);
        setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnViewBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBooks v = new ViewBooks();
                dispose();
            }
        });

        btnaddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBooks ab = new addBooks();
                dispose();
            }
        });

        btnexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm l = new LoginForm();
                dispose();
            }
        });

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                statusmsg.setText("Welcome To BookHive");
            }
        };
        btnViewBooks.addMouseListener(listener);
        btnaddBook.addMouseListener(listener);
        btnexit.addMouseListener(listener);
        btnIssueBook.addMouseListener(listener);
        btnDelUpBook.addMouseListener(listener);
        btnViewIssuedBooks.addMouseListener(listener);

        btnViewBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("View and Search Books from here");
            }
        });
        btnaddBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Books to the Hive can be added here");
            }
        });
        btnIssueBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Issue Books to the reader");
            }
        });
        btnDelUpBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Update or delete a Book from the hive");
            }
        });
        btnexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("Exit to the login page");
            }
        });
        btnViewIssuedBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                statusmsg.setText("View Issued books detail");
            }
        });
        btnViewIssuedBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewIssuedBooks vib = new viewIssuedBooks();
                dispose();
            }
        });
        setVisible(true);
        btnIssueBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IssueBook ib = new IssueBook();
                dispose();
            }
        });
    }
}
