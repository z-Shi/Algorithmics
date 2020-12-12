package tries;

public class TrieNode {

    private char letter;
    private boolean isWord;
    private TrieNode parent;
    private TrieNode sibling;
    private TrieNode child;

    public TrieNode(char letter) {
        this.letter = letter;
        this.isWord = false;
        this.parent = null;
        this.sibling = null;
        this.child = null;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public TrieNode getParent() {
        return parent;
    }

    public void setParent(TrieNode parent) {
        this.parent = parent;
    }

    public TrieNode getSibling() {
        return sibling;
    }

    public void setSibling(TrieNode sibling) {
        this.sibling = sibling;
    }

    public TrieNode getChild() {
        return child;
    }

    public void setChild(TrieNode child) {
        this.child = child;
    }

}
