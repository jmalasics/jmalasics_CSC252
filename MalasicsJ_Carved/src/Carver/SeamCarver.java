package Carver;

import edu.neumont.ui.Picture;

import java.util.List;

/**
 * Created by jmalasics on 9/4/2014.
 */
public class SeamCarver {

    private Picture picture;
    private double[] weights;
    private double[] distTo;
    private int[] edgeTo;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture getPicture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) throws IndexOutOfBoundsException {
        if((x < 0 || x >= picture.width()) || (y < 0 || y >= picture.height())) {
            throw new IndexOutOfBoundsException();
        }
        int leftX = x - 1;
        if(leftX < 0) {
            leftX = picture.width() - 1;
        }
        int rightX = x + 1;
        if(rightX >= picture.width()) {
            rightX = 0;
        }
        int aboveY = y - 1;
        if(aboveY < 0) {
            aboveY = picture.height() - 1;
        }
        int belowY = y + 1;
        if(belowY >= picture.height()) {
            belowY = 0;
        }
        int redChange = picture.get(leftX, y).getRed() - picture.get(rightX, y).getRed();
        int greenChange = picture.get(leftX, y).getGreen() - picture.get(rightX, y).getGreen();
        int blueChange = picture.get(leftX, y).getBlue() - picture.get(rightX, y).getBlue();
        double xChange = Math.pow(redChange, 2) + Math.pow(greenChange, 2) + Math.pow(blueChange, 2);
        redChange = picture.get(x, aboveY).getRed() - picture.get(x, belowY).getRed();
        greenChange = picture.get(x, aboveY).getGreen() - picture.get(x, belowY).getGreen();
        blueChange = picture.get(x, aboveY).getBlue() - picture.get(x, belowY).getBlue();
        double yChange = Math.pow(redChange, 2) + Math.pow(greenChange, 2) + Math.pow(blueChange, 2);
        return xChange + yChange;
    }

    private int node(int col, int row) {
        return row * width() + col;
    }

    private int col(int node) {
        return node % width();
    }

    private int row(int node) {
        return node / width();
    }

    public int[] findHorizontalSeam() {
        int row, col, width = width(), height = height();
        init(node(0, 0), node(0, height - 1), width);
        for (int startRow = height - 1; startRow > -width; startRow--) {
            if (startRow >= 0) { col = 0;         row = startRow; }
            else               { col = -startRow; row = 0;        }
            for ( ; col < width - 1 && row < height; col++) {
                int v = node(col, row);
                if (row > 0)
                    relax(v, node(col + 1, row - 1));
                relax(v, node(col + 1, row));
                if (row < height - 1)
                    relax(v, node(col + 1, row + 1));
                row++;
            }
        }
        int endOfSeam = argmin(distTo, node(width - 1, 0), width * height, width);
        return horizontalPath(endOfSeam);
    }

    public double getPathValue(List<Integer> indices, boolean isHorizontal) {
        double pathValue = 0;
        int otherIndex = 0;
        for(Integer index : indices) {
            if(isHorizontal) {
                pathValue += energy(index, otherIndex);
            } else {
                pathValue += energy(otherIndex, index);
            }
            otherIndex++;
        }
        return pathValue;
    }

    public int[] findVerticalSeam() {
        int row, col, width = width(), height = height();
        init(node(0, 0), node(width - 1, 0), 1);
        for (int startCol = width - 1; startCol > -height; startCol--) {
            if (startCol >= 0) { row = 0;         col = startCol; }
            else               { row = -startCol; col = 0;        }
            for ( ; row < height - 1 && col < width; row++) {
                int v = node(col, row);
                if (col > 0)
                    relax(v, node(col - 1, row + 1));
                relax(v, node(col, row + 1));
                if (col < width - 1)
                    relax(v, node(col + 1, row + 1));
                col++;
            }
        }
        int endOfSeam = argmin(distTo, node(0, height - 1), width * height, 1);
        return verticalPath(endOfSeam);
    }

    private void init(int start, int stop, int skip) {
        int width = width(), height = height(), size = width * height;
        if (weights == null || weights.length != size) weights = new double[size];
        if (distTo == null  || distTo.length  != size) distTo  = new double[size];
        if (edgeTo == null  || edgeTo.length  != size) edgeTo  = new int[size];
        for (int v = node(0, 0); v < size; v++) {
            if (v >= start && v < stop && (v - start) % skip == 0)
                distTo[v] = 0.0;
            else
                distTo[v] = Double.POSITIVE_INFINITY;
            edgeTo[v] = -1;
            weights[v] = energy(col(v), row(v));
        }
    }

    private void relax(int from, int to) {
        if (distTo[to] > distTo[from] + weights[to]) {
            distTo[to] = distTo[from] + weights[to];
            edgeTo[to] = from;
        }
    }

    private int argmin(double[] a, int start, int stop, int skip) {
        if (stop <= start || start < 0 || a.length == 0)
            throw new IllegalArgumentException();
        double min = Double.POSITIVE_INFINITY;
        int argmin = start;
        if (stop > a.length)
            stop = a.length;
        for (int i = start; i < stop; i += skip) {
            if (a[i] < min) {
                min = a[i];
                argmin = i;
            }
        }
        return argmin;
    }

    private int[] verticalPath(int end) {
        int[] seam = new int[height()];
        seam[row(end)] = col(end);
        for (int prev = edgeTo[end]; prev >= 0; prev = edgeTo[prev])
            seam[row(prev)] = col(prev);
        return seam;
    }

    private int[] horizontalPath(int end) {
        int[] seam = new int[width()];
        seam[col(end)] = row(end);
        for (int prev = edgeTo[end]; prev >= 0; prev = edgeTo[prev])
            seam[col(prev)] = row(prev);
        return seam;
    }

    public void removeHorizontalSeam(int[] indices) throws IndexOutOfBoundsException, IllegalArgumentException {
        Picture newPic = new Picture(picture.width(), picture.height() - 1);
        int currentY = 0;
        for(int x = 0; x < picture.width(); x++) {
            for(int y = 0; y < picture.height(); y++) {
                if(!(y == indices[x])) {
                    newPic.set(x, currentY, picture.get(x, y));
                    currentY++;
                }
            }
            currentY = 0;
        }
        picture = newPic;
    }

    public void removeVerticalSeam(int[] indices) throws IndexOutOfBoundsException, IllegalArgumentException {
        Picture newPic = new Picture(picture.width() - 1, picture.height());
        int currentX = 0;
        for(int y = 0; y < picture.height(); y++) {
            for(int x = 0; x < picture.width(); x++) {
                if(!(x == indices[y])) {
                    newPic.set(currentX, y, picture.get(x, y));
                    currentX++;
                }
            }
            currentX = 0;
        }
        picture = newPic;
    }

}
