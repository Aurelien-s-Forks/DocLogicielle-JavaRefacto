package main.java.com.helpers;

import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
<<<<<<< HEAD

public class RecHelpers extends Container {

=======
=======
>>>>>>> 77e8a1a7fc95ca8e02d9505dd0063382d079ac11
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class RecHelpers extends Container {

    private final ArrayList<String> allLabels = new ArrayList<>(Arrays.asList("Record ID", "Type of Tool", "Brand Name", "Tool Description", "Part Number", "Quantity", "Price"));

<<<<<<< HEAD
>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160
=======
>>>>>>> 77e8a1a7fc95ca8e02d9505dd0063382d079ac11
    public static void addLabel(String label) {
        JLabel newLabel = new JLabel(label);
        new Container().add(newLabel);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 77e8a1a7fc95ca8e02d9505dd0063382d079ac11
    public void addAllLabels() {
        for (String allLabel : allLabels) RecHelpers.addLabel(allLabel);
    }

    public static void createButtonsActionListener(JButton save, JButton cancel, ActionListener newRec) {
        save.setName("Save Changes");
        cancel.setName("Cancel");
        save.addActionListener(newRec);
        cancel.addActionListener(newRec);
    }

    public static void addAllTextField(ArrayList<JTextField> jTextFieldsList) {
        for (JTextField jTextField : jTextFieldsList) new Container().add(jTextField);
    }

<<<<<<< HEAD
>>>>>>> 565eee926a5ea6335708958569ef1cee68e0e160
=======
    public ArrayList<String> getAllLabels() {
        return allLabels;
    }
>>>>>>> 77e8a1a7fc95ca8e02d9505dd0063382d079ac11
}
