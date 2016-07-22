package ai;

import ai.event.Controller;
import ai.map.ReadSettings;
import ai.ui.Main;
import ai.ui.Settings;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 *
 * @author ÁdámRichárd
 */
public class AI {

    public static void main(String[] args) {
        try {
            //cross-platform Java L&F
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception ex) {}
        Main mainframe = new Main();
        Settings settings = new Settings();
        Controller controll = new Controller(mainframe, settings);
        Toolkit tk = Toolkit.getDefaultToolkit();
        mainframe.setLocation(tk.getScreenSize().width / 2 - mainframe.getSize().width / 2, tk.getScreenSize().height / 2 - mainframe.getSize().height / 2);
        mainframe.setVisible(true);
        try {
            ReadSettings.checkXML();
            Controller.setAllTextField();
        } catch (Exception evt) {
            Controller.setAllTextField();
        }
    }
}
