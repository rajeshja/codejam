package rja.codejam.qual3;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class WelcomeToBeep {

	private static final String PATTERN = "welcome to code jam";

	private void processInput(BufferedReader in, PrintWriter out) throws IOException {

		int count;
		try {
			count = Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			System.out.println("The first line in the input must be the number of test cases in the file.");
			throw e;
		}

		for (int i=0; i<count; i++) {
			
			try {
				String currLine = in.readLine();
				System.out.println("Processing line " + currLine);
				int output = processLine(currLine);
				out.printf("Case #%d: %04d", i+1, output);
				out.println();
			} catch (IOException e) {
				System.out.println("Error reading the input file. File might not have enough lines.");
				throw e;
			}
		}

		in.close();
		out.close();

	}

	private int processLine(String testCase) {

		int count=0;
		int positions[] = new int[PATTERN.length()];
		for (int p=0; p<positions.length; p++) {
			positions[p] = -1;
		}

		//Fint;

		boolean patternFound=true;
		//Traverse the test case
		for (int startPosition=0;
			 (patternFound && (startPosition<=(testCase.length()-PATTERN.length())));
			 startPosition++) {

			//Look for each character one by one
			boolean positionFound=true;
			for (int patternPosition=0;
				 (positionFound && patternPosition<PATTERN.length());
				 patternPosition++) {

				//Look for current character
				boolean currentPatternPositionFound=false;
				for (int testCasePosition=(((positions[patternPosition]==-1) && (patternPosition!=0)) 
										   ? positions[patternPosition-1]+1 
										   : positions[patternPosition]+1);
					 (!currentPatternPositionFound
					  &&(testCasePosition<=(testCase.length()-(PATTERN.length()-patternPosition))));
					 testCasePosition++) {
					if (testCase.charAt(testCasePosition) == PATTERN.charAt(patternPosition)) {
						positions[patternPosition] = testCasePosition;
						currentPatternPositionFound=true;
					}
				}
				//Done looking for current character. Found?
				if (positions[patternPosition] == -1) {
					positionFound = false;
				}

			}
			//Done looking for pattern. Found?
			if (positionFound == false) {
				patternFound = false;
			} else {
				count = (count+1)%10000;
			}

		}

		return count;
	}
	
	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: WelcomeToBeep <inputfile> <outputfile>");
		}

		WelcomeToBeep t = new WelcomeToBeep();

		try {

			BufferedReader in;
			PrintWriter out;
			
			in = new BufferedReader(new FileReader(args[0]));
			out = new PrintWriter(new FileWriter(args[1]));
			t.processInput(in, out);
		} catch (IOException e) {
			System.out.println("Error processing input.");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Error in input file.");
			e.printStackTrace();
		}
	}

}
