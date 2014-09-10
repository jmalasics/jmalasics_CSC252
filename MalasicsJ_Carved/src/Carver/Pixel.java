package Carver;

/**
 * Created by jmalasics on 9/8/2014.
 */
public class Pixel {

    private int x;
    private int y;
    private double energy;

    public Pixel(int x, int y, double energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getEnergy() {
        return energy;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;
        if(obj instanceof Pixel) {
            Pixel otherPixel = (Pixel) obj;
            isEquals = this.x == otherPixel.x && this.y == otherPixel.y && this.energy == otherPixel.energy;
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
