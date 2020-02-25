package preprocess;


import java.util.ArrayList;
import java.util.List;

/**
 * get Ngrams
 */

public class NgramProcessor {

    ArrayList<String> textList;
    ArrayList<String[]> tokens;
    ArrayList<List<String>> bigrams;
    ArrayList<List<String>> trigrams;
    ArrayList<List<String>> fourgrams;
    ArrayList<List<String>> fivegrams;
    int n;

    public NgramProcessor(int n, ArrayList<String> textList){
        this.n = n;
        if(n > 5){
            System.out.println("Exceed the Maximun ngram, please type number under 5");
        }
        this.textList = textList;
        this.tokens = new ArrayList<String[]>();
        this.bigrams = new ArrayList<List<String>>();
        this.trigrams = new ArrayList<List<String>>();
        this.fourgrams = new ArrayList<List<String>>();
        this.fivegrams = new ArrayList<List<String>>();
        generateToken();
        if(n > 1){
            generateNgram();
        }
    }

    private void generateToken(){
        for (String text: textList){
            String[] unigrams = text.split(" ");
            tokens.add(unigrams);
        }
    }

    private void generateNgram(){
        List<String> bigramList = new ArrayList<String>();
        List<String> trigramList = new ArrayList<String>();
        List<String> fourgramList = new ArrayList<String>();
        List<String> fivegramList = new ArrayList<String>();
        for(String[] tokenList: tokens){
            for(int i = 0; i<tokenList.length-2; i++ ){
                String bigram = tokenList[i] + " " + tokenList[i+1];
                bigramList.add(bigram);
            }
            bigrams.add(bigramList);
            for(int i = 0; i < tokenList.length-3; i++){
                String trigram = tokenList[i] + " " + tokenList[i+1] + " " + tokenList[i+2];
                trigramList.add(trigram);
            }
            trigrams.add(trigramList);

            for(int i = 0; i < tokenList.length-4; i++){
                String fourgram = tokenList[i] + " " + tokenList[i+1] + " " + tokenList[i+2] + " " + tokenList[i+3];
                fourgramList.add(fourgram);
            }
            fourgrams.add(fourgramList);

            for(int i = 0; i < tokenList.length-5; i++){
                String fivegram = tokenList[i] + " " + tokenList[i+1] + " " + tokenList[i+2] + " " + tokenList[i+3] + " " + tokenList[i+4];
                fivegramList.add(fivegram);
            }
            fivegrams.add(fivegramList);

        }
    }

    public ArrayList<String[]> getTokens(){return tokens;}

    public ArrayList<List<String>> getBigrams(){return bigrams;}

    public ArrayList<List<String>> getTrigrams(){return trigrams;}

    public ArrayList<List<String>> getFourgrams(){return fourgrams;}

    public ArrayList<List<String>> getFivegrams(){return fivegrams;}

}
