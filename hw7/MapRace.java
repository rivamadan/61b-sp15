// import java.util.HashMap;
// import java.util.TreeMap;
// import java.util.Map;
// We don't need these! We made our own!

public class MapRace {

    /* Tests the put action the specified number of times. */
    private static long timePuts61B(Map61B<Integer, Integer> map, 
                int num_puts, int key_range, int val_range) {
        long start = System.currentTimeMillis();
        for (int count = 0; count < num_puts; count++) {
            int val = (int) Math.random() * key_range;
            int key = (int) Math.random() * val_range;
            map.put(val, key);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /* Tests the get action the specified number of times. */
    private static long timeGets61B(Map61B<Integer, Integer> map, 
                int num_gets, int key_range) {
        long start = System.currentTimeMillis();
        for (int count = 0; count < num_gets; count++) {
            int key = (int) Math.random() * key_range;
            map.get(key);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /* Tests the get action the specified number of times. */
    private static long timeRemove61B(Map61B<Integer, Integer> map, 
                int num_removes, int key_range) {
        long start = System.currentTimeMillis();
        for (int count = 0; count < num_removes; count++) {
            int key = (int) Math.random() * key_range;
            map.remove(key);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /* Warms up Java to get the cache hot and ready. If you don't warm up, 
     * you'll see that the first test has an unfair handicap. */
    private static void warmup() {
        Map61B<Integer, Integer> trashMap1 = new MyHashMap<Integer, Integer>();
        Map61B<Integer, Integer> trashMap2 = new BSTMap<Integer, Integer>();
        timePuts61B(trashMap1, MIL, MIL, MIL);
        timePuts61B(trashMap2, MIL, MIL, MIL);
        timeGets61B(trashMap1, MIL, MIL);
        timeGets61B(trashMap2, MIL, MIL);
    }

    private static final int MIL = 1000000;

    private static void run61BTimedTests(int num, int key_range, 
                int val_range) {
        Map61B<Integer, Integer> hMap = new MyHashMap<Integer, Integer>();
        Map61B<Integer, Integer> tMap = new BSTMap<Integer, Integer>();

        // TreeMap puts
        long tPuts = timePuts61B(tMap, num, key_range, val_range);
        String tm = "TreeMap " + num + " puts: " + tPuts + " ms.";
        System.out.println(tm);

        // HashMap puts
        long hPuts = timePuts61B(hMap, num, key_range, val_range);
        String hm = "HashMap " + num + " puts: " + hPuts + " ms.";
        System.out.println(hm);

        // TreeMap gets
        long tGets = timeGets61B(tMap, num, key_range);
        String tg = "TreeMap " + num + " gets: " + tGets + " ms.";
        System.out.println(tg);

        // HashMap gets
        long hGets = timeGets61B(hMap, num, key_range);
        String hg = "HashMap " + num + " gets: " + hGets + " ms.";
        System.out.println(hg);
        if (0 == 0) return; // Don't test remove if not implemented

        // HashMap removes
        long hRemove = timeRemove61B(hMap, num, key_range);
        String hr = "HashMap " + num + " removes: " + hRemove + " ms.";
        System.out.println(hr);

        // TreeMap removes
        long tRemove = timeRemove61B(tMap, num, key_range);
        String tr = "TreeMap " + num + " removes: " + tRemove + " ms.";
        System.out.println(tr);
    }

    public static final String followUp() {
        String answer = "yes, because I done it ";
        answer += "yuh huh I did";
        /* For most people, the answer should be no. This is because the way 
         * our BSTMap was implemented, it wasn't a self balancing tree. A 
         * runtime of log(n) is not guaranteed. Similarly, most 
         * implementations of MyHashMap do not reach the actual optimized 
         * runtime of java.util.HashMap. If imports were used as opposed to 
         * student code, the result should be yes, HashMap wins for large
         * inputs. */
        return answer;
    }

    public static void main(String[] args) {
        warmup();
        System.out.println("######## 1 Million ########");
        run61BTimedTests(MIL, MIL, MIL);

        System.out.println();
        System.out.println("######## 5 Million ########");
        run61BTimedTests(5 * MIL, 5 * MIL, 5 * MIL);

        System.out.println();
        System.out.println("######## 10 Million ########");
        run61BTimedTests(10 * MIL, 10 * MIL, 10 * MIL);

        System.out.println();
        System.out.println("######## 50 Million ########");
        run61BTimedTests(50 * MIL, 50 * MIL, 50 * MIL);
    }
}