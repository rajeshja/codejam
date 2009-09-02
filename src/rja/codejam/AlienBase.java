package rja.codejam;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe class AlienBase here.
 *
 *
 * Created: Wed Sep 02 18:15:23 2009
 *
 * @author <a href="mailto:rajeshja@GDYLPT13432"></a>
 * @version 1.0
 */
public class AlienBase {

	private int base;
	private char[] baseDigits;
	private Map<Character, Integer> baseDigitMap;

	public AlienBase(String baseString) {

		base = baseString.length();
		baseDigits = new char[base];
		baseString.getChars(0, base, baseDigits, 0);

		baseDigitMap = new HashMap<Character, Integer>();

		for (int i=0; i<base; i++) {
			baseDigitMap.put(baseDigits[i], i);
		}
	}

	public int convertToBase10(String input) {

		System.out.println("input = " + input + "; base digits = " + new String(baseDigits));
		System.out.println(baseDigitMap);

		int value = 0;
		char[] digits = new char[input.length()];
		input.getChars(0, input.length(), digits, 0);

		for (int i=0; i<digits.length; i++) {
			int pos = digits.length-i-1;

			int currDigitValue = baseDigitMap.get(digits[pos]);
			value += (currDigitValue * power(base, i));
		}

		return value;
	}

	public String convertFromBase10(int input) {
		
		int value = input;
		
		String result = "";

		while (value > 0) {
			int remainder = value % base;
			value = value / base;

			result = baseDigits[remainder] + result;
		}

		return result;
	}

	private int power(int value, int power) {

		int result = 1;

		for (int i=0; i<power; i++) {
			result *= value;
		}

		return result;
	}

}
