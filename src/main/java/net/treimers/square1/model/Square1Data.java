package net.treimers.square1.model;

import javafx.scene.paint.Color;
import net.treimers.square1.util.Utils;

/**
 * Instances of this class are used to persist and restore
 * Square-1 data (color scheme and position).
 */
public class Square1Data {
	/** The Strings with color scheme. */
	private String[] colorStrings;
	/** The position String. */
	private String positionString;
	/** The solution String. */
	private MoveSequence solution;

	/**
	 * Creates a new empty data object.
	 */
	public Square1Data() {
	}

	/**
	 * Creates a new data object.
	 * 
	 * @param colorStrings the color scheme.
	 * @param positionString the position String.
	 * @param solution the solution String.
	 */
	public Square1Data(String[] colorStrings, String positionString, MoveSequence solution) {
		this.colorStrings = colorStrings;
		this.positionString = positionString;
		this.solution = solution;
	}

	/**
	 * Creates a new data object.
	 * 
	 * @param color the color scheme.
	 * @param position the position.
	 * @param solution the solution String.
	 */
	public Square1Data(Color[] color, Position position, MoveSequence solution) {
		colorStrings = new String[color.length];
		for (int i = 0; i < color.length; i++) {
			colorStrings[i] = Utils.colorToString(color[i]);
		}
		this.positionString = position.toString();
		this.solution = solution;
	}

	/**
	 * Gets the Strings with the color scheme.
	 * 
	 * @return the Strings with the color scheme.
	 */
	public String[] getColorStrings() {
		return colorStrings;
	}

	/**
	 * Sets the Strings with the color scheme.
	 * 
	 * @param colorStrings the Strings with the color scheme.
	 */
	public void setColorStrings(String[] colorStrings) {
		this.colorStrings = colorStrings;
	}

	/**
	 * Gets the position String.
	 * 
	 * @return the position String.
	 */
	public String getPositionString() {
		return positionString;
	}

	/**
	 * Sets the position String.
	 * 
	 * @param positionString the position String.
	 */
	public void setPositionString(String positionString) {
		this.positionString = positionString;
	}

	/**
	 * Gets the solution.
	 * 
	 * @return the solution.
	 */
	public MoveSequence getSolution() {
		return solution;
	}

	/**
	 * Sets the solution.
	 * 
	 * @param solution the solution.
	 */
	public void setSolution(MoveSequence solution) {
		this.solution = solution;
	}

	/**
	 * Builds the color scheme from this data object.
	 * 
	 * @return the color scheme.
	 */
	public Color[] buildColors() {
		Color[] colors = new Color[colorStrings.length];
		for (int i = 0; i < colorStrings.length; i++)
			colors[i] = Utils.stringToColor(colorStrings[i]);
		return colors;
	}

	/**
	 * Builds the position from this data object.
	 * 
	 * @return the position.
	 */
	public Position buildPosition() {
		return new Position(positionString);
	}
}
