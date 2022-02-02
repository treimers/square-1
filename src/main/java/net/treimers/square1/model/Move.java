package net.treimers.square1.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.treimers.square1.exception.Square1Exception;

public class Move {
	// https://www.regexplanet.com/advanced/java/index.html
	private static final String PATTERN_STRING = "(?:(-?\\d+),(-?\\d+))([/-])?|([/-])";
	public static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);
	private int topRotation;
	private int bottomRotation;
	private boolean twisted;

	public Move(String move) throws Square1Exception {
		Matcher m = PATTERN.matcher(move);
		if (!m.matches())
			throw new Square1Exception("Illegal move " + move);
		if (m.group(4) != null) {
			topRotation = 0;
			bottomRotation = 0;
			twisted = m.group(4).equals("/");
		} else {
			topRotation = m.group(1) == null ? 0 : Integer.parseInt(m.group(1));
			bottomRotation = m.group(2) == null ? 0 : Integer.parseInt(m.group(2));
			twisted = m.group(3) != null && m.group(3).equals("/");
		}
	}

	public Move(int topRotation, int bottomRotation, boolean twisted) {
		this.topRotation = topRotation;
		this.bottomRotation = bottomRotation;
		this.twisted = twisted;
	}

	
	public int getTopRotation() {
		return topRotation;
	}

	public void setTopRotation(int topRotation) {
		this.topRotation = topRotation;
	}

	public int getBottomRotation() {
		return bottomRotation;
	}

	public void setBottomRotation(int bottomRotation) {
		this.bottomRotation = bottomRotation;
	}

	public boolean isTwisted() {
		return twisted;
	}

	public void setTwisted(boolean twisted) {
		this.twisted = twisted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bottomRotation, topRotation, twisted);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		return bottomRotation == other.bottomRotation && topRotation == other.topRotation && twisted == other.twisted;
	}

	@Override
	public String toString() {
		String val = "";
		if (topRotation != 0 || bottomRotation != 0)
			val = topRotation + "," + bottomRotation;
		val += twisted ? "/" : "-";
		return val;
		/*
		return topRotation + "," + bottomRotation + (twisted ? "/" : "-");
		*/
	}

	public static Move fromString(String move) throws Square1Exception {
		return new Move(move);
	}
}
