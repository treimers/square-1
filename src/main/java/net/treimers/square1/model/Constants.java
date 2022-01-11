package net.treimers.square1.model;

import java.util.Arrays;

/**
 * Provides several constants for colors, angles and length.
 */
public class Constants {
	// Colors indices
	/** The index for the top side color. */
	public static final int TOP = 0;
	/** The index for the left side color. */
	public static final int LEFT = 1;
	/** The index for the front side color. */
	public static final int FRONT = 2;
	/** The index for the right side color. */
	public static final int RIGHT = 3;
	/** The index for the back side color. */
	public static final int BACK = 4;
	/** The index for the bottom side color. */
	public static final int BOTTOM = 5;
	/** The index for the inner vertical side color. */
	public static final int INNER_VERTICAL = 6;
	/** The index for the inner horizontal side color. */
	public static final int INNER_HORIZONTAL = 7;
	/** The number for colors. */
	public static final int NUM_COLORS = 8;
	/** The vertical pick position of the colored squares (50%) */
	public static final float COLOR_HEIGHT = 0.5f;
	/** The index array used to pick the colors from the writable image. */
	private static final float[] COLOR_ARRAY = {
		// position of top color
		(Constants.TOP + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of left color
		(Constants.LEFT + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of front color
		(Constants.FRONT + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of right color
		(Constants.RIGHT + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of back color
		(Constants.BACK + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of bottom color
		(Constants.BOTTOM + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of inner vertical color
		(Constants.INNER_VERTICAL + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// position of inner horizontal color
		(Constants.INNER_HORIZONTAL + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
	};
	// Some Angles
	/** Angle 15 degrees. */
	public static final double ANGLE_15 = 15.0 / 180.0 * Math.PI;
	/** Angle 30 degrees. */
	public static final double ANGLE_30 = 30.0 / 180.0 * Math.PI;
	/** Angle 45 degrees. */
	public static final double ANGLE_45 = 45.0 / 180.0 * Math.PI;
	/** Angle 90 degrees. */
	public static final double ANGLE_90 = 90.0 / 180.0 * Math.PI;
	/** Angle 180 degrees. */
	public static final double ANGLE_180 = Math.PI;
	// Some lengths
	/** The size of the cube (half length of a side). */
	public static final float SIZE = 1.0f;
	/** Width of an edge piece. */
	public static final float EDGE_WIDTH = (float) (2 * Constants.SIZE * Math.sin(Constants.ANGLE_15));
	/** Width of a corner piece. */
	public static final float CORNER_WIDTH = Constants.SIZE - EDGE_WIDTH / 2.0f;
	/** Small delta used to create gaps between pieces. */
	public static final float DELTA = 0.02f;
	
	/**
	 * Don't let anyone instantiate this class.
	 */
	private Constants() {
	}
	
	/**
	 * Gets the color array.
	 * @return the color array.
	 */
	public static float[] getColorArray() {
		return Arrays.copyOf(COLOR_ARRAY, COLOR_ARRAY.length);
	}
}
