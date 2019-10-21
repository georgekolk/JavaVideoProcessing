import java.io.File;
import java.io.FileWriter;

public class CreateHashFiles {
    public static void create(File dir) throws Exception {

        if (dir.isDirectory() && dir.listFiles().length > 0) {
            FileWriter hashFile = new FileWriter(dir+"\\"+dir.hashCode()+".hsh");
            hashFile.write(dir.listFiles().length);
            hashFile.close();
        }else{
            throw new Exception("Recieved file not a directory or no files in directory");
        }
    }
}
