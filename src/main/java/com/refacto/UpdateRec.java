import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import main.java.com.helpers.*;

public class UpdateRec extends Dialog implements ActionListener {
    private final HardwareStore hardwareStore;
    private RandomAccessFile file;
    private JTextField recID, toolType, brandName, toolDesc,
            partNum, quantity, price;
    private JButton cancel, save;
    private Record data;
    private int theRecID;
    private final String[][] pData;
    private final HardwareStore hwstore;

    public UpdateRec(HardwareStore hardwareStore, HardwareStore hw_store, RandomAccessFile f,
                     String[][] p_Data) {
        super(new Frame(), "Update Record", true);
        this.hardwareStore = hardwareStore;
        setSize(400, 280);
        setLayout(new GridLayout(9, 2));
        file = f;
        pData = p_Data;
        hwstore = hw_store;

        upDSetup();
    }

    private JLabel addLabel(String label) {
        JLabel newLabel = new JLabel(label);
        add(newLabel);
        return newLabel;
    }

    void setFields() {
        price = new JTextField(10);

        new RecHelpers().addAllLabels();

        save = new JButton();
        cancel = new JButton();
        RecHelpers.createButtonsActionListener(save, cancel, this);

        recID.addActionListener(this);

        ArrayList<JTextField> allTextField = new ArrayList<JTextField>(Arrays.asList(recID, toolType, brandName, toolDesc, partNum, quantity, price));

        RecHelpers.addAllTextField(allTextField);

        data = new Record();
    }

    public void upDSetup() {
        recID = new JTextField(10);
        toolType = new JTextField(10);
        brandName = new JTextField(10);
        toolDesc = new JTextField(10);
        partNum = new JTextField(10);
        quantity = new JTextField(10);
        setFields();
    }

    public boolean checkDigit(String strVal) {
        int strLength = strVal.length();
        int i = 0;
        boolean notDig = true;

        while (i < strLength && (Character.isDigit((strVal.charAt(i))))) {
            if (!Character.isDigit(strVal.charAt(i))) notDig = false;
            i++;
        }
        return notDig;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == recID) {
            if (checkDigit(recID.getText())) {
                theRecID = Integer.parseInt(recID.getText());
            } else if (theRecID < 0 || theRecID > 250) {
                JOptionPane.showMessageDialog(null,
                        "A recID entered was:  less than 0 or greater than 250, which is invalid.\n" +
                                "Please enter a number greater than 0 and less than 251.", "RecID Entered",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            theRecID = Integer.parseInt(recID.getText());
            System.out.println("UpdateRec(): 2a - The record id being sought is " + theRecID);

            for (String[] pDatum : pData) {
                if (pDatum[0] != null) {
                    if (Integer.parseInt(pDatum[0]) == theRecID) {
                        theRecID = Integer.parseInt(pDatum[0]);
                        System.out.println("UpdateRec(): 2b - The record id was found.");
                        break;
                    }
                }
            }

            try {
                file = new RandomAccessFile(hwstore.aFile, "rw");
                file.seek((theRecID) * Record.getSize());
                data.ReadRec(file);

                recID.setText("" + theRecID);
                toolType.setText(data.getToolType().trim());
                brandName.setText(data.getBrandName().trim());
                toolDesc.setText(data.getToolDesc().trim());
                partNum.setText(data.getPartNumber().trim());
                quantity.setText(Integer.toString(data.getQuantity()));
                price.setText(data.getCost().trim());
                System.out.println("UpdateRec(): 2c - The record found was " +
                        data.getRecID() + " " +
                        data.getBrandName() + " " +
                        data.getToolDesc() + " " +
                        data.getQuantity() + " " +
                        data.getCost() + " in file " + hwstore.aFile);
            } catch (IOException ex) {
                recID.setText("UpdateRec(): 2d -  Error reading file");
            }


            recID.setText("This record " +
                    theRecID + " does not exist");
        } else if (e.getSource() == save) {
            try {
                data.setRecID(Integer.parseInt(recID.getText()));
                data.setToolType(toolType.getText().trim());
                data.setBrandName(brandName.getText().trim());
                data.setToolDesc(toolDesc.getText().trim());
                data.setPartNumber(partNum.getText().trim());
                data.setQuantity(Integer.parseInt(quantity.getText().trim()));
                data.setCost(price.getText().trim());

                file.seek(0);
                file.seek(theRecID * Record.getSize());
                data.write(file);

                System.out.println("UpdateRec(): 3 - The record found was " +
                        data.getRecID() + " " +
                        data.getBrandName() + " " +
                        data.getToolDesc() + " " +
                        data.getQuantity() + " " +
                        data.getCost() + " in file " + hwstore.aFile);

                hardwareStore.Redisplay(file, pData);
            } catch (IOException ex) {
                recID.setText("Error writing file");
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
                upClear();
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
            upClear();
        }
    }

    private void upClear() {
        recID.setText("");
        brandName.setText("");
        quantity.setText("");
        price.setText("");
        setVisible(false);
    }
}
