import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassWord extends Dialog
        implements ActionListener {

    private final JButton buttonEnter;
    private final JButton buttonCancel;
    private final JTextField userID;
    private final JPasswordField password;
    private final HardwareStore hwStore;
    private String whichDialog;

    public PassWord(HardwareStore hw_Store) {
        super(new Frame(), "Password Check", true);

        hwStore = hw_Store;
        buttonEnter = new JButton("Enter");
        buttonCancel = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(3, 2));
        add(mainPanel, BorderLayout.CENTER);

        userID = new JTextField(10);
        password = new JPasswordField(10);

        JLabel userIDLabel = new JLabel("Enter your user ID");
        JLabel passwordLabel = new JLabel("Enter your user password");

        mainPanel.add(userIDLabel);
        mainPanel.add(userID);
        mainPanel.add(passwordLabel);
        mainPanel.add(password);

        buttonPanel.add(buttonEnter);
        buttonPanel.add(buttonCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        buttonEnter.addActionListener(this);
        buttonCancel.addActionListener(this);

        setSize(400, 300);
    }

    public void displayDialog(String which_Dialog) {
        whichDialog = which_Dialog;
        userID.setText("admin");
        password.setText("hwstore");
        setVisible(true);
    }

    public void closeDialog() {
        whichDialog = "closed";
        userID.setText("");
        password.setText("");
        clear();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonEnter) {
            String pwd = new String(password.getPassword());
            String uID = userID.getText();

            if ((uID.equals("admin")) && (pwd.equals("hwstore"))) {
                switch (whichDialog) {
                    case "delete":
                        hwStore.displayDeleteDialog();
                        closeDialog();
                        break;

                    case "update":
                        hwStore.displayUpdateDialog();
                        closeDialog();
                        break;

                    case "add":
                        hwStore.displayAddDialog();
                        closeDialog();
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "A userid or the password was incorrect.\n",
                        "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                userID.setText("");
                password.setText("");
            }
        }
        clear();
    }

    private void clear() {
        setVisible(false);
    }
}
