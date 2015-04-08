import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    
    /* Copies the file from it's source location relative to .gitlet to the destination folder */
    public static void copyFile(String src, String dst) throws IOException {
        String currDirectory = System.getProperty("user.dir");
        String separator = File.separator;
        Path fileToCopy = Paths.get(currDirectory + separator + src);
        Path destination = Paths.get(currDirectory + separator + dst + src);
        Files.copy(fileToCopy, destination);
    }
    
    /* Creates a new folder either in the current directory if 
     * folder is null or in the specified folder of the current directory. 
     * If the new folder already exists, it throw a runtime exception. */
    public static boolean createFolder(String name, String folder) {
        String subFolder = System.getProperty("user.dir");
        String separator = File.separator;
        if (folder != null) {
            subFolder = subFolder + separator + folder;
        }
        File newFolder = new File(subFolder, name);
        System.out.println(subFolder);
        if (newFolder.exists()) {
            throw new RuntimeException();
        } else {
            boolean test = newFolder.mkdir();
            return test;
        }       
    } 
}
