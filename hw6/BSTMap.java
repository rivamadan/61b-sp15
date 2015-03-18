import java.util.Set;
/* Based off of the Algorithms 4th edition's BST */

public class BSTMap<K extends Comparable<K>, V>  implements Map61B<K, V> {

	private Node root;
	private int size;

	private class Node {
		private K key;
		private V val;
		private Node left, right;

		public Node(K key, V val) {
			this.key = key;
			this.val = val;
		}
	}

	public void clear() {
		root = null;
	}

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
    	return containsKey(root, key);
    }

    private boolean containsKey(Node n, K key) {
        if (n == null) {
    		return false;
    	}
    	int compared = key.compareTo(n.key);
        if (compared < 0) {
        	return containsKey(n.left, key);
        }
        if (compared > 0) {
        	return containsKey(n.right, key);
        } else {
    		return true;
    	}
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. 
     */
    public V get(K key) {
    	return get(root, key);
    }

    private V get(Node n, K key) {
   		if (n == null) {
    		return null;
    	}
    	int compared = key.compareTo(n.key);
        if (compared < 0) {
        	return get(n.left, key);
        } if (compared > 0) {
        	return get(n.right, key);
    	} else {
    		return n.val;
    	}
    }

   /* Returns the number of key-value mappings in this map. */
    public int size() {
    	return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
    	put(root, key, value);
    }

    private void put(Node n, K key, V value) {
     	if (n == null) {
        	n = new Node(key, value);
        }
        int compared = key.compareTo(n.key);
        if (compared < 0) {
        	put(n.left, key, value);
        }
        else if (compared > 0) {
        	put(n.right, key, value);
        }
        else {
        	n.val = value;
       	}
        this.size += 1;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for HW6. */
    public V remove(K key) {
    	throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for HW6a. */
    public V remove(K key, V value) {
    	throw new UnsupportedOperationException();
    }

    /* Returns a Set view of the keys contained in this map. Not required for HW6. */
    public Set<K> keySet() {
    	throw new UnsupportedOperationException();
    }
}