package Sorting;

import Graph.Graph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TopologicalSortTest {

    @Test
    public void testSort() throws Exception {
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 20, 10);
        graph.addEdge(10, 30, 40);
        graph.addEdge(20, 30, 10);
        TopologicalSort<Integer> sort = new TopologicalSort<Integer>();
        List<Integer> sorted = sort.sort(graph);
        for(Integer i : sorted) {
            System.out.println(i);
        }
    }
}