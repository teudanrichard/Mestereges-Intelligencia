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
                    //legelső sor a pálya nagyságát mutatja meg ezért beállítjuk mielőtt elkezdjük beolvasni az adatokat.
                    setMeret(Integer.parseInt(sor));
                } else {
                    //ha nem első sorban vagyunk akkor jöhetnek az adatok, ciklus fut pálya nagyságáig
                    for (int i = 0; i < palya.length; i++) {
                        //beolvassuk az adatot, spaccel elválasztva van megadva a mátrix így nagyon egyszerűen egy sorból betudjuk olvasni az egészet :)
                        palya[count - 2][i] = Integer.parseInt(sor.split(" ")[i]);
                    }
                }
                //növeljük a számlálót
                count++;
            }
            //getTombAllapot();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //beállítja a pálya méretét
    private static void setMeret(int n) {
        palya = new int[n][n];
    }

    //kiírja a tömb jelenlegi állapotát
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

        //rajta állunk már a kijáraton?
        if (palya[x][y] != 5) {
            // megnézzük jobbra,hogy nem-e a pálya szélén vagyunk, ha nem akkor megnézzük,hogy 0-e a mellette lévő értéke tehát akkor mehetünk arra....
            if (((y + 1) < palya.length) && (palya[x][y + 1] == 0 || palya[x][y + 1] == 5)) {
                //2-est rakunk mert itt már jártunk
                palya[x][y] = 2;
                //újra keresünk csak már a következő lépéstől.
                Keres(x, y + 1);
            }
            // megnézzük balra,hogy nem-e a pálya szélén vagyunk, ha nem akkor megnézzük,hogy 0-e a mellette lévő értéke tehát akkor mehetünk arra....
            if (((y - 1) >= 0) && (palya[x][y - 1] == 0 || palya[x][y - 1] == 5)) {
                //2-est rakunk mert itt már jártunk
                palya[x][y] = 2;
                //újra keresünk csak már a következő lépéstől.
                Keres(x, y - 1);
            }
            // ha nem akkor megnézzük felfelé,hogy nem-e a pálya tetején vagyunk, ha nem akkor megnézzük,hogy 0-e a felette lévő értéke tehát akkor mehetünk arra....
            if (((x - 1) >= 0) && (palya[x - 1][y] == 0 || palya[x - 1][y] == 5)) {
                //2-est rakunk mert itt már jártunk
                palya[x][y] = 2;
                //újra keresünk csak már a következő lépéstől.
                Keres(x - 1, y);
            }
            // megnézzük lefelé,hogy nem-e a pálya alján vagyunk, ha nem akkor megnézzük,hogy 0-e az alatta lévő értéke tehát akkor mehetünk arra....
            if (((x + 1) < palya.length) && (palya[x + 1][y] == 0 || palya[x + 1][y] == 5)) {
                //2-est rakunk mert itt már jártunk
                palya[x][y] = 2;
                //újra keresünk csak már a következő lépéstől.
                Keres(x + 1, y);
            }
            //ez zsákutca :'(
            //ezt jelöljük hármassal nehogy visszamenjünk oda
            palya[x][y] = 3;
        } else {
            int k = 0, c = 0;
            for (int i = 0; i < palya.length; i++) {
                for (int j = 0; j < palya.length; j++) {
                    if (palya[i][j] == 2) {
                        c++;
                    }
                }
            }
            for (int i = 0; i < palya.length; i++) {
                for (int j = 0; j < palya.length; j++) {
                    if (palya[i][j] == 3) {
                        k++;
                    }
                }
            }
            //sikerült megtaláltuk a kijáratot. kiírjuk az állapotot 
            getTombAllapot();
            setVege(x + 1, y + 1, c, k);
        }
    }

    private static void setVege(int x, int y, int lepes, int hibas) {
        System.out.println("Megvan a kijárat: " + (x + 1) + "," + (y + 1));
        System.out.println("Lépésszám: " + lepes);
        System.out.println("Hibasan bejárt terület: " + hibas);
    }

    public static void main(String[] args) {
        //beolvassuk a fájlt
        feltolt(new File("test.txt"));
        //pálya kezdés bal felső sarokban
        palya[1][1] = 3;
        //keresés bal felső sarokban a többit intézi a rekurzió
        Keres(1, 1);
    }

}
