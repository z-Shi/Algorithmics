package compression;

public class CompressionMain {

    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree("This is a good day!");

        String encodedText = huffmanTree.encode("This is a good day!");
        String text = huffmanTree.decode(encodedText);
        int weightedPathLength = huffmanTree.getWeightedPathLength();

        System.out.println("Huffman Tree: " + huffmanTree.getHuffmanCode().toString());
        System.out.println("Encoded Text: " + encodedText);
        System.out.println("Decoded Text: " + text);
        System.out.println("Weighted Path Length: " + weightedPathLength);

        text = "GACGATACGATACG";
        LZW lzw = new LZW(text);

        encodedText = lzw.encode(text);
        System.out.println("Dictionary: " + lzw.getBaseDictionary().toString());
        System.out.println("Encoded Text: " + encodedText);
        System.out.println("Compressed Size: " + encodedText.length());

        text = lzw.decode(encodedText);
        System.out.println("Decoded Text: " + text);
    }

}
