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
        StringBuilder path = new StringBuilder();
        StringBuilder res = new StringBuilder();
        longestPrefixOf(key, root, res, path, 0);
        if (res.length() == 0) {
            return null;
        }
        return res.toString();
    }

    private void longestPrefixOf(String key, TrieNode x, StringBuilder res, StringBuilder path, int d) {
        if (x == null || d >= key.length()) {return;}
        if (x.isKey) {
             res.append(path);
             path.setLength(0);
        }
        Character c = key.charAt(d);
        path.append(c);
        if (x.next == null) {return;}
        longestPrefixOf(key, x.next.get(c), res, path, d + 1);
    }
}
