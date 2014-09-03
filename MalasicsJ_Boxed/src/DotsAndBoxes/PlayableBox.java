package DotsAndBoxes;

/**
 * Created by jmalasics on 8/28/2014.
 */
public class PlayableBox extends Box {

    public PlayableBox(int x, int y) {
        super(x, y);
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PlayableBox) {
            PlayableBox box = (PlayableBox) obj;
            return this.getX() == box.getX() && this.getY() == box.getY() && this.getOwner() == box.getOwner();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Playable Box : x = " + this.getX() + " y = " + this.getY();
    }

}
