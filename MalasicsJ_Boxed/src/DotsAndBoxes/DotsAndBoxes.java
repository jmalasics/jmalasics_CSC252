package DotsAndBoxes;

import Graph.*;
import Traversal.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by jmalasics on 8/28/2014.
 */
public class DotsAndBoxes {

    private Graph<Box> boxes = new Graph<Box>();
    private Box[][] board;
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;
    private int sizeX;
    private int sizeY;

    public DotsAndBoxes(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        buildBoxes();
    }

    private void buildBoxes() {
        int graphSizeX = sizeX + 1;
        int graphSizeY = sizeY + 1;
        board = buildArray(graphSizeX, graphSizeY);
        buildGraph();
        int currentX;
        int currentY;
        for(currentY = 0; currentY < graphSizeY; currentY++) {
            for(currentX = 0; currentX < graphSizeX; currentX++) {
                if(currentX < graphSizeX) {
                    if(currentX - 1 > -1) {
                        connectVertices(board[currentY][currentX], board[currentY][currentX - 1]);
                    }
                    if(currentX + 1 < graphSizeX) {
                        connectVertices(board[currentY][currentX], board[currentY][currentX + 1]);
                    }
                }
                if(currentY < graphSizeY) {
                    if(currentY - 1 > -1) {
                        connectVertices(board[currentY][currentX], board[currentY - 1][currentX]);
                    }
                    if(currentY + 1 < graphSizeY) {
                        connectVertices(board[currentY][currentX], board[currentY + 1][currentX]);
                    }
                }
            }
        }
    }

    private Box[][] buildArray(int sizeX, int sizeY) {
        Box[][] board = new Box[sizeY][sizeX];
        for(int i = 0; i < sizeY; i++) {
            for(int j = 0; j < sizeX; j++) {
                if(i == 0 || j == 0 || i == sizeY - 1 || j == sizeX - 1) {
                    board[i][j] = new BorderBox(j, i);
                } else {
                    board[i][j] = new PlayableBox(j, i);
                }
            }
        }
        return board;
    }

    private void buildGraph() {
        for(int i = 0; i <= sizeY; i++) {
            for(int j = 0; j <= sizeX; j++) {
                boxes.addVertex(board[i][j]);
            }
        }
    }

    public int drawLine(int player, int x1, int y1, int x2, int y2) {
        int boxCount = 0;
        int yDifference = y1 - y2;
        int xDifference = x1 - x2;
        if(yDifference < 0 || xDifference < 0) {
            x1 += 1;
            y1 += 1;
        } else if(yDifference > 0 || xDifference > 0) {
            x2 += 1;
            y2 += 1;
        }
        Box boxOne = board[y1][x1];
        Box boxTwo = board[y2][x2];
        Vertex vertexOne = boxes.getVertex(boxOne);
        Vertex vertexTwo = boxes.getVertex(boxTwo);
        disconnectVertices(boxOne, boxTwo);
        if(!vertexOne.getNeighborIterator().hasNext()) {
            ((Box) vertexOne.getValue()).setOwner(player);
            boxCount++;
        }
        if(!vertexTwo.getNeighborIterator().hasNext()) {
            ((Box) vertexTwo.getValue()).setOwner(player);
            boxCount++;
        }
        return boxCount;
    }

    public int score(int player) {
        int score = 0;
        Iterator vertexIterator = boxes.getVertexIterator();
        while(vertexIterator.hasNext()) {
            Vertex vertex = (Vertex) vertexIterator.next();
            if(((Box) vertex.getValue()).getOwner() == player) {
                score++;
            }
        }
        return score;
    }

    public boolean anyMovesLeft() {
        Iterator edgeIterator = boxes.getEdgeIterator();
        if(!edgeIterator.hasNext()) {
            return false;
        }
        return true;
    }

    private void connectVertices(Box boxOne, Box boxTwo) {
        boxes.addEdge(boxOne, boxTwo, 1);
    }

    private void disconnectVertices(Box boxOne, Box boxTwo) {
        boxes.removeEdge(boxOne, boxTwo, 1);
    }

    public int countDoubleCrosses() {
        int doubleCrossCount = 0;
        Traversal traversal = new BFSGraphTraversal();
        List<List<Vertex>> connectedComponenets = traversal.traverse(boxes);
        for(List<Vertex> connectedComponent : connectedComponenets) {
            if(connectedComponent.size() == 2) {
                doubleCrossCount++;
            }
        }
        return doubleCrossCount;
    }

    public int countCycles() {
        int cycleCount = 0;
        Traversal traversal = new BFSGraphTraversal();
        List<List<Vertex>> connectedComponents = traversal.traverse(boxes);
        for(List<Vertex> connectedComponent : connectedComponents) {
            boolean isCycle = true;
            if(hasCycle(connectedComponent)) {
                for (Vertex vertex : connectedComponent) {
                    if (vertex.degree() != 2) {
                        isCycle = false;
                    }
                }
            } else {
                isCycle = false;
            }
            if(isCycle) {
                cycleCount++;
            }
        }
        return cycleCount;
    }

    private boolean hasCycle(List<Vertex> connectedComponent) {
        Traversal dfs = new DFSGraphTraversal();
        dfs.traverse(boxes);
        return dfs.hasCycle();
    }

    public int countOpenChains() {
        int openChainCount = 0;
        Traversal initialTraversal = new DFSGraphTraversal();
        List<List<Vertex>> connectedComponents = initialTraversal.traverse(boxes);
        boxes.resetVisitedVertices();
        for(List<Vertex> connectedComponent : connectedComponents) {
            for(Vertex vertex : connectedComponent) {
                if(vertex.degree() == 3 || vertex.degree() == 4) {
                    vertex.visit();
                    Iterator iterator = vertex.getNeighborIterator();
                    while(iterator.hasNext()) {
                        Vertex neighbor = (Vertex) iterator.next();
                        if(neighbor.degree() == 2) {
                            if(checkForChain(neighbor)) {
                                openChainCount++;
                            }
                        }
                    }
                }
            }
        }
        return openChainCount;
    }

    private boolean checkForChain(Vertex start) {
        boolean isChain = false;
        int numInChain = 1;
        start.visit();
        Vertex current = unVisited(start);
        if(current != null) {
            while (current.degree() == 2 && !current.isVisited() && !(current.getValue() instanceof BorderBox)) {
                numInChain++;
                current.visit();
                current = unVisited(current);
            }
        }
        if(numInChain >= 3) {
            isChain = true;
        }
        return isChain;
    }

    private Vertex unVisited(Vertex vertex) {
        Iterator iterator = vertex.getNeighborIterator();
        while(iterator.hasNext()) {
            Vertex neighbor = (Vertex) iterator.next();
            if(!neighbor.isVisited()) {
                return neighbor;
            }
        }
        return null;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

}
