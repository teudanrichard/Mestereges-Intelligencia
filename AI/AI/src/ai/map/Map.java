/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.map;

import ai.cell.Cell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public static int meret = 0;

    public static List<Cell> readMap(File f, int meret, String karakter_elvalaszto, String karakter_fal, String karakter_urescella,String karakter_start,String karakter_cel) {
        try {
            Map.meret = meret;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String sor;
            
            int count = 0;
            
            if(karakter_elvalaszto == null)
                karakter_elvalaszto = "";
            while ((sor = br.readLine()) != null) {
                count++;
                for (int i = 1; i <= meret; i++) {
                    Cell cs = new Cell();
                    if(sor.split(karakter_elvalaszto)[i].equals(karakter_fal)){
                        cs.setWall(true);
                        cs.setFree(false);
                        cs.setFinish(false);
                        cs.setStart(false);
                        cs.setWrong_way(false);
                        cs.setValue(-1);
                    }else if(sor.split(karakter_elvalaszto)[i].equals(karakter_cel)){
                        cs.setWall(false);
                        cs.setFree(false);
                        cs.setFinish(true);                        
                        cs.setStart(false);
                        cs.setWrong_way(false);                        
                        cs.setValue(0);
                    }else if(sor.split(karakter_elvalaszto)[i].equals(karakter_start)){
                        cs.setWall(false);
                        cs.setFree(false);
                        cs.setFinish(false);
                        cs.setStart(true);    
                        cs.setWrong_way(false);    
                        cs.setValue(0);               
                    }else{
                        cs.setWall(false);
                        cs.setFree(true);
                        cs.setFinish(false);
                        cs.setStart(false);  
                        cs.setWrong_way(false); 
                        cs.setValue(0);
                    }
                    cs.setX(count);
                    cs.setY(i);
                    map.add(cs);                        
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public static Cell getCell(int x, int y){
        for(int i=0;i<=map.size();i++){
            if(map.get(i).getX() == x && map.get(i).getY() == y)
                return map.get(i);
        }
        return null;
    }
}
