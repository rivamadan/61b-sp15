/* based off of princeton's TST */

public class TST {
    private Node root = new Node();

    private static class Node {
        private char c;
        private Node left, mid, right;
        private double val;
        private double max;
        private String word;
    }

    /* Inserts new string into Trie. */
    public void put(String s, double weight) {
        root = put(root, s, weight, 0);
    }

    private Node put(Node x, String s, double weight, int d) {
        char c = s.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if (c < x.c) {
            x.left = put(x.left, s, weight, d);
        } else if (c > x.c) {
            x.right = put(x.right, s, weight, d);
        } else if (d < s.length() - 1) {
            x.mid = put(x.mid, s, weight, d + 1);
        } else {
            x.val = weight;
            x.word = s;
        }

        if (x.left == null && x.right == null && x.mid == null) {
            x.max = weight;
        } else if (x.left == null && x.right == null) {
            x.max = x.mid.val;
        } else if (x.left == null && x.mid == null) {
            x.max = x.right.val;
        } else if (x.right == null && x.mid == null) {
            x.max = x.left.val;
        } else if (x.left == null) {
            x.max = Math.max(x.mid.val, x.right.val);
        } else if (x.right == null) {
            x.max = Math.max(x.mid.val, x.left.val);
        } else if (x.mid == null) {
            x.max = Math.max(x.left.val, x.right.val);
        } else {
            x.max = Math.max(x.mid.val, Math.max(x.left.val, x.right.val));
        }
        return x;
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
        if (x.val != 0.0) {
            return x.word;
        }
        if (x.max == x.left.max) {
            return topMatch(x.left);
        }
        if (x.max == x.right.max) {
            return topMatch(x.right);
        } else {
            return topMatch(x.mid);
        }
    }
}
