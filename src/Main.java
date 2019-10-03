import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args)throws Exception {

        FFprobe ffprobe = new FFprobe("C:\\ffmpeg-4.2.1-win64-static\\bin\\ffprobe.exe");

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

    System.out.println(directories.toString() + "\\" +files.getName());
    FFmpegProbeResult probeResult = ffprobe.probe(files.toString());
    FFmpegStream stream = probeResult.getStreams().get(0);
    System.out.println("Width: " + stream.width + ", " + "height: "+ stream.height);

    File endpointDimensionDir = new File(directories.toString() + "\\" + stream.height);
    endpointDimensionDir.mkdirs();
    FileUtils.copyFileToDirectory(files,endpointDimensionDir);
}


            }
        }


        /*FFmpegProbeResult probeResult = ffprobe.probe("C:\\KKK\\explore\\tags\\cat\\480x270__159518588490514_2935850347625824249_n.mp4");

        FFmpegFormat format = probeResult.getFormat();
        System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
                format.filename,
                format.format_long_name,
                format.duration

        );

        FFmpegStream stream = probeResult.getStreams().get(0);
        System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
                stream.codec_long_name,
                stream.width,
                stream.height
        );*/

    }
}
