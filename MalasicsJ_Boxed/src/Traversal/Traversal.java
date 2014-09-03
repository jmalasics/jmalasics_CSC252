package Traversal;

import Graph.Graph;

import java.util.Iterator;
import java.util.List;
import Graph.*;

/**
 * Created by jmalasics on 8/26/2014.
 */
public abstract class Traversal {

    private boolean hasCycle = false;

    public abstract List<List<Vertex>> traverse(Graph g);

    protected void printVertex(Vertex vertx) {
        System.out.print(vertx.getValue().toString() + ", ");
    }

    protected Vertex getUnvisitedChildVertex(Vertex vertex, Vertex parent) {
        Iterator vertices = vertex.getNeighborIterator();
        while(vertices.hasNext()) {
            Vertex child = (Vertex) vertices.next();
            if(!child.isVisited()) {
                return child;
            } else {
                if(!child.equals(parent)) {
                    hasCycle = true;
                }
            }
        }
        return null;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
