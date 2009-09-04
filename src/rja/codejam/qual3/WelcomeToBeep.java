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
				long output = processLine(currLine);
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

	private long processLine(String testCase) {

		long count=0;

		count = findMatches(testCase, PATTERN, 0);

		if (true) {
			count = (count+1)%10000;
		}

		return count%10000;
	}

	private long findMatches(String testCase, String pattern, long count) {

		char firstPatternChar = pattern.charAt(0);
		int firstMatch = testCase.indexOf(firstPatternChar);

		if (firstMatch == -1) {
			return 0;
		} else {
			count++;
			if (pattern.length() != 1) {
				String remainingCase = testCase.substring(firstMatch+1);
				String remainingPattern = pattern.substring(1);

				long returnedCount = findMatches(remainingCase, remainingPattern, count);
				//count = (count*returnedCount);
			} else {
				//Last char of pattern
				

				if (testCase.length() != 1) {
					String remainingCase = testCase.substring(firstMatch+1);
					long returnedCount = findMatches(remainingCase, pattern, count);
					count = (count+returnedCount);
				} else {
					return count;
				}
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
