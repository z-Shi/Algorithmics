package tries;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode(Character.MIN_VALUE);
    }

    public void insert(String word) {
        int position = 0;
        TrieNode current = root;
        TrieNode next = current.getChild();
        TrieNode previous = null;

        while (position < word.length()) {
            if ((next != null) && next.getLetter() == word.charAt(position)) {
                current = next;
                next = current.getChild();
                previous = null;
                position++;
            } else if (next != null) {
                if (next.getLetter() < word.charAt(position)) {
                    previous = next;
                    next = next.getSibling();
                } else {
                    TrieNode node = new TrieNode(word.charAt(position));
                    if (previous != null) {
                        previous.setSibling(node);
                    } else {
                        current.setChild(node);
                    }
                    node.setParent(next.getParent());
                    node.setSibling(next);
                    position++;
                    current = node;
                    next = null;
                    previous = null;
                }
            } else {
                TrieNode node = new TrieNode(word.charAt(position));
                if (previous != null) {
                    previous.setSibling(node);
                } else {
                    current.setChild(node);
                }
                node.setParent(current);
                position++;
                current = node;
                next = null;
                previous = null;
            }
        }

        if (word.length() > 0) {
            current.setWord(true);
        }
    }

    public boolean search(String word) {
        int position = 0;
        TrieNode current = root.getChild();
        boolean found = false;
        boolean searchInProgress = true;

        while (searchInProgress) {
            if (current == null) {
                searchInProgress = false;
            } else if (current.getLetter() == word.charAt(position)) {
                if ((position == word.length() - 1) && (current.isWord())) {
                    found = true;
                    searchInProgress = false;
                } else {
                    current = current.getChild();
                    position++;
                }
            } else {
                current = current.getSibling();
            }
        }

        return found;
    }

    public boolean delete(String word) {
        int position = 0;
        TrieNode current = root.getChild();
        boolean found = false;
        boolean searchInProgress = true;

        while (searchInProgress) {
            if (current == null) {
                searchInProgress = false;
            } else if (current.getLetter() == word.charAt(position)) {
                if ((position == word.length() - 1) && (current.isWord())) {
                    found = true;
                    searchInProgress = false;
                } else {
                    current = current.getChild();
                    position++;
                }
            } else {
                current = current.getSibling();
            }
        }

        if (!found) {
            return false;
        }

        if (current.getChild() != null) {
            current.setWord(false);
            return true;
        }

        TrieNode parent = current.getParent();

        while ((parent != null) && (!parent.isWord())) {
            if (parent.getChild() != current) {
                TrieNode child = parent.getChild();

                while (child.getSibling() != current) {
                    child = child.getSibling();
                }

                TrieNode sibling = child.getSibling();
                child.setSibling(sibling.getSibling());

                break;
            }
            parent.setChild(null);
            current = parent;
            parent = current.getParent();
        }

        return true;
    }

    public List<String> extract() {
        List<String> words = new ArrayList<>();
        words = getWords(root,"", words);
        return words;
    }

    private List<String> getWords(TrieNode node, String word, List<String> words) {
        word += node.getLetter();

        if (node.isWord()) {
            words.add(word);
        }

        for (TrieNode child : getChildren(node)) {
            words = getWords(child, word, words);
        }

        return words;
    }

    private List<TrieNode> getChildren(TrieNode node) {
        List<TrieNode> children = new ArrayList<>();
        TrieNode child = node.getChild();

        if (child != null) {
            children.add(child);

            while (child.getSibling() != null) {
                child = child.getSibling();
                children.add(child);
            }
        }

        return children;
    }

}
