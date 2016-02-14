/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.algorithm;

import ai.cell.Cell;
import ai.map.Map;
import ai.output.Console;

/**
 *
 * @author ÁdámRichárd
 */
public class Find {

    public static void leftHand(int x, int y, int count) {
        count++;
        //rajta állunk már a kijáraton?
        if (!Map.getCell(x, y).isFinish()) {
            // megnézzük jobbra,hogy nem-e a pálya szélén vagyunk, ha nem akkor megnézzük,hogy 0-e a mellette lévő értéke tehát akkor mehetünk arra....
            if (((y + 1) <= Map.meret) && (Map.getCell(x, y + 1).isFree() && !Map.getCell(x, y + 1).isWrong_way()) || Map.getCell(x, y + 1).isFinish()) {
                //2-est rakunk mert itt már jártunk
                Map.getCell(x, y).setValue(count);
                Map.getCell(x, y).setWrong_way(true);
                //újra keresünk csak már a következő lépéstől.
                leftHand(x, y + 1, count);
            }
            // ha nem akkor megnézzük felfelé,hogy nem-e a pálya tetején vagyunk, ha nem akkor megnézzük,hogy 0-e a felette lévő értéke tehát akkor mehetünk arra....
            if (((x - 1) > 0) && (Map.getCell(x - 1, y).isFree() && !Map.getCell(x - 1, y).isWrong_way() || Map.getCell(x - 1, y).isFinish())) {
                //2-est rakunk mert itt már jártunk
                Map.getCell(x, y).setValue(count);
                Map.getCell(x, y).setWrong_way(true);
                //újra keresünk csak már a következő lépéstől.
                leftHand(x - 1, y, count);
            }
            // megnézzük lefelé,hogy nem-e a pálya alján vagyunk, ha nem akkor megnézzük,hogy 0-e az alatta lévő értéke tehát akkor mehetünk arra....
            if (((x + 1) <= Map.meret) && (Map.getCell(x + 1, y).isFree() && !Map.getCell(x + 1, y).isWrong_way() || Map.getCell(x + 1, y).isFinish())) {
                //2-est rakunk mert itt már jártunk
                Map.getCell(x, y).setValue(count);
                Map.getCell(x, y).setWrong_way(true);
                //újra keresünk csak már a következő lépéstől.
                leftHand(x + 1, y, count);
            }
            // megnézzük balra,hogy nem-e a pálya szélén vagyunk, ha nem akkor megnézzük,hogy 0-e a mellette lévő értéke tehát akkor mehetünk arra....
            if (((y - 1) > 0) && (Map.getCell(x, y - 1).isFree() && !Map.getCell(x, y - 1).isWrong_way() || Map.getCell(x, y - 1).isFinish())) {
                //2-est rakunk mert itt már jártunk
                Map.getCell(x, y).setValue(count);
                Map.getCell(x, y).setWrong_way(true);
                //újra keresünk csak már a következő lépéstől.
                leftHand(x, y - 1, count);
            }
            //ez zsákutca :'(
            //ezt jelöljük -1-el nehogy visszamenjünk oda
            Map.getCell(x, y).setValue(-1);
            Map.getCell(x, y).setFree(false);
            Map.getCell(x, y).setWrong_way(true);
        } else {
            //sikerült megtaláltuk a kijáratot. kiírjuk az állapotot 
            Console.showFullMapWithNumbers();
        }
    }

    public static void shortestWay(int x, int y, int count) {
        //számláló növelése
        count++;
        //a cella értéket kap a számlálóból (azért kap már most értéket mert szeretnénk tudni,hogy az azt követő lépésnél a cella értéke nagyobb-e, hisz ha nagyobb akkor az hosszabb úton jutott oda tehát nyugodtan felülírhatjuk)
        Map.getCell(x, y).setValue(count);

        //rajta állunk a célon?
        if (!Map.getCell(x, y).isFinish()) {
            //megnézzük,hogy a tőlünk jobbra lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x, y + 1) && Map.getCell(x, y + 1).isFree() || Map.getCell(x, y + 1).getValue() >= Map.getCell(x, y).getValue()) {
                //bejárt területnek állítjuk be
                Map.getCell(x, y).setFree(false);
                //meghívjuk ismét a függvényt új paraméterekkel
                shortestWay(x, y + 1, count);
            }            
            //megnézzük,hogy az alattunk lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x - 1, y) && Map.getCell(x - 1, y).isFree() || Map.getCell(x - 1, y).getValue() >= Map.getCell(x, y).getValue()) {
                Map.getCell(x, y).setFree(false);
                shortestWay(x - 1, y, count);
            }
            //megnézzük,hogy a felettünk lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x + 1, y) && Map.getCell(x + 1, y).isFree() || Map.getCell(x + 1, y).getValue() >= Map.getCell(x, y).getValue()) {
                Map.getCell(x, y).setFree(false);
                shortestWay(x + 1, y, count);
            }            
            //megnézzük,hogy a tőlünk balra lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x, y - 1) && (Map.getCell(x, y - 1).isFree()) || Map.getCell(x, y - 1).getValue() >= Map.getCell(x, y).getValue()) {
                Map.getCell(x, y).setFree(false);
                shortestWay(x, y - 1, count);
            }
            //zsákutca :(
            Map.getCell(x, y).setWrong_way(true);
        } else {
            //megtaláltuk a célt. kis if módosítgatással itt lesz kiírva a legrövidebb útvonal.
        }
    }

    private static boolean isCellValid(int x, int y) {
        if (x > 0 && y > 0 && x < Map.meret && y < Map.meret) {
            return true;
        }
        return false;
    }

    public static Cell findStartPosition() {
        for (int i = 1; i < Map.meret; i++) {
            for (int j = 1; j < Map.meret; j++) {
                if (Map.getCell(i, j).isStart()) {
                    return Map.getCell(i, j);
                }
            }
        }
        return null;
    }
}
