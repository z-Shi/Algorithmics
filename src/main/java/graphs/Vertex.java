package graphs;

import java.util.LinkedList;

public class Vertex {

    private int index;
    private boolean visited;
    private int predecessor;
    private int label;
    private int count;
    private LinkedList<AdjacencyListNode> adjacencyListNodes;

    public Vertex(int index) {
        this.index = index;
        this.label = -1;
        this.count = 0;
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

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LinkedList<AdjacencyListNode> getAdjacencyListNodes() {
        return adjacencyListNodes;
    }

    public boolean containsNodeInAdjacencyList(int index) {
        boolean containsNode = false;

        for (AdjacencyListNode node : adjacencyListNodes) {
            if (node.getIndex() == index) {
                containsNode = true;
                break;
            }
        }

        return containsNode;
    }

    public void addNodeToAdjacencyList(int index) {
        adjacencyListNodes.addLast(new AdjacencyListNode(index));
        count++;
    }

    public void removeNodeFromAdjacencyList(int index) {
        AdjacencyListNode nodeToRemove = null;

        for (AdjacencyListNode node : adjacencyListNodes) {
            if (node.getIndex() == index) {
                nodeToRemove = node;
                break;
            }
        }

        if (nodeToRemove != null) {
            adjacencyListNodes.remove(nodeToRemove);
            count--;
        }
    }

    public int getDegree() {
        return adjacencyListNodes.size();
    }

    @Override
    public String toString() {
        StringBuilder vertexInfo = new StringBuilder();

        vertexInfo.append("Vertex [").append(index).append("] \n");
        vertexInfo.append("  Visited [").append(visited).append("] \n");
        vertexInfo.append("  Predecessor [").append(predecessor).append("] \n");
        vertexInfo.append("  Count [").append(count).append("] \n");
        vertexInfo.append("  Label [").append(label).append("] \n");
        vertexInfo.append("  Adjacent To: [ \n");

        for (AdjacencyListNode node : getAdjacencyListNodes()) {
            vertexInfo.append("    Index: [").append(node.getIndex()).append("] ");
            vertexInfo.append("Edge Weight: [").append(node.getWeight()).append("] \n");
        }

        vertexInfo.append("  ] \n");

        return vertexInfo.toString();
    }

}
