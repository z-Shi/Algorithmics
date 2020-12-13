package compression;

import static java.util.Comparator.comparingInt;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    private Map<Character, String> huffmanCode;
    private HuffmanTreeNode root;
    private int weightedPathLength;

    public HuffmanTree() {
        this.huffmanCode = new HashMap<>();
        this.root = null;
        this.weightedPathLength = 0;
    }

    public Map<Character, String> getHuffmanCode() {
        return huffmanCode;
    }

    public int getWeightedPathLength() {
        return weightedPathLength;
    }

    public void buildHuffmanTree(String text) {
        if ((text == null) || (text.length() == 0)) {
            return;
        }

        Map<Character, Integer> frequencies = new HashMap<>();
        for (char chara : text.toCharArray()) {
            frequencies.put(chara, frequencies.getOrDefault(chara, 0) + 1);
        }

        PriorityQueue<HuffmanTreeNode> priorityQueue = new PriorityQueue<>(comparingInt(HuffmanTreeNode::getFrequency));

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanTreeNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() != 1) {
            HuffmanTreeNode left = priorityQueue.poll();
            HuffmanTreeNode right = priorityQueue.poll();

            int sum = left.getFrequency() + right.getFrequency();
            priorityQueue.add(new HuffmanTreeNode('\0', sum, left, right));
        }

        root = priorityQueue.peek();

        prepareHuffmanCode(root, "");

        for(Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            int valueToAdd = frequencies.get(entry.getKey()) * entry.getValue().length();
            weightedPathLength += valueToAdd;
        }
    }

    public String encode(String text) {
        StringBuilder encodedText = new StringBuilder();

        for (char letter : text.toCharArray()) {
            encodedText.append(huffmanCode.get(letter));
        }

        return encodedText.toString();
    }

    public String decode(String encodedText) {
        StringBuilder decodedText = new StringBuilder();

        if (isLeaf(root)) {
            while (root.getFrequency() == 0) {
                decodedText.append(root.getLetter());
            }
        } else {
            int index = -1;
            while (index < encodedText.length() - 1) {
                index = decode(root, index, encodedText, decodedText);
            }
        }

        return decodedText.toString();
    }

    private void prepareHuffmanCode(HuffmanTreeNode root, String path) {
        if (root == null) {
            return;
        }

        if (isLeaf(root)) {
            huffmanCode.put(root.getLetter(), path.length() > 0 ? path : "1");
        }

        prepareHuffmanCode(root.getLeft(), path + '0');
        prepareHuffmanCode(root.getRight(), path + '1');
    }

    private int decode(HuffmanTreeNode root, int index, String path, StringBuilder decryptedText) {
        if (root == null) {
            return index;
        }

        if (isLeaf(root)) {
            decryptedText.append(root.getLetter());
            return index;
        }

        index++;

        root = (path.charAt(index) == '0') ? root.getLeft() : root.getRight();
        index = decode(root, index, path, decryptedText);
        return index;
    }

    private boolean isLeaf(HuffmanTreeNode root) {
        return (root.getLeft() == null) && (root.getRight() == null);
    }

}
