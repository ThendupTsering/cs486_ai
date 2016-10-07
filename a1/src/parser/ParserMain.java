package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.*;

public class ParserMain {

	public static String generate(String startingWord, String[] sentenceSpec, String searchStrategy, String graph) {

		Parser parser = new Parser(startingWord, sentenceSpec);
		String output;

		parser.parseGraphToMainMap(graph);

		if(searchStrategy.equals("BREADTH_FIRST")){
			parser.findSequencesBFS();
			Sequence best = parser.getBestSequence(sentenceSpec.length);
			String bestProbability = String.format("%.12f", best.probability);
			output = "\"" + best.sentence + "\"" + " with probability " + bestProbability
					+ "\nTotal nodes considered: " + parser.nodesConsidered;
		} else if(searchStrategy.equals("DEPTH_FIRST")){
			parser.findSequencesDFS();
			output = "";
		} else { // HEURISTIC
			Sequence sentence = parser.findSequencesHS();
			String sentenceProbability = String.format("%.12f", sentence.probability);
			output = "\"" + sentence.sentence + "\"" + " with probability " + sentenceProbability
					+ "\nTotal nodes considered: " + parser.nodesConsidered;
		}

//		parser.printSequences();

		return output;

	}

	public static void main(String args[]) {
		String graph = "input.txt";
		String[] searchStrategies = {"BREADTH_FIRST", "DEPTH_FIRST", "HEURISTIC"};
//
//		// Part 1
//		String startingWord = "hans";
//		String[] sentenceSpec = {"NNP", "VBD", "DT", "NN"};
//		String output = generate(startingWord, sentenceSpec, searchStrategies[0], graph);
//		System.out.println(output);
//		System.out.println();
//
//		// Part 2.1
//		String startingWord1 = "benjamin";
//		String[] sentenceSpec1 = {"NNP", "VBD", "DT", "NN"};
//		String output1 = generate(startingWord1, sentenceSpec1, searchStrategies[0], graph);
//		System.out.println(output1);
//		System.out.println();
//
//		// Part 2.2
//		String startingWord2 = "a";
//		String[] sentenceSpec2 = {"DT", "NN", "VBD", "NNP"};
//		String output2 = generate(startingWord2, sentenceSpec2, searchStrategies[0], graph);
//		System.out.println(output2);
//		System.out.println();
//
//		// Part 2.3
//		String startingWord3 = "benjamin";
//		String[] sentenceSpec3 = {"NNP", "VBD", "DT", "JJS", "NN"};
//		String output3 = generate(startingWord3, sentenceSpec3, searchStrategies[0], graph);
//		System.out.println(output3);
//		System.out.println();
//
//		// Part 2.4
//		String startingWord4 = "a";
//		String[] sentenceSpec4 = {"DT", "NN", "VBD", "NNP", "IN", "DT", "NN"};
//		String output4= generate(startingWord4, sentenceSpec4, searchStrategies[0], graph);
//		System.out.println(output4);

		// Part 3 DEPTH FIRST
//		String startingWordDPS = "hans";
//		String[] sentenceSpecDPS = {"NNP", "VBD", "DT", "NN"};
//		String outputDPS = generate(startingWordDPS, sentenceSpecDPS, searchStrategies[1], graph);
//		System.out.println(outputDPS);
//		System.out.println();
//
//		// Part 3 HEURISTIC
		String startingWordHS = "hans";
		String[] sentenceSpecHS = {"NNP", "VBD", "DT", "NN"};
		String outputHS = generate(startingWordHS, sentenceSpecHS, searchStrategies[2], graph);
		System.out.println(outputHS);
		System.out.println();
	}
}
