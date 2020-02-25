package features;

import java.util.ArrayList;
import java.util.List;

/**
 * turn Ngrams into feature vectors (one hot)
 */

public class NgramFeatures {

    ArrayList<ArrayList<Integer>> featureMatrix;
    ArrayList<String[]> tokens;
    ArrayList<List<String>> bigrams;
    ArrayList<List<String>> trigrams;
    ArrayList<List<String>> fourgrams;
    ArrayList<List<String>> fivegrams;
    int featureNumber;

    public NgramFeatures(ArrayList<String[]> tokens, ArrayList<List<String>> bigrams, ArrayList<List<String>> trigrams, ArrayList<List<String>> fourgrams, ArrayList<List<String>> fivegrams, int featureNumber){
        this.tokens = tokens;
        this.bigrams = bigrams;
        this.trigrams = trigrams;
        this.fourgrams = fourgrams;
        this.fivegrams = fivegrams;
        this.featureNumber = featureNumber;
        generateFeatures();
    }

    private ArrayList<ArrayList<Integer>> generateUnigramVector(){
        ArrayList<ArrayList<Integer>> unigramVectors = new ArrayList<ArrayList<Integer>>();
        for(String[] tokenList: tokens){
            ArrayList<Integer> vector = new ArrayList<Integer>();
            for (int i = 0; i < tokenList.length; i ++) {
                int hashcode = tokenList[i].hashCode() % featureNumber;
                vector.add(hashcode);
            }
            int hashcodeBias = "bias".hashCode() % featureNumber;
            vector.add(hashcodeBias);
            unigramVectors.add(vector);
        }
        return unigramVectors;
    }

    private ArrayList<ArrayList<Integer>> generateNgramVectors(ArrayList<List<String>> ngrams){
        ArrayList<ArrayList<Integer>> ngramVectors = new ArrayList<ArrayList<Integer>>();
        for(List<String> ngramList : ngrams){
            ArrayList<Integer> vector = new ArrayList<Integer>();
            for (int i = 0; i < ngramList.size(); i++){
                int hashcode = ngramList.get(i).hashCode() % featureNumber;
                vector.add(hashcode);
            }
            int hashcodeBias = "bias".hashCode() % featureNumber;
            vector.add(hashcodeBias);
            ngramVectors.add(vector);
        }
        return ngramVectors;
    }

    private void generateFeatures(){
        featureMatrix = generateUnigramVector();
        VectorAppender appender = new VectorAppender();
        if(bigrams !=null){
            ArrayList<ArrayList<Integer>> bigramMatrix = generateNgramVectors(bigrams);
            appender.appendVectors(featureMatrix, bigramMatrix);
        }
        if(trigrams != null){
            ArrayList<ArrayList<Integer>> trigramMatrix = generateNgramVectors(trigrams);
            appender.appendVectors(featureMatrix, trigramMatrix);
        }
        if(fourgrams != null){
            ArrayList<ArrayList<Integer>> fourgramMatrix = generateNgramVectors(fourgrams);
            appender.appendVectors(featureMatrix, fourgramMatrix);
        }
        if(fivegrams != null){
            ArrayList<ArrayList<Integer>> fivegramMatrix = generateNgramVectors(fivegrams);
            appender.appendVectors(featureMatrix, fivegramMatrix);
        }
    }

    public ArrayList<ArrayList<Integer>> getFeatureMatrix() {
        return featureMatrix;
    }
}
