import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class Main {


    /**
     * BOILERPLATE AND HARDCODE TIME | (• ◡•)| (❍ᴥ❍ʋ)
     *
     * сканирует заданные папки на наличие видеофайлов,
     * создает папки с названиями высоты кадра,
     * и закидывает туда видеофайлы,
     * папки с высотой видео создает для каждой заданной папки отдельно
     *
     * после чего в папку TEMP сохраняет файлы с названием названием_заданной папки + высота кадра
     * для дальнейшей обработки их батничком
     *
     * **/
    public static void main(String[] args)throws Exception {

        File tempDir = new File("C:\\KKK\\temp\\");
        FFprobe ffprobe = new FFprobe("C:\\ffmpeg-4.2.1-win64-static\\bin\\ffprobe.exe");
        DbHandler dbHandler = DbHandler.getInstance("jdbc:sqlite:C:/dd.db");

        ArrayList<File> fileListArray  = new ArrayList<File>();
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\cat\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\catvideo\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\dog\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\dogvideo\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\video\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\naturevideo\\"));


        for (File directories:fileListArray) {
            for (File files:directories.listFiles()) {
                //System.out.println(files.getName());
                if (files.getName().contains(".mp4")) {
                    //FileUtils.copyFile(directories.toString() + );

                    //System.out.println(directories.toString() + "\\" +files.getName());
                    FFmpegProbeResult probeResult = ffprobe.probe(files.toString());
                    FFmpegStream stream = probeResult.getStreams().get(0);
                    //System.out.println("Width: " + stream.width + ", " + "height: "+ stream.height);

                    File endpointDimensionDir = new File(directories.toString() + "\\" + stream.height);
                    endpointDimensionDir.mkdirs();
                    FileUtils.copyFileToDirectory(files,endpointDimensionDir);


                }
            }

            for (File files:directories.listFiles()){
                if (files.isDirectory()){
                    System.out.println(files.getName() + " " + files.listFiles().length);
                    if (files.listFiles().length > 10){
                        //TODO: create list
                        CreateFmpegFileList.CreateFmpegFileList(files,tempDir);
                        //TODO: create tag list for all gathered list

                        String fileName =  files.toString().substring(files.toString().lastIndexOf("\\") + 1);

                        System.out.println(fileName);

                            String table = files.toString().replace("C:\\KKK\\explore\\tags\\", "");
                            table = table.substring(0, table.lastIndexOf("\\"));
                            table = table.substring(0, table.lastIndexOf("\\"));

                        System.out.println(table);

                                dbHandler.getTagsByFilename(fileName,table);

                    }
                }
            }

            /*for (File listsInTempDirToFmpegProcess:tempDir.listFiles()){
                Runtime.getRuntime().exec("c:\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg -f concat -i "+ listsInTempDirToFmpegProcess + " -c copy C:\\vid\\"+listsInTempDirToFmpegProcess.getName()+".mp4");
            }*/

        }
    }
}
