package net.treimers.square1.model;

public class Position {
	private String positionString;

	public Position() {
		positionString = "";
	}

	public boolean accept(String name) {
		return !positionString.contains(name);
	}

	public boolean add(String name) {
		if (accept(name)) {
			positionString += name;
			return true;
		} else
			return false;
	}

	public void reset() {
		positionString = "";
	}
	
	@Override
	public String toString() {
		return positionString;
	}
}
