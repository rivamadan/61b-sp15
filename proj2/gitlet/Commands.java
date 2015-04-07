package gitlet;

/* methods for gitlet's commands */

public class Commands {
    
    /*initializes gitlet*/
    public static void initialize() {
        FileUtils.createFolder(".gitlet", null);
    }
}
