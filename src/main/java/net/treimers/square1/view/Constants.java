package net.treimers.square1.view;

/**
 * Provides several constants for colors, angles and length.
 */
public interface Constants {
	// Colors indices
	/** The index for top color. */
	public static final int TOP = 0;
	/** The index for left color. */
	public static final int LEFT = 1;
	/** The index for front color. */
	public static final int FRONT = 2;
	/** The index for right color. */
	public static final int RIGHT = 3;
	/** The index for back color. */
	public static final int BACK = 4;
	/** The index for bottom color. */
	public static final int BOTTOM = 5;
	/** The index for color black. */
	public static final int BLACK = 6;
	/** The index for color gray. */
	public static final int GRAY = 7;
	/** The number for colors. */
	public static final int NUM_COLORS = 8;
	/** The vertical pick position of the colored squares (50%) */
	public static final float COLOR_HEIGHT = 0.5f;
	/** The index array used to pick the colors from the writable image. */
	public static final float[] COLOR_ARRAY = {
		// top
		(Constants.TOP + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// left
		(Constants.LEFT + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// front
		(Constants.FRONT + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// right
		(Constants.RIGHT + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// back
		(Constants.BACK + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// bottom
		(Constants.BOTTOM + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// gray
		(Constants.GRAY + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// black
		(Constants.BLACK + 0.5f) / Constants.NUM_COLORS,
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
	public static final double ANGLE_180 = 180.0 / 180.0 * Math.PI;
	// Some lengths
	/** The size of the cube (half length of a side). */
	public static final float SIZE = 1.0f;
	/** Width of an edge piece. */
	public static final float EDGE_WIDTH = (float) (2 * Constants.SIZE * Math.sin(Constants.ANGLE_15));
	/** Width of a corner piece. */
	public static final float CORNER_WIDTH = (float) (Constants.SIZE - EDGE_WIDTH / 2.0f);
	/** Small delta used to create gaps between pieces. */
	public static final float DELTA = 0.02f;
}
