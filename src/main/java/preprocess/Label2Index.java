package preprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * get language index and label-language index
 */
public class Label2Index {

    ArrayList<Integer> labelIndex;
    ArrayList<String> labels;
    Map<String, String> sentlangMap;
    ArrayList<String> languageList;
    int languageNumber;

    public Label2Index(Map<String, String> sentlangMap){
        this.sentlangMap = sentlangMap;
        this.labelIndex = new ArrayList<Integer>();
        this.labels = new ArrayList<String>();
        this.languageList = new ArrayList<String>();
        labelAndLangList();
        convertLabel2Index();
        this.languageNumber = languageList.size();
    }


    private void labelAndLangList(){
        Iterator it = sentlangMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
            labels.add(entry.getValue().toString());
        }
        HashSet<String> languageSet = new HashSet<String>(labels);
        languageList = new ArrayList<String>(languageSet);
    }


    private void convertLabel2Index(){
        for (String label: labels){
            for(String language: languageList){
                if (label.equals(language)){
                    labelIndex.add(languageList.indexOf(language));
                }
            }
        }
    }

    public ArrayList<Integer> getLabelIndex() {
        return labelIndex;
    }

    public ArrayList<String> getLanguageList() {
        return languageList;
    }

    public int getLanguageNumber() { return languageNumber; }
}
