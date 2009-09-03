package rja.codejam.qual2;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Describe class Watersheds here.
 *
 *
 * Created: Thu Sep  3 23:07:07 2009
 *
 * @author <a href="mailto:rajeshja@rja-desktop-lnx">Rajesh</a>
 * @version 1.0
 */
public final class Watersheds {

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

				char[][] output = processMap(in);
				out.printf("Case #%d:", i+1);
				out.println();
				for (int j=0; j<output.length; j++) {
					int k=0;
					for (; k<output[0].length-1; k++) {
						out.printf("%c ", output[j][k]);
					}
					out.printf("%c", output[j][k]);
					out.println();
				}
			} catch (IOException e) {
				System.out.println("Error reading the input file. File might not have enough lines.");
				throw e;
			} catch (NumberFormatException e) {
				System.out.println("Error reading the input file. File might not have enough lines.");
				throw e;
			}
		}

		in.close();
		out.close();

	}

	private char[][] processMap(BufferedReader in) throws NumberFormatException, IOException {

		String[] dimensions=in.readLine().split(" +");
		int height=Integer.parseInt(dimensions[0]);
		int width=Integer.parseInt(dimensions[1]);

		WaterMap map = new WaterMap();

		map.readMap(in, height, width);
		map.processFlow();
		return map.getBasinLabels();
	}

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: AlienTranslator <inputfile> <outputfile>");
		}

		Watersheds t = new Watersheds();

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
