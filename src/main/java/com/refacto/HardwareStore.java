import main.java.com.helpers.SystemHelpers;
import main.java.com.mocks.HardwareStoreMocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.stream.IntStream;

public class HardwareStore extends JFrame implements ActionListener {
    private final String[][] pData = new String[250][7];
    private final MenuHandler menuHandler = new MenuHandler(this);
    private final String[] columnNames = {"Record ID", "Type of tool",
            "Brand Name", "Tool Description", "partNum",
            "Quantity", "Price"};
    private final Container c;
    public File aFile;
    HardwareStore hws;
    private PassWord pWord;
    private UpdateRec update;
    private DeleteRec deleteRec;
    private Record data;
    private JMenuItem eMI;
    private JMenuItem lmMI, lmtMI, hdMI, dpMI, hamMI, csMI, tabMI, bandMI,
            sandMI, stapMI, wdvMI, sccMI;
    // Options Menu Items
    private JMenuItem deleteMI, addMI, updateMI, listAllMI;
    // Tools Menu Items
    private JMenuItem debugON, debugOFF;
    // Help Menu Items
    private JMenuItem helpHWMI;
    // About Menu Items
    private JMenuItem aboutHWMI;
    private JTable table;
    private RandomAccessFile file;
    private JButton cancel, refresh;
    private boolean myDebug = false;
    private int numEntries = 0, ZERO;

    public HardwareStore() {
        super("Hardware Store: Lawn Mower ");

        data = new Record();
        aFile = new File("lawnmower.dat");
        c = getContentPane();
        setupMenu();

        /** Values initializer **/
        String[][] lawnMower = new HardwareStoreMocks().getLawnMower();
        InitRecord("lawnmower.dat", lawnMower, 27);

        String[][] lawnTractor = new HardwareStoreMocks().getLawnTractor();
        InitRecord("lawnTractor.dat", lawnTractor, 11);

        String[][] handDrill = new HardwareStoreMocks().getHandDrill();
        InitRecord("handDrill.dat", handDrill, 15);

        String[][] drillPress = new HardwareStoreMocks().getDrillPress();
        InitRecord("drillPress.dat", drillPress, 10);

        String[][] circularSaw = new HardwareStoreMocks().getCircularSaw();
        InitRecord("circularSaw.dat", circularSaw, 12);

        String[][] hammer = new HardwareStoreMocks().getHammer();
        InitRecord("hammer.dat", hammer, 12);

        String[][] tableSaw = new HardwareStoreMocks().getTableSaw();
        InitRecord("tableSaw.dat", tableSaw, 15);

        String[][] bandSaw = new HardwareStoreMocks().getBandSaw();
        InitRecord("bandSaw.dat", bandSaw, 10);

        String[][] sanders = new HardwareStoreMocks().getSanders();
        InitRecord("sanders.dat", sanders, 15);

        String[][] stapler = new HardwareStoreMocks().getStapler();
        InitRecord("stapler.dat", stapler, 15);

        setup();

        addWindowListener(new WindowHandler(this));
        setSize(700, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HardwareStore().hws = new HardwareStore();
    }

    public JMenuItem setupItem(JMenu viewmenu, String label) {
        JMenuItem item = new JMenuItem(label);
        viewmenu.add(item);
        item.addActionListener(menuHandler);
        return item;
    }

    public void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");

        menuBar.add(fileMenu);
        eMI = setupItem(fileMenu, "Exit");

        JMenu viewMenu = new JMenu("View");
        lmMI = setupItem(viewMenu, "Lawn Mowers");
        lmtMI = setupItem(viewMenu, "Lawn Mowing Tractors");
        hdMI = setupItem(viewMenu, "Hand Drills Tools");
        dpMI = setupItem(viewMenu, "Drill Press Power Tools");
        csMI = setupItem(viewMenu, "Circular Saws");
        hamMI = setupItem(viewMenu, "Hammers");
        tabMI = setupItem(viewMenu, "Table Saws");
        bandMI = setupItem(viewMenu, "Band Saws");
        sandMI = setupItem(viewMenu, "Sanders");
        stapMI = setupItem(viewMenu, "Staplers");
        wdvMI = setupItem(viewMenu, "Wet-Dry Vacs");
        sccMI = setupItem(viewMenu, "Storage, Chests & Cabinets");

        menuBar.add(viewMenu);
        JMenu optionsMenu = new JMenu("Options");

        listAllMI = setupItem(optionsMenu, "List All");
        optionsMenu.addSeparator();

        addMI = setupItem(optionsMenu, "Add");
        updateMI = setupItem(optionsMenu, "Update");
        optionsMenu.addSeparator();

        deleteMI = setupItem(optionsMenu, "Delete");
        menuBar.add(optionsMenu);

        JMenu toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);
        debugON = setupItem(toolsMenu, "Debug On");
        debugOFF = setupItem(toolsMenu, "Debug Off");

