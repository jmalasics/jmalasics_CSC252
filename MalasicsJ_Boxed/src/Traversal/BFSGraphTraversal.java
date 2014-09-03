package Traversal;

import Graph.*;

import java.util.*;

/**
 * Created by jmalasics on 8/26/2014.
 */
public class BFSGraphTraversal extends Traversal {

    @Override
    public List<List<Vertex>> traverse(Graph g) {
        List<List<Vertex>> adjacencyList = new ArrayList<List<Vertex>>();
        g.resetVisitedVertices();
        Iterator vertexIterator = g.getVertexIterator();
        while(vertexIterator.hasNext()) {
            Vertex vertex = (Vertex) vertexIterator.next();
            if(!vertex.isVisited()) {
                adjacencyList.add(breadthFirstSearch(vertex));
            }
        }
        return adjacencyList;
    }

    public List<Vertex> breadthFirstSearch(Vertex vertex) {
        Vertex parent = null;
        List<Vertex> polledList = new ArrayList<Vertex>();
        Queue<Vertex> queue = new LinkedList<Vertex>();
        queue.add(vertex);
        //printVertex(vertex);
        vertex.visit();
        while(!queue.isEmpty()) {
            Vertex v = queue.remove();
            polledList.add(v);
            Vertex child = getUnvisitedChildVertex(v, parent);
            while(child != null) {
                child.visit();
                queue.add(child);
                child = getUnvisitedChildVertex(v, parent);
            }
            parent = v;
        }
        return polledList;
    }

}
