package ai;

import ai.ui.Main;
import java.awt.Toolkit;

/**
 *
 * @author ÁdámRichárd
 */
public class AI {

    public static void main(String[] args) {
        Main mainframe = new Main();
        Toolkit tk = Toolkit.getDefaultToolkit();
        mainframe.setLocation(tk.getScreenSize().width / 2 - 500, tk.getScreenSize().height / 2 - 300);
        mainframe.setVisible(true);
    }
}
