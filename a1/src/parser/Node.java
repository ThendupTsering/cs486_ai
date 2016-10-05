package parser;

/**
 * Created by athinavandame on 2016-10-04.
 */
public class Node {

    String partOfSpeech1;
    String partOfSpeech2;
    float probability;

    public Node(String partOfSpeech1, String partOfSpeech2, float probability){
        this.partOfSpeech1 = partOfSpeech1;
        this.partOfSpeech2 = partOfSpeech2;
        this.probability = probability;
    }

}
