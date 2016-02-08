/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ÁdámRichárd
 */
public class MI {

    private static int[][] palya;

    private static void feltolt(File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String sor;
            int count = 1;
            while ((sor = br.readLine()) != null) {
                if (count == 1) {
                    setMeret(Integer.parseInt(sor));
                } else {
                    for (int i = 0; i < palya.length; i++) {
                        palya[count - 2][i] = Integer.parseInt(sor.split(" ")[i]);
                    }
                }
                count++;
            }
            getTombAllapot();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void setMeret(int n) {
        palya = new int[n][n];
    }

    private static void getTombAllapot() {
        for (int i = 0; i < palya.length; i++) {
            for (int j = 0; j < palya.length; j++) {
                System.out.printf("%d ", palya[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void Keres(int x, int y) {
        if (palya[x][y] != 5) {
            if (((x - 1) >= 0) && (palya[x - 1][y] == 0 || palya[x - 1][y] == 5)) {
                palya[x][y] = 2; 
                Keres(x - 1, y);
            }
            if (((y + 1) < palya.length) && (palya[x][y + 1] == 0 || palya[x][y + 1] == 5)) {
                palya[x][y] = 2; 
                Keres(x, y + 1);
            }
            if (((y - 1) >= 0) && (palya[x][y - 1] == 0 || palya[x][y - 1] == 5)) {
                palya[x][y] = 2; 
                Keres(x, y - 1);
            }
            if (((x + 1) < palya.length) && (palya[x + 1][y] == 0 || palya[x + 1][y] == 5)) {
                palya[x][y] = 2; 
                Keres(x + 1, y);
            }
            palya[x][y] = 0;            
        }else{
            getTombAllapot();
            setVege(x+1,y+1);
        }
    }

    private static void setVege(int x, int y) {
        System.out.println("Vége a játéknak, meglett a kettes: ");
    }

    public static void main(String[] args) {
        feltolt(new File("test.txt"));
        palya[0][0] = 3;
        Keres(0, 0);
    }

}
