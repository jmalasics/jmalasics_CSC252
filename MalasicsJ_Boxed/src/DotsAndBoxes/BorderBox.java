package DotsAndBoxes;

/**
 * Created by jmalasics on 8/28/2014.
 */
public class BorderBox extends Box {

    public BorderBox(int x, int y) {
        super(x, y);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BorderBox) {
            BorderBox box = (BorderBox) obj;
            return this.getX() == box.getX() && this.getY() == box.getY() && this.getOwner() == box.getOwner();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Border Box : x = " + this.getX() + " y = " + this.getY();
    }

}
