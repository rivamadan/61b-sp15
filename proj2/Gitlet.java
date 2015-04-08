import java.io.IOException;

public class Gitlet {

    public static void main(String[] args) {

        String command = args[0];

        switch (command) {
        case "init":
            try {
                Commands.initialize();
            } catch (RuntimeException e) {
                System.err
                        .println("A gitlet version control system already exits in the current directory.");
            }
            break;
        case "add":
            try {
                Commands.add(args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case "commit":
            try {
                Commands.commit(args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case "checkout":
            try {
                Commands.checkout(args[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case "log":
            Commands.log();
            break;
        }
    }

}
