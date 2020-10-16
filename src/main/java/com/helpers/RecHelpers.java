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

>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160
    public static void addLabel(String label) {
        JLabel newLabel = new JLabel(label);
        new Container().add(newLabel);
    }

<<<<<<< HEAD
=======
    public void addAllLabels() {
        for (String allLabel : allLabels) {
            RecHelpers.addLabel(allLabel);
        }
    }

    public static void createButtonsActionListener(JButton save, JButton cancel, ActionListener newRec) {
        save.setName("Save Changes");
        cancel.setName("Cancel");
        save.addActionListener(newRec);
        cancel.addActionListener(newRec);
    }

    public static void addAllTextField(ArrayList<JTextField> jTextFieldsList) {
        for (JTextField jTextField : jTextFieldsList) {
            new Container().add(jTextField);
        }
    }

>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160
}
