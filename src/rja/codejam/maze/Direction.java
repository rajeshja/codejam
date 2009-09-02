package rja.codejam.maze;

public enum Direction {
	
	N, E, S, W;
	
	public Direction clockwise() {
		return values()[(ordinal()+1)%values().length];
	}
	
	public Direction counterClockwise() {
		return values()[(ordinal()+3)%values().length];
	}
	
}
