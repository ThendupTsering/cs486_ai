package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by athinavandame on 2016-10-04.
 */
public class Parser {

    public Parser() {
    }

    public Map<String, Map<String, Node>> mapMain = new HashMap<String, Map<String, Node>>();
    public Map <String, Float> wordsWithProbabilities = new HashMap<>();
    public int nodesVisited = 0;

    public void addToMap(String word1, String partOfSpeech1, String word2, String partOfSpeech2, float probability) {
        Map <String, Node> map;
        if (this.mapMain.get(word1) != null) {
            map = this.mapMain.get(word1);
        } else {
            map = new HashMap <String, Node>();
        }
        Node node = new Node(partOfSpeech1, partOfSpeech2, probability);
        map.put(word2, node);
        this.mapMain.put(word1, map);
    }

    public float getProbability(String word1, String word2) {
        return (this.mapMain.get(word1).get(word2).probability);
    }

    public String getPartOfSpeech1(String word1, String word2) {
        return (this.mapMain.get(word1).get(word2).partOfSpeech1);
    }

    public String getPartOfSpeech2(String word1, String word2) {
        return (this.mapMain.get(word1).get(word2).partOfSpeech2);
    }

    public Map <String, Node> getValues(String word1) {
        return this.mapMain.get(word1);
    }

//    public void generateHelper(String word1, String partOfSpeech2, int index) {
//
//
//        for (Map.Entry<String,Node> entry : mapValues.entrySet()) {
//            String word2 = entry.getKey();
//            Node word2Values = entry.getValue();
//        }
//
//    }

    public ArrayList<String> getNextWords(String word1) {
        Map <String, Node> mapValues = this.getValues(word1);
        ArrayList<String> output = new ArrayList<String>();
        String word2 = "";
        for (Map.Entry<String,Node> entry : mapValues.entrySet()) {
            word2 = entry.getKey();
            output.add(word2);
        }
        return output;
    }

}
