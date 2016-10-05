package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by athinavandame on 2016-10-04.
 */
public class Parser {

    public Parser(String startingWord, String[] sentenceSpec) {
        this.startingWord = startingWord;
        this.sentenceSpec = sentenceSpec;
        for (int i = 0; i < sentenceSpec.length-1; i++) {
            this.sentenceSpecMap.put(sentenceSpec[i], sentenceSpec[i+1]);
        }
    }

    public Map<String, Map<String, Node>> mapMain = new HashMap<>();
    public String startingWord;
    public String[] sentenceSpec;
    public Map<String, String> sentenceSpecMap = new HashMap<>();
    public Map<String, Node> parseMap = new HashMap<>();

    public void addToMap(String word1, String partOfSpeech1, String word2, String partOfSpeech2, float probability) {
        Map <String, Node> map;
        if (this.mapMain.get(word1) != null) {
            map = this.mapMain.get(word1);
        } else {
            map = new HashMap <>();
        }

        String partOfSpeech2Check = this.sentenceSpecMap.get(partOfSpeech1);
        if (partOfSpeech2Check != null) {
            if (partOfSpeech2Check.equals(partOfSpeech2)) {
                Node node = new Node(partOfSpeech1, partOfSpeech2, probability);
                map.put(word2, node);
                this.mapMain.put(word1, map);
//                System.out.println("Found " + partOfSpeech1 + ", " + partOfSpeech2);
            }
        }
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

    public ArrayList<String> getNextWords(String word1) {
        Map <String, Node> mapValues = this.getValues(word1);
        ArrayList<String> output = new ArrayList<>();
        String word2 = "";
        for (Map.Entry<String,Node> entry : mapValues.entrySet()) {
            word2 = entry.getKey();
            output.add(word2);
        }
        return output;
    }

    public void parseFrom(String word1) {
        ArrayList <String> word2s = this.getNextWords(word1);
        for (String word2: word2s) {
            System.out.println(word1);
            System.out.println(word2);
            System.out.println(this.getPartOfSpeech1(word1, word2));
            System.out.println(this.getPartOfSpeech2(word1, word2));
            System.out.println(this.getProbability(word1, word2));
        }
    }


}
