package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

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

    public Map<Integer, Integer> dijkstrasShortestPath(int startingIndex) {
        Map<Integer, Integer> distances = new HashMap<>();
        Vertex startVertex = vertices[startingIndex];

        List<Integer> knownShortestPaths = new ArrayList<>();
        knownShortestPaths.add(startingIndex);

        for (Vertex vertex : vertices) {
            if (vertex.getIndex() == startingIndex) {
                continue;
            }

            int weight = getWeightOfEdgeBetween(startVertex, vertex);
            distances.put(vertex.getIndex(), weight);
        }

        while (knownShortestPaths.size() != numOfVertices) {
            Vertex minimumVertex = null;

            for (Vertex vertex : vertices) {
                int vertexIndex = vertex.getIndex();

                if (!knownShortestPaths.contains(vertexIndex)) {
                    if (minimumVertex == null) {
                        minimumVertex = vertex;
                        continue;
                    }

                    if (distances.get(vertexIndex) < distances.get(vertexIndex)) {
                        minimumVertex = vertex;
                    }
                }
            }

            if (minimumVertex == null) {
                break;
            }

            knownShortestPaths.add(minimumVertex.getIndex());

            for (Vertex vertex : vertices) {
                int vertexIndex = vertex.getIndex();

                if ((!knownShortestPaths.contains(vertexIndex)) &&
                        (minimumVertex.containsNodeInAdjacencyList(vertexIndex))) {
                    int relaxedDistance = min(distances.get(vertexIndex),
                            distances.get(vertexIndex) + getWeightOfEdgeBetween(minimumVertex, vertex));
                    distances.put(vertex.getIndex(), relaxedDistance);
                }
            }
        }

        return distances;
    }

    private int getWeightOfEdgeBetween(Vertex startVertex, Vertex endVertex) {
        int weightBetween = Integer.MAX_VALUE;

        for (AdjacencyListNode node : startVertex.getAdjacencyListNodes()) {
            if (node.getIndex() == endVertex.getIndex()) {
                weightBetween = node.getWeight();
                break;
            }
        }

        return weightBetween;
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
