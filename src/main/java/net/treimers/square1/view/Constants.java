package net.treimers.square1.view;

/**
 * Several constants for colors, angles, ...
 */
public interface Constants {
	// Colors
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
	// Angles
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
	// Some sizes
	public static final float SIZE = 100.0f;
	public static final float EDGE_SIZE = (float) (2 * SIZE * Math.sin(ANGLE_15));
	public static final float CORNER_SIZE = (float) (SIZE - EDGE_SIZE / 2.0);
	// Corner pieces
	/** Corner piece 'A'. */
	public final CornerPiece A = new CornerPiece(new int[] { WHITE, YELLOW, ORANGE, }, false, 0);
	/** Corner piece 'B'. */
	public final CornerPiece B = new CornerPiece(new int[] { WHITE, RED, YELLOW, }, false, 3);
	/** Corner piece 'C'. */
	public final CornerPiece C = new CornerPiece(new int[] { WHITE, BLUE, RED, }, false, 2);
	/** Corner piece 'D'. */
	public final CornerPiece D = new CornerPiece(new int[] { WHITE, ORANGE, BLUE, }, false, 1);
	/** Corner piece 'E'. */
	public final CornerPiece E = new CornerPiece(new int[] { GREEN, BLUE, ORANGE, }, true, 0);
	/** Corner piece 'F'. */
	public final CornerPiece F = new CornerPiece(new int[] { GREEN, ORANGE, YELLOW, }, true, 1);
	/** Corner piece 'G'. */
	public final CornerPiece G = new CornerPiece(new int[] { GREEN, YELLOW, RED, }, true, 2);
	/** Corner piece 'H'. */
	public final CornerPiece H = new CornerPiece(new int[] { GREEN, RED, BLUE, }, true, 3);
	// Edge pieces
	/** Edge piece '1'. */
	public final EdgePiece _1 = new EdgePiece(new int[] { WHITE, YELLOW }, false, 0);
	/** Edge piece '2'. */
	public final EdgePiece _2 = new EdgePiece(new int[] { WHITE, ORANGE, }, false, 1);
	/** Edge piece '3'. */
	public final EdgePiece _3 = new EdgePiece(new int[] { WHITE, BLUE, }, false, 2);
	/** Edge piece '4'. */
	public final EdgePiece _4 = new EdgePiece(new int[] { WHITE, RED, }, false, 3);
	/** Edge piece '5'. */
	public final EdgePiece _5 = new EdgePiece(new int[] { GREEN, BLUE, }, true, 0);
	/** Edge piece '6'. */
	public final EdgePiece _6 = new EdgePiece(new int[] { GREEN, ORANGE, }, true, 1);
	/** Edge piece '7'. */
	public final EdgePiece _7 = new EdgePiece(new int[] { GREEN, YELLOW, }, true, 2);
	/** Edge piece '8'. */
	public final EdgePiece _8 = new EdgePiece(new int[] { GREEN, RED, }, true, 3);
}
