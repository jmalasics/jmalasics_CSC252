package Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jmalasics on 9/8/2014.
 */
public class Graph<T> {

    private List<Vertex<T>> vertices = new ArrayList<Vertex<T>>();
    private List<Edge> edges = new ArrayList<Edge>();
    private int numVertices = 0;
    private int numEdges = 0;

        public boolean addVertex(T value) {
            boolean added = false;
            Vertex<T> newVertex = new Vertex<T>(value);
            if(!vertices.contains(newVertex)) {
                added = vertices.add(newVertex);
                numVertices++;
            }
            return added;
        }

        public boolean addEdge(T valueOne, T valueTwo, int weight) {
            boolean added = false;
            Vertex<T> vertexOne = getVertex(valueOne);
            Vertex<T> vertexTwo = getVertex(valueTwo);
            Edge edge = new Edge(vertexOne, vertexTwo, weight);
            if(!edges.contains(edge)) {
                if(edges.add(edge)) {
                    vertexOne.addNeighbor(vertexTwo);
                    added = true;
                    numEdges++;
                }
            }
            return added;
        }

        public boolean removeEdge(T valueOne, T valueTwo, int weight) {
            boolean removed = false;
            Vertex<T> vertexOne = getVertex(valueOne);
            Vertex<T> vertexTwo = getVertex(valueTwo);
            Edge edge = new Edge(vertexOne, vertexTwo, weight);
            if(edges.contains(edge)) {
                if(edges.remove(edge)) {
                    vertexOne.removeNeighbor(vertexTwo);
                    removed = true;
                    numEdges--;
                }
            }
            return removed;
        }

        public void visitVertex(Vertex vertex) {
            vertex.visit();
        }

        public void visitEdge(Edge edge) {
            edge.visit();
        }

        public Vertex<T> getVertex(T value) {
            Vertex<T> vertex = new Vertex<T>(value);
            if(vertices.contains(vertex)) {
                return vertices.get(vertices.indexOf(vertex));
            }
            return null;
        }

        public void resetVisitedEdges() {
            for(Edge edge : edges) {
                edge.resetVisited();
            }
        }

        public void resetVisitedVertices() {
            for(Vertex vertex : vertices) {
                vertex.resetVisited();
            }
        }

        public int degree(Vertex vertex) {
            return vertex.degree();
        }

        public Color getMark(Vertex vertex) {
            return vertex.getColor();
        }

        public void setMark(Vertex vertex, Color color) {
            vertex.setColor(color);
        }

        public Iterator getVertexIterator() {
            return vertices.iterator();
        }

        public Iterator getEdgeIterator() {
            return edges.iterator();
        }

        public int getNumVertices() {
            return numVertices;
        }

        public int getNumEdges() {
            return numEdges;
        }

}
