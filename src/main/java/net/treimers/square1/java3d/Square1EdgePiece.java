package net.treimers.square1.java3d;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Represents an edge pieces of the Square-1.
 * An edge piece is defined using two colors (for front and top side) together with
 * orientation and movement definitions.
 * <p>The standard edge piece (with angles and movement zero) will show the front side.
 * In order to get an edge piece with different orientation use constructor parameter.
 * Use <code>y</code> to rotate with multiple of 90 degrees around top and
 * <code>x</code> to rotate top side to bottom. With <code>z</code> the piece can be moved up and down.
 */
public class Square1EdgePiece extends AbstractPiece {
    private static final Point3d A = new Point3d(0.0,                          0.0,         0.0);
    private static final Point3d B = new Point3d(-LENGTH * Math.sin(ANGLE_15), 0.0,         LENGTH);
    private static final Point3d C = new Point3d(-LENGTH * Math.sin(ANGLE_15), CORNER_SIZE, LENGTH);
    private static final Point3d D = new Point3d(0.0,                          CORNER_SIZE, 0.0);
    private static final Point3d E = new Point3d(LENGTH * Math.sin(ANGLE_15),  0.0,         LENGTH);
    private static final Point3d F = new Point3d(LENGTH * Math.sin(ANGLE_15),  CORNER_SIZE, LENGTH);
    private final Color3f[] colors;
    private final int x;
    private final int y;
    private final int z;

    /**
     * Creates a new edge piece using two colors (for front and top) and remaining parameters
     * to define position and orientation of piece.
     * @param colors array with two colors for front and top.
     * @param x position to flip top of piece to bottom (must be 0 for no flip or 1 for flip).
     * @param y value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
     * or 3 for 270 degrees).
     * @param z movement with 0 for up and 1 for down.
     */
    public Square1EdgePiece(Color3f [] colors, int x, int y, int z) {
        this.colors = colors;
        this.x = x;
        this.y = y;
        this.z = z;
        double xAngle = x * ANGLE_180;
        double yAngle = y * ANGLE_90;
        Vector3d zTranslation = DIRS[z];
        Transform3D rotX = new Transform3D();
        rotX.rotX(xAngle);
        Transform3D rotY = new Transform3D();
        rotY.rotY(yAngle);
        Transform3D move = new Transform3D();
        move.setTranslation(zTranslation);
        rotY.mul(rotX);
        move.mul(rotY);
        setTransform(move);
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        Shape3D shape1 = new Shape3D();
        List<Point3d> points = new ArrayList<Point3d>();
        // left
        points.add(A);
        points.add(B);
        points.add(C);
        points.add(C);
        points.add(D);
        points.add(A);
        // front
        points.add(B);
        points.add(E);
        points.add(F);
        points.add(F);
        points.add(C);
        points.add(B);
        // right
        points.add(E);
        points.add(A);
        points.add(D);
        points.add(D);
        points.add(F);
        points.add(E);
        // oben
        points.add(C);
        points.add(F);
        points.add(D);
        // unten
        points.add(B);
        points.add(A);
        points.add(E);
        Point3d[] shapes = points.toArray(new Point3d[points.size()]);
        // strip count
        Color3f [] allColors = new Color3f[] {
            DARKGRAY, DARKGRAY, DARKGRAY,
            DARKGRAY, DARKGRAY, DARKGRAY,
            colors[0], colors[0], colors[0],
            colors[0], colors[0], colors[0],
            DARKGRAY, DARKGRAY, DARKGRAY,
            DARKGRAY, DARKGRAY, DARKGRAY,
            colors[1], colors[1], colors[1], 
            DARKGRAY, DARKGRAY, DARKGRAY,
        };
        TriangleArray triangleArray = new TriangleArray(shapes.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        triangleArray.setCoordinates(0, shapes);
        triangleArray.setColors(0, allColors);
        shape1.setGeometry(triangleArray);
        addChild(shape1);
        Shape3D shape2 = new Shape3D();
        points = new ArrayList<Point3d>();
        // left
        points.add(A);
        points.add(B);
        points.add(B);
        points.add(C);
        points.add(C);
        points.add(D);
        points.add(D);
        points.add(A);
        // front
        points.add(B);
        points.add(E);
        points.add(E);
        points.add(F);
        points.add(F);
        points.add(C);
        points.add(C);
        points.add(B);
        // right
        points.add(E);
        points.add(A);
        points.add(A);
        points.add(D);
        points.add(D);
        points.add(F);
        points.add(F);
        points.add(E);
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
        return new Square1EdgePiece(colors, x, y, z);
    }
}
