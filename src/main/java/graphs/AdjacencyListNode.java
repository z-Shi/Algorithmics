package graphs;

public class AdjacencyListNode {

    private int index;
    private int weight;

    public AdjacencyListNode(int index) {
        this.index = index;
    }

    public AdjacencyListNode(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
