package net.treimers.square1.java3d;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;

/**
 * Represents a complete Square1.
 */
public class Square1 extends TransformGroup implements Constants {
    /**
     * Creates a new square1 with an initial orientation.
     * Adds all square1 pieces and a mouse behavior
     * to rotate the square1 cube.
     */
    public Square1() {
        // set up initial view
        Transform3D rotY = new Transform3D();
        rotY.rotY(Math.PI / 4.0);
        Transform3D rotX = new Transform3D();
        rotX.rotX(Math.PI / 6.0);
        rotX.mul(rotY);
        setTransform(rotX);
        // add all corner, edge and middle pieces
        for (int i = 0; i < ALL_PIECES.length; i++) {
            addChild(ALL_PIECES[i].duplicate());
        }
        // set up mouse behavior
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(this);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        addChild(myMouseRotate);
    }
}
