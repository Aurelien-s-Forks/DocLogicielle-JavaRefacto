package main.java.com.helpers;

import javax.swing.*;
import java.awt.*;

public class RecHelpers extends Container {

    public static void addLabel(String label) {
        JLabel newLabel = new JLabel(label);
        new Container().add(newLabel);
    }

}
