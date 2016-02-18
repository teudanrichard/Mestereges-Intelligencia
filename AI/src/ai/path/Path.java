/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.path;

/**
 *
 * @author ÁdámRichárd
 */
public class Path {

    private final int step_coordinates[][];
    private final int max_step;

    public int getMax_step() {
        return max_step;
    }

    public int[][] getStep_coordinates() {
        return step_coordinates;
    }

    public void setStep_coordinates(int row, int x, int y,int ertek) {
        step_coordinates[row][0] = x;
        step_coordinates[row][1] = y;
        step_coordinates[row][2] = ertek;
    }

    public Path(int steps) {
        step_coordinates = new int[steps][3];
        max_step = steps;
    }
}
