package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.treimers.square1.view.piece.Layer;

public class Position {
	private static final int CIRCLE = 12;
	private static final String SOLVED_POSITION_STRING = "A1B2C3D45E6F7G8H-";
	private String positionString;

	public static Position fromString(String positionString) {
		return new Position(positionString);
	}
			
	public Position() {
		this(SOLVED_POSITION_STRING);
	}
	
	public Position(Position position) {
		this(position.positionString);
	}
	
	private Position(String positionString) {
		this.positionString = positionString;
	}

	public boolean canAdd(Character name) {
		// do not allow anything after all positions filled
		if (positionString.length() >= SOLVED_POSITION_STRING.length())
			return false;
		// allow middle pieces as last position only
		if (name == '-' || name == '/')
			return positionString.length() == SOLVED_POSITION_STRING.length() - 1;
		// disallow A-H pieces when only small slot left in top layer
		if (getAngle(positionString) == CIRCLE - 1 && Character.isAlphabetic(name))
			return false;
		return !positionString.contains(name.toString());
	}

	public boolean isAvailable(Character name) {
		if (name == '-' || name == '/')
			return positionString.length() < SOLVED_POSITION_STRING.length();
		return !positionString.contains(name.toString());
	}
	
	public boolean add(Character name) {
		if (canAdd(name)) {
			this.positionString += name;
			return true;
		} else
			return false;
	}

	public void clear() {
		positionString = "";
	}

	public Map<Layer, Character[]> getPieces() {
		Map<Layer, Character[]> retval = new EnumMap<>(Layer.class);
		int angle = 0;
		List<Character> top = new ArrayList<>();
		List<Character> bottom = new ArrayList<>();
		List<Character> middle = new ArrayList<>();
		for (int i = 0; i < positionString.length(); i++) {
			char c = positionString.charAt(i);
			if (angle < CIRCLE)
				top.add(c);
			else if (angle < 2 * CIRCLE)
				bottom.add(c);
			else
				middle.add(c);
			angle++;
			if (Character.isAlphabetic(c))
				angle++;
		}
		retval.put(Layer.TOP, top.toArray(new Character[top.size()]));
		retval.put(Layer.BOTTOM, bottom.toArray(new Character[bottom.size()]));
		retval.put(Layer.MIDDLE, middle.toArray(new Character[middle.size()]));
		return retval;
	}

	@Override
	public String toString() {
		return this.positionString;
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
