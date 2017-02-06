import java.util.Random;
import java.util.Scanner;

/**
 * This object is based off the idea of evolution and genetics. An offspring receives
 * a random assortment of genes from its parent, some from the mother and others from the 
 * father. Some genes may also be mutated. This is the basic concept is to "evolve." Its 
 * starts with randomly generated strings and through "natural selection" the start becomes
 * a target string.
 * Limitations: Not the most efficient algorithm 
 */
public class Evolution {
	String targetPattern;
	String[] generatedPatterns;
	int length;
	Scanner scnr = new Scanner(System.in);
	Random r = new Random();
	String bestPattern;
	String bestPattern2;
	int numGenerations;
	String foundPattern;
	boolean found;

	public Evolution() {
		generatedPatterns = new String[10];
		numGenerations = 1;
		found = false;
	}

	/**
	 * This method get a string of numbers from a user which will become the target string.
	 *  Some limitations: must be numbers, not characters
	 */
	public void getTargetPattern() {
		boolean valid = false;
		System.out.println("Please enter some numbers:");
		while (!valid) {
			valid = true;
			
			//It must be a number, so I am checking for the next long
			if (scnr.hasNextLong()) {
				targetPattern = "" + scnr.nextLong();
			}
			else{
				valid=false;
				System.out.println("You must enter a number.Try again.");
			}
			
			//It takes a long time if the length is greater than 8
//			if (valid&&targetPattern.length() > 10) {
//				System.out.println("The length for the target pattern must be 8 or less. " +
//						"Please renter a number:");
//				valid = false;
//			}
			
			if (valid){
				length=targetPattern.length();
			}
			//Empty the scanner 
			scnr.nextLine();
		}
	}

	/**
	 * This method creates 10 randomly generated strings of numbers. This is the
	 * starting point for the algorithm.
	 */
	public void getGeneratedPatterns() {
		String pattern = new String();
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < length; i++) {
				pattern = pattern + r.nextInt(10);
			}
			generatedPatterns[j] = pattern;
			
			//There's a chance the target string was randomly generated
			if (checkIfFound(pattern)){
				System.out.println("Original patterns before mixing: ");
				printPatterns(j);
				return;
			}
			pattern = "";
		}
		System.out.println("Original patterns before mixing: ");
		printPatterns(9);

	}	
	/**
	 * Prints all the patterns
	 */
	public void printPatterns(int limit) {
		for (int i = 0; i <= limit; i++) {
			System.out.println(generatedPatterns[i]);
		}
	}
	/**
	 * This scans through all the generated patterns and picks the two that are the
	 *  most similar to the target string. This is the "mother" and "father" string
	 *  which will be the base for the offspring strings.
	 */
	public void findBestPatterns() {
		//BestPattern cannot be empty
		bestPattern = generatedPatterns[0];
		bestPattern2 = generatedPatterns[1];
		
		int count = 0;
		int countBest = 0;
		int countBest2 = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < length; j++) {
				if (targetPattern.charAt(j) == generatedPatterns[i].charAt(j)) {
					//keep track of how many numbers are in the right position
					count++;
				}
			}
			if (count > countBest || count > countBest2) {
				//replace the bestPattern with the lowest score
				if (countBest > countBest2) {
					countBest2 = count;
					bestPattern2 = generatedPatterns[i];
				} else {
					countBest = count;
					bestPattern = generatedPatterns[i];
				}
			}
			count = 0;
		}
		System.out.println("Best Generated Pattern 1:" + bestPattern);
		System.out.println("Best Generated Pattern 2:" + bestPattern2);
	}
	/**
	 * Checks if the pattern matches the target pattern
	 */
	public boolean checkIfFound(String pattern){
		if (pattern.equals(targetPattern)) {
			found = true;
			foundPattern=pattern;
			return true;
		}
		return false;
	}

	/**
	 * Using the "parent" strings, a new generation of strings are created. At each position,
	 * the mother or the father's number is randomly selected. And mutations are also introduced.
	 */
	public void createNewGeneration() {
		numGenerations++;
		boolean firstPattern;
		String pattern = "";
		//In nature, mutation are based on different factors. To mimic this, the frequency
		//of mutations (mutation index) is randomly generated
		int mutation = r.nextInt(8) + 1;
		System.out.println("Mutation index:" + mutation);
		int count = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < length; j++) {
				//determines which "parent" passes on its genes (number)
				firstPattern = r.nextBoolean();
				count++;
				if (count == mutation) {
					pattern = pattern + r.nextInt(10);
					count = 0;
				} else if (firstPattern) {
					pattern = pattern + bestPattern.charAt(j);
				} else {
					pattern = pattern + bestPattern2.charAt(j);
				}
			}
			generatedPatterns[i] = pattern;
			
			//check if the target string was created
			if (checkIfFound(pattern)){
				System.out.println("Generated Patterns: ");
				printPatterns(i);
				return;
			}
			pattern = "";
			
		}
		System.out.println("Generated Patterns: ");
		printPatterns(9);

	}
}
