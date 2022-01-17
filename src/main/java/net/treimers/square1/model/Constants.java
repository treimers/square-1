package net.treimers.square1.model;

import java.util.Arrays;

/**
 * Provides several constants for colors, angles and length.
 */
public class Constants {
	/** The vertical pick position of the colored squares (50%) */
	public static final float COLOR_HEIGHT = 0.5f;
	/** The index array used to pick the colors from the writable image. */
	private static final float[] COLOR_ARRAY = {
		// position of top color
		(Side.TOP.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of left color
		(Side.LEFT.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of front color
		(Side.FRONT.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of right color
		(Side.RIGHT.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of back color
		(Side.BACK.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of bottom color
		(Side.BOTTOM.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of inner vertical color
		(Side.INNER_VERTICAL.ordinal() + 0.5f) / Side.values().length,
		COLOR_HEIGHT,
		// position of inner horizontal color
		(Side.INNER_HORIZONTAL.ordinal() + 0.5f) / Side.values().length,
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
