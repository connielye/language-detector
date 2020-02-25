package model;

import features.NgramFeatures;
import preprocess.NgramProcessor;

import java.util.ArrayList;

/**
 * turn string to vector
 */

public class String2Vector {

    public ArrayList<Integer> convertText2Vector(String text, int n){
        ArrayList<Integer> x;
        ArrayList<String> list = new ArrayList<String>();
        list.add(text);
        NgramProcessor processor = new NgramProcessor(n, list);
        NgramFeatures features = new NgramFeatures(processor.getTokens(), null, null, null, null, 10000);
        x = features.getFeatureMatrix().get(0);
        return x;
    }
}
