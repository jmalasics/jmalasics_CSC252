package Graph;

/**
 * Created by jmalasics on 8/26/2014.
 */
public class Edge {

    private Vertex vertexOne;
    private Vertex vertexTwo;
    private int weight;
    private boolean visited;

    public Edge(Vertex vertexOne, Vertex vertexTwo, int weight) {
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
        this.weight = weight;
    }

    public Vertex getVertexOne() {
        return vertexOne;
    }

    public Vertex getVertexTwo() {
        return vertexTwo;
    }

    public int getWeight() {
        return weight;
    }

    public void visit() {
        visited = true;
    }

    public void resetVisited() {
        visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj != null && obj instanceof Edge) {
            Edge otherEdge = (Edge) obj;
            isEqual = this.vertexOne.equals(otherEdge.getVertexOne()) && this.vertexTwo.equals(otherEdge.getVertexTwo());
        }
        return isEqual;
    }

}
