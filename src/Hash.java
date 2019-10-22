import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

public class Hash {
    public static void create(File dir) throws Exception {
        if (dir.isDirectory() && dir.listFiles().length > 0) {
            FileWriter hashFile = new FileWriter(dir + "\\" + getDirHash(dir) + ".hsh");
            hashFile.write(String.valueOf(dir.listFiles().length));
            hashFile.close();
        }else{
            throw new Exception("Recieved file not a directory or no files in directory");
        }
    }

    public static int getDirHash(File dir) {
        if (dir.isDirectory() && dir.listFiles().length > 0) {
            File[] hshFiles = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".mp4");
                }
            });
            return Arrays.hashCode(hshFiles);
        }
            return 0;
    }

    public static int getDirHashByFile(File dir){
        File[] hshFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".hsh");
            }
        });

        if (hshFiles.length > 0) {
            String oldHash = hshFiles[0].getName().replace(".hsh","");
            return Integer.parseInt(oldHash);
        }

        return 0;
    }

    public static void delHshFiles(File dir) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".hsh")) {
                f.delete();
            }
        }
    }
}
