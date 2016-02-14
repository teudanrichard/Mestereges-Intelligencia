package ai;

import ai.algorithm.Find;
import ai.map.Map;
import ai.output.Console;
import java.io.File;

/**
 *
 * @author ÁdámRichárd
 */
public class AI {

    public static void main(String[] args) {
        Map.readMap(new File("test.txt"), 20, null, "#", " ", "1", "+");
        System.out.println("");
        Find.shortestWay(Find.findStartPosition().getX(), Find.findStartPosition().getY(), 0);
        Console.showShortestMapWithNumbers();
    }
}
