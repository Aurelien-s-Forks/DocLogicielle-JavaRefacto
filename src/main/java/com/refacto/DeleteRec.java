import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DeleteRec extends Dialog
        implements ActionListener {
    private final HardwareStore hardwareStore;
    private RandomAccessFile file;
    private JTextField recID;
    private JButton cancel, delete;
    private Record data;
    private int theRecID = -1;
    private final String[][] pData;
    private final HardwareStore hwStore;

    public DeleteRec(HardwareStore hardwareStore, HardwareStore hw_store, RandomAccessFile f,
                     String[][] p_Data) {

        super(new Frame(), "Delete Record", true);
        this.hardwareStore = hardwareStore;
        setSize(400, 150);
        setLayout(new GridLayout(2, 2));
        file = f;
        pData = p_Data;
        hwStore = hw_store;
        delSetup();
    }

    /**
     * Sets action listener.
     *
     * @param recIDLabel the rec id label
     */
    public void setActionListener(JLabel recIDLabel) {
        cancel = new JButton("Cancel");

        cancel.addActionListener(this);
        delete.addActionListener(this);
        recID.addActionListener(this);

        add(recIDLabel);
        add(recID);
        add(delete);
        add(cancel);
    }

    public void delSetup() {
        JLabel recIDLabel = new JLabel("Record ID");
        recID = new JTextField(10);
        delete = new JButton("Delete Record");
        setActionListener(recIDLabel);

        data = new Record();
    }


    public void actionPerformed(ActionEvent e) {
        System.out.println("DeleteRec(): 1a - In the actionPerformed() method. ");
        if (e.getSource() == recID) {
            theRecID = Integer.parseInt(recID.getText());

            if (theRecID < 0 || theRecID > 250) {
                recID.setText("Invalid part number");
            } else {

                try {
                    file = new RandomAccessFile(hwStore.aFile, "rw");

                    file.seek(theRecID * Record.getSize());
                    data.ReadRec(file);
                    System.out.println("DeleteRec(): 1b - The record read is recid " +
                            data.getRecID() + " " +
                            data.getToolType() + " " +
                            data.getBrandName() + " " +
                            data.getToolDesc() + " " +
                            data.getQuantity() + " " +
                            data.getCost());
                } catch (IOException ex) {
                    recID.setText("Error reading file");
                }
            }
        } else if (e.getSource() == delete) {
            theRecID = Integer.parseInt(recID.getText());

            for (String[] pDatum : pData) {
                if ((pDatum[0]).equals("" + theRecID)) {
                    theRecID = Integer.parseInt(pDatum[0]);
                    System.out.println("DeleteRec(): 2 - The record id was found is  "
                            + pDatum[0]);
                    break;
                }
            }

            try {

                System.out.println("DeleteRec(): 3 - The data file is " + hwStore.aFile +
                        "The record to be deleted is " + theRecID);
                file = new RandomAccessFile(hwStore.aFile, "rw");
                data.setRecID(theRecID);

                hwStore.setEntries(hwStore.getEntries() - 1);
                System.out.println("DeleteRec(): 4 - Go to the beginning of the file.");
                file.seek((0));
                file.seek((theRecID) * Record.getSize());
                data.ReadRec(file);
                System.out.println("DeleteRec(): 5 - Go to the record " + theRecID + " to be deleted.");
                data.setRecID(-1);
                System.out.println("DeleteRec(): 6 - Write the deleted file to the file.");
                file.seek((0));
                file.seek((theRecID) * Record.getSize());
                data.writeInteger(file, -1);

                System.out.println("DeleteRec(): 7 - The number of entries is " + hwStore.getEntries());

                file.close();
            } catch (IOException ex) {
                recID.setText("Error writing file");
                return;
            }


            int toCont = JOptionPane.showConfirmDialog(null,
                    "Do you want to delete another record? \nChoose one",
                    "Select Yes or No",
                    JOptionPane.YES_NO_OPTION);

            if (toCont == JOptionPane.YES_OPTION) {
                recID.setText("");
            } else {
                delClear();
            }
        } else if (e.getSource() == cancel) {
            delClear();
        }
    }

    private void delClear() {
        try {
            System.out.println("DeleteRec(): 3 - The data file is " + hwStore.aFile +
                    "The record to be deleted is " + theRecID);
            file = new RandomAccessFile(hwStore.aFile, "rw");

            hardwareStore.Redisplay(file, pData);
            file.close();
        } catch (IOException ex) {
            recID.setText("Error writing file");
            return;
        }
        setVisible(false);
        recID.setText("");
    }
}
