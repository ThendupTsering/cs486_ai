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
    }

    public Map<String, Map<String, Node>> mapMain = new HashMap<>(); // only has valid sentence spec lines
    public String startingWord;
    public String[] sentenceSpec;
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
        for (int i = 0; i < sentenceSpec.length-1; i++) {
            if (partOfSpeech1.equals(sentenceSpec[i]) && partOfSpeech2.equals(sentenceSpec[i+1])) {
                valid = true;
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
                    ArrayList<Float> probabilityArray = new ArrayList<>();
                    Sequence s = new Sequence(this.startingWord, partOfSpeech1, 1, 1, probabilityArray);
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
        Map <String, Node> mapValues = this.getValues(this.startingWord);
        Map.Entry<String, Node> firstEntry = mapValues.entrySet().iterator().next();
        String partOfSpeech1 = firstEntry.getValue().partOfSpeech1;

        ArrayList<Float> probabilityArray = new ArrayList<>();
        Sequence s = new Sequence(this.startingWord, partOfSpeech1, 1, 1, probabilityArray);
        ArrayList<Integer> levelCounters = new ArrayList<>();
        for (int i = 0; i < this.sentenceSpec.length; i++) {
            levelCounters.add(0);
        }

        while (s.level <= this.sentenceSpec.length) {
            int levelCountersIndex = s.level-1;
            System.out.println("While Loop Start, Level: " + s.level + ", Counter: " + levelCounters.get(levelCountersIndex));
            ArrayList<String> word2s = this.getNextWords(s.lastWord);
            if (s.level == this.sentenceSpec.length-1 && levelCounters.get(s.level) == word2s.size()) {
                break;
            }
            if (word2s.size() > 0) {
                String word2 = word2s.get(levelCounters.get(s.level));

                float probabilityOfWord2 = this.getProbability(s.lastWord, word2);
                s = s.addWordToSequence(word2, this.getPartOfSpeech2(s.lastWord, word2), probabilityOfWord2);
                System.out.println("Added \"" + s.lastWord + "\" to our stack");
            } else {
                System.out.println("Removing \'" + s.lastWord + "\" from our stack");
                s.removeWordFromSequence();
                levelCounters.set(levelCountersIndex, levelCounters.get(levelCountersIndex)+1);
            }

            if (s.level == this.sentenceSpec.length){
                s.removeWordFromSequence();
                levelCounters.set(levelCountersIndex, levelCounters.get(levelCountersIndex)+1);
            }
            System.out.println("While Loop End, Level " + s.level + ", Counter: " + levelCounters.get(levelCountersIndex));
            s.printSequence();
        }

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

    public float multiplyProbArray(ArrayList<Float> arr) {
        float prob = 1;
        for (float p: arr) {
            prob = prob * p;
        }
        return prob;
    }


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
            ArrayList<Float> probabilityArray = new ArrayList<>();
            Sequence s = new Sequence(seq.sentence, seq.sentenceSpec, seq.level, seq.probability, probabilityArray);
            float probabilityOfWord2 = this.getProbability(seq.lastWord, word2);
            s = s.addWordToSequence(word2, this.getPartOfSpeech2(seq.lastWord, word2), probabilityOfWord2);
            if (isSequenceValid(s)) {
                this.validSequences.add(this.validSequences.size(), s);
            }
        }
    }

    public boolean isSequenceValid(Sequence seq) {
        boolean valid = true;
        String[] seqArray = seq.sentenceSpec.split("-");
        for (int i = 0; i < seqArray.length; i++) {
            if (!seqArray[i].equals(this.sentenceSpec[i])) { valid = false; }
        }
        return valid;
    }

    public Sequence getBestSequence(int level) {
        ArrayList<Float> probabilityArray = new ArrayList<>();
        Sequence best = new Sequence("TTAV", "TT-AV", -1, 0, probabilityArray);
        float probSoFar = 0;
        for(Sequence s: this.validSequences) {
            if (s.level == level && s.probability > probSoFar) {
                probSoFar = s.probability;
                best = s;
            }
            this.nodesConsidered++;
        }
        return best;
    }

    public void printSequences() {
        for (Sequence s: this.validSequences) {
            s.printSequence();
        }
    }

}
