package model;


import com.google.gson.Gson;
import features.NgramFeatures;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import preprocess.Label2Index;
import preprocess.NgramProcessor;
import preprocess.TextReader;
import preprocess.TrainTestSplitter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

/**
 * softmax classifier
 */

public class Softmax {
    final static Logger logger = Logger.getLogger(Softmax.class.getName());
    ComputeHelper helper;

    public Softmax(){
        this.helper = new ComputeHelper();
    }


    private ArrayList<Double> probabilities (ArrayList<ArrayList<Double>> weights, ArrayList<Integer> x, int languageNumber){
        ArrayList<Double> temp = new ArrayList<Double>();
        ArrayList<Double> probs = new ArrayList<Double>();
        ArrayList<Double> products = new ArrayList<Double>();
        double sum = 0d;
        for (int i = 0; i< languageNumber; i ++) {
            ArrayList<Double> theta = weights.get(i);
            double dProduct = helper.dotProduction(theta, x);
            products.add(dProduct);
        }
        for (int j = 0; j< products.size(); j++){
            double max_product = helper.argmax(products);
            double expo = Math.exp(products.get(j) - max_product);
            temp.add(expo);
            sum = sum + expo;
        }
        for (double expo : temp){
            double prob = expo/sum;
            probs.add(prob);
        }
        return probs;
    }

    private void sgd (ArrayList<Integer> x, int y, int languageNumber, ArrayList<ArrayList<Double>> weights, double learningRate){
        ArrayList<Double> probs = probabilities(weights, x, languageNumber);
        double yp;
        for (int j = 0; j < languageNumber; j ++){
            if (j == y){
                yp = 1d;
            }else{
                yp = 0d;
            }
            double scalar = learningRate * (yp - probs.get(j));
            ArrayList<Double> theta = weights.get(j);
            ArrayList<Double> newTheta = new ArrayList<Double>();
            for (int i = 0; i < theta.size(); i++){
                double itheta = theta.get(i) + scalar * x.get(i);
                newTheta.add(itheta);
            }
            weights.set(j, newTheta);
        }
    }

    public ArrayList<ArrayList<Double>> fit(ArrayList<ArrayList<Integer>> xTrain, ArrayList<Integer> yTrain, ArrayList<ArrayList<Integer>> xValidate, ArrayList<Integer> yValidate, ArrayList<String> languageList,
                                            int languageNummber, int featureNumber, int epochNumber, double learningRate, String outputFile) throws IOException{
        ArrayList<ArrayList<Double>> weights = helper.generateWeights(languageNummber, featureNumber);
        for (int epoch = 1; epoch < epochNumber+1; epoch++){
            int time = 0;
            int printPoint = 1;
            for (int i = 0; i < yTrain.size(); i++ ){
                ArrayList<Integer> x = helper.xVector(xTrain.get(i), featureNumber);
                int y = yTrain.get(i);
                sgd(x, y, languageNummber, weights,learningRate);
                time ++;
                if (time == printPoint){
                    printPoint = printPoint * 2;
                    logger.info("Epoch: " + epoch + "; Iteration: " + time + ";");
                }
            }
            if (xValidate != null){
                logger.info("Validation on Dev, Epoch: " + epoch );
                validation(xValidate, yValidate, weights, languageList, featureNumber);
            }
        }
        saveModel(outputFile, weights);
        return weights;
    }



    private void validation(ArrayList<ArrayList<Integer>> xTest, ArrayList<Integer> yTest, ArrayList<ArrayList<Double>> weights, ArrayList<String> languageList, int featureNumber){
        ArrayList<Integer> yHats = new ArrayList<Integer>();
        for (int i = 0; i < yTest.size(); i++){
            ArrayList<Integer> x = helper.xVector(xTest.get(i), featureNumber);
            ArrayList<Double> probs = probabilities(weights, x, languageList.size());
            double max = helper.argmax(probs);
            int languageIndex = probs.indexOf(max);
            yHats.add(languageIndex);
        }
        Double accuracy = helper.accuracy(yHats, yTest);
        logger.info("Accuracy on Dev set: " + accuracy);
    }

    public void predict(String text, int n, ArrayList<String> languageList, ArrayList<ArrayList<Double>> weights, int featureNumber){
        String2Vector s2v = new String2Vector();
        ArrayList<Integer> x = s2v.convertText2Vector(text, n);
        ArrayList<Integer> xVector = helper.xVector(x, featureNumber);
        ArrayList<Double> probs = probabilities(weights, xVector, languageList.size());
        double max = helper.argmax(probs);
        int languageIndex = probs.indexOf(max);
        String language = languageList.get(languageIndex);
        System.out.println("Predicted: " + language + "; Probability: " + max);

    }

    private void saveModel(String outputFile, ArrayList<ArrayList<Double>> weights) throws IOException{
        Writer writer = new FileWriter(outputFile);
        JSONObject json = new JSONObject();
        json.put("Model", "Softmax");
        json.put("Weights", weights);
        Gson gson = new Gson();
        gson.toJson(json, writer);

    }


}
