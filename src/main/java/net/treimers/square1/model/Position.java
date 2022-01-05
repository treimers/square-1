package net.treimers.square1.model;

public class Position {
	private String positionString;
	private int angle;

	public Position() {
		positionString = "A1B2C3D45E6F7G8H-";
		angle = 0;
	}

	public Position(Position position) {
		this.positionString = position.positionString;
		this.angle = position.angle;
	}
	
	public boolean accept(String name) {
		// do not allow anything after all positions filled
		if (positionString.length() >= 17)
			return false;
		// allow middle pieces as last position only
		if (name.equals("-") || name.equals("/"))
			return positionString.length() == 16;
		// disallow A-H pieces when only small slot left in top layer
		if (angle == 11 && Character.isAlphabetic(name.charAt(0)))
			return false;
		return !positionString.contains(name);
	}

	public boolean add(String name) {
		if (accept(name)) {
			if (Character.isAlphabetic(name.charAt(0)))
				angle++;
			angle++;
			positionString += name;
			return true;
		} else
			return false;
	}

	public void clear() {
		positionString = "";
		angle = 0;
	}

	@Override
	public String toString() {
		return positionString;
	}
}
