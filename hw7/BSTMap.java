import java.util.Set;

/* Based off of the Algorithms 4th edition's BST */

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K k;
        private V v;
        private Node left, right;
        private int size;

        public Node(K key, V val, int numNodes) {
            this.k = key;
            this.v = val;
            this.size = numNodes;
        }
    }

    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(root, key) != null;
    }

    /*
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (n == null) {
            return null;
        }
        int compared = key.compareTo(n.k);
        if (compared < 0) {
            return get(n.left, key);
        }
        if (compared > 0) {
            return get(n.right, key);
        } else {
            return n.v;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) {
            return 0;
        } else {
            return n.size;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V value) {
        if (n == null) {
            return new Node(key, value, 1);
        }
        int compared = key.compareTo(n.k);
        if (compared < 0) {
            n.left = put(n.left, key, value);
        } else if (compared > 0) {
            n.right = put(n.right, key, value);
        } else {
            n.v = value;
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node n) {
        if (n != null) {
            printInOrder(n.left);
            System.out.println(n.k);
            printInOrder(n.right);
        }
    }

    /*
     * Removes the mapping for the specified key from this map if present. Not
     * required for HW6.
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /*
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for HW6a.
     */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /*
     * Returns a Set view of the keys contained in this map. Not required for
     * HW6.
     */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}
