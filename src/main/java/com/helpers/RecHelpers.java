package main.java.com.helpers;

import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD

public class RecHelpers extends Container {

=======
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class RecHelpers extends Container {

    private ArrayList<String> allLabels = new ArrayList<String>(Arrays.asList("Record ID", "Type of Tool", "Brand Name", "Tool Description", "Part Number", "Quantity", "Price"));

>>>>>>> ce81414ee849970da0b376dee969190d916314da
    public static void addLabel(String label) {
        JLabel newLabel = new JLabel(label);
        new Container().add(newLabel);
    }

<<<<<<< HEAD
=======
    public void addAllLabels() {
        for(int i = 0; i < allLabels.size(); i++) {
            RecHelpers.addLabel((allLabels.get(i)));
        }
    }

    public static void createButtonsActionListener(JButton save, JButton cancel, ActionListener newRec) {
        save.setName("Save Changes");
        cancel.setName("Cancel");
        save.addActionListener(newRec);
        cancel.addActionListener(newRec);
    }

    public static void addAllTextField(ArrayList<JTextField> jTextFieldsList) {
        for(int i = 0; i < jTextFieldsList.size(); i++) {
            new Container().add((jTextFieldsList.get(i)));
        }
    }

>>>>>>> ce81414ee849970da0b376dee969190d916314da
}
