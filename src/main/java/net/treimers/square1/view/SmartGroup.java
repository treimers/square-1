package net.treimers.square1.view;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * Instances of this class are used as rotatable group.
 */
public class SmartGroup extends Group {
	/** The initial transform. */
	private Transform t = new Rotate();

	/**
	 * Rotates the smart group by an angle around x-axis.
	 * @param ang the rotation angle.
	 */
	public void rotateByX(int ang) {
		Rotate r = new Rotate(ang, Rotate.X_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}

	/**
	 * Rotates the smart group by an angle around y-axis.
	 * @param ang the rotation angle.
	 */
	public void rotateByY(int ang) {
		Rotate r = new Rotate(ang, Rotate.Y_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}

	/**
	 * Rotates the smart group by an angle around z-axis.
	 * @param ang the rotation angle.
	 */
	public void rotateByZ(int ang) {
		Rotate r = new Rotate(ang, Rotate.Z_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}
}