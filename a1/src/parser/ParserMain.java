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
			Sequence best = parser.getBestSequence(sentenceSpec.length);
			String bestProbability = String.format("%.12f", best.probability);
			output = "\"" + best.sentence + "\"" + " with probability " + bestProbability
					+ "\nTotal nodes considered: " + parser.nodesConsidered;
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

		System.out.println("**************** BREADTH FIRST *******************");
		System.out.println();

		// Part 1
		String startingWord = "hans";
		String[] sentenceSpec = {"NNP", "VBD", "DT", "NN"};
		String output = generate(startingWord, sentenceSpec, searchStrategies[0], graph);
		System.out.println(output);
		System.out.println();

		// Part 2.1
		String startingWord1 = "benjamin";
		String[] sentenceSpec1 = {"NNP", "VBD", "DT", "NN"};
		String output1 = generate(startingWord1, sentenceSpec1, searchStrategies[0], graph);
		System.out.println(output1);
		System.out.println();

		// Part 2.2
		String startingWord2 = "a";
		String[] sentenceSpec2 = {"DT", "NN", "VBD", "NNP"};
		String output2 = generate(startingWord2, sentenceSpec2, searchStrategies[0], graph);
		System.out.println(output2);
		System.out.println();

		// Part 2.3
		String startingWord3 = "benjamin";
		String[] sentenceSpec3 = {"NNP", "VBD", "DT", "JJS", "NN"};
		String output3 = generate(startingWord3, sentenceSpec3, searchStrategies[0], graph);
		System.out.println(output3);
		System.out.println();

		// Part 2.4
		String startingWord4 = "a";
		String[] sentenceSpec4 = {"DT", "NN", "VBD", "NNP", "IN", "DT", "NN"};
		String output4= generate(startingWord4, sentenceSpec4, searchStrategies[0], graph);
		System.out.println(output4);
		System.out.println();


		System.out.println();
		System.out.println("**************** DEPTH FIRST *******************");
		System.out.println();

		// Part 3 DEPTH FIRST
		String outputDPS = generate(startingWord, sentenceSpec, searchStrategies[1], graph);
		System.out.println(outputDPS);
		System.out.println();

		// Part 3.1
		String outputDPS1 = generate(startingWord1, sentenceSpec1, searchStrategies[1], graph);
		System.out.println(outputDPS1);
		System.out.println();

		// Part 3.2
		String outputDPS2 = generate(startingWord2, sentenceSpec2, searchStrategies[1], graph);
		System.out.println(outputDPS2);
		System.out.println();

		// Part 3.3
		String outputDPS3 = generate(startingWord3, sentenceSpec3, searchStrategies[1], graph);
		System.out.println(outputDPS3);
		System.out.println();

//		// Part 3.4
//		String outputDPS4= generate(startingWord4, sentenceSpec4, searchStrategies[1], graph);
//		System.out.println(outputDPS4);
//		System.out.println();


		System.out.println();
		System.out.println("**************** HEURISTIC *******************");
		System.out.println();

		// Part 4 HEURISTIC
		String outputHS = generate(startingWord, sentenceSpec, searchStrategies[2], graph);
		System.out.println(outputHS);
		System.out.println();

		// Part 4.1
		String outputHS1 = generate(startingWord1, sentenceSpec1, searchStrategies[2], graph);
		System.out.println(outputHS1);
		System.out.println();

		// Part 4.2
		String outputHS2 = generate(startingWord2, sentenceSpec2, searchStrategies[2], graph);
		System.out.println(outputHS2);
		System.out.println();

		// Part 4.3
		String outputHS3 = generate(startingWord3, sentenceSpec3, searchStrategies[2], graph);
		System.out.println(outputHS3);
		System.out.println();

		// Part 4.4
		String outputHS4= generate(startingWord4, sentenceSpec4, searchStrategies[2], graph);
		System.out.println(outputHS4);
		System.out.println();


	}
}
