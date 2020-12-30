package graphs;

import static graphs.Graph.getWeightOfEdgeBetween;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimJarnik {

    private Graph graph;
    private List<Vertex> treeVertices;
    private Map<String, Integer> minimumSpanningTree;
    private List<Vertex> nonTreeVertices;

    public PrimJarnik(Graph graph) {
        this.graph = graph;
        prepareVariables();
    }

    public void resetPrimJarnik() {
        prepareVariables();
    }

    public Map<String, Integer> standardPrimJarnik() {
        if (nonTreeVertices.size() < 1) {
            return minimumSpanningTree;
        }

        treeVertices.add(nonTreeVertices.remove(0));

        while (nonTreeVertices.size() > 0) {
            int minimumEdgeWeight = Integer.MAX_VALUE;
            Vertex minimumVertex = null;
            Vertex connectingVertex = null;

            for (Vertex nonTreeVertex : nonTreeVertices) {
                for (Vertex treeVertex : treeVertices) {
                    int edgeWeight = getWeightOfEdgeBetween(treeVertex, nonTreeVertex);
                    if (edgeWeight < minimumEdgeWeight) {
                        connectingVertex = treeVertex;
                        minimumVertex = nonTreeVertex;
                        minimumEdgeWeight = edgeWeight;
                    } else {
                        nonTreeVertex.removeNodeFromAdjacencyList(treeVertex.getIndex());
                    }
                }
            }

            treeVertices.add(minimumVertex);

            String edge = connectingVertex.getIndex() + " -> " + minimumVertex.getIndex();
            minimumSpanningTree.put(edge, minimumEdgeWeight);

            nonTreeVertices.remove(minimumVertex);
        }

        return minimumSpanningTree;
    }

    // todo
    public void dijkstrasRefinement() {
        // set an arbitrary vertex r to be a tree-vertex (tv);
        // set all other vertices to be non-tree-vertices (ntv);
        // for (each ntv s) set s.bestTV = r; // r is the only tv
        // while (number of ntv > 0){
        //   find ntv q for which wt({q, q.bestTV}) is minimal;
        //   adjoin {q, q.bestTV} to the tree;
        //   make q a tv;
        //   for (each ntv s) update s.bestTV;
        //   // update bestTV as tree vertices have changed
        // }
    }

    private void prepareVariables() {
        this.treeVertices = new ArrayList<>();
        this.minimumSpanningTree = new HashMap<>();
        this.nonTreeVertices = new ArrayList<>(asList(graph.getVertices()));
    }

}
