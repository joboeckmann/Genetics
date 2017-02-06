import java.util.Scanner;

public class Main {


	/**
	 * Main method for the Evolution program
	 */
	public static void main(String[] args) {

		Evolution e = new Evolution();
		
		//First create the original strings that will be modified
		e.getTargetPattern();
		e.getGeneratedPatterns();
		//If we generate the target string, we can quit 
		if (!e.found) {
			e.findBestPatterns();
		}
		System.out.println("***************");

		//Then we create new generations until we find the target string
		while (!e.found) {
			System.out.println("Number of generations: " + e.numGenerations);
			e.createNewGeneration();
			if (!e.found) {
				e.findBestPatterns();
			}
			System.out.println("***************");
		}
		//Final results
		System.out.println("Target Pattern: " + e.targetPattern);
		System.out.println("Pattern Found: " + e.foundPattern);

	}

}
