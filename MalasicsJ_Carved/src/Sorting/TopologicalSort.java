package Sorting;

import Graph.Graph;

import java.util.*;

import Graph.Edge;

/**
 * Created by jmalasics on 9/8/2014.
 */
public class TopologicalSort<T> {

    public List<T> sort(Graph<T> graph) {
        Graph<T> reverse = reverseGraph(graph);
        List<T> result = new ArrayList<T>();
        Set<T> visited = new HashSet<T>();
        Set<T> expanded = new HashSet<T>();
        Iterator iterator = graph.getVertexIterator();
        while(iterator.hasNext()) {
            T value = (T) iterator.next();
            explore(value, reverse, result, visited, expanded);
        }
        return result;
    }

    private void explore(T value, Graph<T> graph, List<T> ordering, Set<T> visited, Set<T> expanded) {
        if(graph.getVertex(value).isVisited()) {
            if(expanded.contains(value)) return;
            throw new IllegalArgumentException("Graph contains a cycle.");
        }
        visited.add(value);
        Iterator iterator = graph.getVertex(value).getNeighborIterator();
        while(iterator.hasNext()) {
            T predecessor = (T) iterator.next();
            explore(predecessor, graph, ordering, visited, expanded);
        }
        ordering.add(value);
        expanded.add(value);
    }

    private Graph<T> reverseGraph(Graph<T> graph) {
        Graph<T> result = new Graph<T>();
        Iterator iterator = graph.getVertexIterator();
        while(iterator.hasNext()) {
            result.addVertex((T) iterator.next());
        }
        iterator = graph.getEdgeIterator();
        while(iterator.hasNext()) {
            Edge edge = (Edge) iterator.next();
            result.addEdge((T) edge.getVertexTwo().getValue(), (T) edge.getVertexOne().getValue(), edge.getWeight());
        }
        return result;
    }

}
