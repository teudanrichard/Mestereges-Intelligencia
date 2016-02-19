/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.map;

import ai.cell.Cell;
import ai.path.Path;
import ai.ui.CanvasPanel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ÁdámRichárd
 */
public class Map {

    public static List<Cell> map = new ArrayList<>();
    public static List<Path> shortestPaths = new ArrayList<>();
    public static int meret = 0;
    public static String solve_time = "";    
    public static String map_read_t = "";
    private static ArrayList<String> row = new ArrayList<>();

    public static List<Cell> readMap(File f) {
        ReadSettings.checkXML();
        //lekérjük a kezdő időt amikor beléptünk a függvénybe
        long start = System.nanoTime();
        try {
            //leszedi az összes eltárolt képet
            CanvasPanel.removeAllImage();
            //töröljük a listát ahol a pályákat tároltuk
            for (int i = 0; i < shortestPaths.size(); i++) {
                shortestPaths.remove(i);
                //csökkentjük az i-t mert ha töröltünk egy elemet minden csúszik hátrafelé így lényegében a 0-s pozíción maradva végig töröljük a listát
                i--;
            }
            //
            for (int i = 0; i < map.size(); i++) {
                map.remove(i);
                i--;
            }
            for (int i = 0; i < row.size(); i++) {
                row.remove(i);
                i--;
            }

            meret = 0;
            //beolvassuk a megadott fájlt
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            String sor;

            int count = 0;

            //lecsekkoljuk a pálya nagyságát (sorszámot nézünk következtetünk arra,hogy oszlopszám is stimmel)
            //ha nem akkor hibás lesz a beolvasás
            while ((sor = br.readLine()) != null) {
                meret++;
                row.add(sor);
            }
            //sorszámig fut
            for (String s : row) {
                count++;
                //az adott sort elválasztjuk az elválasztókarakterrel így kapva egy tömbböt minden elemről
                String temp[] = s.split(ReadSettings.getKarakter_elvalaszto());
                //a tömb nagyságáig futtatjuk
                for (int i = 0; i < temp.length; i++) {
                    Cell cs = new Cell();
                    //karakter kódolás miatt le kell csekkolni az elemet ha a hossza nem nulla akkor mehet egyébként az átugorja 
                    if (temp[i].length() != 0) {
                        //fal esetén beállítja a cella tulajdonságait
                        if (temp[i].equals(ReadSettings.getKarakter_fal())) {
                            cs.setWall(true);
                            cs.setFree(false);
                            cs.setFinish(false);
                            cs.setStart(false);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(-1);
                        } else if (temp[i].equals(ReadSettings.getKarakter_cel())) {
                            cs.setWall(false);
                            cs.setFree(false);
                            cs.setFinish(true);
                            cs.setStart(false);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(0);
                        } else if (temp[i].equals(ReadSettings.getKarakter_start())) {
                            cs.setWall(false);
                            cs.setFree(false);
                            cs.setFinish(false);
                            cs.setStart(true);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(0);
                        } else if (temp[i].equals(ReadSettings.getKarakter_ut())){
                            cs.setWall(false);
                            cs.setFree(true);
                            cs.setFinish(false);
                            cs.setStart(false);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(0);
                        }
                        //x,y koordináta megadásas
                        cs.setX(count);
                        cs.setY(i);
                        //hozzáadjuk a páyát a maphoz
                        map.add(cs);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Hiba történt",JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Hiba történt",JOptionPane.WARNING_MESSAGE);
        } catch(Exception evt){
            JOptionPane.showMessageDialog(null,evt.getMessage(),"Hiba történt",JOptionPane.WARNING_MESSAGE);
        }
        //futás után letároljuk az időt ezzel kitudjuk számolni mennyi ideig futott a programrész
        long end = System.nanoTime();
        //formázzuk
        DecimalFormat df = new DecimalFormat("0.0000");
        //eltároljuk egy stringben az eredményt
        map_read_t = ""+df.format((end - start) / 1000000000d)+" mp";
        //visszatér a pályával
        return map;
    }

    //visszatér x,y koordináta kikeresése után a cellával
    public static Cell getCell(int x, int y) {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).getX() == x && map.get(i).getY() == y) {
                return map.get(i);
            }
        }
        return null;
    }

    //visszatér a letárolt útvonalak listájával
    public static List<Path> getShortestPaths() {
        return shortestPaths;
    }
    //új pályát hoz létre az útvonal alapján
    public static void createNewMap(Path p) {
        Map.shortestPaths.add(p);
    }

}
