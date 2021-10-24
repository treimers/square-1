package net.treimers.square1.java3d;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Represents a corner pieces of the Square-1.
 * A corner piece is defined using three colors (for front left, front right and top side) together with
 * orientation and movement definitions.
 * <p>The standard corner piece (with angles and movement zero) will show the front side.
 * In order to get a corner piece with different orientation use constructor parameters.
 * Use <code>y</code> to rotate with multiple of 90 degrees around top and
 * <code>x</code> to rotate top side to bottom. With <code>z</code> the piece can be moved up and down.
 */
public class Square1CornerPiece extends AbstractPiece  {
    private static final double S = LENGTH / Math.cos(ANGLE_15);
    private static final Point3d A = new Point3d(0.0,                          0.0,         0.0);
    private static final Point3d B = new Point3d(-S * Math.sin(ANGLE_30),      0.0,         S * Math.cos(ANGLE_30));
    private static final Point3d C = new Point3d(-S * Math.sin(ANGLE_30),      CORNER_SIZE, S * Math.cos(ANGLE_30));
    private static final Point3d D = new Point3d(0.0,                          CORNER_SIZE, 0.0);
    private static final Point3d E = new Point3d(0.0,                          0.0,         S * (Math.cos(ANGLE_30) + Math.sin(ANGLE_30)));
    private static final Point3d F = new Point3d(0.0,                          CORNER_SIZE, S * (Math.cos(ANGLE_30) + Math.sin(ANGLE_30)));
    private static final Point3d G = new Point3d(S * Math.sin(ANGLE_30),       0.0,         S * Math.cos(ANGLE_30));
    private static final Point3d H = new Point3d(S * Math.sin(ANGLE_30),       CORNER_SIZE, S * Math.cos(ANGLE_30));
    private final Color3f[] colors;
    private final int x;
    private final int y;
    private final int z;

    /**
     * Creates a new corner piece using three colors (for front left, front right and top) and remaining parameters
     * to define position and orientation of piece.
     * @param colors array with three colors for front left, front right and top.
     * @param x position to flip top of piece to bottom (must be 0 for no flip or 1 for flip).
     * @param y value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
     * or 3 for 270 degrees).
     * @param z movement with 0 for up and 1 for down.
     */
    public Square1CornerPiece(Color3f [] colors, int x, int y, int z) {
        this.colors = colors;
        this.x = x;
        this.y = y;
        this.z = z;
        double xAngle = x * ANGLE_180;
        double yAngle = y * ANGLE_90 + ANGLE_45;
        Vector3d zTranslation = DIRS[z];
        Transform3D rotY = new Transform3D();
        rotY.rotY(yAngle);
        Transform3D rotX = new Transform3D();
        rotX.rotX(xAngle);
        Transform3D move = new Transform3D();
        move.setTranslation(zTranslation);
        rotX.mul(rotY);
        move.mul(rotX);
        setTransform(move);
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        Shape3D shape1 = new Shape3D();
        List<Point3d> points = new ArrayList<Point3d>();
        // left back
        points.add(A);
        points.add(B);
        points.add(C);
        points.add(D);
        // left front
        points.add(B);
        points.add(E);
        points.add(F);
        points.add(C);
        // right front
        points.add(E);
        points.add(G);
        points.add(H);
        points.add(F);
        // right back
        points.add(A);
        points.add(D);
        points.add(H);
        points.add(G);
        // top
        points.add(D);
        points.add(C);
        points.add(F);
        points.add(H);
        // bottom
        points.add(A);
        points.add(G);
        points.add(E);
        points.add(B);
        Point3d[] shapes = points.toArray(new Point3d[points.size()]);
        // strip count
        Color3f [] allColors = new Color3f[] {
            DARKGRAY, DARKGRAY, DARKGRAY, DARKGRAY,
            colors[0], colors[0], colors[0], colors[0],
            colors[1], colors[1], colors[1], colors[1], 
            DARKGRAY, DARKGRAY, DARKGRAY, DARKGRAY,
            colors[2], colors[2], colors[2], colors[2],
            DARKGRAY, DARKGRAY, DARKGRAY, DARKGRAY,
        };
        QuadArray quadarray = new QuadArray(shapes.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        quadarray.setCoordinates(0, shapes);
        quadarray.setColors(0, allColors);
        shape1.setGeometry(quadarray);
        addChild(shape1);
        Shape3D shape2 = new Shape3D();
        points = new ArrayList<Point3d>();
        // left back
        points.add(A);
        points.add(B);
        points.add(B);
        points.add(C);
        points.add(C);
        points.add(D);
        points.add(D);
        points.add(A);
        // left front
        points.add(B);
        points.add(E);
        points.add(E);
        points.add(F);
        points.add(F);
        points.add(C);
        points.add(C);
        points.add(B);
        // right front
        points.add(E);
        points.add(G);
        points.add(G);
        points.add(H);
        points.add(H);
        points.add(F);
        points.add(F);
        points.add(E);
        // right back
        points.add(A);
        points.add(D);
        points.add(D);
        points.add(H);
        points.add(H);
        points.add(G);
        points.add(G);
        points.add(A);
        // Colors
        allColors = new Color3f[points.size()];
        for (int i = 0; i < allColors.length; i++) {
            allColors[i] = BLACK;
        };
        Point3d[] lines = points.toArray(new Point3d[points.size()]);
        LineArray linearray = new LineArray(lines.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        linearray.setCoordinates(0, lines);
        linearray.setColors(0, allColors);
        shape2.setGeometry(linearray);
        Appearance appear = new Appearance();
        LineAttributes lineAttributes = new LineAttributes();
        lineAttributes.setLineWidth(LINE_THICKNESS);
        appear.setLineAttributes(lineAttributes);
        shape2.setAppearance(appear);
        addChild(shape2);
    }

    /* (non-Javadoc)
     * @see net.treimers.square1.AbstractPiece#duplicate()
     */
    public AbstractPiece duplicate() {
        return new Square1CornerPiece(colors, x, y, z);
    }
}
