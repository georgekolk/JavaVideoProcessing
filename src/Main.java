import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

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

        LoadConf config = new LoadConf(new File("config.json"));


        /*System.out.println(config.returnDBConnectionString());
        System.out.println(config.returnFfmpegPath());
        System.out.println(config.returnSaveDir());
        System.out.println(config.returnDirList());*/


        /*File tempDir = new File("C:\\KKK\\temp\\");
        FFprobe ffprobe = new FFprobe("C:\\ffmpeg-4.2.1-win64-staxtic\\bin\\ffprobe.exe");
        DbHandler dbHandler = DbHandler.getInstance("jdbc:sqlite:C:/dd.db");*/

        File tempDir = new File(config.returnTempDir());
        FFprobe ffprobe = new FFprobe(config.returnFfmpegPath());
        DbHandler dbHandler = DbHandler.getInstance(config.returnDBConnectionString());

        ArrayList<File> fileListArray  = config.returnDirList();

        /*fileListArray.add(new File("C:\\KKK\\explore\\tags\\cat\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\catvideo\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\dog\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\dogvideo\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\video\\"));
        fileListArray.add(new File("C:\\KKK\\explore\\tags\\naturevideo\\"));

        System.exit(10);*/

        if (args.length >0 && args[0].equals("sort")) {
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

                //todo: create hash files
                for (File files:directories.listFiles()){
                    if (files.isDirectory()){
                        CreateHashFiles.create(files);
                    }
                }
            }

        } else if (args.length >0 && args[0].equals("list")){
            for (File directories:fileListArray) {
                for (File files:directories.listFiles()){
                    if (files.isDirectory()){
                        System.out.println(files.getName() + " " + files.listFiles().length);
                        //TODO: find hsh file

                        File[] hshFiles = files.listFiles(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String name) {
                                return name.toLowerCase().endsWith(".hsh");
                            }
                        });

                        String oldHash;
                        if (hshFiles.length > 0) {
                            System.out.println(hshFiles[0].toString());
                            oldHash = hshFiles[0].getName().replace("hsh","");

                        }

                        if (files.listFiles().length > 10){
                            //TODO: create list
                            if (hshFiles.length > 0) {
                                System.out.println(hshFiles[0].toString());
                                oldHash = hshFiles[0].getName().replace(".hsh","");

                                System.out.println(files.hashCode());

                                if (files.hashCode() == Integer.parseInt(oldHash)){
                                    System.out.println(hshFiles[0].toString()  + "------------equal");
                                }else if (files.hashCode() != Integer.parseInt(oldHash)){
                                    System.out.println(hshFiles[0].toString()  + "------------NOT equal");

                                    CreateFmpegFileList.CreateFmpegFileList(files,tempDir);
                                    //TODO: create tag list for all gathered list

                                    String fileName =  files.toString().substring(files.toString().lastIndexOf("\\") + 1);

                                    System.out.println(fileName);

                                    String table = files.toString().replace(config.returnSaveDir(), "");
                                    table = table.substring(0, table.lastIndexOf("\\"));
                                    table = table.substring(0, table.lastIndexOf("\\"));

                                    System.out.println(table);

                                    //dbHandler.getTagsByFilename(fileName, table);

                                }


                            }


                        }
                    }
                }

            }
        }
    }

}
