package graphs;

import static java.lang.Integer.parseInt;
import static util.Utility.getFileFromResourceAsStream;
import static util.Utility.readFromInputStream;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GraphMain {

    public static void main(String[] args) throws IOException {
        List<String> lines = readFromInputStream(getFileFromResourceAsStream("graphs/graph.txt"));

        if (lines.size() < 2) {
            System.out.println("Error! Not enough lines in file to create a graph");
        }

        int numOfVertices = parseInt(lines.remove(0));
        Graph graph = new Graph(numOfVertices);

        int vertex = 0;
        for (String line : lines) {
            int edgeVertexNo = 0;
            int edgeNo = 0;
            String[] edges = line.split(" ");

            for (String edge : edges) {
                if (!edge.equals("0")) {
                    graph.getVertex(vertex).addNodeToAdjacencyList(edgeVertexNo);
                    graph.getVertex(vertex).getAdjacencyListNodes().get(edgeNo).setWeight(parseInt(edge));

                    edgeNo++;
                }

                edgeVertexNo++;
            }

            vertex++;
        }

        System.out.println("\nInitial Graph State: ");
        outputGraphState(graph);

        graph.depthFirstSearch();

        System.out.println("\nDepth-First Search Graph State: ");
        outputGraphState(graph);

        graph.breadthFirstSearch();

        System.out.println("\nBreadth-First Search Graph State: ");
        outputGraphState(graph);

        int startIndex = 0;
        Map<Integer, Integer> shortestDistances = graph.dijkstrasShortestPath(startIndex);
        System.out.println("\nShortest Distances: ");

        for (Map.Entry<Integer, Integer> entry : shortestDistances.entrySet()) {
            System.out.println("Shortest Distance from " + startIndex + " -> " + entry.getKey() + " is: " + entry.getValue());
        }

        PrimJarnik primJarnik = new PrimJarnik(graph);
        Map<String, Integer> minimumSpanningTree = primJarnik.standardPrimJarnik();
        System.out.println("\nMinimum Spanning Tree:");

        for (Map.Entry<String, Integer> entry : minimumSpanningTree.entrySet()) {
            System.out.println("Edge from: " + entry.getKey() + " with Weight: " + entry.getValue());
        }

        graph.topologicalOrdering();

        System.out.println("\nTopological Ordering: ");
        outputGraphState(graph);
    }

    private static void outputGraphState(Graph graph) {
        for (Vertex vertex : graph.getVertices()) {
            System.out.println(vertex.toString());
        }
    }

}
