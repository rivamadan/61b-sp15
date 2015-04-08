
public class Gitlet {

    public static void main(String[] args) {

       String command = args[0];
       
       switch(command) {
       case "init":
           try {
               Commands.initialize();
           } catch (RuntimeException e) {
               System.err.println("A gitlet version control system already exits in the current directory.");
           }
       case "add":
       }

    }

}
