package de.softquadrat.square1.java3d;

import java.awt.Font;
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

import com.sun.j3d.utils.geometry.Text2D;

/**
 * Represents a middle pieces of the Square-1.
 * A middle piece is defined using three colors (for front, right and back side) together with
 * one angle and optional text.
 * <p>The middle corner piece (with angle zero) will show the front side.
 * In order to get a middle piece with different orientation use <code>y</code> to rotate
 * around top. An optional <code>text</code> can be given that will be placed on the right side.
 */
public class Square1MiddlePiece extends AbstractPiece {
    private static final Point3d A = new Point3d(-LENGTH * Math.sin(ANGLE_15), -EDGE_SIZE / 2.0, LENGTH);
    private static final Point3d B = new Point3d(-LENGTH * Math.sin(ANGLE_15), EDGE_SIZE / 2.0, LENGTH);
    private static final Point3d C = new Point3d(LENGTH * Math.sin(ANGLE_15), EDGE_SIZE / 2.0, -LENGTH);
    private static final Point3d D = new Point3d(LENGTH * Math.sin(ANGLE_15), -EDGE_SIZE / 2.0, -LENGTH);
    private static final Point3d E = new Point3d(LENGTH, -EDGE_SIZE / 2.0, LENGTH);
    private static final Point3d F = new Point3d(LENGTH, -EDGE_SIZE / 2.0, -LENGTH);
    private static final Point3d G = new Point3d(LENGTH, EDGE_SIZE / 2.0, -LENGTH);
    private static final Point3d H = new Point3d(LENGTH, EDGE_SIZE / 2.0, LENGTH);
    private final Color3f[] colors;
    private final int y;
    private final String text;

    /**
     * Creates a new middle piece using three colors (for front, right and back), an angle to define
     * orientation of piece and an optional text.
     * @param colors array with three colors for front, right and back.
     * @param y value to rotate piece around top (allowed values are 0 for no rotation and 1 for 180 degrees.
     * @param text optional text to be displayed on right side (may be null).
     */
    public Square1MiddlePiece(Color3f [] colors, int y, String text) {
        this.colors = colors;
        this.y = y;
        this.text = text;
        double yAngle = y * Math.PI;
        Transform3D rotY = new Transform3D();
        rotY.rotY(yAngle);
        setTransform(rotY);
        Shape3D shape1 = new Shape3D();
        List<Point3d> points = new ArrayList<Point3d>();
        // left
        points.add(A);
        points.add(B);
        points.add(C);
        points.add(D);
        // front
        points.add(A);
        points.add(E);
        points.add(H);
        points.add(B);
        // right
        points.add(E);
        points.add(F);
        points.add(G);
        points.add(H);
        // back
        points.add(D);
        points.add(C);
        points.add(G);
        points.add(F);
        // top
        points.add(B);
        points.add(H);
        points.add(G);
        points.add(C);
        // bottom
        points.add(A);
        points.add(D);
        points.add(F);
        points.add(E);
        // strip count
        Color3f [] allColors = new Color3f[] {
            DARKGRAY, DARKGRAY, DARKGRAY, DARKGRAY,
            colors[0], colors[0], colors[0], colors[0],
            colors[1], colors[1], colors[1], colors[1], 
            colors[2], colors[2], colors[2], colors[2],
            DARKGRAY, DARKGRAY, DARKGRAY, DARKGRAY,
            DARKGRAY, DARKGRAY, DARKGRAY, DARKGRAY,
        };
        Point3d[] shapes = points.toArray(new Point3d[points.size()]);
        QuadArray quadarray = new QuadArray(shapes.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        quadarray.setCoordinates(0, shapes);
        quadarray.setColors(0, allColors);
        shape1.setGeometry(quadarray);
        addChild(shape1);

        if (text != null) {
            Text2D text2D = new Text2D(text, BLACK, "Helvetica", (int) (72 * LENGTH), Font.BOLD);
            Transform3D transform3D = new Transform3D();
            transform3D.rotY(Math.PI / 2.0);
            transform3D.setTranslation(new Vector3d(LENGTH + 0.01, -EDGE_SIZE / 3.0, CORNER_SIZE));
            TransformGroup group = new TransformGroup(transform3D);
            group.addChild(text2D);
            addChild(group);
        }

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
        points.add(A);
        points.add(E);
        points.add(E);
        points.add(H);
        points.add(H);
        points.add(D);
        points.add(D);
        points.add(A);
        // right
        points.add(E);
        points.add(F);
        points.add(F);
        points.add(G);
        points.add(G);
        points.add(H);
        points.add(H);
        points.add(E);
        // back
        points.add(D);
        points.add(C);
        points.add(C);
        points.add(G);
        points.add(G);
        points.add(F);
        points.add(F);
        points.add(D);
        // top
        points.add(B);
        points.add(H);
        points.add(H);
        points.add(G);
        points.add(G);
        points.add(C);
        points.add(C);
        points.add(B);
        // bottom
        points.add(A);
        points.add(D);
        points.add(D);
        points.add(F);
        points.add(F);
        points.add(E);
        points.add(E);
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
    
    /**
     * Creates a new middle piece using three colors (for front, right and back), an angle to define
     * orientation of piece.
     * @param colors array with three colors for front, right and back.
     * @param y value to rotate piece around top (allowed values are 0 for no rotation and 1 for 180 degrees.
     */
    public Square1MiddlePiece(Color3f [] colors, int y) {
        this(colors, y, null);
    }
    
    /* (non-Javadoc)
     * @see de.softquadrat.square1.AbstractPiece#duplicate()
     */
    public AbstractPiece duplicate() {
        return new Square1MiddlePiece(colors, y, text);
    }
}
