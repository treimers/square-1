package de.softquadrat.square1.java3d;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

/**
 * Several constants for length and colors.
 */
public interface Constants {
    // Some lenghts
    /**
     * Distance cube center to front.
     */
    public static final float LENGTH = 0.25F;
    /**
     * Angle 15 degrees.
     */
    public static final double ANGLE_15 = 15.0 / 180.0 * Math.PI;
    /**
     * Angle 30 degrees.
     */
    public static final double ANGLE_30 = 30.0 / 180.0 * Math.PI;
    /**
     * Angle 45 degrees.
     */
    public static final double ANGLE_45 = 45.0 / 180.0 * Math.PI;
    /**
     * Angle 90 degrees.
     */
    public static final double ANGLE_90 = 90.0 / 180.0 * Math.PI;
    /**
     * Angle 180 degrees.
     */
    public static final double ANGLE_180 = 180.0 / 180.0 * Math.PI;
    /**
     * Length of edge front side.
     */
    public static final double EDGE_SIZE = 2 * LENGTH * Math.sin(ANGLE_15);
    /**
     * Thickness of black lines.
     */
    public static final float LINE_THICKNESS = 2.0F;

    // Some colors
    public static final double CORNER_SIZE = LENGTH - EDGE_SIZE / 2.0;
    /**
     * Green color.
     */
    public static final Color3f GREEN = new Color3f(0.0F, 1.0F, 0.0F);
    /**
     * Yellow color.
     */
    public static final Color3f YELLOW = new Color3f(1.0F, 1.0F, 0.0F);
    /**
     * Red color.
     */
    public static final Color3f RED = new Color3f(1.0F, 0.0F, 0.0F);
    /**
     * Blue color.
     */
    public static final Color3f BLUE = new Color3f(0.0F, 0.0F, 1.0F);
    /**
     * Orange color.
     */
    public static final Color3f ORANGE = new Color3f(1.0F, 0.5F, 0.0F);
    /**
     * White color.
     */
    public static final Color3f WHITE = new Color3f(1.0F, 1.0F, 1.0F);
    /**
     * Black color.
     */
    public static final Color3f BLACK = new Color3f(0.0F, 0.0F, 0.0F);
    /**
     * Dark gray color.
     */
    public static final Color3f DARKGRAY = new Color3f(0.5F, 0.5F, 0.5F);
    
    // Some movements from center of universe.
    /**
     * Movement for top of square-1.
     */
    public static final Vector3d UP = new Vector3d(0.0, EDGE_SIZE / 2.0, 0.0);
    /**
     * Movement for bottom of square-1.
     */
    public static final Vector3d DOWN = new Vector3d(0.0, -EDGE_SIZE / 2.0, 0.0);
    /**
     * Movement directions.
     */
    public static final Vector3d [] DIRS = { UP, DOWN };

    // Pieces
    // Corner pieces
    /**
     * Corner piece 'A'.
     */
    public final Square1CornerPiece A = new Square1CornerPiece(new Color3f [] { YELLOW, ORANGE, WHITE }, 0, 3, 0);
    /**
     * Corner piece 'B'.
     */
    public final Square1CornerPiece B = new Square1CornerPiece(new Color3f [] { RED,    YELLOW, WHITE }, 0, 2, 0);
    /**
     * Corner piece 'C'.
     */
    public final Square1CornerPiece C = new Square1CornerPiece(new Color3f [] { BLUE,   RED,    WHITE }, 0, 1, 0);
    /**
     * Corner piece 'D'.
     */
    public final Square1CornerPiece D = new Square1CornerPiece(new Color3f [] { ORANGE, BLUE,   WHITE }, 0, 0, 0);
    /**
     * Corner piece 'E'.
     */
    public final Square1CornerPiece E = new Square1CornerPiece(new Color3f [] { BLUE,   ORANGE, GREEN }, 1, 1, 1);
    /**
     * Corner piece 'F'.
     */
    public final Square1CornerPiece F = new Square1CornerPiece(new Color3f [] { ORANGE, YELLOW, GREEN }, 1, 2, 1);
    /**
     * Corner piece 'G'.
     */
    public final Square1CornerPiece G = new Square1CornerPiece(new Color3f [] { YELLOW, RED,    GREEN }, 1, 3, 1);
    /**
     * Corner piece 'H'.
     */
    public final Square1CornerPiece H = new Square1CornerPiece(new Color3f [] { RED,    BLUE,   GREEN }, 1, 0, 1);
    /**
     * All corner pieces.
     */
    // Edge pieces
    /**
     * Edge piece '1'.
     */
    public final Square1EdgePiece _1 = new Square1EdgePiece(new Color3f [] { YELLOW, WHITE }, 0, 3, 0);
    /**
     * Edge piece '2'.
     */
    public final Square1EdgePiece _2 = new Square1EdgePiece(new Color3f [] { RED,    WHITE }, 0, 2, 0);
    /**
     * Edge piece '3'.
     */
    public final Square1EdgePiece _3 = new Square1EdgePiece(new Color3f [] { BLUE,   WHITE }, 0, 1, 0);
    /**
     * Edge piece '4'.
     */
    public final Square1EdgePiece _4 = new Square1EdgePiece(new Color3f [] { ORANGE, WHITE }, 0, 0, 0);
    /**
     * Edge piece '5'.
     */
    public final Square1EdgePiece _5 = new Square1EdgePiece(new Color3f [] { ORANGE, GREEN }, 1, 2, 1);
    /**
     * Edge piece '6'.
     */
    public final Square1EdgePiece _6 = new Square1EdgePiece(new Color3f [] { BLUE,   GREEN }, 1, 3, 1);
    /**
     * Edge piece '7'.
     */
    public final Square1EdgePiece _7 = new Square1EdgePiece(new Color3f [] { RED,    GREEN }, 1, 0, 1);
    /**
     * Edge piece '8'.
     */
    public final Square1EdgePiece _8 = new Square1EdgePiece(new Color3f [] { YELLOW, GREEN }, 1, 1, 1);
    /**
     * All edge pieces.
     */
    // Middle pieces
    /**
     * Middle piece 'M1'.
     */
    public final Square1MiddlePiece M1 = new Square1MiddlePiece(new Color3f [] { ORANGE, BLUE,   RED    }, 0); 
    /**
     * Middle piece 'M2'.
     */
    public final Square1MiddlePiece M2 = new Square1MiddlePiece(new Color3f [] { RED,    YELLOW, ORANGE }, 1, "SQUARE-1");
    /**
     * All middle pieces.
     */
    /**
     * All square-1 pieces.
     */
    public final AbstractPiece [] ALL_PIECES = {
        A, _1, B, _2, C, _3, D, _4, _5, E, _6, F, _7, G, _8, H, M1, M2,
    };
}
