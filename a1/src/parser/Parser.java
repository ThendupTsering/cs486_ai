package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by athinavandame on 2016-10-04.
 */
public class Parser {

    public Parser(String startingWord, String[] sentenceSpec) {
        this.startingWord = startingWord;
        this.sentenceSpec = sentenceSpec;
        for (int i = 0; i < sentenceSpec.length-1; i++) {
//            if (this.sentenceSpecMap.get(sentenceSpec[i]) == null) {
                ArrayList<String> newList = new ArrayList<>();
                newList.add(0, sentenceSpec[i+1]);
                this.sentenceSpecMap.put(sentenceSpec[i], newList);
//            } else {
//                ArrayList<String> existingList = this.sentenceSpecMap.get(sentenceSpec[i]);
//                existingList.add(existingList.size(), sentenceSpec[i+1]);
//                this.sentenceSpecMap.put(sentenceSpec[i], existingList);
//            }
        }
    }

    public Map<String, Map<String, Node>> mapMain = new HashMap<>(); // only has valid sentence spec lines
    public String startingWord;
    public String[] sentenceSpec;
    public Map<String, ArrayList<String>> sentenceSpecMap = new HashMap<>();
    public ArrayList<Sequence> validSequences = new ArrayList<>();
    public int nodesConsidered = 0;

    public void parseGraphToMainMap(String graph) {
        try (Stream<String> stream = Files.lines(Paths.get(graph))) {
            for (Iterator<String> i = stream.iterator(); i.hasNext(); ) {
                String line = i.next();
                String[] parts = line.split("/");
                String word1 = parts[0];
                String partOfSpeech1 = parts[1];
                String word2 = parts[3];
                String partOfSpeech2 = parts[4];
                String probabilityString = parts[6];
                Float probability = Float.valueOf(probabilityString);
                this.addToMap(word1, partOfSpeech1, word2, partOfSpeech2, probability);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToMap(String word1, String partOfSpeech1, String word2, String partOfSpeech2, float probability) {
        Map <String, Node> map;
        if (this.mapMain.get(word1) != null) {
            map = this.mapMain.get(word1);
        } else {
            map = new HashMap <>();
        }

        if (this.checkSpecIsValid(partOfSpeech1, partOfSpeech2)) {
            Node node = new Node(partOfSpeech1, partOfSpeech2, probability);
            map.put(word2, node);
            this.mapMain.put(word1, map);
        }
    }

    private boolean checkSpecIsValid(String partOfSpeech1, String partOfSpeech2) {
        boolean valid = false;
        if (this.sentenceSpecMap.get(partOfSpeech1) != null) {
            for(String s: this.sentenceSpecMap.get(partOfSpeech1)) {
                if (s.equals(partOfSpeech2)) { valid = true; }
            }
        }
        return valid;
    }

    public void findSequencesBFS() {
        Map <String, Node> mapValues = this.getValues(this.startingWord);
        Map.Entry<String, Node> firstEntry = mapValues.entrySet().iterator().next();
        String partOfSpeech1 = firstEntry.getValue().partOfSpeech1;

        if (partOfSpeech1.equals(this.sentenceSpec[0])) {
            for (int i = 0; i < this.sentenceSpec.length-1; i++) {
                if (i == 0) {
                    Sequence s = new Sequence(this.startingWord, partOfSpeech1, 1, 1);
                    this.parseFromLastWord(s);
                } else {
                    ArrayList<Sequence> currentSequences = new ArrayList<>(this.validSequences);
                    for(Sequence s: currentSequences) {
                        this.parseFromLastWord(s);
                    }
                }
            }
        }
    }

    public void findSequencesDFS(){
//        Map <String, Node> mapValues = this.getValues(this.startingWord);
//        Map.Entry<String, Node> firstEntry = mapValues.entrySet().iterator().next();
//        String partOfSpeech1 = firstEntry.getValue().partOfSpeech1;
//        Stack<Sequence> sequenceStack = new Stack<Sequence>();
//
//        if (partOfSpeech1.equals(this.sentenceSpec[0])) {
//            for (int i = 0; i < this.sentenceSpec.length-1; i++) {
//                if (i == 0) {
//                    Sequence s = new Sequence(this.startingWord, partOfSpeech1, 1, 1);
//                    sequenceStack.push(s);
//                }
//                //sequenceStack.push()
//            }
//        }
        System.out.println("DFS Search");
    }

    public void findSequencesHS(){
        System.out.println("HS Search");
    }

    public float getProbability(String word1, String word2) {
        return (this.mapMain.get(word1).get(word2).probability);
    }

//    public String getPartOfSpeech1(String word1, String word2) {
//        return this.mapMain.get(word1).get(word2).partOfSpeech1;
//    }

    public String getPartOfSpeech2(String word1, String word2) {
        return this.mapMain.get(word1).get(word2).partOfSpeech2;
    }

    public Map <String, Node> getValues(String word1) {
        return this.mapMain.get(word1);
    }

    public ArrayList<String> getNextWords(String word1) {
        Map <String, Node> mapValues = this.getValues(word1);
        ArrayList<String> output = new ArrayList<>();
        if (mapValues != null) {
            String word2;
            for (Map.Entry<String,Node> entry : mapValues.entrySet()) {
                word2 = entry.getKey();
                output.add(word2);
            }
        }
        return output;
    }

    public void parseFromLastWord(Sequence seq) {
        ArrayList <String> word2List = this.getNextWords(seq.lastWord);
        for (String word2: word2List) {
            Sequence s = new Sequence(seq.sentence, seq.sentenceSpec, seq.level, seq.probability);
            float probabilityOfWord2 = this.getProbability(seq.lastWord, word2);
            s = s.addWordToSequence(word2, this.getPartOfSpeech2(seq.lastWord, word2), probabilityOfWord2);
            this.nodesConsidered++;
            this.validSequences.add(this.validSequences.size(), s);
        }
    }

    public Sequence getBestSequence(int level) {
        Sequence best = new Sequence("TTAV", "TT-AV", -1, 0);
        float probSoFar = 0;
        for(Sequence s: this.validSequences) {
            if (s.level == level && s.probability > probSoFar) {
                probSoFar = s.probability;
                best = s;
            }
        }
        return best;
    }

    public void printSequences() {
        for (Sequence s: this.validSequences) {
            s.printSequence();
        }
    }

    public void printSentenceSpecMap() {
        for (String i: sentenceSpec) {
            System.out.println(i);
            if (sentenceSpecMap.get(i) != null) {
                for (String j: sentenceSpecMap.get(i)) {
                    System.out.println(j);
                }
            }
            System.out.println();
        }
    }


}
