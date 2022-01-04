package net.treimers.square1.model;

public class Position {
	private String position;

	public Position() {
		position = "";
	}

	public boolean accept(String name) {
		return !position.contains(name);
	}

	public boolean add(String name) {
		if (accept(name)) {
			position += name;
			return true;
		} else
			return false;
	}

	public void reset() {
		position = "";
	}
	
	@Override
	public String toString() {
		return position;
	}
}
