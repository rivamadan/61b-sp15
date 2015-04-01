import java.lang.Math;
// Don't forget to answer the follow-up question!
public class Bin15 {

    // A string of exactly 15 characters, each a 0 or 1.
    private String myBinStr;

    // A constantly-whining constructor for your testing purposes.
    public Bin15(String input) {

        // Check for null input
        if (input == null) {
            String msg = "Your binary string is null";
            throw new NullPointerException(msg);
        }

        // Check for length
        if (input.length() != 15) {
            String msg = "Your binary string isn't of length 15";
            throw new IllegalArgumentException(msg);
        }

        // Check for illegal characters
        for (int count = 0; count < 15; count++) {
            char c = input.charAt(count);
            // Careful with comparing vs 0 and comparing vs '0'
            if (c != '0' && c != '1') {
                String msg = "Your binary string contains a non-binary character";
                throw new IllegalArgumentException(msg);
            }
        }

        // The input is good. Let's roll.
        this.myBinStr = input;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Bin15) {
        	Bin15 other = (Bin15) o;
            return this.myBinStr.equals(other.myBinStr);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int multiplyer = 1;
        int hash = 0;
        for(int i = myBinStr.length() - 1; i > -1; i--, multiplyer*=2) {
        	char eachChar = myBinStr.charAt(i);
        	int num = Character.getNumericValue(eachChar);
        	hash += num*multiplyer;
        }
        return hash;
    }

    /* DO THIS LAST, AFTER IMPLEMENTING EVERYTHING
    Follow-up question: The current length of our myBinStr is 15. What is the
    longest length possible for this String such that we still can produce a
    perfect hash (assuming we can rewrite the hash function)? Write your answer
    in the method followUpAnswer(). 
    */
    public static final int followUpAnswer() {
        return 32;
    }
    
    public static void main(String[] args) {
    	Bin15 num31 = new Bin15("000000000011111");
    	Bin15 num7 = new Bin15("000000000000111");
    	System.out.println(num31.hashCode());
    	System.out.println(num7.hashCode());
    	System.out.println(num31.equals(num7));

        // Optional testing here. Potentially useless information:
        int c = 0x9 - 1 - 0b01;
        // 0x9 means 9 in hexadecimal
        // 1 means 1 in decimal
        // 0b01 means 01 or 1 in binary
        System.out.println("Note to self: Answer follow-up question!");
    }
}

