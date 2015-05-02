/* based off of princeton's TST */

public class TST {
    private Node root = new Node();

    private class Node {
        private char c;
        private Node left, mid, right;
        private double val;
        private double max;
        private String word;
    }

    /* Inserts new string into Trie. */
    public void put(String s, double weight) {
        root = put(root, s, weight, 0);
        updateMax(root);
    }

    private Node put(Node x, String s, double weight, int d) {
        char c = s.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if (c < x.c) {
            x.left = put(x.left, s, weight, d);
            updateMax(x);
        } else if (c > x.c) {
            x.right = put(x.right, s, weight, d);
            updateMax(x);
        } else if (d < s.length() - 1) {
            x.mid = put(x.mid, s, weight, d + 1);
            updateMax(x);
        } else {
            x.val = weight;
            x.word = s;
            updateMax(x);
        }
        return x;
    }

    private void updateMax(Node x) {
        if (x.left == null && x.right == null && x.mid == null) {
            x.max = x.val;
        } else if (x.left == null && x.right == null) {
            x.max = x.mid.max;
        } else if (x.left == null && x.mid == null) {
            x.max = x.right.max;
        } else if (x.right == null && x.mid == null) {
            x.max = x.left.max;
        } else if (x.left == null) {
            x.max = Math.max(x.mid.max, x.right.max);
        } else if (x.right == null) {
            x.max = Math.max(x.mid.max, x.left.max);
        } else if (x.mid == null) {
            x.max = Math.max(x.left.max, x.right.max);
        } else {
            x.max = Math.max(x.mid.max, Math.max(x.left.max, x.right.max));
        }
    }

    /* Returns the weight associated with the given s. */
    public double get(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        if (s.length() == 0) {
            throw new IllegalArgumentException("s must have length >= 1");
        }
        Node x = get(root, s, 0);
        if (x == null) {
            return 0.0;
        }
        return x.val;
    }

    private Node get(Node x, String s, int d) {
        if (s == null) {
            throw new NullPointerException();
        }
        if (s.length() == 0) {
            throw new IllegalArgumentException("s must have length >= 1");
        }
        if (x == null) {
            return null;
        }
        char c = s.charAt(d);
        if (c < x.c) {
            return get(x.left, s, d);
        } else if (c > x.c) {
            return get(x.right, s, d);
        } else if (d < s.length() - 1) {
            return get(x.mid, s, d + 1);
        } else {
            return x;
        }
    }
    
    public String topMatch(String prefix) {
        Node start = get(root, prefix, 0);
        return topMatch(start);

    }

    private String topMatch(Node x) {
        if (x == null) {
            return null;
        }
        if (x.max == x.val) {
            return x.word;
        }
        if (x.left != null && x.max == x.left.max) {
            return topMatch(x.left);
        }
        if (x.right != null && x.max == x.right.max) {
            return topMatch(x.right);
        }
        if (x.mid != null && x.max == x.mid.max) {
            return topMatch(x.mid);
        }
        return null;
    }
}
