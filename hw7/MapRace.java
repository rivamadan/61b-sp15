import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Random;
// We don't need these! We made our own!

public class MapRace {

    private static class Stopwatch { 

    private final long start;

   /**
     * Create a stopwatch object.
     */
    public Stopwatch() {
        start = System.currentTimeMillis();
    } 


   /**
     * Return elapsed time (in seconds) since this object was created.
     */
    public long elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / (long) 1000.0;
    }

} 

    /* Tests the put action the specified number of times. */
    private static long timePuts61B(Map<Integer, Integer> map, 
                int num_puts, int key_range, int val_range) {
        Stopwatch timer = new Stopwatch();
        while (num_puts > 0) {
            Random r = new Random();
            int key = r.nextInt(key_range  + 1);
            int val = r.nextInt(val_range + 1);
            map.put(key,val);
            num_puts-=1;
        }
        long time = timer.elapsedTime();
        return time;
    }

    /* Tests the get action the specified number of times. */
    private static long timeGets61B(Map<Integer, Integer> map, 
                int num_gets, int key_range) {
        Stopwatch timer = new Stopwatch();
        while (num_gets > 0) {
            Random r = new Random();
            int key = r.nextInt(key_range  + 1);
            map.get(key);
            num_gets-=1;
        }
        long time = timer.elapsedTime();
        return time;
    }

    /* Tests the get action the specified number of times. */
    private static long timeRemove61B(Map<Integer, Integer> map, 
                int num_removes, int key_range) {
        Stopwatch timer = new Stopwatch();
        while (num_removes > 0) {
            Random r = new Random();
            int key = r.nextInt(key_range  + 1);
            map.remove(key);
            num_removes-=1;
        }
        long time = timer.elapsedTime();
        return time;
    }

    /* Warms up Java to get the cache hot and ready. If you don't warm up, 
     * you'll see that the first test has an unfair handicap. */
    private static void warmup() {
        Map<Integer, Integer> trashMap1 = new HashMap<Integer, Integer>();
        Map<Integer, Integer> trashMap2 = new TreeMap<Integer, Integer>();
        timePuts61B(trashMap1, MIL, MIL, MIL);
        timePuts61B(trashMap2, MIL, MIL, MIL);
        timeGets61B(trashMap1, MIL, MIL);
        timeGets61B(trashMap2, MIL, MIL);
    }

    private static final int MIL = 1000000;

    private static void run61BTimedTests(int num, int key_range, 
                int val_range) {
        Map<Integer, Integer> hMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> tMap = new TreeMap<Integer, Integer>();

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
        String answer = "no, the treemap is usually slower because it has to maintain order,
         but for many puts the speed is almost the same. (I used java's built in maps.)"
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