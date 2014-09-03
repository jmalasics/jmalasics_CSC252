package DotsAndBoxes;

/**
 * Created by jmalasics on 8/28/2014.
 */
public abstract class Box {

    private int x;
    private int y;
    private int owner;

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setOwner(int player) {
        owner = player;
    }

    public int getOwner() {
        return owner;
    }

}
