package net.treimers.square1.view;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class SmartGroup extends Group {
	private Transform t = new Rotate();

	public void rotateByX(int ang) {
		Rotate r = new Rotate(ang, Rotate.X_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}

	public void rotateByY(int ang) {
		Rotate r = new Rotate(ang, Rotate.Y_AXIS);
		t = t.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(t);
	}
}