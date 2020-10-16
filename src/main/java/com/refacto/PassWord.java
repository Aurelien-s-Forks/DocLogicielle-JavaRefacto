import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassWord extends Dialog implements ActionListener {

    private final JButton buttonEnter;
    private final JButton buttonCancel;
    private final JTextField userID;
    private final JPasswordField password;
    private final HardwareStore hwStore;
    private String whichDialog;

    private JTextField addTextField(JPanel panel, int col) {
        JTextField textField = new JTextField(col);
        panel.add(textField);
        return textField;
    }

    private JPasswordField addPasswordField(JPanel panel, int col) {
        JPasswordField passwordField = new JPasswordField(col);
        panel.add(passwordField);
        return passwordField;
    }

    private void addLabel(JPanel panel, String l) {
        JLabel label = new JLabel(l);
        panel.add(label);
    }

    private JButton addButton(JPanel panel, String label) {
        JButton button = new JButton(label);
        panel.add(button);
        return button;
    }

    public PassWord(HardwareStore hw_Store) {
        super(new Frame(), "Password Check", true);
        hwStore = hw_Store;

        JPanel buttonPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(3, 2));
        userID = addTextField(mainPanel, 10);
        password = addPasswordField(mainPanel, 10);

        add(mainPanel, BorderLayout.CENTER);

        addLabel(mainPanel, "Enter your user ID");
        addLabel(mainPanel, "Enter your user password");

        buttonEnter = addButton(buttonPanel, "Enter");
        buttonCancel = addButton(buttonPanel, "Cancel");
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
                    case "delete" -> {
                        hwStore.displayDeleteDialog();
                        closeDialog();
                    }
                    case "update" -> {
                        hwStore.displayUpdateDialog();
                        closeDialog();
                    }
                    case "add" -> {
                        hwStore.displayAddDialog();
                        closeDialog();
                    }
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
