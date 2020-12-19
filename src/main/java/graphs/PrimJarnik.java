package graphs;

import java.util.ArrayList;
import java.util.List;

import static graphs.Graph.getWeightOfEdgeBetween;
import static java.util.Arrays.asList;

public class PrimJarnik {

    private List<Vertex> treeVertices;
    private List<Vertex> nonTreeVertices;

    public PrimJarnik(Graph graph) {
        this.treeVertices = new ArrayList<>();
        this.nonTreeVertices = new ArrayList<>(asList(graph.getVertices()));
    }

    public List<Vertex> getMinimumSpanningTree() {
        if (nonTreeVertices.size() < 1) {
            return treeVertices;
        }

        treeVertices.add(nonTreeVertices.remove(0));

        while (nonTreeVertices.size() > 0) {
            int minimumEdgeWeight = Integer.MAX_VALUE;
            Vertex minimumVertex = null;

            for (Vertex nonTreeVertex : nonTreeVertices) {
                for (Vertex treeVertex : treeVertices) {
                    if (getWeightOfEdgeBetween(treeVertex, nonTreeVertex) < minimumEdgeWeight) {
                        minimumVertex = nonTreeVertex;
                    } else {
                        nonTreeVertex.removeNodeFromAdjacencyList(treeVertex.getIndex());
                    }
                }
            }

            treeVertices.add(minimumVertex);
            nonTreeVertices.remove(minimumVertex);
        }

        return treeVertices;
    }

}
