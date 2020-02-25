package preprocess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * read text
 */
public class TextReader {

    String filename;
    Map<String, String> sentLangMap;

    public TextReader(String filename) throws IOException{
        this.filename = filename;
        this.sentLangMap = trainDataReader();
    }

    private Map<String, String> trainDataReader() throws IOException {
        Map<String, String> dataMap = new HashMap<String, String>();
        ArrayList<String> temp = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
        String line;
        while((line=reader.readLine()) != null){
            temp.add(line);
        }
        for(String langSent: temp){
            String[] tempList= langSent.split("\t");
            dataMap.put(tempList[1], tempList[0]);
        }
        return dataMap;
    }


    public Map<String, String> getlangSentMap(){
        return sentLangMap;
    }

}
