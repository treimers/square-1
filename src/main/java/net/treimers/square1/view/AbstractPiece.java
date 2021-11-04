package net.treimers.square1.view;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * <p>This class is the base class for pieces of the Square-1 cube.
 * 
 * <p>It provides a triangle mesh to model the faces of the piece. A material with
 * colors for the faces is loaded and applied to the mesh. The colors are  picked
 * from an image with colored squares.
 * 
 * <p>
 * <img src=" data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANAAAAAaCAYAAADG19KmAAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV8/RNGqgxVEHDJUJwuiRRy1CkWoEGqFVh1MLv2CJg1Jiouj4Fpw8GOx6uDirKuDqyAIfoC4uTkpukiJ/0sKLWI8OO7Hu3uPu3eAv15mqhmcAFTNMlKJuJDJrgqdr+hBEH2IYVBipj4nikl4jq97+Ph6F+VZ3uf+HL1KzmSATyCeZbphEW8QT29aOud94jArSgrxOfG4QRckfuS67PIb54LDfp4ZNtKpeeIwsVBoY7mNWdFQiWPEEUXVKN+fcVnhvMVZLVdZ8578haGctrLMdZojSGARSxAhQEYVJZRhIUqrRoqJFO3HPfzDjl8kl0yuEhg5FlCBCsnxg//B727N/NSkmxSKAx0vtv0xCnTuAo2abX8f23bjBAg8A1day1+pAzOfpNdaWuQI6N8GLq5bmrwHXO4AQ0+6ZEiOFKDpz+eB9zP6piwwcAt0r7m9Nfdx+gCkqavkDXBwCIwVKHvd491d7b39e6bZ3w9rhHKkm0NW2wAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+ULBA41C02SsOMAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAAfElEQVR42u3TUQmAMBRA0TcDWGkh7GGCgVhBDLQwfg4EC2iLB8I5Be7XLbXWNxL03jMyMUbJ6ewpmbjOnM4yHymdZ1tTOu1uKZ0pAAOBgcBAYCDAQGAgMBAYCDAQGAgMBAYCAwEGAgOBgcBAgIHAQGAgMBBgIDAQGAh+6gN3aA2n5ogvngAAAABJRU5ErkJggg==" />
 * 
 * 
 */
public abstract class AbstractPiece extends MeshView {
	/** The height of the colored squares (50%) */
	private static final float COLOR_HEIGHT = 0.5f;
	/** The color array used for inking the material. */
	private static final float[] COLOR_ARRAY = {
		// 0 black
		(Constants.BLACK + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 1 white
		(Constants.WHITE + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 2 yellow
		(Constants.YELLOW + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 3 orange
		(Constants.ORANGE + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 4 red
		(Constants.RED + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 5 blue
		(Constants.BLUE + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 6 green
		(Constants.GREEN + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
		// 7 gray
		(Constants.GRAY + 0.5f) / Constants.NUM_COLORS,
		COLOR_HEIGHT,
	};
	/** The triangle mesh used to model the Square-1 piece. */
	private TriangleMesh triangleMesh;
	/** The transform applied to the piece. */
	private Transform transform;
    
	/**
	 * Creates a new instance.
	 */
	public AbstractPiece() {
		// init fields.
		transform = new Rotate();
		triangleMesh = new TriangleMesh();
		// set cull mode
		setCullFace(CullFace.NONE);
		// triangle mesh
		setMesh(triangleMesh);
		// colors
		triangleMesh.getTexCoords().addAll(COLOR_ARRAY);
		// material
		Image colorImage = new Image(AbstractPiece.class.getResourceAsStream("colors.png"));
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseMap(colorImage);
		setMaterial(mat);
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
	 * Rotates piece by angle around z-axis.
	 * @param ang
	 */
	public void rotateByZ(int ang) {
		Rotate r = new Rotate(ang, Rotate.Z_AXIS);
		transform = transform.createConcatenation(r);
		this.getTransforms().clear();
		this.getTransforms().addAll(transform);
	}
}
