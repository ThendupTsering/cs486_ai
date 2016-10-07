package parser;

import java.util.ArrayList;

/**
 * Created by athinavandame on 2016-10-05.
 */
public class Sequence {

    String sentence;
    float probability;
    ArrayList<Float> probabilityArray;
    String lastWord;
    String sentenceSpec;
    int level;

    public Sequence(String word, String sentenceSpec, int level, float probability, ArrayList<Float> probabilityArray) {
        this.sentence = word;
        this.probability = probability;
        this.probabilityArray = new ArrayList<>();
        this.probabilityArray.add(probability);
        this.lastWord = word;
        this.sentenceSpec = sentenceSpec;
        this.level = level;
    }

    public Sequence addWordToSequence(String word, String sentenceSpec, Float probability) {
        this.sentence = this.sentence + " " + word;
        this.probability = this.probability * probability;
        this.probabilityArray.add(probability);
        this.lastWord = word;
        this.sentenceSpec = this.sentenceSpec + "-" + sentenceSpec;
        this.level++;
        return this;
    }

    public void removeWordFromSequence() {
        this.sentence = this.sentence.replace(" " + this.lastWord, "");
        this.probability = this.probability / this.probabilityArray.get(this.probabilityArray.size()-1);
        this.probabilityArray.remove(this.level-1);
        this.lastWord = this.sentence.substring(this.sentence.lastIndexOf(' ') + 1, this.sentence.length());
        this.sentenceSpec = this.sentenceSpec.substring(0, this.sentenceSpec.lastIndexOf('-'));
        this.level--;

    }

    public void printSequence() {
        System.out.println("Sentence: " + this.sentence);
        System.out.println("Probability: " + this.probability);
        System.out.println("Last Word: " + this.lastWord);
        System.out.println("Sentence Spec: " + this.sentenceSpec);
        System.out.println("Level: " + this.level);
        System.out.println();
    }

}
