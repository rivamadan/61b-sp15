import java.util.Scanner;

public class Gitlet {

    public static void main(String[] args) {
        String command = args[0];
        switch (command) {
        case "init":
            Commands.initialize();
            break;
        case "add":
            Commands.add(args[1]);
            break;
        case "commit":
            if (args.length == 1) {
                System.out.println("Please enter a commit message.");
            } else {
                Commands.commit(args[1]);
            }
            break;
        case "rm":
            Commands.remove(args[1]);
            break;
        case "log":
            Commands.log();
            break;
        case "global-log":
            Commands.globalLog();
            break;
        case "find":
            Commands.find(args[1]);
            break;
        case "status":
            Commands.status();
            break;
        case "checkout":
            if (dangerousOK()) {
                if (args.length == 3) {
                    Commands.checkoutTwo(args[1], args[2]);
                } else {
                    Commands.checkoutOne(args[1]);
                }
            }
            break;
        case "branch":
            Commands.branch(args[1]);
            break;
        case "rm-branch":
            Commands.removeBranch(args[1]);
            break;
        case "reset":
            if (dangerousOK()) {
                Commands.reset(args[1]);     
            } break;
        case "merge":
            if (dangerousOK()) {
                Commands.merge(args[1]);     
            } break;
        case "rebase":
            if (dangerousOK()) {
                Commands.rebase(args[1]);     
            } break;
        case "i-rebase":
            if (dangerousOK()) {
                Commands.irebase(args[1]);     
            } break;
        }
    }

    public static boolean dangerousOK() {
        Scanner keyboard = new Scanner(System.in);
        System.out
                .println("Warning: The command you entered may alter the files in your working directory. "
                        + "Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
        String answer = keyboard.next();
        
        return answer.equals("yes");
    }

}
