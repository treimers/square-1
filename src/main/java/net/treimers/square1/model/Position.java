package net.treimers.square1.model;

public class Position {
	private static final String SOLVED_POSITION_STRING = "A1B2C3D45E6F7G8H-";
	private String positionString;
	private int angle;

	public static Position fromString(String positionString) {
		return new Position(positionString);
	}
			
	public Position() {
		this.positionString = SOLVED_POSITION_STRING;
		this.angle = 24;
	}
	
	public Position(Position position) {
		this.positionString = position.positionString;
		this.angle = position.angle;
	}
	
	protected Position(String positionString) {
		this.positionString = positionString;
		angle = 0;
		for (int i = 0; i < positionString.length(); i++) {
			char c = positionString.charAt(i);
			if (Character.isLetter(c))
				angle++;
			angle++;
		}
	}

	public boolean accept(String name) {
		// do not allow anything after all positions filled
		if (positionString.length() >= SOLVED_POSITION_STRING.length())
			return false;
		// allow middle pieces as last position only
		if (name.equals("-") || name.equals("/"))
			return positionString.length() == SOLVED_POSITION_STRING.length() - 1;
		// disallow A-H pieces when only small slot left in top layer
		if (angle == 11 && Character.isAlphabetic(name.charAt(0)))
			return false;
		return !positionString.contains(name);
	}

	public boolean allows(String name) {
		if (name.equals("-") || name.equals("/"))
			return positionString.length() < SOLVED_POSITION_STRING.length();
		return !positionString.contains(name);
	}
	
	public boolean add(String name) {
		if (accept(name)) {
			if (Character.isAlphabetic(name.charAt(0)))
				angle++;
			this.angle++;
			this.positionString += name;
			return true;
		} else
			return false;
	}

	public void clear() {
		positionString = "";
		angle = 0;
	}

	public int getLength() {
		return this.positionString.length();
	}
	
	public char getPiece(int index) {
		return this.positionString.charAt(index);
	}
	
	@Override
	public String toString() {
		return this.positionString;
	}
}
