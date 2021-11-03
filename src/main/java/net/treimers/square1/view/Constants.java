package net.treimers.square1.view;

/**
 * Several constants for colors, angles, ...
 */
public interface Constants {
	// Colors used
	/** The value for color black. */
	public static final int BLACK = 0;
	/** The value for color white. */
	public static final int WHITE = 1;
	/** The value for color yellow. */
	public static final int YELLOW = 2;
	/** The value for color orange. */
	public static final int ORANGE = 3;
	/** The value for color red. */
	public static final int RED = 4;
	/** The value for color blue. */
	public static final int BLUE = 5;
	/** The value for color green. */
	public static final int GREEN = 6;
	/** The value for color gray. */
	public static final int GRAY = 7;
	/** The number for colors. */
	public static final int NUM_COLORS = 8;
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
	public static final double ANGLE_180 = 180.0 / 180.0 * Math.PI;
	// Some lengths
	/** The size of the cube (half length of a side). */
	public static final float SIZE = 1.0f;
	/** Small delta used to create gaps between pieces. */
	public static final float DELTA = 0.02f;
}
