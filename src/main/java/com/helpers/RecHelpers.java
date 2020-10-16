package main.java.com.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class RecHelpers extends Container {

    private final ArrayList<String> allLabels = new ArrayList<>(Arrays.asList("Record ID", "Type of Tool", "Brand Name", "Tool Description", "Part Number", "Quantity", "Price"));

    public static void addLabel(String label) {
        JLabel newLabel = new JLabel(label);
        new Container().add(newLabel);
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

    public void addAllLabels() {
        for (String allLabel : allLabels) RecHelpers.addLabel(allLabel);
    }

    public ArrayList<String> getAllLabels() {
        return allLabels;
    }
}
