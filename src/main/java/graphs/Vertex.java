package graphs;

import java.util.LinkedList;

public class Vertex {

    private int index;
    private boolean visited;
    private int predecessor;
    private LinkedList<AdjacencyListNode> adjacencyListNodes;

    public Vertex(int index) {
        this.index = index;
        adjacencyListNodes = new LinkedList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean wasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }

    public LinkedList<AdjacencyListNode> getAdjacencyListNodes() {
        return adjacencyListNodes;
    }

    public void addNodeToAdjacencyList(int index) {
        adjacencyListNodes.addLast(new AdjacencyListNode(index));
    }

    public int getDegree() {
        return adjacencyListNodes.size();
    }

}
