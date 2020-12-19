package graphs;

import static java.lang.Integer.parseInt;
import static util.Utility.getFileFromResourceAsStream;
import static util.Utility.readFromInputStream;

import java.io.IOException;
import java.util.List;

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

        System.out.println("Initial Graph State: ");
        outputGraphState(graph);
    }

    private static void outputGraphState(Graph graph) {
        for (Vertex vertex : graph.getVertices()) {
            StringBuilder vertexInfo = new StringBuilder();

            vertexInfo.append("Vertex [").append(vertex.getIndex()).append("] ");
            vertexInfo.append("Visited [").append(vertex.wasVisited()).append("] ");
            vertexInfo.append("Adjacent To: [");

            for (AdjacencyListNode node : vertex.getAdjacencyListNodes()) {
                vertexInfo.append("Index: [").append(node.getIndex()).append("] ");
                vertexInfo.append("Edge Weight: [").append(node.getWeight()).append("] ");
            }

            vertexInfo.append("]");
            vertexInfo.append("\n");

            System.out.print(vertexInfo.toString());
        }
    }

}
