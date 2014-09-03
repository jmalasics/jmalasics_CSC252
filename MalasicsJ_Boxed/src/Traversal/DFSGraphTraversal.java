package Traversal;

import Graph.Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import Graph.*;

/**
 * Created by jmalasics on 8/26/2014.
 */
public class DFSGraphTraversal extends Traversal {

    @Override
    public List<List<Vertex>> traverse(Graph g) {
        List<List<Vertex>> adjacencyList = new ArrayList<List<Vertex>>();
        g.resetVisitedVertices();
        Iterator vertexIterator = g.getVertexIterator();
        while(vertexIterator.hasNext()) {
            Vertex vertex = (Vertex) vertexIterator.next();
            if(!vertex.isVisited()) {
                adjacencyList.add(depthFirstSearch(vertex));
            }
        }
        return adjacencyList;
    }

    private List<Vertex> depthFirstSearch(Vertex vertex) {
        Vertex parent = null;
        Stack<Vertex> stack = new Stack<Vertex>();
        stack.push(vertex);
        vertex.visit();
        List<Vertex> neighborsList = new ArrayList<Vertex>();
        //printVertex(vertex);
        while(!stack.isEmpty()) {
            Vertex v = stack.peek();
            Vertex child = getUnvisitedChildVertex(v, parent);
            if(child != null) {
                child.visit();
                stack.push(child);
            }
            else {
                Vertex poppedVertex = stack.pop();
                neighborsList.add(poppedVertex);
                parent = poppedVertex;
            }
        }
        return neighborsList;
    }

}
