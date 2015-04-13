import java.io.IOException;

public class Gitlet {

	public static void main(String[] args) {

		String command = args[0];

		switch (command) {
		case "init":
			Commands.initialize();
			break;
		case "add":
			try {
				Commands.add(args[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "commit":
			if (args.length == 1) {
				System.out.println("Please enter a commit message.");
			}
			try {
				Commands.commit(args[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "remove":
		    Commands.remove(args[1]);
		    break;
	    case "log":
	        Commands.log();
	        break;
	    case "global-log":
	        Commands.globalLog();
	        break;
		case "checkout":
			try {
				Commands.checkout(args[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

}
