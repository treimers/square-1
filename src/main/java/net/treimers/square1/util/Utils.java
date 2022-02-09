package net.treimers.square1.util;

import javafx.scene.paint.Color;

/**
 * This utility class provides methods to convert colors to and from Strings.
 */
public final class Utils {
	/**
	 * This is a private constructor, that gets never called.
	 */
	private Utils() {
	}
	
	/**
	 * Converts a Color to a Strings.
	 * 
	 * @param color the Color.
	 * @return the String representation.
	 */
	public static String colorToString(Color color) {
		return String.format("0x%02X%02X%02X%02X", (int) (255 * color.getRed() + 0.5), (int) (255 * color.getGreen() + 0.5),
				(int) (255 * color.getBlue() + 0.5), (int) (255 * color.getOpacity() + 0.5));
	}

	/**
	 * Converts a String representation to a color.
	 * 
	 * @param s the String representation.
	 * @return the Color.
	 */
	public static Color stringToColor(String s) {
		return Color.web(s);
	}
}
