package parser;

/**
 * Created by athinavandame on 2016-10-05.
 */
public class Sequence {

    String sentence;
    float probability;
    String lastWord;
    String sentenceSpec;
    int level;

    public Sequence(String word, String sentenceSpec, int level, float probability) {
        this.sentence = word;
        this.probability = probability;
        this.lastWord = word;
        this.sentenceSpec = sentenceSpec;
        this.level = level;
    }

    public Sequence addWordToSequence(String word, String sentenceSpec, Float probability) {
        this.sentence = this.sentence + " " + word;
        this.probability = this.probability * probability;
        this.lastWord = word;
        this.sentenceSpec = this.sentenceSpec + "-" + sentenceSpec;
        this.level = this.level+1;
        return this;
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
