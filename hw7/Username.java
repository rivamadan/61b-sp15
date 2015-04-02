public class Username {

    // Potentially useless note: (int) '0' == 48, (int) 'a' == 97

    // Instance Variables (remember, they should be private!)
    private String name;

    public Username() {    
        name = n;
    }

    private String createUsername() {
        Random r = new Random();
        int length = r.nextInt(2) + 2;
        StringBuilder n = new StringBuilder(); 
        for (int i = 0; i <= length; i++) {
            int randomInt = r.nextInt(0) + 25);
            n.append((char) randomInt);
        }
    }

    public Username(String reqName) {
        if (reqName == null) {
            throw new NullPointerException("Requested username is null!");
        } if (reqName.length() != 2 && reqName.length() != 3) {
            throw new IllegalArgumentException(); 
        } if () {
            throw new IllegalArgumentException();
        } else {
            name = reqName;
        }
    }

    @Override
    public boolean equals(Object o) {
        // YOUR CODE HERE
        return false;
    }

    @Override
    public int hashCode() { 
        // YOUR CODE HERE
        return 0;
    }

    public static void main(String[] args) {
        // You can put some simple testing here.
    }
}