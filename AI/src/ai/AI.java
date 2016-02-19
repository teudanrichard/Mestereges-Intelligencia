package ai;

import ai.map.ReadSettings;
import ai.ui.Main;
import ai.ui.Settings;
import java.awt.Toolkit;

/**
 *
 * @author ÁdámRichárd
 */
public class AI {

    public static void main(String[] args) {

        Main mainframe = new Main();
        Toolkit tk = Toolkit.getDefaultToolkit();
        //monitor közepére pakoljuk a frame-t
        mainframe.setLocation(tk.getScreenSize().width / 2 - 500, tk.getScreenSize().height / 2 - 300);
        mainframe.setVisible(true);
        try {
            ReadSettings.checkXML();
            Settings.setAllTextField();
        } catch (Exception evt) {            
            Settings.setAllTextField();
        }
    }
}
