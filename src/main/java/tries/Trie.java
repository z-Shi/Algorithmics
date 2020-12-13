package tries;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode(Character.MIN_VALUE);
    }

    /**
     * This is a method used to insert a word into a trie
     * @param word this is a word to be inserted
     */
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

    /**
     * This is a method used to check whether a word is in the trie
     * @param word this is the word to search for
     * @return boolean this is the result, whether the word is in the trie
     */
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

    /**
     * This is a method used to delete a word from the trie
     * @param word this is the word to delete
     * @return boolean this is whether the word has been deleted from the trie
     */
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

    /**
     * This is a method used to get all of the words found in the trie
     * @return list this is the list of words in the trie
     */
    public List<String> extract() {
        List<String> words = new ArrayList<>();
        words = getWords(root,"", words);
        return words;
    }

    /**
     * This is a method used to get the words from the tree, it is a recursive method
     * @param node this is the node we get words from
     * @param word this is the word we are finding
     * @param words this is the list of words found so far
     * @return list this is the list of words from the trie
     */
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

    /**
     * This is a method used to get the children, given a node, by following along the siblings
     * @param node this is the node we are to get children from
     * @return list this is the list of children
     */
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
