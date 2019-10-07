import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFmpegFileList {

    public static void CreateFmpegFileList(File dir,File tempDir)throws IOException{

        FileWriter nFile = new FileWriter(tempDir + "\\" + prepareYourAnus(dir.toString()));

        for (File fileName: dir.listFiles()){
            nFile.write("file '"+fileName.getCanonicalPath()+"'\n"  );
        }

        nFile.close();

    }

    private static String prepareYourAnus(String stringToPrepareYourAnus){
        //System.out.println(stringToPrepareYourAnus.replace("C:\\KKK\\explore\\tags\\", ""));

        String newStringToPrepareYourAnus = stringToPrepareYourAnus;
        newStringToPrepareYourAnus = stringToPrepareYourAnus.replace("C:\\KKK\\explore\\tags\\", "");
        newStringToPrepareYourAnus = newStringToPrepareYourAnus.replace("\\", "");
        return newStringToPrepareYourAnus;
    }


}
