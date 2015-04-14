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
		case "find":
			Commands.find(args[1]);
			break;
		case "status":
			Commands.status();
			break;
		case "checkout":
			System.out
					.println("Warning: The command you entered may alter the files in your working directory. "
							+ "Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
			if (args.length == 3) {
				try {
					Commands.checkoutTwo(args[1], args[2]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Commands.checkoutOne(args[1]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

}