        JMenu helpMenu = new JMenu("Help");
        helpHWMI = setupItem(helpMenu, "Help on HW Store");
        menuBar.add(helpMenu);

        JMenu aboutMenu = new JMenu("About");
        aboutHWMI = setupItem(aboutMenu, "About HW Store");
        menuBar.add(aboutMenu);
    }

    public void setup() {
        data = new Record();

        try {
            file = new RandomAccessFile("lawnmower.dat", "rw");
            aFile = new File("lawnmower.dat");
            numEntries = toArray(file, pData);

            file.close();
        } catch (IOException e) {
            System.err.println("InitRecord() " + e.toString() + " " + aFile);
            System.exit(1);
        }

        /* pData contains the data to be loaded into the JTable.
         * columnNames contains the column names for the table. */
        table = new JTable(pData, columnNames);
        table.addMouseListener(new MouseClickedHandler(this, file, table, pData));
        table.setEnabled(true);

        c.add(table, BorderLayout.CENTER);
        c.add(new JScrollPane(table));
        cancel = new JButton("Cancel");
        refresh = new JButton("Refresh");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancel);
        c.add(buttonPanel, BorderLayout.SOUTH);

        refresh.addActionListener(this);
        cancel.addActionListener(this);

        update = new UpdateRec(this, hws, file, pData);
        deleteRec = new DeleteRec(this, file, pData);
        pWord = new PassWord(this);
    }

    public void InitRecord(String fileDat, String[][] FileRecord, int loopCtl) {
        aFile = new File(fileDat);
        sysPrint("initRecord(): 1a - the value of fileData is " + aFile);

        try {
            sysPrint("initTire(): 1ab - checking to see if " + aFile + " exist.");
            if (!aFile.exists()) {

                sysPrint("initTire(): 1b - " + aFile + " does not exist.");

                file = new RandomAccessFile(aFile, "rw");
                data = new Record();

                for (int i = 0; i < loopCtl; i++) {
                    data.setRecID(Integer.parseInt(FileRecord[i][0]));
                    sysPrint("initTire(): 1c - The value of record ID is " + data.getRecID());
                    data.setToolType(FileRecord[i][1]);
                    sysPrint("initTire(): 1cb - The length of ToolType is " + data.getToolType().length());
                    data.setBrandName(FileRecord[i][2]);
                    data.setToolDesc(FileRecord[i][3]);
                    sysPrint("initTire(): 1cc - The length of ToolDesc is " + data.getToolDesc().length());
                    data.setPartNumber(FileRecord[i][4]);
                    data.setQuantity(Integer.parseInt(FileRecord[i][5]));
                    data.setCost(FileRecord[i][6]);

                    sysPrint("initTire(): 1d - Calling Record method write() during initialization. " + i);
                    file.seek(i * Record.getSize());
                    data.write(file);
                }
            } else {
                sysPrint("initTire(): 1e - " + fileDat + " exists.");
                file = new RandomAccessFile(aFile, "rw");
            }

            file.close();
        } catch (IOException e) {
            System.err.println("InitRecord() " + e.toString() + " " + aFile);
            System.exit(1);
        }
    }

    public void display(String str) {
        String df = null, title = null;

        switch (str) {
            case "Lawn Mowers":
                df = "lawnmower.dat";
                aFile = new File("lawnmower.dat");
                title = "Hardware Store: Lawn Mowers";
                break;

            case "Lawn Tractor Mowers":
                df = "lawnTractor.dat";
                aFile = new File("lawnTractor.dat");
                title = "Hardware Store: Lawn Tractor Mowers";
                break;

            case "Hand Drill Tools":
                df = "handDrill.dat";
                aFile = new File("handDrill.dat");
                title = "Hardware Store:  Hand Drill Tools";
                break;

            case "Drill Press Power Tools":
                df = "drillPress.dat";
                aFile = new File("drillPress.dat");
                title = "Hardware Store: Drill Press Power Tools";
                break;

            case "Circular Saws":
                df = "circularSaw.dat";
                aFile = new File("circularSaw.dat");
                title = "Hardware Store: Circular Saws";
                break;

            case "Hammers":
                df = "hammer.dat";
                aFile = new File("hammer.dat");
                title = "Hardware Store: Hammers";
                break;
            case "Table Saws":
                df = "tableSaw.dat";
                aFile = new File("tableSaw.dat");
                title = "Hardware Store: Table Saws";
                break;

            case "Band Saws":
                df = "bandSaw.dat";
                aFile = new File("bandSaw.dat");
                title = "Hardware Store: Band Saws";
                break;

            case "Sanders":
                df = "sanders.dat";
                aFile = new File("sanders.dat");
                title = "Hardware Store: Sanders";
                break;

            case "Staplers":
                df = "stapler.dat";
                aFile = new File("stapler.dat");
                title = "Hardware Store: Staplers";
                break;

            default:
                break;
        }

        try {
            sysPrint("display(): 1a - checking to see if " + df + " exists.");
            if (!aFile.exists()) {
                sysPrint("display(): 1b - " + df + " does not exist.");
            } else {
                file = new RandomAccessFile(df, "rw");
                this.setTitle(title);
                Redisplay(file, pData);
            }

            file.close();
        } catch (IOException e) {
            System.err.println(e.toString());
            System.err.println("Failed in opening " + df);
            System.exit(1);
        }

    }

    public void Redisplay(RandomAccessFile file, String[][] a) {
        IntStream.range(0, numEntries + 5).forEach(ii -> {
            a[ii][0] = "";
            a[ii][1] = "";
            a[ii][2] = "";
            a[ii][3] = "";
            a[ii][4] = "";
            a[ii][5] = "";
            a[ii][6] = "";
        });
        int entries = toArray(file, a);
        sysPrint("Redisplay(): 1  - The number of entries is " + entries);
        setEntries(entries);
        c.remove(table);
        table = new JTable(a, columnNames);
        table.setEnabled(true);
        c.add(table, BorderLayout.CENTER);
        c.add(new JScrollPane(table));
        c.validate();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refresh) {
            sysPrint("\nThe Refresh button was pressed. ");
            Container cc = getContentPane();

            table = new JTable(pData, columnNames);
            cc.validate();
        } else if (e.getSource() == cancel)
            cleanup();
    }

    public void cleanup() {
        try {
            file.close();
        } catch (IOException e) {
            System.exit(1);
        }

        setVisible(false);
        System.exit(0);
    }

    public void displayDeleteDialog() {
        sysPrint("The Delete Record Dialog was made visible.\n");
        deleteRec.setVisible(true);
    }

    public void displayUpdateDialog() {
        sysPrint("The Update Record Dialog was made visible.\n");
        JOptionPane.showMessageDialog(null,
                "Enter the record ID to be updated and press enter.",
                "Update Record", JOptionPane.INFORMATION_MESSAGE);
        update = new UpdateRec(this, hws, file, pData);
        update.setVisible(true);
    }

    public void displayAddDialog() {
        sysPrint("The New/Add Record Dialog was made visible.\n");
        NewRec newRec = new NewRec(hws, file, table, pData);
        newRec.setVisible(true);
    }

    public void sysPrint(String str) {
        SystemHelpers.sysPrint(myDebug, str);
    }

    /**
     * Returns an array containing all of the elements in this list in the correct order.
     */
    public int toArray(RandomAccessFile file, String[][] a) {
        Record NodeRef = new Record();
        int ii = 0, iii = 0, fileSize = 0;

        try {
            fileSize = (int) file.length() / Record.getSize();
            sysPrint("toArray(): 1 - The size of the file is " + fileSize);
            /* If the file is empty, do nothing.  */
            if (fileSize > ZERO) {
                NodeRef.setFileLen(file.length());

                while (ii < fileSize) {
                    sysPrint("toArray(): 2 - NodeRef.getRecID is "
                            + NodeRef.getRecID());

                    file.seek(0);
                    file.seek(ii * Record.getSize());
                    NodeRef.setFilePos(ii * Record.getSize());
                    sysPrint("toArray(): 3 - input data file - Read record " + ii);
                    NodeRef.ReadRec(file);

                    sysPrint("toArray(): 4 - the value of a[ ii ] [ 0 ] is " +
                            a[0][0]);

                    if (NodeRef.getRecID() != -1) {
                        a[iii][0] = String.valueOf(NodeRef.getRecID());
                        a[iii][1] = NodeRef.getToolType().trim();
                        a[iii][2] = NodeRef.getBrandName().trim();
                        a[iii][3] = NodeRef.getToolDesc().trim();
                        a[iii][4] = NodeRef.getPartNumber().trim();
                        a[iii][5] = String.valueOf(NodeRef.getQuantity());
                        a[iii][6] = NodeRef.getCost().trim();

                        sysPrint("toArray(): 5 - 0- " + a[iii][0] +
                                " 1- " + a[iii][1] +
                                " 2- " + a[iii][2] +
                                " 3- " + a[iii][3] +
                                " 4- " + a[iii][4] +
                                " 5- " + a[iii][5] +
                                " 6- " + a[iii][6]);

                        iii++;

                    } else {
                        sysPrint("toArray(): 5a the record ID is " + ii);
                    }

                    ii++;

                }
            }
        } catch (IOException ex) {
            sysPrint("toArray(): 6 - input data file failure. Index is " + ii
                    + "\nFilesize is " + fileSize);
        }
        return ii;
    }

    /* Getters and setters */
    public void setUpdate(HardwareStore hardwareStore, HardwareStore hws, RandomAccessFile file, String[][] strings) {
        new UpdateRec(hardwareStore, hws, file, strings);
    }

    public UpdateRec getUpdate() {
        return update;
    }

    public void setDeleteRec(HardwareStore hardwareStore, HardwareStore hws, RandomAccessFile file, String[][] strings) {
        new DeleteRec(hardwareStore, file, strings);
    }

    public DeleteRec getDeleteRec() {
        return deleteRec;
    }

    public void setMyDebug(boolean value) {
        myDebug = value;
    }

    public int getEntries() {
        return numEntries;
    }

    public void setEntries(int ent) {
        numEntries = ent;
    }

    public JMenuItem geteMI() {
        return eMI;
    }

    public PassWord getpWord() {
        return pWord;
    }

    public JMenuItem getLmMI() {
        return lmMI;
    }

    public JMenuItem getHdMI() {
        return hdMI;
    }

    public JMenuItem getLmtMI() {
        return lmtMI;
    }

    public JMenuItem getDpMI() {
        return dpMI;
    }

    public JMenuItem getHamMI() {
        return hamMI;
    }

    public JMenuItem getCsMI() {
        return csMI;
    }

    public JMenuItem getTabMI() {
        return tabMI;
    }

    public JMenuItem getBandMI() {
        return bandMI;
    }

    public JMenuItem getSandMI() {
        return sandMI;
    }

    public JMenuItem getStapMI() {
        return stapMI;
    }

    public JMenuItem getWdvMI() {
        return wdvMI;
    }

    public JMenuItem getSccMI() {
        return sccMI;
    }

    public JMenuItem getDeleteMI() {
        return deleteMI;
    }

    public JMenuItem getAddMI() {
        return addMI;
    }

    public JMenuItem getUpdateMI() {
        return updateMI;
    }

    public JMenuItem getListAllMI() {
        return listAllMI;
    }

    public JMenuItem getDebugON() {
        return debugON;
    }

    public JMenuItem getDebugOFF() {
        return debugOFF;
    }

    public JMenuItem getHelpHWMI() {
        return helpHWMI;
    }

    public JMenuItem getAboutHWMI() {
        return aboutHWMI;
    }

    public String[][] getpData() {
        return pData;
    }

    public RandomAccessFile getFile() {
        return file;
    }
}
