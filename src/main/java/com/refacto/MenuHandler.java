import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuHandler implements ActionListener {
   private final HardwareStore hardwareStore;

   public MenuHandler(HardwareStore hardwareStore) {
      this.hardwareStore = hardwareStore;
   }

   public void actionPerformed(ActionEvent e) {

      if (e.getSource() == hardwareStore.geteMI()) {
         hardwareStore.cleanup();
      } else if (e.getSource() == hardwareStore.getLmMI()) {
         hardwareStore.sysPrint("The Lawn Mower menu Item was selected.\n");
         hardwareStore.display("Lawn Mowers");
      } else if (e.getSource() == hardwareStore.getLmtMI()) {
         hardwareStore.sysPrint("The Lawn Mower Tractor menu Item was selected.\n");
         hardwareStore.display("Lawn Tractor Mowers");
      } else if (e.getSource() == hardwareStore.getHdMI()) {
         hardwareStore.sysPrint("The Hand Drill Tools menu Item was selected.\n");
         hardwareStore.display("Hand Drill Tools");
      } else if (e.getSource() == hardwareStore.getDpMI()) {
         hardwareStore.sysPrint("The Drill Press Power Tools menu Item was selected.\n");
         hardwareStore.display("Drill Press Power Tools");
      } else if (e.getSource() == hardwareStore.getCsMI()) {
         hardwareStore.sysPrint("The Circular Saws Tools menu Item was selected.\n");
         hardwareStore.display("Circular Saws");
      } else if (e.getSource() == hardwareStore.getHamMI()) {
         hardwareStore.sysPrint("The Hammer menu Item was selected.\n");
         hardwareStore.display("Hammers");
      } else if (e.getSource() == hardwareStore.getTabMI()) {
         hardwareStore.sysPrint("The Table Saws menu Item was selected.\n");
         hardwareStore.display("Table Saws");
      } else if (e.getSource() == hardwareStore.getBandMI()) {
         hardwareStore.sysPrint("The Band Saws menu Item was selected.\n");
         hardwareStore.display("Band Saws");
      } else if (e.getSource() == hardwareStore.getSandMI()) {
         hardwareStore.sysPrint("The Sanders menu Item was selected.\n");
         hardwareStore.display("Sanders");
      } else if (e.getSource() == hardwareStore.getStapMI()) {
         hardwareStore.sysPrint("The Staplers menu Item was selected.\n");
         hardwareStore.display("Staplers");
      } else if (e.getSource() == hardwareStore.getWdvMI()) {
         hardwareStore.sysPrint("The Wet-Dry Vacs menu Item was selected.\n");
      } else if (e.getSource() == hardwareStore.getSccMI()) {
         hardwareStore.sysPrint("The Storage, Chests & Cabinets menu Item was selected.\n");
      } else if (e.getSource() == hardwareStore.getDeleteMI()) {
         hardwareStore.sysPrint("The Delete Record Dialog was made visible.\n");
         hardwareStore.setDeleteRec(hardwareStore, hardwareStore.hws, hardwareStore.getFile(), hardwareStore.getpData());
         hardwareStore.getDeleteRec().setVisible(true);
      } else if (e.getSource() == hardwareStore.getAddMI()) {
         hardwareStore.sysPrint("The Add menu Item was selected.\n");
         hardwareStore.getpWord().displayDialog("add");
      } else if (e.getSource() == hardwareStore.getUpdateMI()) {
         hardwareStore.sysPrint("The Update menu Item was selected.\n");
         hardwareStore.setUpdate(hardwareStore, hardwareStore.hws, hardwareStore.getFile(), hardwareStore.getpData());
         hardwareStore.getUpdate().setVisible(true);
      } else if (e.getSource() == hardwareStore.getListAllMI()) {
         hardwareStore.sysPrint("The List All menu Item was selected.\n");
      } else if (e.getSource() == hardwareStore.getDebugON()) {
         hardwareStore.setMyDebug(true);
         hardwareStore.sysPrint("Debugging for this execution is turned on.\n");
      } else if (e.getSource() == hardwareStore.getDebugOFF()) {
         hardwareStore.sysPrint("Debugging for this execution is turned off.\n");
         hardwareStore.setMyDebug(false);
      } else if (e.getSource() == hardwareStore.getHelpHWMI()) {
         hardwareStore.sysPrint("The Help menu Item was selected.\n");
         File hd = new File("HW_Tutorial.html");

         Runtime rt = Runtime.getRuntime();
         String[] callAndArgs = {"c:\\Program Files\\Internet Explorer\\IEXPLORE.exe", "" + hd.getAbsolutePath()};

         try {
            Process child = rt.exec(callAndArgs);
            child.waitFor();
            hardwareStore.sysPrint("Process exit code is: " +
                    child.exitValue());
         } catch (IOException e2) {
            hardwareStore.sysPrint(
                    "IOException starting process!");
         } catch (InterruptedException e3) {
            System.err.println("Interrupted waiting for process!");
         }
      } else if (e.getSource() == hardwareStore.getAboutHWMI()) {
         hardwareStore.sysPrint("The About menu Item was selected.\n");
         Runtime rt = Runtime.getRuntime();
         String[] callAndArgs = {"c:\\Program Files\\Internet Explorer\\IEXPLORE.exe",
                 "http://www.sumtotalz.com/TotalAppsWorks/ProgrammingResource.html"};
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
}
