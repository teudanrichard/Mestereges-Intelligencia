package ai;

import ai.event.Controller;
import ai.map.ReadSettings;
import ai.ui.CanvasPanel;
import ai.ui.Main;
import ai.ui.Settings;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ÁdámRichárd
 */
public class AI {

    public static void main(String[] args) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        Main mainframe = new Main();
        Settings settings = new Settings();
        Controller controll = new Controller(mainframe, settings);
        Toolkit tk = Toolkit.getDefaultToolkit();
        //monitor közepére pakoljuk a frame-t
        mainframe.setLocation(tk.getScreenSize().width / 2 - 500, tk.getScreenSize().height / 2 - 300);
        mainframe.setVisible(true);
        try {
            ReadSettings.checkXML();
            Controller.setAllTextField();
        } catch (Exception evt) {
            Controller.setAllTextField();
        }
    }
}
