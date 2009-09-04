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

		char[] testChars = new char[testCase.length()];
		testCase.getChars(0, testCase.length(), testChars, 0); 
		char[] patternChars = new char[PATTERN.length()];
		PATTERN.getChars(0, PATTERN.length(), patternChars, 0); 

		count = findMatches(testCase, PATTERN);

		return count%10000;
	}

//	private int findMatches(char[] testCase, int tPos, char[] pattern, int pPos) {
//
//		int count = 0;
//
//		int matchIndex;
//		do {
//			matchIndex = find(testCase. tPos, pattern[pPos]);
//
//			if (matchIndex != -1) {
//
//				tPos = matchIndex;
//
//				if (pattern.length == pPos) {
//					//The multiplication is the reason I
//					//can't remove leading digits at this stage.
//					//Or can I? Oh.
//					count += findMatches(testCase, tPos, pattern, pPos+1);
//				} else {
//					count++;
//				}
//				count %= 10000;
//			}
//		} while (matchIndex != -1);
//		
//		return count;
//	}

//	private int find(char[] case, int startPos, char patternChar) {
//		int index=-1;
//		for (int i=startPos; i<case.length; i++) {
//			if (case[i] == patternChar) {
//				index = i;
//			}
//		}
//		return index;
//	}

	private int findMatches(String testCase, String pattern) {

		int count = 0;

		int matchIndex;
		do {
			matchIndex = testCase.indexOf(pattern.charAt(0));

			if (matchIndex != -1) {

				testCase = testCase.substring(matchIndex + 1);

				if (pattern.length() != 1) {
					//The multiplication is the reason I
					//can't remove leading digits at this stage.
					//Or can I? Oh.
					count += findMatches(testCase, pattern.substring(1));
				} else {
					count++;
				}
				count %= 10000;
			}
		} while (matchIndex != -1);
		
		return count;
	}

	
	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: WelcomeToBeep <inputfile> <outputfile>");
		}

		long start = System.currentTimeMillis();
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

		long end = System.currentTimeMillis();

		System.out.println("Took " + (end-start)/1000 + " seconds.");
	}

}
