package ai.cell;

public class Cell {

    private int x;
    private int y;
    private boolean finish;
    private boolean start;
    private boolean wall;
    private boolean free;
    private boolean wrong_way;
    private boolean currently_used;
    private int value;

    public boolean isCurrently_used() {
        return currently_used;
    }

    public void setCurrently_used(boolean currently_used) {
        this.currently_used = currently_used;
    }

    public boolean isWrong_way() {
        return wrong_way;
    }

    public void setWrong_way(boolean wrong_way) {
        this.wrong_way = wrong_way;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

}
