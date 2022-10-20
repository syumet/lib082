package ds;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        root.insert(word);
    }

    public boolean search(String word) {
        return root.search(word);
    }

    public boolean startsWith(String prefix) {
        return root.startsWith(prefix);
    }

}

class TrieNode {

    private final Map<Character, TrieNode> children;
    private boolean isStop;

    TrieNode() {
        isStop = false;
        children = new HashMap<>();
    }

    public void insert(String word) {
        if (word.isEmpty()) {
            isStop = true;
            return;
        }
        char c = word.charAt(0);
        if (!children.containsKey(c)) {
            children.put(c, new TrieNode());
        }
        children.get(c).insert(word.substring(1));
    }

    public boolean search(String word) {
        if (word.isEmpty()) {
            return isStop;
        }
        char c = word.charAt(0);
        if (!children.containsKey(c)) {
            return false;
        }
        return children.get(c).search(word.substring(1));
    }

    public boolean startsWith(String prefix) {
        if (prefix.isEmpty()) {
            return true;
        }
        char c = prefix.charAt(0);
        if (!children.containsKey(c)) {
            return false;
        }
        return children.get(c).startsWith(prefix.substring(1));
    }
}
