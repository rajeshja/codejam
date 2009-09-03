package rja.test;

/**
 * Describe class Main here.
 *
 *
 * Created: Fri Sep  4 00:51:37 2009
 *
 * @author <a href="mailto:rajeshja@rja-desktop-lnx">Rajesh</a>
 * @version 1.0
 */
public final class Main {

	private Main() {

	}

	private static void update(int[][] array) {
		array[1][1] = 5;
	}


	public static void main(final String[] args) {
		
		int[][] array = new int[3][4];

		update(array);
		
		System.out.println(array[0][0]);
		System.out.printf("%03d", array[1][1]);
		System.out.println();
		System.out.println(array[2][3]);
		
	}

}
