/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.algorithm;

import ai.cell.Cell;
import ai.map.Map;
import ai.output.Console;
import ai.path.Path;

/**
 *
 * @author ÁdámRichárd
 */
public class Find {

//    public static void leftHand(int x, int y, int count) {
//        count++;
//        //rajta állunk már a kijáraton?
//        if (!Map.getCell(x, y).isFinish()) {
//            // megnézzük jobbra,hogy nem-e a pálya szélén vagyunk, ha nem akkor megnézzük,hogy 0-e a mellette lévő értéke tehát akkor mehetünk arra....
//            if (((y + 1) <= Map.meret) && (Map.getCell(x, y + 1).isFree() && !Map.getCell(x, y + 1).isWrong_way()) || Map.getCell(x, y + 1).isFinish()) {
//                //2-est rakunk mert itt már jártunk
//                Map.getCell(x, y).setValue(count);
//                Map.getCell(x, y).setWrong_way(true);
//                //újra keresünk csak már a következő lépéstől.
//                leftHand(x, y + 1, count);
//            }
//            // ha nem akkor megnézzük felfelé,hogy nem-e a pálya tetején vagyunk, ha nem akkor megnézzük,hogy 0-e a felette lévő értéke tehát akkor mehetünk arra....
//            if (((x - 1) > 0) && (Map.getCell(x - 1, y).isFree() && !Map.getCell(x - 1, y).isWrong_way() || Map.getCell(x - 1, y).isFinish())) {
//                //2-est rakunk mert itt már jártunk
//                Map.getCell(x, y).setValue(count);
//                Map.getCell(x, y).setWrong_way(true);
//                //újra keresünk csak már a következő lépéstől.
//                leftHand(x - 1, y, count);
//            }
//            // megnézzük lefelé,hogy nem-e a pálya alján vagyunk, ha nem akkor megnézzük,hogy 0-e az alatta lévő értéke tehát akkor mehetünk arra....
//            if (((x + 1) <= Map.meret) && (Map.getCell(x + 1, y).isFree() && !Map.getCell(x + 1, y).isWrong_way() || Map.getCell(x + 1, y).isFinish())) {
//                //2-est rakunk mert itt már jártunk
//                Map.getCell(x, y).setValue(count);
//                Map.getCell(x, y).setWrong_way(true);
//                //újra keresünk csak már a következő lépéstől.
//                leftHand(x + 1, y, count);
//            }
//            // megnézzük balra,hogy nem-e a pálya szélén vagyunk, ha nem akkor megnézzük,hogy 0-e a mellette lévő értéke tehát akkor mehetünk arra....
//            if (((y - 1) > 0) && (Map.getCell(x, y - 1).isFree() && !Map.getCell(x, y - 1).isWrong_way() || Map.getCell(x, y - 1).isFinish())) {
//                //2-est rakunk mert itt már jártunk
//                Map.getCell(x, y).setValue(count);
//                Map.getCell(x, y).setWrong_way(true);
//                //újra keresünk csak már a következő lépéstől.
//                leftHand(x, y - 1, count);
//            }
//            //ez zsákutca :'(
//            //ezt jelöljük -1-el nehogy visszamenjünk oda
//            Map.getCell(x, y).setValue(-1);
//            Map.getCell(x, y).setFree(false);
//            Map.getCell(x, y).setWrong_way(true);
//        } else {
//            //sikerült megtaláltuk a kijáratot. kiírjuk az állapotot 
//            Console.showFullMapWithNumbers();
//        }
//    }
    public static void shortestWay(int x, int y, int count) {
        //számláló növelése
        count++;
        //a cella értéket kap a számlálóból (azért kap már most értéket mert szeretnénk tudni,hogy az azt követő lépésnél a cella értéke nagyobb-e, hisz ha nagyobb akkor az hosszabb úton jutott oda tehát nyugodtan felülírhatjuk)
        Map.getCell(x, y).setValue(count);
        Map.getCell(x, y).setCurrently_used(true);
        if (isCellValid(x + 1, y) && Map.getCell(x + 1, y).isFinish()) {
            clearMap(x, y, count);
        } else if (isCellValid(x - 1, y) && Map.getCell(x - 1, y).isFinish()) {
            clearMap(x, y, count);
        } else if (isCellValid(x, y + 1) && Map.getCell(x, y + 1).isFinish()) {
            clearMap(x, y, count);
        } else if (isCellValid(x, y - 1) && Map.getCell(x, y - 1).isFinish()) {
            clearMap(x, y, count);
        }
        //rajta állunk a célon?
        if (!Map.getCell(x, y).isFinish()) {
            //megnézzük,hogy a tőlünk jobbra lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x, y + 1)) {
                if (Map.getCell(x, y + 1).isFree() || Map.getCell(x, y + 1).getValue() >= Map.getCell(x, y).getValue()) {
                    //bejárt területnek állítjuk be
                    Map.getCell(x, y).setFree(false);
                    //meghívjuk ismét a függvényt új paraméterekkel
                    shortestWay(x, y + 1, count);
                }
            }
            //megnézzük,hogy az alattunk lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x - 1, y)) {
                if (Map.getCell(x - 1, y).isFree() || Map.getCell(x - 1, y).getValue() >= Map.getCell(x, y).getValue()) {
                    Map.getCell(x, y).setFree(false);
                    shortestWay(x - 1, y, count);
                }
            }
            //megnézzük,hogy a felettünk lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x + 1, y)) {
                if (Map.getCell(x + 1, y).isFree() || Map.getCell(x + 1, y).getValue() >= Map.getCell(x, y).getValue()) {
                    Map.getCell(x, y).setFree(false);
                    shortestWay(x + 1, y, count);
                }
            }
            //megnézzük,hogy a tőlünk balra lévő cella valós (nem lógunk ki a pályáról) a cella bejárható (tehát útvonal) a cella utoljára kapott értéke nagyobb vagy egyenlő-e a jelenlegi celláéval (ha igen akkor az hosszabb utat járt be felülírjuk)
            if (isCellValid(x, y - 1)) {
                if (Map.getCell(x, y - 1).isFree() || Map.getCell(x, y - 1).getValue() >= Map.getCell(x, y).getValue()) {
                    Map.getCell(x, y).setFree(false);
                    shortestWay(x, y - 1, count);
                }
            }

            //zsákutca :(
            Map.getCell(x, y).setWrong_way(true);
            Map.getCell(x, y).setCurrently_used(false);
        }
    }

    private static void clearMap(int cx, int cy, int steps) {
        Path p = new Path(steps);
        p.setStep_coordinates(steps - 1, cx, cy, steps);

        for (int i = steps - 1; i > 0; i--) {
            if (isCellValid(cx - 1, cy) && Map.getCell(cx - 1, cy).getValue() == i && Map.getCell(cx - 1, cy).isCurrently_used()) {
                p.setStep_coordinates(i - 1, cx - 1, cy, i);
                cx--;
            } else if (isCellValid(cx + 1, cy) && Map.getCell(cx + 1, cy).getValue() == i && Map.getCell(cx + 1, cy).isCurrently_used()) {
                p.setStep_coordinates(i - 1, cx + 1, cy, i);
                cx++;
            } else if (isCellValid(cx, cy - 1) && Map.getCell(cx, cy - 1).getValue() == i && Map.getCell(cx, cy - 1).isCurrently_used()) {
                p.setStep_coordinates(i - 1, cx, cy - 1, i);
                cy--;
            } else if (isCellValid(cx, cy + 1) && Map.getCell(cx, cy + 1).getValue() == i && Map.getCell(cx, cy + 1).isCurrently_used()) {
                p.setStep_coordinates(i - 1, cx, cy + 1, i);
                cy++;
            }
        }
        Map.createNewMap(p);
    }

    private static boolean isCellValid(int x, int y) {
        return x > 0 && y > 0 && x <= Map.meret && y <= Map.meret;
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
