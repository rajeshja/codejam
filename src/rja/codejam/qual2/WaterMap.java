package rja.codejam.qual2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WaterMap {

	private int height;
	private int width;
	private int[][] elevationMap;
	private int[][] flowMap;

	private char[] basinNames = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};

	private int currSubPath = 1;

	private Map<Integer, Integer> pathBasinMap = new HashMap<Integer, Integer>();

	public WaterMap() {}

	public void readMap(BufferedReader in, int height, int width) throws IOException {

		this.height = height;
		this.width = width;

		this.elevationMap = new int[height][width];
		this.flowMap = new int[height][width];

		for (int i=0; i<height; i++) {
			try {
				String[] currLine = in.readLine().split(" +");
				for (int j=0; j<width; j++) {
					try {
					elevationMap[i][j] = Integer.parseInt(currLine[j]);
					} catch (NumberFormatException e) {
						System.out.println("All elevations must be numbers!");
						throw e;
					}
				}
			} catch (IOException e) {
				System.out.println("Error reading the file. There must not be enough lines!");
				throw e;
			}
		}
	}

	public void processFlow() {

		flowMap[0][0] = 0;
		
		for(int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				
				//Decide if this a new sub path
				if (flowMap[i][j] == 0) {

					currSubPath++;
					flowMap[i][j] = currSubPath;

					//Map sub-path to the current basin
					pathBasinMap.put(currSubPath, currSubPath);
				}

				int current = elevationMap[i][j];
				
				int north = Integer.MAX_VALUE;
				int south = Integer.MAX_VALUE;
				int west  = Integer.MAX_VALUE;
				int east  = Integer.MAX_VALUE;

				//Get North elevation
				if (i!=0) {
					north = elevationMap[i-1][j];
				}
				//Get South elevation
				if (i!=(height-1)) {
					south = elevationMap[i+1][j];
				}
				//Get North elevation
				if (j!=0) {
					west = elevationMap[i][j-1];
				}
				//Get North elevation
				if (j!=(width-1)) {
					east = elevationMap[i][j+1];
				}

				//Which direction?
				Direction flow = getDirection(current, north, south, west, east);

				updateFlow(flow, i, j);
			}
		}
	}

	public char[][] getBasinLabels() {
		char[][] basinLabels = new char[height][width];

		Map<Integer, Integer> labelMap = new HashMap<Integer, Integer>();  
		int nextBasinIndex = 0;

		for(int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				int oldestPath = getOldestPath(flowMap[i][j]);
				if (labelMap.get(oldestPath) == null) {
					labelMap.put(oldestPath, nextBasinIndex);
					nextBasinIndex++;
				}
				basinLabels[i][j] = basinNames[labelMap.get(oldestPath)];
			}
		}
		return basinLabels;
	}

	private int getOldestPath(int path) {
		if (pathBasinMap.get(path) == path) {
			return path;
		} else {
			return getOldestPath(pathBasinMap.get(path));
		}
	}

	private void updateFlow(Direction flow, int i, int j) {

		switch (flow) {
		case N: 
			drawFlow(i-1, j, flowMap[i][j]);
			break;
		case S:
			drawFlow(i+1, j, flowMap[i][j]);
				break;
		case W:
			drawFlow(i, j-1, flowMap[i][j]);
				break;
		case E:
			drawFlow(i, j+1, flowMap[i][j]);
				break;
		case C:
		default:
			break;
		}

	}

	private void drawFlow(int i, int j, int subPath) {
		if (flowMap[i][j] == 0) {
			flowMap[i][j] = subPath;
		} else {
			pathBasinMap.put(subPath, flowMap[i][j]);
		}
	}

	private Direction getDirection(int current, int north, int south, int west, int east) {

		Direction direction = Direction.C;
		
		//Doesn't look very efficient, but that's what you get at 1:30am
		if (north<current) {
			direction = Direction.N;
		}

		if ((west<north) && (west<current)) {
			direction = Direction.W;
		}

		if ((east<west) && (east<north) && (east<current)) {
			direction = Direction.E;
		}

		if ((south<east) && (south<west) && (south<north) && (south<current)) {
			direction = Direction.S;
		}
		
		return direction;
	}

	private enum Direction {
		N, W, E, S, C
	}



}
