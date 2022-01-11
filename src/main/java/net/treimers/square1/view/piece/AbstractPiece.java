package net.treimers.square1.view.piece;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;

/**
 * <p>This class is the base class for pieces of the Square-1 cube.
 * 
 * <p>It provides a triangle mesh to model the faces of the piece. A material with
 * colors for the faces is loaded and applied to the mesh. The colors are  picked
 * from a writable image with width = #colors and height = 1.
 * 
 */
public abstract class AbstractPiece extends MeshView implements PropertyChangeListener {
	/** The triangle mesh used to model the Square-1 piece. */
	private TriangleMesh triangleMesh;
	/** The transform applied to the piece. */
	private Transform transform;
	/** The material used to color the sides of the piece. */
	private PhongMaterial material;
    
	/**
	 * Creates a new instance.
	 * @param colorBean the bean with a Square-1 colors.
	 */
	protected AbstractPiece(ColorBean colorBean) {
		// init fields.
		transform = new Rotate();
		triangleMesh = new TriangleMesh();
		// set cull mode
		setCullFace(CullFace.NONE);
		// triangle mesh
		setMesh(triangleMesh);
		// colors
		triangleMesh.getTexCoords().addAll(Constants.getColorArray());
		material = new PhongMaterial();
		setColors(colorBean.getColors());
		setMaterial(material);
		// register
		colorBean.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Color[] colors = (Color[]) evt.getNewValue();
		setColors(colors);
	}

	/**
	 * Sets the colors of the Square-1 sides.
	 * @param colors array with colors of the Square-1 sides.
	 */
	private void setColors(Color[] colors) {
		WritableImage image = new WritableImage(colors.length, 1);
		PixelWriter writer = image.getPixelWriter();
		for (int i = 0; i < colors.length; i++)
			writer.setColor(i, 0, colors[i]);
		material.setDiffuseMap(image);
	}

	/**
	 * Adds all points to the piece.
	 * @param points the point array.
	 */
	public void addAllPoints(float[] points) {
		triangleMesh.getPoints().addAll(points);
	}

	/**
	 * Adds all faces to the piece.
	 * @param faces the face array.
	 */
	public void addAllFaces(int[] faces) {
		triangleMesh.getFaces().addAll(faces);
	}

	/**
	 * Rotates piece by angle around x-axis.
	 * @param ang the angle.
	 */
	public void rotateByX(int ang) {
		Rotate r = new Rotate(ang, Rotate.X_AXIS);
		transform = transform.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(transform);
	}

	/**
	 * Rotates piece by angle around y-axis.
	 * @param ang the angle.
	 */
	public void rotateByY(int ang) {
		Rotate r = new Rotate(ang, Rotate.Y_AXIS);
		transform = transform.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(transform);
	}

	/**
	 * Rotates piece by angle around z-axis.
	 * @param ang the angle.
	 */
	public void rotateByZ(int ang) {
		Rotate r = new Rotate(ang, Rotate.Z_AXIS);
		transform = transform.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(transform);
	}
}
