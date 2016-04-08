/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.Events;

import ai.algorithm.Find;
import ai.cell.Cell;
import ai.map.Map;
import ai.map.ReadSettings;
import ai.path.Path;
import ai.ui.About;
import ai.ui.CanvasPanel;
import ai.ui.Main;
import static ai.ui.Main.canvasPanel;
import ai.ui.Settings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author AdamRichard
 */
public class Controller {

    private static final Toolkit tk = Toolkit.getDefaultToolkit();
    public static int index = 0;
    public static int max = 0;
    private static final About about = new About();
    private static Settings settings;
    Main main;

    public Controller(Main m, Settings s) {
        main = m;
        settings = s;
        main.nextButton.addActionListener(new nextButtonListener());
        main.nextButton.addKeyListener(new nextButtonKeyListener());
        main.previousButton.addActionListener(new previousButtonListener());
        main.previousButton.addKeyListener(new previousButtonKeyListener());
        main.exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //0-ás kóddal kilép a programból
                System.exit(0);
            }
        });
        main.defaultSettingsMenuItem.addActionListener(new defaultSettingsMenuItemListener());
        main.refreshMapMenuItem.addActionListener(new refreshMapMenuItemListener());
        main.openMenuItem.addActionListener(new openMenuItem());
        main.topPanel.addKeyListener(new topPanelKeyListener());
        main.middleTextField.addKeyListener(new middleTextFieldListener());
        main.visualSettingsMenuItem.addActionListener(new visualSettnigsMenuItemListener());
        settings.saveSettingsButton.addActionListener(new saveSettingsButtonListener());
        settings.jButton2.addActionListener(new Color1ActionListener());
        settings.jButton3.addActionListener(new Color2ActionListener());
        settings.jButton4.addActionListener(new Color3ActionListener());
        settings.jButton5.addActionListener(new Color4ActionListener());
        main.aboutMenuItem.addActionListener(new aboutMenuItemListener());
        
        //beállítjuk a két képet a buttonoknak, eltűntetjük a gomb keretét és a kék hátteret
        ImageIcon img = new ImageIcon(getClass().getResource("/ai/ui/image/right.png"));
        main.previousButton.setBorderPainted(false);
        main.previousButton.setContentAreaFilled(false);
        main.previousButton.setFocusPainted(false);
        main.previousButton.setOpaque(false);
        main.previousButton.setIcon(img);

        ImageIcon img2 = new ImageIcon(getClass().getResource("/ai/ui/image/left.png"));
        main.nextButton.setBorderPainted(false);
        main.nextButton.setContentAreaFilled(false);
        main.nextButton.setFocusPainted(false);
        main.nextButton.setOpaque(false);
        main.nextButton.setIcon(img2);
    }

    private class visualSettnigsMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //szintén középrerakjuk az ablakot majd megjelenítjük a beállításokat
            settings.setLocation(tk.getScreenSize().width / 2 - 250, tk.getScreenSize().height / 2 - 112);
            settings.setVisible(true);
        }
    }

    private class Color1ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Válassz fal színt!", ReadSettings.getSzin_fal());
            if (c != null) {
                ReadSettings.setSzin_fal(c);
                settings.jTextField5.setBackground(c);
            }
        }
    }

    private class Color2ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Válassz út színt!", ReadSettings.getSzin_ut());
            if (c != null) {
                ReadSettings.setSzin_ut(c);
                settings.jTextField6.setBackground(c);
            }
        }

    }

    private class Color3ActionListener implements ActionListener {

        public Color3ActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Válassz Start színt!", ReadSettings.getSzin_start());
            if (c != null) {
                ReadSettings.setSzin_start(c);
                settings.jTextField7.setBackground(c);
            }
        }
    }

    private class Color4ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Válassz cél színt!", ReadSettings.getSzin_cel());
            if (c != null) {
                ReadSettings.setSzin_cel(c);
                settings.jTextField8.setBackground(c);
            }
        }
    }

    private class aboutMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //kis pozicionálás és a névjegy megjelenítése
            about.setLocation(tk.getScreenSize().width / 2 - 250, tk.getScreenSize().height / 2 - 112);
            about.setVisible(true);
        }
    }

    private class previousButtonKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            keyLeftRight(e.getKeyCode());
        }
    }

    private class nextButtonKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            keyLeftRight(e.getKeyCode());
        }
    }

    private class saveSettingsButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean diff = ReadSettings.isLegrovidebb_ut() != settings.shortestPathCheckBox.isSelected();
            ReadSettings.setKarakter_fal(settings.jTextField1.getText());
            ReadSettings.setKarakter_ut(settings.jTextField2.getText());
            ReadSettings.setKarakter_start(settings.jTextField3.getText());
            ReadSettings.setKarakter_cel(settings.jTextField4.getText());
            ReadSettings.setRacsozott_palya(settings.jCheckBox1.isSelected());
            ReadSettings.setLegrovidebb_ut(settings.shortestPathCheckBox.isSelected());
            ReadSettings.createXML();
            ReadSettings.checkXML();
            if (diff && ReadSettings.getLast_selected_file() != null) {
                JOptionPane.showMessageDialog(null, "Útvonalak változása miatt újra kell tölteni a pályát", "Hiba történt", JOptionPane.WARNING_MESSAGE);
                readFile();
            } else if (ReadSettings.getLast_selected_file() != null) {
                index = 0;
                CanvasPanel.removeAllImage();
                try {
                    //kirajzoltatjuk a pályát és letároljuk
                    for (int i = 0; i < Map.getShortestPaths().size(); i++) {
                        drawPath(Map.getShortestPaths().get(i));
                    }
                    //üres pályát állítjuk be alapértelmezetten
                    panelUjrarajzol();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba történt", JOptionPane.WARNING_MESSAGE);
                }
            }
            settings.hide();
        }
    }

    private class refreshMapMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //ha az utolsó fájl nem null (nincs értéke a változónak) akkor újratölt
            if (ReadSettings.getLast_selected_file() != null) {
                readFile();
            }
        }
    }

    private class openMenuItem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            index = 0;
            //megnyitunk egy új ablakban egy fájlchoosert ami visszatérési értéke a kiválasztott fájl
            //a program futtatási helyét nyitja meg alapértelmezett mappaként
            JFileChooser chooser = new JFileChooser("" + System.getProperty("user.dir"));
            //megjelenítjük
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                ReadSettings.setLast_selected_file(chooser.getSelectedFile());
                main.refreshMapMenuItem.setEnabled(true);
                readFile();
            }
        }
    }

    private class topPanelKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            //jobb nyíllal váltunk a következő pályára
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (index < Map.getShortestPaths().size() - 1) {
                    index++;
                }
                if (index == 0) {
                    main.middleTextField.setText("Üres pálya");
                } else {
                    main.middleTextField.setText(index + "/" + (Map.shortestPaths.size() - 1) + " megoldás");
                }
                canvasPanel.setImageIndex(index);
                canvasPanel.repaint();
                canvasPanel.revalidate();
            } //bal nyíllal pakolunk másik képet
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (index > 0) {
                    index--;
                }
                if (index == 0) {
                    main.middleTextField.setText("Üres pálya");
                } else {
                    main.middleTextField.setText(index + "/" + (Map.shortestPaths.size() - 1) + " megoldás");
                }

                canvasPanel.setImageIndex(index);
                canvasPanel.repaint();
                canvasPanel.revalidate();
            }
        }
    }

    private class middleTextFieldListener implements KeyListener {

        public middleTextFieldListener() {}

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            keyLeftRight(e.getKeyCode());
        }
    }

    private class nextButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //ha az index nagyobb mint nulla akkor még csökkenthetem ellenkező esetben már az első elemen vagyunk...
            if (index > 0) {
                index--;
            }
            // ha nulla az index írja ki,hogy üres a pála ellenkező esetben a pálya száma/összes pálya
            if (index == 0) {
                main.middleTextField.setText("Üres pálya");
            } else {
                main.middleTextField.setText(index + "/" + (Map.shortestPaths.size() - 1) + " megoldás");
            }
            //beállítjuk az indexet és újrarajzoljuk a canvas-t
            canvasPanel.setImageIndex(index);
            canvasPanel.repaint();
            canvasPanel.revalidate();
        }
    }

    private class previousButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
                        //lekérem a legrövidebb útvonal listájának méretét és megnézem,hogy nagyobb-e mint az index
            if (index < Map.getShortestPaths().size() - 1) {
                index++;
            }
            // ha nulla akkor üres pályán vagyunk
            if (index == 0) {
                main.middleTextField.setText("Üres pálya");
            } else {
                main.middleTextField.setText(index + "/" + (Map.shortestPaths.size() - 1) + " megoldás");
            }
            //beállítjuk az indexet a másik osztályban ez alapján a feltöltött képek alapján beállítja a pályát
            canvasPanel.setImageIndex(index);
            canvasPanel.repaint();
            canvasPanel.revalidate();
        }
    }

    private class defaultSettingsMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //beállítjuk az alapértelmezett beállításokat,hogyha a pálya megoldásainak száma különböző beállításon voltak újraolvassuk a fájlt
            boolean diff = ReadSettings.isLegrovidebb_ut() != true;
            ReadSettings.setKarakter_fal("#");
            ReadSettings.setKarakter_start("1");
            ReadSettings.setKarakter_cel("+");
            ReadSettings.setKarakter_ut(" ");
            ReadSettings.setSzin_fal(Color.GRAY);
            ReadSettings.setSzin_ut(Color.WHITE);
            ReadSettings.setSzin_cel(Color.RED);
            ReadSettings.setSzin_start(Color.BLUE);
            ReadSettings.setRacsozott_palya(true);
            ReadSettings.setLegrovidebb_ut(true);
            setAllTextField();
            ReadSettings.createXML();
            if (diff && ReadSettings.getLast_selected_file() != null) {
                JOptionPane.showMessageDialog(null, "Útvonalak változása miatt újra kell tölteni a pályát", "Hiba történt", JOptionPane.WARNING_MESSAGE);
                readFile();
            } else if (ReadSettings.getLast_selected_file() != null) {
                index = 0;
                CanvasPanel.removeAllImage();
                try {
                    //kirajzoltatjuk a pályát és letároljuk
                    for (int i = 0; i < Map.getShortestPaths().size(); i++) {
                        drawPath(Map.getShortestPaths().get(i));
                    }
                    //üres pályát állítjuk be alapértelmezetten
                    panelUjrarajzol();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba történt", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }

    public void readFile() {
        //indexet nullára állítjuk mert nem tudjuk hány pálya lesz, és ezért üresen kezdünk
        index = 0;

        try {
            main.refreshMapMenuItem.setEnabled(false);
            Map.readMap(ReadSettings.getLast_selected_file());

            //hozzáadjuk az üres pályát alapértlemezetten 0 lépésszámmal
            Map.createNewMap(new Path(0));
            //méretet nézünk
            long start = System.nanoTime();
            //legrövidebb útvonalat keres
            try {
                Find.shortestWay(Find.findStartPosition().getX(), Find.findStartPosition().getY(), 0);
            } catch (Exception evt) {
                throw new Exception("hiba a fájl olvasása közben");
            }
            //végzési időt
            long end = System.nanoTime();
            //formázzuk
            DecimalFormat df = new DecimalFormat("0.0000");

            Map.solve_time = "" + df.format((end - start) / 1000000000d) + " mp";
            try {
                if (ReadSettings.isLegrovidebb_ut()) {
                    for (int i = 0; i < Map.getShortestPaths().size(); i++) {
                        if (Map.getShortestPaths().get(Map.getShortestPaths().size() - 1).getMax_step() < Map.getShortestPaths().get(i).getMax_step()) {
                            Map.shortestPaths.remove(i);
                            i--;
                        }
                    }
                }
            } catch (Exception evt) {
                throw new Exception("hiba a legrövidebb út keresése közben");
            }
            try {
                //kirajzoltatjuk a pályát és letároljuk
                for (int i = 0; i < Map.getShortestPaths().size(); i++) {

                    drawPath(Map.getShortestPaths().get(i));

                }
            } catch (Exception evt) {
                throw new Exception("hiba a pálya kirajzolása közben");
            }
            //üres pályát állítjuk be alapértelmezetten

            main.middleTextField.setText("Üres pálya");
            panelUjrarajzol();

            main.refreshMapMenuItem.setEnabled(true);
        } catch (Exception ex) {
            ReadSettings.setLast_selected_file(null);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba történt", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void panelUjrarajzol() {
        canvasPanel.setImageIndex(index);
        canvasPanel.repaint();
        canvasPanel.revalidate();
    }

    //jobb bal nyil lekezlése
    private void keyLeftRight(int keyCode) {
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (index < Map.getShortestPaths().size() - 1) {
                index++;
            }
            if (index == 0) {
                main.middleTextField.setText("Üres pálya");
            } else {
                main.middleTextField.setText(index + "/" + (Map.shortestPaths.size() - 1) + " megoldás");
            }
            canvasPanel.setImageIndex(index);
            canvasPanel.repaint();
            canvasPanel.revalidate();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (index > 0) {
                index--;
            }
            if (index == 0) {
                main.middleTextField.setText("Üres pálya");
            } else {
                main.middleTextField.setText(index + "/" + (Map.shortestPaths.size() - 1) + " megoldás");
            }

            canvasPanel.setImageIndex(index);
            canvasPanel.repaint();
            canvasPanel.revalidate();
        }
    }

    //kirajzolja a pályát különböző színekkel amiket az adott elemeknek beállítottam
    public void drawPath(Path p) {
        BufferedImage bufferedImage = new BufferedImage(canvasPanel.getSize().width, canvasPanel.getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g2d = bufferedImage.getGraphics();
        int meret = Map.meret;
        //pályaméret szerint állítjuk a kockák nagyságát.
        if (Map.meret <= 10) {
            meret = 40;
        } else if (Map.meret <= 20) {
            meret = 20;
        } else if (Map.meret <= 30) {
            meret = 15;
        } else if (Map.meret <= 40) {
            meret = 10;
        }
        int gbox = 50;
        int rbox = 150;
        int rboxw = 10;
        //innentől rajzolgatunk....
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, canvasPanel.getWidth(), canvasPanel.getHeight());
        g2d.setColor(Color.GREEN);

        g2d.drawString("Pálya adatok", 10, gbox - 6);
        g2d.drawRect(10, gbox, 200, 60);
        g2d.drawString("Pálya mérete: " + Map.meret + " x " + Map.meret, 20, gbox + (1 * 15));
        g2d.drawString("Kiválasztott megoldás: " + CanvasPanel.bimage.size(), 20, gbox + (2 * 15));
        g2d.drawString("Megoldás lépésszáma: " + p.getMax_step(), 20, gbox + (3 * 15));
        g2d.drawString("Beolvasással kapcsolatos adatok", rboxw, rbox - 6);
        g2d.drawRect(rboxw, rbox, 200, 40);
        g2d.drawString("Pálya beolvasása: " + Map.map_read_t, rboxw + 10, rbox + (1 * 15));
        g2d.drawString("Algoritmus bejárása: " + Map.solve_time, rboxw + 10, rbox + (2 * 15));

        int width = meret;
        int height = meret;
        int x, y;

        for (Cell al1 : Map.map) {
            x = 300 + meret * al1.getY();
            y = meret * al1.getX();
            if (al1.isWall()) {
                g2d.setColor(ReadSettings.getSzin_fal());
                g2d.fillRect(x, y, width, height);
            } else if (al1.isFinish()) {
                g2d.setColor(ReadSettings.getSzin_cel());
                g2d.fillRect(x, y, width, height);
            } else if (al1.isStart()) {
                g2d.setColor(ReadSettings.getSzin_start());
                g2d.fillRect(x, y, width, height);
            } else {
                int c = -1;
                for (int k = 0; k < p.getMax_step(); k++) {
                    if (al1.getX() == p.getStep_coordinates()[k][0] && al1.getY() == p.getStep_coordinates()[k][1]) {
                        c = p.getStep_coordinates()[k][2];
                    }
                }
                if (c > -1) {
                    if (c >= 10) {
                        g2d.setColor(Color.GREEN);
                        g2d.fillRect(x, y, width, height);
                        g2d.setColor(Color.BLACK);
                        if (Map.meret <= 25) {
                            g2d.drawString("" + c, x + 4, y + 15);
                        }
                    } else {
                        g2d.setColor(Color.GREEN);
                        g2d.fillRect(x, y, width, height);
                        g2d.setColor(Color.BLACK);
                        if (Map.meret <= 25) {
                            g2d.drawString("" + c, x + 8, y + 15);
                        }
                    }
                } else {
                    g2d.setColor(ReadSettings.getSzin_ut());
                    g2d.fillRect(x, y, width, height);
                }
            }

            if (ReadSettings.isRacsozott_palya()) {
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, width, height);
            }

        }
        //a képet átküldjük a canvasnak
        CanvasPanel.setBimage(bufferedImage);
        //memória problémák optimalizálása
        bufferedImage = null;
        g2d = null;
        System.gc();
    }
    //beállít minden szövegmezőt a megfelelő karakterrel, színnel és a checkBoxokat is bállítjuk
    //a ReadSettings osztály alapján

    public static void setAllTextField() {

        settings.jTextField1.setText(ReadSettings.getKarakter_fal());
        settings.jTextField2.setText(ReadSettings.getKarakter_ut());
        settings.jTextField3.setText(ReadSettings.getKarakter_start());
        settings.jTextField4.setText(ReadSettings.getKarakter_cel());

        settings.jTextField5.setBackground(ReadSettings.getSzin_fal());
        settings.jTextField6.setBackground(ReadSettings.getSzin_ut());
        settings.jTextField7.setBackground(ReadSettings.getSzin_start());
        settings.jTextField8.setBackground(ReadSettings.getSzin_cel());

        settings.jCheckBox1.setSelected(ReadSettings.isRacsozott_palya());
        settings.shortestPathCheckBox.setSelected(ReadSettings.isLegrovidebb_ut());

    }

}
