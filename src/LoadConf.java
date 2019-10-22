import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadConf {

    private String tempDir = "";
    private ArrayList<File> dirList = new ArrayList<File>();
    private String saveDir = "";
    private String ffmpegPath = "";
    private String archiveDir = "";

    private String DBConnectionString = null;

    public LoadConf(File configFile){

        try {
            Scanner s = new Scanner(configFile);
            StringBuilder builder = new StringBuilder();

            while (s.hasNextLine()) builder.append(s.nextLine());

            JSONParser pars = new JSONParser();

            try {

                Object obj = pars.parse(builder.toString());
                JSONObject overallConfig = (JSONObject) obj;


                if(overallConfig.containsKey("dirList")){
                    JSONArray dirListJson = (JSONArray)overallConfig.get("dirList");

                    for (int i = 0; i < dirListJson.size(); i++) {
                        //System.out.print(dirListJson.get(i));

                        JSONObject dirListJsonItem = (JSONObject) dirListJson.get(i);
                        //System.out.print(i+":");

                        String tempStringForDirList = (String) dirListJsonItem.get("name");

                        //System.out.println(tempStringForDirList);

                        dirList.add(new File(tempStringForDirList));
                    }
                }

                if(overallConfig.containsKey("archiveDir")){
                    this.archiveDir = (String)overallConfig.get("archiveDir");
                }

                if(overallConfig.containsKey("DBConnectionString")){
                    this.DBConnectionString = (String)overallConfig.get("DBConnectionString");
                }

                if(overallConfig.containsKey("tempDir")){
                    this.tempDir = (String)overallConfig.get("tempDir");
                }

                if(overallConfig.containsKey("saveDir")){
                    this.saveDir = (String)overallConfig.get("saveDir");
                }

                if(overallConfig.containsKey("ffmpegPath")){
                    this.ffmpegPath = (String)overallConfig.get("ffmpegPath");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<File> returnDirList(){
        return this.dirList;
    }
    public String returnDBConnectionString(){
        return this.DBConnectionString;
    }
    public String returnSaveDir(){
        return this.saveDir;
    }
    public String returnFfmpegPath() {
        return this.ffmpegPath;
    }
    public String returnTempDir(){
        return this.tempDir;
    }
    public String returnArchiveDir(){
        return this.archiveDir;
    }
}
