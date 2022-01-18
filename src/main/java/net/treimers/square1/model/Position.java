package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.treimers.square1.view.piece.Layer;

/**
 * Instances of this class are used to model a Square-1 position.
 */
public class Position {
	/** Number of 30° pieces that gives a full circle. */
	private static final int CIRCLE = 12;
	/** The solved position as string. */
	public static final String SOLVED_POSITION_STRING = "A1B2C3D45E6F7G8H-";
	/** The string with pieces (top and bottom layer). */
	private String pieceString;
	/** The position of the middle piece ('-' or '/'). */
	private Character middlePiece;

	/**
	 * Creates a solved position.
	 */
	public Position() {
		this(SOLVED_POSITION_STRING);
	}

	/**
	 * Creates a new position copied from given one.
	 * 
	 * @param position the given position.
	 */
	public Position(Position position) {
		this(position.toString());
	}

	/**
	 * Creates a new position from a String.
	 * 
	 * @param positionString the position String.
	 */
	public Position(String positionString) {
		this.pieceString = "";
		this.middlePiece = null;
		for (int i = 0; i < positionString.length(); i++) {
			char c = positionString.charAt(i);
			add(c);
		}
	}

	/**
	 * <p>
	 * Checks whether a piece with name can be added to this position.
	 * 
	 * <p>
	 * Piece M and N are handled together using name '-' or '/'.
	 * 
	 * @param name the piece name.
	 * @return true if piece can be added, false otherwise.
	 */
	public boolean canAdd(Character name) {
		// allow middle pieces?
		if (name == '-' || name == '/')
			return middlePiece == null;
		if (!SOLVED_POSITION_STRING.contains(name.toString()))
			return false;
		// disallow A-H pieces when only small slot left in top layer
		if (getAngle(pieceString) == CIRCLE - 1 && Character.isAlphabetic(name))
			return false;
		return !pieceString.contains(name.toString());
	}

	/**
	 * <p>
	 * Checks whether a piece with name is contained in this position.
	 * 
	 * <p>
	 * Piece M and N are handled together using name '-' or '/'.
	 * 
	 * @param name the piece name.
	 * @return true if piece is contained in this position, false otherwise.
	 */
	public boolean isAvailable(Character name) {
		if (name == '-' || name == '/')
			return middlePiece == null;
		return !pieceString.contains(name.toString());
	}

	/**
	 * <p>
	 * Adds a piece with name to this position.
	 * 
	 * <p>
	 * Piece M and N are handled together using name '-' or '/'.
	 * 
	 * @param name the piece name.
	 */
	public boolean add(Character name) {
		if (canAdd(name)) {
			if (name == '-' || name == '/')
				middlePiece = name;
			else
				this.pieceString += name;
			return true;
		} else
			return false;
	}

	/**
	 * Clears this position.
	 */
	public void clear() {
		pieceString = "";
		middlePiece = null;
	}

	/**
	 * Gets the piece string with top and bottom layer pieces.
	 * 
	 * @return the piece string.
	 */
	public String getPieceString() {
		return pieceString;
	}

	/**
	 * Gets the middle piece string (empty, "-" or "/").
	 * 
	 * @return the middle piece string.
	 */
	public String getMiddlePiece() {
		if (middlePiece == null)
			return "";
		else
			return middlePiece.toString();
	}

	/**
	 * Gets all pieces from this position.
	 * @return the pieces from this position.
	 */
	public Map<Layer, Character[]> getPieces() {
		Map<Layer, Character[]> retval = new EnumMap<>(Layer.class);
		int angle = 0;
		List<Character> top = new ArrayList<>();
		List<Character> bottom = new ArrayList<>();
		for (int i = 0; i < pieceString.length(); i++) {
			char c = pieceString.charAt(i);
			if (angle < CIRCLE)
				top.add(c);
			else if (angle < 2 * CIRCLE)
				bottom.add(c);
			angle++;
			if (Character.isAlphabetic(c))
				angle++;
		}
		retval.put(Layer.TOP, top.toArray(new Character[top.size()]));
		retval.put(Layer.BOTTOM, bottom.toArray(new Character[bottom.size()]));
		List<Character> middle = new ArrayList<>();
		if (middlePiece != null)
			middle.add(middlePiece);
		retval.put(Layer.MIDDLE, middle.toArray(new Character[middle.size()]));
		return retval;
	}

	@Override
	public String toString() {
		return getPieceString() + getMiddlePiece();
	}

	/**
	 * <p>
	 * Gets the angle of all pieces from top and bottom layer.
	 * 
	 * <p>This is an integer between 0 and 23.
	 * 
	 * @param pos the position string.
	 * @return the angle of all pieces.
	 */
	private int getAngle(String pos) {
		int angle = 0;
		for (int i = 0; i < pos.length(); i++) {
			char c = pos.charAt(i);
			if (Character.isDigit(c))
				angle += 1;
			else if (Character.isAlphabetic(c))
				angle += 2;
		}
		return angle;
	}
}
