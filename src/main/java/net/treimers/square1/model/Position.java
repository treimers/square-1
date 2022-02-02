package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.view.piece.Layer;

/**
 * Instances of this class are used to model a Square-1 position.
 */
public class Position {
	private static final int MAX_PIECES = 24;
	/** The solved position as string. */
	public static final String SOLVED_POSITION_STRING = "A1B2C3D45E6F7G8H-";
	/** The pieces (top and bottom layer). */
	private int[] pieces;
	/** The number of pieces. */
	private int length;
	/** The middle layer ('-' or '/'). */
	private Character middleLayer;

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
		clear();
		for (int i = 0; i < positionString.length(); i++) {
			char c = positionString.charAt(i);
			add(c);
		}
	}

	/**
	 * Adds a piece with name to this position.
	 * 
	 * @param name the piece name.
	 */
	public void add(Character name) {
		if (canAdd(name)) {
			if (name == '-' || name == '/')
				middleLayer = name;
			else {
				int val = convertToPieceNumber(name);
				pieces[length++] = val;
				if (val < 8)
					pieces[length++] = val;
			}
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
			return middleLayer == null;
		// allow A-H and 1-8 pieces only
		if (!SOLVED_POSITION_STRING.contains(name.toString()))
			return false;
		if (length >= MAX_PIECES)
			return false;
		// disallow A-H pieces when only small slot in top layer
		if (length == 11 && Character.isAlphabetic(name))
			return false;
		return isAlreadyContained(name);
	}

	/**
	 * <p>
	 * Checks whether a piece with name is contained in this position.
	 * 
	 * <p>
	 * Piece M and N are handled together using name '-' or '/'.
	 * 
	 * @param name the piece name.
	 * @return true if piece is already contained in this position, false otherwise.
	 */
	public boolean isAvailable(Character name) {
		if (name == '-' || name == '/')
			return middleLayer == null;
		return isAlreadyContained(name);
	}

	/**
	 * Clears this position.
	 */
	public void clear() {
		this.pieces = new int[MAX_PIECES];
		this.length = 0;
		this.middleLayer = null;
	}

	/**
	 * Gets all pieces from this position.
	 * 
	 * @return the pieces from this position.
	 */
	public Map<Layer, Character[]> getPieces() {
		Map<Layer, Character[]> retval = new EnumMap<>(Layer.class);
		List<Character> top = new ArrayList<>();
		int count = 0;
		for (; count < length && count < MAX_PIECES / 2; count++) {
			int piece = pieces[count];
			top.add(convertFromPieceNumber(piece));
			if (piece < 8)
				count++;
		}
		List<Character> bottom = new ArrayList<>();
		for (; count < length; count++) {
			int piece = pieces[count];
			bottom.add(convertFromPieceNumber(piece));
			if (piece < 8)
				count++;
		}
		retval.put(Layer.TOP, top.toArray(new Character[top.size()]));
		retval.put(Layer.BOTTOM, bottom.toArray(new Character[bottom.size()]));
		List<Character> middle = new ArrayList<>();
		if (middleLayer != null)
			middle.add(middleLayer);
		retval.put(Layer.MIDDLE, middle.toArray(new Character[middle.size()]));
		return retval;
	}

	/**
	 * Gets the piece string with top and bottom layer pieces.
	 * 
	 * @return the top and bottom piece string.
	 */
	public String getPieceString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int piece = pieces[i];
			sb.append(convertFromPieceNumber(piece));
			// skip second half of a corner piece
			if (piece < 8)
				i++;
		}
		return sb.toString();
	}

	/**
	 * Gets the middle piece string (empty, "-" or "/").
	 * 
	 * @return the middle piece string.
	 */
	public String getMiddlePiece() {
		if (middleLayer == null)
			return "";
		else
			return middleLayer.toString();
	}

	public void move(Move move) throws Square1Exception {
		int topRotation = move.getTopRotation();
		int[] newTop = new int[MAX_PIECES / 2];
		for (int i = 0; i < newTop.length; i++) {
			int floorMod = Math.floorMod(i - topRotation, MAX_PIECES / 2);
			newTop[i] = pieces[floorMod];
		}
		if (newTop[0] == newTop[newTop.length - 1] || newTop[MAX_PIECES / 4] == newTop[MAX_PIECES / 4 - 1])
			throw new Square1Exception(String.format("Illegal move %s for position %s", move, toString()));
		System.arraycopy(newTop, 0, pieces, 0, MAX_PIECES / 2);
		int bottomRotation = move.getBottomRotation();
		int[] newBottom = new int[MAX_PIECES / 2];
		for (int i = 0; i < newBottom.length; i++)
			newBottom[i] = pieces[Math.floorMod(i - bottomRotation, MAX_PIECES / 2) + MAX_PIECES / 2];
		if (newBottom[0] == newBottom[newBottom.length - 1] || newBottom[MAX_PIECES / 4] == newBottom[MAX_PIECES / 4 - 1])
			throw new Square1Exception(String.format("Illegal move %s for position %s", move, toString()));
		System.arraycopy(newBottom, 0, pieces, MAX_PIECES / 2, MAX_PIECES / 2);
		if (move.isTwisted()) {
			// 0..5 -> 0..5
			// 6..11 -> 12..17
			// 12..17 -> 6..11
			// 18..23 -> 18..23
			for (int i = MAX_PIECES / 4; i < MAX_PIECES * 2 / 4; i++) {
				int help = pieces[i];
				pieces[i] = pieces[i + MAX_PIECES / 4];
				pieces[i + MAX_PIECES / 4] = help;
			}
			int twistIndex = (middleLayer == null || middleLayer.equals("-")) ? 0 : 1; 
			middleLayer = "/-".charAt(twistIndex);
		}
	}

	public void move(MoveSequence sequence) throws Square1Exception {
		Move[] moves = sequence.getMoves();
		for (int j = 0; j < moves.length; j++) {
			Move move = moves[j];
			move(move);
		}
	}

	@Override
	public String toString() {
		return getPieceString() + getMiddlePiece();
	}

	/**
	 * Converts a piece name to a number. The method returns
	 * <ul>
	 * <li>0 - 7 for corner piece names from 'A' - 'H'</li>
	 * <li>8 - 15 for edge piece names from '1' - '8'</li>
	 * </ul>
	 * 
	 * @param name the piece name.
	 * @return the piece number from 0 to 15.
	 */
	private int convertToPieceNumber(Character name) {
		if (Character.isAlphabetic(name))
			return name - 'A';
		else
			return name - '1' + 8;
	}

	/**
	 * Converts a piece number to a name . The method returns
	 * <ul>
	 * <li>'A' - 'H' for corner piece with numbers 0 - 7</li>
	 * <li>'1' - '8' for edge piece with numbers 8 - 15</li>
	 * </ul>
	 * 
	 * @param number the piece number.
	 * @return the piece name.
	 */
	private char convertFromPieceNumber(int number) {
		return "ABCDEFGH12345678".charAt(number);
	}

	/**
	 * Checks whether corner or edge piece is already contained in this position.
	 * 
	 * @param name the piece name.
	 * @return true if the piece is already contained in this position.
	 */
	private boolean isAlreadyContained(Character name) {
		int val = convertToPieceNumber(name);
		for (int i = 0; i < length; i++) {
			if (pieces[i] == val)
				return false;
		}
		return true;
	}
}
