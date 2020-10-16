import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.RandomAccessFile;

public class MouseClickedHandler extends MouseAdapter {

    private final HardwareStore hardwareStore;
    JTable table;
    String[][] pData;
    RandomAccessFile f;

    MouseClickedHandler(HardwareStore hardwareStore, RandomAccessFile fPassed, JTable tablePassed,
                        String[][] p_Data) {
        this.hardwareStore = hardwareStore;
        table = tablePassed;
        pData = p_Data;
        f = fPassed;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == table) {
            int RowSelected = table.getSelectedRow();
            JOptionPane.showMessageDialog(null,
                    "Enter the record ID to be updated and press enter.",
                    "Update Record", JOptionPane.INFORMATION_MESSAGE);
            UpdateRec update = new UpdateRec(hardwareStore, hardwareStore.hws, f, pData);
            if (RowSelected < 250) {
                update.setVisible(true);
                table.repaint();
            }
        }
    }
}
