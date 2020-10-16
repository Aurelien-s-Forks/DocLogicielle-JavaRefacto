import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuHandler implements ActionListener {
    private final HardwareStore hardwareStore;

    public MenuHandler(HardwareStore hardwareStore) {
        this.hardwareStore = hardwareStore;
    }

    private void eventItem(ActionEvent e, JMenuItem item, String print, String display) {
        if (e.getSource() == item) {
            hardwareStore.sysPrint(print);
            hardwareStore.display(display);
        }
    }

    private void eventDebug(ActionEvent e, JMenuItem item, String print, boolean status) {
        if (e.getSource() == item) {
            hardwareStore.sysPrint(print);
            hardwareStore.setMyDebug(status);
        }
    }

    private void eventOptions(ActionEvent e, JMenuItem item, String print, String status) {
        if (e.getSource() == item) {
            hardwareStore.sysPrint(print);
            switch (status) {
                case "delete" -> {
                    hardwareStore.setDeleteRec(hardwareStore, hardwareStore.hws, hardwareStore.getFile(), hardwareStore.getpData());
                    hardwareStore.getDeleteRec().setVisible(true);
                }
                case "update" -> {
                    hardwareStore.setUpdate(hardwareStore, hardwareStore.hws, hardwareStore.getFile(), hardwareStore.getpData());
                    hardwareStore.getUpdate().setVisible(true);
                }
                case "add" -> hardwareStore.getpWord().displayDialog("add");
            }
        }
    }

    private void eventHelpAbout(ActionEvent e, JMenuItem item, String print, String status) {
        if (e.getSource() == item) {
            hardwareStore.sysPrint(print);

            Runtime rt = Runtime.getRuntime();
            String[] callAndArgs;

            switch (status) {
                case "about" -> {
                    File hd = new File("HW_Tutorial.html");
                    callAndArgs = new String[]{"c:\\Program Files\\Internet Explorer\\IEXPLORE.exe", "" + hd.getAbsolutePath()};
                }
                case "help" -> callAndArgs = new String[]{"c:\\Program Files\\Internet Explorer\\IEXPLORE.exe",
                        "http://www.sumtotalz.com/TotalAppsWorks/ProgrammingResource.html"};
                default -> throw new IllegalStateException("Unexpected value: " + status);
            }

            try {
                Process child = rt.exec(callAndArgs);
                child.waitFor();
                hardwareStore.sysPrint("Process exit code is: " +
                        child.exitValue());
            } catch (IOException e2) {
                System.err.println(
                        "IOException starting process!");
            } catch (InterruptedException e3) {
                System.err.println(
                        "Interrupted waiting for process!");
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hardwareStore.geteMI()) {
            hardwareStore.cleanup();
        }
        eventItem(e, hardwareStore.getLmMI(), "The Lawn Mower menu Item was selected.\n", "Lawn Mowers");
        eventItem(e, hardwareStore.getLmtMI(), "The Lawn Mower Tractor menu Item was selected.\n", "Lawn Tractor Mowers");
        eventItem(e, hardwareStore.getHdMI(), "The Hand Drill Tools menu Item was selected.\n", "Hand Drill Tools");
        eventItem(e, hardwareStore.getDpMI(), "The Drill Press Power Tools menu Item was selected.\n", "Drill Press Power Tools");
        eventItem(e, hardwareStore.getCsMI(), "The Circular Saws Tools menu Item was selected.\n", "Circular Saws");
        eventItem(e, hardwareStore.getHamMI(), "The Hammer menu Item was selected.\n", "Hammers");
        eventItem(e, hardwareStore.getTabMI(), "The Table Saws menu Item was selected.\n", "Table Saws");
        eventItem(e, hardwareStore.getBandMI(), "The Band Saws menu Item was selected.\n", "Band Saws");
        eventItem(e, hardwareStore.getSandMI(), "The Sanders menu Item was selected.\n", "Sanders");
        eventItem(e, hardwareStore.getStapMI(), "The Staplers menu Item was selected.\n", "Staplers");
        eventItem(e, hardwareStore.getWdvMI(), "The Wet-Dry Vacs menu Item was selected.\n", "");
        eventItem(e, hardwareStore.getSccMI(), "The Storage, Chests & Cabinets menu Item was selected.\n", "");
        eventItem(e, hardwareStore.getListAllMI(), "The List All menu Item was selected.\n", "");
        eventDebug(e, hardwareStore.getDebugON(), "Debugging for this execution is turned on.\n", true);
        eventDebug(e, hardwareStore.getDebugOFF(), "Debugging for this execution is turned off.\n", false);
        eventOptions(e, hardwareStore.getDeleteMI(), "The Delete Record Dialog was made visible.\n", "delete");
        eventOptions(e, hardwareStore.getUpdateMI(), "The Update menu Item was selected.\n", "update");
        eventOptions(e, hardwareStore.getAddMI(), "The Add menu Item was selected.\n", "add");
        eventHelpAbout(e, hardwareStore.getHelpHWMI(), "The Help menu Item was selected.\n", "help");
        eventHelpAbout(e, hardwareStore.getAboutHWMI(), "The About menu Item was selected.\n", "about");
    }
}
