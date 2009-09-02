package rja.codejam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class AlienTranslator {

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
				String output = processLine(currLine);
				out.printf("Case #%d: %s", i+1, output);
				out.println();
			} catch (IOException e) {
				System.out.println("Error reading the input file. File might not have enough lines.");
				throw e;
			}
		}

		in.close();
		out.close();

	}

	private String processLine(String testCase) {
		String[] fields = testCase.split(" +");

		if (fields.length != 3) {
			System.out.println("There must be exactly 3 fields on each line!" 
							   + "Fields on current line = " + fields.length);
			throw new IllegalArgumentException("Wrong number of fields on the line.");
		}

		AlienBase srcBase = new AlienBase(fields[1]);
		AlienBase destBase = new AlienBase(fields[2]);

		return destBase.convertFromBase10(srcBase.convertToBase10(fields[0]));
	}
	

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: AlienTranslator <inputfile> <outputfile>");
		}

		AlienTranslator t = new AlienTranslator();

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
