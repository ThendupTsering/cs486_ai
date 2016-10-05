package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.*;

public class ParserMain {

	public static String generate(String startingWord, String[] sentenceSpec, String graph) {

		Parser parser = new Parser(startingWord, sentenceSpec);
		String output = "";

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
				parser.addToMap(word1, partOfSpeech1, word2, partOfSpeech2, probability);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		Map <String, Node> mapValues = parser.getValues(startingWord);
		Map.Entry<String, Node> firstEntry = mapValues.entrySet().iterator().next();
		String partOfSpeech1 = firstEntry.getValue().partOfSpeech1;

		if (partOfSpeech1.equals(sentenceSpec[0])) {
			parser.parseFrom(startingWord);
		}

		return output;

	}

	public static void main(String args[]) {
//		String[] sentenceSpec = {"NNP", "VBD", "DT", "NN"};
		String[] sentenceSpec = {"NNP", "VBD"};
		String startingWord = "hans";
		String graph = "input.txt";

		String output = generate(startingWord, sentenceSpec, graph);

	}
}
