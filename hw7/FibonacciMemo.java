import java.util.HashMap; // Import Java's HashMap so we can use it

public class FibonacciMemo {

    private static HashMap<Integer, Integer> memoMap = new HashMap<Integer, Integer>();

    /**
     * The classic recursive implementation with no memoization. Don't care
     * about graceful error catching, we're only interested in performance.
     * 
     * @param n
     * @return The nth fibonacci number
     */
    public static int fibNoMemo(int n) {
        if (n <= 1) {
            return n;
        }
        return fibNoMemo(n - 2) + fibNoMemo(n - 1);
    }

    /**
     * Your optimized recursive implementation with memoization. 
     * You may assume that n is non-negative.
     * 
     * @param n
     * @return The nth fibonacci number
     */
    public static int fibMemo(int n) {
        if (memoMap.containsKey(n)) {
            return memoMap.get(n);
        } if (n <= 1) {
            return n;
        } else {
            int fibNum = fibMemo(n - 2) + fibMemo(n - 1);
            memoMap.put(n, fibNum);
            return fibNum;
        }
    }

    /**
     * Answer the following question as a returned String in this method:
     * Why does even a correctly implemented fibMemo not return 2,971,215,073
     * as the 47th Fibonacci number?
     */
    public static String why47() {
        String answer = "overflow, 2,971,215,073 is too large to be represented by a 32-bit int";
        return answer;
    }

    public static void main(String[] args) {
        // Optional testing here        
        String m = "Fibonacci's real name was Leonardo Pisano Bigollo.";
        m += "\n" + "He was the son of a wealthy merchant.\n";
        System.out.println(m);
        System.out.println("0: " + FibonacciMemo.fibMemo(0));
        System.out.println("1: " + FibonacciMemo.fibNoMemo(1));
        System.out.println("2: " + FibonacciMemo.fibNoMemo(2));
        System.out.println("3: " + FibonacciMemo.fibNoMemo(3));
        System.out.println("4: " + FibonacciMemo.fibNoMemo(4));
        System.out.println("46: " + FibonacciMemo.fibMemo(46));
        System.out.println("47: " + FibonacciMemo.fibMemo(47));
        System.out.println("300: " + FibonacciMemo.fibMemo(300));

        // 46th Fibonacci = 1,836,311,903
        // 47th Fibonacci = 2,971,215,073
    }
}
