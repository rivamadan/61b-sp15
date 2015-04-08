import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    
    /* Copies the file from it's source location relative to .gitlet to the destination folder */
    public static void copyFile(String src, String dst) throws IOException {
        String currDirectory = System.getProperty("user.dir");
        String separator = File.separator;
        String fileToCopy = currDirectory + separator + src;
        String destination = currDirectory + separator + dst;
        InputStream inStream = null;
        OutputStream outStream = null;
        try{
 
            File read =new File(fileToCopy);
            File out =new File(destination);
 
            inStream = new FileInputStream(read);
            outStream = new FileOutputStream(out);
 
            byte[] buffer = new byte[1024];
 
            int length;
            while ((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }
 
            if (inStream != null)inStream.close();
            if (outStream != null)outStream.close();
 
            System.out.println("File Copied..");
        }catch(IOException e){
            e.printStackTrace();
        }
//        Files.copy(fileToCopy, destination, StandardCopyOption.REPLACE_EXISTING);
    }
    
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
    
    /* Creates a new folder in the current directory.
     * If the new folder already exists, it throw a runtime exception. */
    public static void createFolder(String name) {
        String currDir = System.getProperty("user.dir");
        
        File newFolder = new File(currDir, name);
        if (newFolder.exists()) {
            //throw new RuntimeException();
        } else {
            newFolder.mkdir();
        }       
    } 
}
