import net.bramp.ffmpeg.FFprobe;
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

        LoadConf config = new LoadConf(new File("config.json"));
        File tempDir = new File(config.returnTempDir());
        FFprobe ffprobe = new FFprobe(config.returnFfmpegPath());
        ArrayList<File> fileListArray  = config.returnDirList();


        if (args.length >0 && args[0].equals("sort")) {
            for (File directories:fileListArray) {
                for (File files:directories.listFiles()) {
                    if (files.getName().contains(".mp4")) {
                        FFmpegProbeResult probeResult = ffprobe.probe(files.toString());
                        FFmpegStream stream = probeResult.getStreams().get(0);
                        File endpointDimensionDir = new File(directories.toString() + "\\" + stream.height);
                        endpointDimensionDir.mkdirs();
                        FileUtils.copyFileToDirectory(files,endpointDimensionDir);
                    }
                }

                for (File files:directories.listFiles()){
                    if (files.isDirectory()){
                        Hash.create(files);
                    }
                }
            }

        } else if (args.length >0 && args[0].equals("list")){
            for (File directories:fileListArray) {
                for (File files:directories.listFiles()){
                    if (files.isDirectory() && files.listFiles().length > 10){
                        if (Hash.getDirHash(files) != Hash.getDirHashByFile(files) && Hash.getDirHashByFile(files) > 0){
                            CreateFmpegFileList.CreateFmpegFileList(files,tempDir);
                        }
                    }
                }
            }
        }else if (args.length >0 && args[0].equals("relist")){
            for (File directories:fileListArray) {
                for (File files:directories.listFiles()){
                    if (files.isDirectory()){
                        CreateFmpegFileList.delHshFiles(files);
                        Hash.create(files);
                    }
                }
            }
        }
    }

}
