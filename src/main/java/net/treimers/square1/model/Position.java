package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.treimers.square1.view.piece.Layer;

public class Position {
	private static final int CIRCLE = 12;
	private static final String SOLVED_POSITION_STRING = "A1B2C3D45E6F7G8H-";
	private String pieceString;
	private Character middlePiece;

	public static Position fromString(String positionString) {
		return new Position(positionString);
	}

	public Position() {
		this(SOLVED_POSITION_STRING);
	}

	public Position(Position position) {
		this(position.toString());
	}

	private Position(String positionString) {
		this.pieceString = "";
		this.middlePiece = null;
		for (int i = 0; i < positionString.length(); i++) {
			char c = positionString.charAt(i);
			add(c);
		}
	}

	public boolean canAdd(Character name) {
		// allow middle pieces?
		if (name == '-' || name == '/')
			return middlePiece == null;
		// disallow A-H pieces when only small slot left in top layer
		if (getAngle(pieceString) == CIRCLE - 1 && Character.isAlphabetic(name))
			return false;
		return !pieceString.contains(name.toString());
	}

	public boolean isAvailable(Character name) {
		if (name == '-' || name == '/')
			return middlePiece == null;
		return !pieceString.contains(name.toString());
	}

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

	public void clear() {
		pieceString = "";
		middlePiece = null;
	}

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
		String retval = this.pieceString;
		if (middlePiece != null)
			retval += this.middlePiece;
		return retval;
	}

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
