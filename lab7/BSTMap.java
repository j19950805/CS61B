import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.*;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V>{
    private Node root;

    private class Node{
        private K key;
        private V value;
        private Node left, right;
        private int size;

        public Node(K key, V value, int size){
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BSTMap() {
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    private boolean containsKey(Node x, K key) {
        if (x == null) {
            return false;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsKey(x.left, key);
        } else {
            return containsKey(x.right, key);
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x.value;
        } else if (cmp < 0) {
            return get(x.left, key);
        } else {
            return get(x.right, key);
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            return new Node(key, value,1);
        }

        if (key.compareTo(x.key) < 0) {
            x.left = put(x.left, key, value);
        } else {
            x.right = put(x.right, key, value);
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x == null) {
            return;
        }
        if (x.left == null) {
            System.out.println(x.key + ": "+ x.value);
            printInOrder(x.right);
        } else {
            printInOrder(x.left);
            System.out.println(x.key + ": "+ x.value);
            printInOrder(x.right);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet();
        return KeySet(root, keySet);
    }

    private Set<K> KeySet(Node x, Set<K> keySet) {
        if (x == null) {
            return null;
        }
        keySet.add(x.key);
        KeySet(x.right, keySet);
        KeySet(x.left, keySet);
        return keySet;
    }

    @Override
    public V remove(K key) {
        return remove(root, null, 0, key);
    }

    private V remove(Node x, Node parent, int dir, K key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0){
            return removeNode(x, parent, dir);
        } else if (cmp < 0) {
            x.size -= 1;
            return remove(x.left, x, 1, key);
        } else {
            x.size -= 1;
            return remove(x.right, x, 2, key);
        }
    }

    private V removeNode(Node x, Node parent, int dir) {
        V res = x.value;
        if (x.left == null && x.right == null) {
            if (dir == 1) {
                parent.left = null;
            } else if (dir == 2) {
                parent.right = null;
            } else {
                clear();
            }
        } else if (x.left == null) {
            if (dir == 1) {
                parent.left = x.right;
            } else if (dir == 2) {
                parent.right = x.right;
            } else {
                root = x.right;
            }
        } else if (x.right == null) {
            if (dir == 1) {
                parent.left = x.left;
            } else if (dir == 2) {
                parent.right = x.left;
            } else {
                root = x.left;
            }
        } else {
            if (size(x.left) > size(x.right)) {
                Node preParent, predecessor;
                if (x.left.right == null) {
                    preParent = x;
                } else {
                    preParent = findLargestNodeParent(x.left);
                }
                predecessor = preParent.right;
                x.key = predecessor.key;
                x.value = predecessor.value;
                removeNode(predecessor, preParent, 2);
            } else {
                Node sucParent, successor;
                if (x.right.left == null) {
                    sucParent = x;
                } else {
                    sucParent = findSmallestNodeParent(x.right);
                }
                successor = sucParent.left;
                x.key = successor.key;
                x.value = successor.value;
                removeNode(successor, sucParent, 1);
            }
        }
        return res;
    }

    private Node findLargestNodeParent(Node x) {
        if (x.right.right == null) {
            return x;
        } else {
            return findLargestNodeParent(x.right);
        }
    }

    private Node findSmallestNodeParent(Node x) {
        if (x.left.left == null) {
            return x;
        } else {
            return findSmallestNodeParent(x.left);
        }
    }


    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        ArrayList<K> l = new ArrayList<>();
        return listIterator(root, l).iterator();
    }

    private ArrayList<K> listIterator(Node x, ArrayList l) {
        if (x == null) {
            return l;
        }
        if (x.left == null) {
            l.add(x.key);
            listIterator(x.right, l);
        } else {
            listIterator(x.left, l);
            l.add(x.key);
            listIterator(x.right, l);
        }
        return l;
    }
}
