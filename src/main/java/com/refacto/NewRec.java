import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Arrays;

>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160
import main.java.com.helpers.*;

public class NewRec extends Dialog implements ActionListener {

    private RandomAccessFile file;
    private JTextField recID, toolType, brandName, toolDesc,
            partNum, quantity, price;
    private JButton cancel, save;
    private Record data;
    private JTable table;
    private final String[][] pData;
    private final String[] columnNames = {"Record ID", "Type of tool",
            "Brand Name", "Tool Description", "partNum",
            "Quantity", "Price"};
    private final HardwareStore hwStore;

    /**
     * Instantiates a new New rec.
     *
     * @param hw_store the hw store
     * @param f        the f
     * @param tab      the tab
     * @param p_Data   the p data
     */
    public NewRec(HardwareStore hw_store, RandomAccessFile
            f, JTable tab, String[][] p_Data) {

        super(new Frame(), "New Record", true);
        file = f;
        table = tab;
        pData = p_Data;
        hwStore = hw_store;

        newSetup();
    }

    void setFields() {
        price = new JTextField(10);

<<<<<<< HEAD
        RecHelpers.addLabel("Record ID");
        RecHelpers.addLabel("Type of Tool");
        RecHelpers.addLabel("Brand Name");
        RecHelpers.addLabel("Tool Description");
        RecHelpers.addLabel("Part Number");
        RecHelpers.addLabel("Quantity");
        RecHelpers.addLabel("Price");
=======
        new RecHelpers().addAllLabels();

        save = new JButton();
        cancel = new JButton();
        RecHelpers.createButtonsActionListener(save, cancel, this);
>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160

        recID.addActionListener(this);
<<<<<<< HEAD
        save.addActionListener(this);
        cancel.addActionListener(this);

        add(recID);
        add(toolType);
        add(brandName);
        add(toolDesc);
        add(partNum);
        add(quantity);
        add(price);
        add(save);
        add(cancel);
=======

        ArrayList<JTextField> allTextField = new ArrayList<JTextField>(Arrays.asList(recID, toolType, brandName, toolDesc, partNum, quantity, price));

        RecHelpers.addAllTextField(allTextField);
>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160

        data = new Record();
    }

    public void newSetup() {
        setSize(400, 250);
        setLayout(new GridLayout(9, 2));

        recID = new JTextField(10);
        recID.setEnabled(false);
        setRecordSize();
        toolType = new JTextField(10);
        brandName = new JTextField(10);
        toolDesc = new JTextField(10);
        partNum = new JTextField(10);
        quantity = new JTextField(10);
        setFields();
        JOptionPane.showMessageDialog(null,
                "The recID field is currently set to the next record ID.\n" +
                        "Please just fill in the " +
                        "remaining fields.",
                "RecID To Be Entered",
                JOptionPane.INFORMATION_MESSAGE);

    }

    public void actionPerformed(ActionEvent e) {
        setRecordSize();

        if (e.getSource() == recID) {
            recID.setEnabled(false);
        } else if (e.getSource() == save) {

            try {
                data.setRecID(Integer.parseInt(recID.getText()));
                data.setToolType(toolType.getText().trim());
                data.setBrandName(brandName.getText().trim());
                data.setToolDesc(toolDesc.getText().trim());
                data.setPartNumber(partNum.getText().trim());
                data.setQuantity(Integer.parseInt(quantity.getText()));
                data.setCost(price.getText().trim());
                file.seek(0);
                file.seek((data.getRecID()) * Record.getSize());
                data.write(file);

                // Account for index starting at 0 and for the next slot
                int theRecID = hwStore.getEntries();
                hwStore.sysPrint("NewRec 1: The numbers of entries is " + (theRecID - 1));

                hwStore.sysPrint("NewRec 2: A new record is being added at " +
                        theRecID);
                pData[theRecID][0] = Integer.toString(data.getRecID());
                pData[theRecID][1] = data.getToolType().trim();
                pData[theRecID][2] = data.getBrandName().trim();
                pData[theRecID][3] = data.getToolDesc().trim();
                pData[theRecID][4] = data.getPartNumber().trim();
                pData[theRecID][5] = Integer.toString(data.getQuantity());
                pData[theRecID][6] = data.getCost().trim();
                table = new JTable(pData, columnNames);
                table.repaint();
                hwStore.setEntries(hwStore.getEntries() + 1);
            } catch (IOException ex) {
                partNum.setText("Error writing file");
                return;
            }

            int toCont = JOptionPane.showConfirmDialog(null,
                    "Do you want to add another record? \nChoose one",
                    "Choose one",
                    JOptionPane.YES_NO_OPTION);

            if (toCont == JOptionPane.YES_OPTION) {
                recID.setText("");
                toolType.setText("");
                quantity.setText("");
                brandName.setText("");
                toolDesc.setText("");
                partNum.setText("");
                price.setText("");
            } else {
                newClear();
            }
        } else if (e.getSource() == cancel) {
            newClear();
        }
    }

    public void setRecordSize() {
        try {
            file = new RandomAccessFile(hwStore.aFile, "rw");
            file.seek(0);
            int fileLen = (int) file.length() / Record.getSize();
            recID.setText("" + fileLen);
        } catch (IOException ex) {
            partNum.setText("Error reading file");
        }
    }

    private void newClear() {
        partNum.setText("");
        toolType.setText("");
        quantity.setText("");
        price.setText("");
        setVisible(false);
    }
}
