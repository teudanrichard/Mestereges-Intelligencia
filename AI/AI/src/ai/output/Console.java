/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.output;

import ai.AI;
import ai.cell.Cell;
import ai.map.Map;

/**
 *
 * @author ÁdámRichárd
 */
public class Console {

    //Tömb jelenlegi állapotát írja ki számokkal jelölve a sorokat, oszlopokat
    public static void showFullMapWithNumbers() {
        //0-a pálya méretéig fut, ha a számláló nulla akkor 2helyet foglaljon ellenkező esetben 3at (így nézett ki jól a kiírásnál)
        for (int i = 0; i <= Map.meret; i++) {
            if (i == 0) {
                System.out.printf("%2d", i);
            } else {
                System.out.printf("%3d", i);
            }
        }

        System.out.println();
        for (int i = 1; i <= Map.meret; i++) {
            System.out.printf("%2d ", i);
            for (Cell al1 : Map.map) {
                if (al1.getX() == i) {
                    if (al1.isWall()) {
                        System.out.print(" # ");
                    } else if (!al1.isFree() && al1.isWrong_way() && !al1.isStart() && !al1.isFinish()) {
                        System.out.print(" - ");
                    } else if (al1.isFree() && !al1.isWrong_way()) {
                        System.out.print("   ");
                    } else if(al1.isWrong_way() && al1.isFree()){
                        if (al1.getValue() >= 10) {
                            System.out.print("" + al1.getValue() + " ");
                        } else {
                            System.out.print(" " + al1.getValue() + " ");
                        }
                    } else if (al1.isFinish()) {
                        System.out.print(" + ");
                    } else if (al1.isStart()) {
                        System.out.print(" 1 ");
                    }
                }
            }
            System.out.println("");
        }
    }
    //Tömb jelenlegi állapotát írja ki számokkal jelölve a sorokat, oszlopokat
    public static void showShortestMapWithNumbers() {

        for (int i = 0; i <= Map.meret; i++) {
            if (i == 0) {
                System.out.printf("%2d", i);
            } else {
                System.out.printf("%3d", i);
            }
        }

        System.out.println();
        for (int i = 1; i <= Map.meret; i++) {
            System.out.printf("%2d ", i);
            for (Cell al1 : Map.map) {
                if (al1.getX() == i) {
                    if (al1.isWall()) {
                        System.out.print(" # ");
                    } else if (al1.isFinish()) {
                        System.out.print(" + ");
                    } else if (al1.isStart()) {
                        System.out.print(" 1 ");
                    }else{
                        if (al1.getValue() >= 10) {
                            System.out.print("" + al1.getValue() + " ");
                        } else {
                            System.out.print(" " + al1.getValue() + " ");
                        }
                    }
                }
            }
            System.out.println("");
        }
    }

    public static void showFullMapWithoutNumbers() {
        for (int i = 1; i <= Map.meret; i++) {
            for (Cell al1 : Map.map) {
                if (al1.getX() == i) {
                    if (al1.isWall()) {
                        System.out.print("#");
                    } else if (al1.isFree()) {
                        System.out.print(" ");
                    } else if (al1.isFinish()) {
                        System.out.print("+");
                    } else if (al1.isStart()) {
                        System.out.print("1");
                    }
                }
            }
            System.out.println("");
        }
    }

    public static void writeMapAttr() {
        for (Cell s : Map.map) {
            System.out.println("(" + s.getX() + ", " + s.getY() + ") : fal:" + s.isWall() + " , ures: " + s.isFree() + ", cel: " + s.isFinish() + ", start: " + s.isStart());
        }
    }

    public static void writeCellAttr(Cell c) {
        System.out.println("(" + c.getX() + ", " + c.getY() + ") : érték: "+c.getValue()+", fal:" + c.isWall() + " , ures: " + c.isFree() + ", cel: " + c.isFinish() + ", start: " + c.isStart());
    }
}
