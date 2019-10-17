package bearmaps.lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private TrieNode root;

    private class TrieNode {
        private boolean isKey;
        private HashMap<Character, TrieNode> next;
    }

    public MyTrieSet() {
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean contains(String key) {
        return contains(key, root, 0);
    }

    private boolean contains(String key, TrieNode x, int d) {
        if (x == null) {return false;}
        if (d == key.length()) {
            return x.isKey;
        }
        if (x.next == null) {return false;}
        Character c = key.charAt(d);
        return (contains(key, x.next.get(c) ,d + 1));
    }

    @Override
    public void add(String key) {
        root = add(key, root, 0);
    }

    private TrieNode add(String key, TrieNode x, int d) {
        if (x == null) {x = new TrieNode();}
        if (d == key.length()) {
            x.isKey = true;
            return x;
        }
        Character c = key.charAt(d);
        if (x.next == null) { x.next = new HashMap<>(); }
        x.next.put(c, add(key, x.next.get(c), d + 1));
        return x;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        TrieNode prefixNode = get(root, prefix, 0);
        List<String> res = new ArrayList<>();
        allStringWithPrefix(prefixNode, new StringBuilder(prefix), res);
        return res;
    }

    private TrieNode get(TrieNode x, String prefix, int d) {
        if (x == null) {return null;}
        if (d == prefix.length()) {
            return x;
        }
        Character c = prefix.charAt(d);
        return get(x.next.get(c), prefix, d + 1);
    }

    private void allStringWithPrefix(TrieNode x, StringBuilder prefix, List<String> res) {
        if (x == null) {
            return;
        }
        if (x.isKey) {
            res.add(prefix.toString());
        }
        if (x.next == null) {return;}
        x.next.forEach((c, Node) -> {
            allStringWithPrefix(Node, prefix.append(c), res);
            prefix.deleteCharAt(prefix.length() - 1);
        });
    }

    @Override
    public String longestPrefixOf(String key) {
        int length = longestPrefixOf(key, root, 0, -1);
        if (length == -1) return null;
        return key.substring(0, length);
    }

    private int longestPrefixOf(String key, TrieNode x, int d,int length) {
        if (x == null || d > key.length()) {return length;}
        if (x.isKey) {
             length = d;
        }
        if (x.next == null) {return length;}
        Character c = key.charAt(d);
        return longestPrefixOf(key, x.next.get(c), d + 1, length);
    }
}
