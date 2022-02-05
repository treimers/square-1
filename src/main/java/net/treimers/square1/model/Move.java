package net.treimers.square1.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.treimers.square1.exception.Square1Exception;

/**
 * Instance of this class are used to model a Square-1 move.
 */
public class Move {
	// https://www.regexplanet.com/advanced/java/index.html
	/** The pattern string used to convert a string representation into a move object. */
	private static final String PATTERN_STRING = "(?:(-?\\d+),(-?\\d+))([/-])?|([/-])";
	/** The pattern used to convert a string representation into a move object. */
	public static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);
	/** The top rotation of this move. */
	private int topRotation;
	/** The bottom rotation of this move. */
	private int bottomRotation;
	/** The twist indicator of this move. */
	private boolean twisted;

	/**
	 * Creates a new move object from a string representation.
	 * 
	 * @param move the string representation.
	 * @throws Square1Exception in case of parse errors.
	 */
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

	/**
	 * Creates a new move using parameters for top rotation, bottom rotation and twist indicator.
	 * 
	 * @param topRotation the top rotation of the new move.
	 * @param bottomRotation the bottom rotation of the new move
	 * @param twisted the twist indicator of the new move
	 */
	public Move(int topRotation, int bottomRotation, boolean twisted) {
		this.topRotation = topRotation;
		this.bottomRotation = bottomRotation;
		this.twisted = twisted;
	}

	/**
	 * Gets the top rotation of this move.
	 * 
	 * @return the top rotation of this move.
	 */
	public int getTopRotation() {
		return topRotation;
	}

	/**
	 * Gets the bottom rotation of this move.
	 * 
	 * @return the bottom rotation of this move.
	 */
	public int getBottomRotation() {
		return bottomRotation;
	}

	/**
	 * Gets the twist indicator of this move.
	 * 
	 * @return the twist indicator of this move.
	 */
	public boolean isTwisted() {
		return twisted;
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
	}
}
