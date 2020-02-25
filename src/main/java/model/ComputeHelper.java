package model;


import java.util.ArrayList;
import java.util.Collections;

/**
 * helper class for computing
 */

public class ComputeHelper {

    public ArrayList<Integer> xVector (ArrayList<Integer> x, int featureNumber){
        ArrayList<Integer> xFeatures = new ArrayList<Integer>(Collections.nCopies(featureNumber, 0));
        for (int j = 0; j < x.size(); j ++){
            int xNumber = x.get(j);
            if(xNumber < 0){
                xNumber = featureNumber - Math.abs(xNumber);
            }
            int xFeature = xFeatures.get(xNumber);
            xFeature ++;
            xFeatures.set(xNumber, xFeature);
        }
        return xFeatures;
    }

    public double dotProduction (ArrayList<Double> theta, ArrayList<Integer> xVector){

        double sum = 0d;
        for (int i = 0; i < xVector.size(); i ++){
            sum = sum + theta.get(i) * xVector.get(i);
        }
        return sum;
    }


    public double argmax(ArrayList<Double> p){
        double max = 0.0f;
        for (double ip : p){
            if (ip > max){
                max = ip;
            }
        }
        return max;
    }

    public ArrayList<ArrayList<Double>> generateWeights(int rows, int columns){
        ArrayList<Double> weightsInRow = new ArrayList<Double>(Collections.nCopies(columns, 0d));
        ArrayList<ArrayList<Double>> weights = new ArrayList<ArrayList<Double>>(Collections.nCopies(rows, weightsInRow));
        return weights;
    }


    public double accuracy(ArrayList<Integer> prediction, ArrayList<Integer> yTest){
        int correct = 0;
        for (int i = 0;  i < prediction.size(); i++){
            if (prediction.get(i) == yTest.get(i)){
                correct ++;
            }
        }
        double accuracy = ((double)correct) / prediction.size() * 100;
        return accuracy;
    }


}
