package tries;

import java.util.List;

public class TrieMain {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("help");
        trie.insert("hell");
        trie.insert("mouse");
        trie.insert("computer");

        boolean isPresent = trie.search("goodbye");
        System.out.println("goodbye is in the trie: " + isPresent);

        isPresent = trie.search("mouse");
        System.out.println("mouse is in the trie: " + isPresent);

        List<String> words = trie.extract();
        System.out.println("words in dictionary: ");
        words.forEach(System.out::println);

        boolean isDeleted = trie.delete("test");
        System.out.println("test has been deleted: " + isDeleted);

        isDeleted = trie.delete("help");
        System.out.println("help has been deleted: " + isDeleted);

        words = trie.extract();
        System.out.println("words in dictionary: ");
        words.forEach(System.out::println);
    }

}
