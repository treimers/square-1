package de.softquadrat.square1.java3d;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

/**
 * Representation of a piece for corner, edge or middle.
 */
public abstract class AbstractPiece extends TransformGroup implements Constants {
    /**
     * Creates a copy of this piece.
     * @return copy of this piece.
     */
    abstract public AbstractPiece duplicate();

    /**
     * Move the piece to a given position.
     * @param position where piece will be moved to.
     */
    public void moveToPosition(Vector3d position) {
        Transform3D transform3D = new Transform3D();
        getTransform(transform3D);
        Transform3D move = new Transform3D();
        move.setTranslation(position);
        setTransform(move);
    }
}
