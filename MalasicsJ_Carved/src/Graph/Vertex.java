package Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jmalasics on 9/8/2014.
 */
public class Vertex<T> {

    private T value;
    private boolean visited;
    private List<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
    private Color color;

    public Vertex(T value) {
        this.value = value;
    }

    public void visit() {
        visited = true;
    }

    public void resetVisited() {
        visited = false;
    }

    public T getValue() {
        return value;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean addNeighbor(Vertex neighbor) {
        boolean added = false;
        if(!neighbors.contains(neighbor)) {
            added = neighbors.add(neighbor);
        }
        return added;
    }

    public int degree() {
        return neighbors.size();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean removeNeighbor(Vertex neighbor) {
        return neighbors.remove(neighbor);
    }

    public Iterator getNeighborIterator() {
        return neighbors.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vertex && this.value.equals(((Vertex<T>)obj).value);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
