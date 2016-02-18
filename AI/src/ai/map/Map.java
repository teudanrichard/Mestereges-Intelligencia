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
    private static final String karakter_elvalaszto = "";
    private static final String karakter_fal = "#";
    private static final String karakter_start = "1";
    private static final String karakter_cel = "+";

    public static List<Cell> readMap(File f) {
        long start = System.nanoTime();
        try {            
            CanvasPanel.removeAllImage();
            for (int i = 0; i < shortestPaths.size(); i++) {
                shortestPaths.remove(i);
                i--;
            }
            for (int i = 0; i < map.size(); i++) {
                map.remove(i);
                i--;
            }

            meret = 0;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            ArrayList<String> row = new ArrayList<>();
            String sor;

            int count = 0;

            while ((sor = br.readLine()) != null) {
                meret++;
                row.add(sor);
            }

            for (String s : row) {
                count++;
                String temp[] = s.split(karakter_elvalaszto);

                for (int i = 0; i < temp.length; i++) {
                    Cell cs = new Cell();
                    if (temp[i].length() != 0) {
                        if (temp[i].equals(karakter_fal)) {
                            cs.setWall(true);
                            cs.setFree(false);
                            cs.setFinish(false);
                            cs.setStart(false);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(-1);
                        } else if (temp[i].equals(karakter_cel)) {
                            cs.setWall(false);
                            cs.setFree(false);
                            cs.setFinish(true);
                            cs.setStart(false);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(0);
                        } else if (temp[i].equals(karakter_start)) {
                            cs.setWall(false);
                            cs.setFree(false);
                            cs.setFinish(false);
                            cs.setStart(true);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(0);
                        } else {
                            cs.setWall(false);
                            cs.setFree(true);
                            cs.setFinish(false);
                            cs.setStart(false);
                            cs.setWrong_way(false);
                            cs.setCurrently_used(false);
                            cs.setValue(0);
                        }
                        cs.setX(count);
                        cs.setY(i);
                        map.add(cs);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("" + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("" + ex.getMessage());
        }
        long end = System.nanoTime();
        DecimalFormat df = new DecimalFormat("0.0000");
        map_read_t = ""+df.format((end - start) / 1000000000d)+" mp";
        return map;
    }

    public static Cell getCell(int x, int y) {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).getX() == x && map.get(i).getY() == y) {
                return map.get(i);
            }
        }
        return null;
    }

    public static List<Path> getShortestPaths() {
        return shortestPaths;
    }

    public static void createNewMap(Path p) {
        Map.shortestPaths.add(p);
    }

}
