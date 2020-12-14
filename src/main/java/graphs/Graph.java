package graphs;

import java.util.LinkedList;

public class Graph {

    private int numOfVertices;
    private Vertex[] vertices;

    public Graph(int numOfVertices) {
        this.numOfVertices = numOfVertices;
        this.vertices = new Vertex[numOfVertices];

        for (int i = 0; i < numOfVertices; i++) {
            vertices[i] = new Vertex(i);
        }
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    /**
     * This method allows a depth first search to occur on the specified graph.
     * Makes use of a helper method, visit.
     */
    public void depthFirstSearch() {
        setAllVerticesUnvisited();

        for (Vertex vertex : vertices) {
            if (!vertex.wasVisited()) {
                visit(vertex, vertex.getIndex());
            }
        }
    }

    /**
     * This method allows a breadth first search to occur on the specified graph.
     */
    public void breadthFirstSearch() {
        LinkedList<Vertex> unprocessedVertices = new LinkedList<>();

        setAllVerticesUnvisited();

        for (Vertex vertex : vertices) {
            if (!vertex.wasVisited()) {
                vertex.setVisited(true);
                vertex.setPredecessor(vertex.getIndex());

                unprocessedVertices.add(vertex);

                while (!unprocessedVertices.isEmpty()) {
                    Vertex unprocessedVertex = unprocessedVertices.removeFirst();

                    for (AdjacencyListNode node : unprocessedVertex.getAdjacencyListNodes()) {
                        Vertex nodeVertex = vertices[node.getIndex()];

                        if (!nodeVertex.wasVisited()) {
                            nodeVertex.setVisited(true);
                            nodeVertex.setPredecessor(unprocessedVertex.getIndex());
                            unprocessedVertices.add(nodeVertex);
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is used to visit a given vertex, and then visit all adjacent nodes.
     * It is used in the depth first search.
     * @param vertex this is the given vertex
     * @param predecessor this is the predecessor node's index, if a new component, it is the given vertex's index
     */
    private void visit(Vertex vertex, int predecessor) {
        LinkedList<AdjacencyListNode> adjacencyListNodes = vertex.getAdjacencyListNodes();

        vertex.setVisited(true);
        vertex.setPredecessor(predecessor);

        for (AdjacencyListNode node : adjacencyListNodes) {
            int index = node.getIndex();

            if (!vertices[index].wasVisited()) {
                visit(vertices[index], vertex.getIndex());
            }
        }
    }

    /**
     * This method is used to set the visited status of all vertices to unvisited.
     */
    private void setAllVerticesUnvisited() {
        for (Vertex vertex : vertices) {
            vertex.setVisited(false);
        }
    }

}
