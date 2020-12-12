package compression;

import static java.util.Comparator.comparingInt;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    private Map<Character, String> huffmanCode;
    private HuffmanTreeNode root;

    public HuffmanTree() {
        this.huffmanCode = new HashMap<>();
        this.root = null;
    }

    public Map<Character, String> getHuffmanCode() {
        return huffmanCode;
    }

    public void setHuffmanCode(Map<Character, String> huffmanCode) {
        this.huffmanCode = huffmanCode;
    }

    public HuffmanTreeNode getRoot() {
        return root;
    }

    public void setRoot(HuffmanTreeNode root) {
        this.root = root;
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
    }

    public String encode(String text) {
        StringBuilder encodedText = new StringBuilder();

        for (char letter : text.toCharArray()) {
            encodedText.append(huffmanCode.get(letter));
        }

        return encodedText.toString();
    }

    public String decode(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();

        if (isLeaf(root)) {
            while (root.getFrequency() == 0) {
                decryptedText.append(root.getLetter());
            }
        } else {
            int index = -1;
            while (index < encryptedText.length() - 1) {
                index = decode(root, index, encryptedText, decryptedText);
            }
        }

        return decryptedText.toString();
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

    public int decode(HuffmanTreeNode root, int index, String path, StringBuilder decryptedText) {
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
