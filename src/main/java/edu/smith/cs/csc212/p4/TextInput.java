package edu.smith.cs.csc212.p4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextInput {
	/**
	 * The source of data for text input.
	 */
	private BufferedReader input;

	/**
	 * For playing interactively, ask the user for input.
	 */
	public TextInput() {
		this.input = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * For my testing/grading usage, so you can provide me a copy of the winning
	 * script.
	 * 
	 * @param testFile - a script file that wins the game.
	 */
	public TextInput(File testFile) {
		try {
			this.input = new BufferedReader(new FileReader(testFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File does not exist or cannot be read: " + testFile, e);
		}
	}

	/**
	 * For testing, given an array of commands, execute them in order then quit.
	 * 
	 * @param testInput - the commands that the game will run in order.
	 */
	public TextInput(String[] testInput) {
		StringBuilder join = new StringBuilder();
		for (String line : testInput) {
			join.append(line).append('\n');
		}
		this.input = new BufferedReader(new StringReader(join.toString()));
	}

	/**
	 * Get the parsed-out words from the user.
	 * 
	 * @param prompt - what to print before asking the user.
	 * @return
	 */
	public List<String> getUserWords(String prompt) {
		while (true) {
			System.out.print(prompt);
			System.out.print(" ");
			System.out.flush();
			String resp;
			try {
				resp = input.readLine();
				if (resp == null) {
					return Collections.emptyList();
				}
			} catch (IOException e) {
				// Turn user error into a crash.
				throw new RuntimeException("User Input Error", e);
			}

			// Make sure they typed something.
			List<String> input = WordSplitter.splitTextToWords(resp);
			if (!input.isEmpty()) {
				return input;
			}
			// otherwise go around the loop again.
		}

	}

	/**
	 * Ask the user a yes/no question.
	 * 
	 * @param prompt - the question to ask.
	 * @return true if yes, false if no.
	 */
	public boolean confirm(String prompt) {
		while (true) {
			List<String> response = this.getUserWords(prompt + " (y/n): ");
			if (response.contains("yes") || response.contains("y")) {
				return true;
			} else if (response.contains("no") || response.contains("n")) {
				return false;
			}
			System.err.println("Couldn't understand: " + response + " try one of [yes, no]");
		}
	}
	
	/**
	 * Return a list of important words from a string
	 * @param s - string to passed in
	 * @return list of words from string excluding unimportant words.
	 * 		   Will return the list of words in string if the string contains nothing but unimportant words (doesn't return empty list).
	 */
	private List<String> getKeywordsFromString(String s) {
		String[] unimportantWords = new String[] {"a", "an", "the", "of", "some", "many", "lots", "lot", "few"};
		List<String> keywords = WordSplitter.splitTextToWords(s);
		keywords.removeAll(Arrays.asList(unimportantWords));
		if (keywords.size() > 0) {
			return keywords;
		} else {
			return WordSplitter.splitTextToWords(s);
		}
	}
	
	/**
	 * Give the user a list of choice and ask them to choose (according to index).
	 * @param prompt - what to ask
	 * @param choices - list of choice to choose
	 * @return name of the choice the user picked from the list of choices above.
	 */
	public String singleChoiceConfirm(String prompt, List<String> choices) {
		String choiceName = "";
		while (true) {
			// Print the prompt and list of choices
			System.out.println(prompt);
			for (int i=0; i<choices.size(); i++) {
				System.out.println("      ["+i+"] " + choices.get(i));
			}
			// Get user input
			List<String> response = getUserWords(">");
			Integer choiceIndex = null;
			try {
				// Convert choice into Integer type
				choiceIndex = Integer.parseInt(response.get(0));
				choiceName = choices.get(choiceIndex);
				break;
			// Catch all format error and out of bounds error. I didn't bother to fix negative number error.
			// So if you enter -1 it would work as if you enter 1. Not sure to call this a bug or a feature.
			} catch (NumberFormatException | IndexOutOfBoundsException error) {
				System.out.println("Please input a valid number. This request couldn't be simpler.");
			}
		}
		return choiceName;
	}
	
	/**
	 * Give user a list of choice and allow them to enter multiple choice.
	 * @param prompt - What to ask
	 * @param choices - list of choices
	 * @return list of choices the user picked (by numbers or keywords).
	 */
	public List<String> multipleChoicesConfirm(String prompt, List<String> choices) {
		// Unique list of choice
		Set<String> choiceSet = new HashSet<>();
		// Print prompt and list of choices
		System.out.println("Type choice number, keywords (separated) or 'all' to select all:");
		System.out.println(prompt);
		for (int i=0; i<choices.size(); i++) {
			System.out.println("      ["+i+"] " + choices.get(i));
		}
		// Get user input
		List<String> response = getUserWords(">");
		// User type all only
		if (response.size() == 1 && response.get(0).equals("all")) {
			choiceSet.addAll(choices);
			return new ArrayList<String>(choiceSet);
		}
		
		// User type something other than "all"
		for (String word : response) {
			Integer choiceIndex = null;
			// Test if user typed a number
			try {
				choiceIndex = Integer.parseInt(word);
				// Add choice index (if parsed to int) to choiceSet.
				if (choiceIndex	>= 0 || choiceIndex < choices.size()) {
					choiceSet.add(choices.get(choiceIndex));
				}
			} catch (NumberFormatException nfe) {
				// If user didn't type a number, test if user type a word in the choice list.
				for (String choice : choices) {
					// Get keywords and check if the word user typed in is a keywords of a choice
					if (getKeywordsFromString(choice).contains(word)) {
						choiceSet.add(choice);
						break;
					}
				}
			}
		}
		return new ArrayList<String>(choiceSet);
	}

	/**
	 * Use command-line arguments to set up a TextInput.
	 * @param args - from main()
	 * @return a TextInput object.
	 */
	public static TextInput fromArgs(String[] args) {
		// No arguments, this is what you get from Eclipse.
		if (args.length == 0) {
			return new TextInput();
		} else if (args.length == 1) {
			// 1 argument -- I may automatically test your changes to SpookyMansion here.
			return new TextInput(new File(args[0]));
		} else {
			throw new RuntimeException("Not sure what to do with CLI Args: " + Arrays.toString(args));
		}

	}
}
