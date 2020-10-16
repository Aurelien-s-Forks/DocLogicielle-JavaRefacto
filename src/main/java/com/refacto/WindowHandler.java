import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowHandler extends WindowAdapter {

    HardwareStore h;

    /**
     * Instantiates a new Window handler.
     *
     * @param s the s
     */
    public WindowHandler(HardwareStore s) {
        h = s;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        h.cleanup();
    }
}
