package features;

import java.util.ArrayList;

/**
 * helper to concatenate vectors
 */

public class VectorAppender {


    public void appendVectors(ArrayList<ArrayList<Integer>> oldMatrix, ArrayList<ArrayList<Integer>> newMatrix){
        if(newMatrix != null) {
            for (int i = 0; i < oldMatrix.size(); i++) {
                ArrayList<Integer> oldVector = oldMatrix.get(i);
                ArrayList<Integer> newVector = newMatrix.get(i);
                oldVector.addAll(newVector);
            }
        }
    }

}
