package preprocess;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * split data into training, develop and testing datasets
 */

public class TrainTestSplitter {

    Map<String, String> dataset;
    float trainPercetage;
    ArrayList<String> textTrain;
    ArrayList<String> langTrain;
    ArrayList<String> textTest;
    ArrayList<String> langTest;

    public TrainTestSplitter(Map<String, String> dataset, float trainPercentage){
        this.dataset = dataset;
        this.trainPercetage = trainPercentage;
        this.textTrain = new ArrayList<String>();
        this.langTrain = new ArrayList<String>();
        this.textTest = new ArrayList<String>();
        this.langTest = new ArrayList<String>();
        createSubmap();

    }

    private void createSubmap(){
        ArrayList<String> temp = new ArrayList<String>();
        int size = dataset.size();
        int trainsize = Math.round(size * trainPercetage);
        Iterator it = dataset.entrySet().iterator();
        int count = 0;
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            count ++;
            if(count < trainsize){
                this.langTrain.add(entry.getValue().toString());
                this.textTrain.add(entry.getKey().toString());
            } else {
                this.langTest.add(entry.getValue().toString());
                this.textTest.add(entry.getKey().toString());
            }

        }
    }


    public ArrayList<String> getTextTest() {
        return textTest;
    }

    public ArrayList<String> getLangTrain() {
        return langTrain;
    }

    public ArrayList<String> getLangTest() {
        return langTest;
    }

    public ArrayList<String> getTextTrain() {
        return textTrain;
    }
}
