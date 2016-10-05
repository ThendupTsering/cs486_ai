package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.*;

public class ParserMain {

	public static String generate(String startingWord, String[] sentenceSpec, String graph) {

		Parser parser = new Parser();
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


		ArrayList<String> word2s = parser.getNextWords(startingWord);
		Iterator it = word2s.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}

		output = "hello";

//		String word1 = startingWord;
//
//		Map <String, Node> mapValues = parser.getValues(word1);
//		Map.Entry<String, Node> firstEntry = mapValues.entrySet().iterator().next();
//		String partOfSpeech1 = firstEntry.getValue().partOfSpeech1;
//
//		if (partOfSpeech1 == sentenceSpec[0]) {
//			for (int index = 1; index < 4; index++) {
//				parser.generateHelper(word1, sentenceSpec[index], index);
//			}
//		}

////		float[] allProbabilities = new float[sentenceSpec.length];
//		int index = 0;
//
//		Map <String, Node> mapValues = parser.getValues(startingWord);
//
//		Map.Entry<String, Node> firstEntry = mapValues.entrySet().iterator().next();
//		String partOfSpeech1 = firstEntry.getValue().partOfSpeech1;
//
//		if (partOfSpeech1 == sentenceSpec[index]) {
////			allProbabilities[index] = 1;
//			index++;
//
//			for (Map.Entry<String,Node> entry : mapValues.entrySet()) {
//				String word2 = entry.getKey();
//				Node word2Values = entry.getValue();
//				String wordsSoFarTemp;
//
//				if (word2Values.partOfSpeech2 == sentenceSpec[index]) {
//					wordsSoFarTemp = wordsSoFar + " " + word2;
////					allProbabilities[index] = tempProbability;
//					parser.wordsWithProbabilities.put(wordsSoFar, word2Values.probability);
//				}
//			}
//		}

		return output;

	}

	public static void main(String args[]) {
		String[] sentenceSpec = {"NNP", "VBD", "DT", "NN"};
		String startingWord = "hans";
		String graph = "input.txt";

		String output = generate(startingWord, sentenceSpec, graph);
		System.out.println(output);

	}
}
