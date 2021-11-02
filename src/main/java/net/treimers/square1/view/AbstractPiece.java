package net.treimers.square1.view;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/*

https://stackoverflow.com/questions/26831871/coloring-individual-triangles-in-a-triangle-mesh-on-javafx

more sources

https://stackoverflow.com/questions/19459012/how-to-create-custom-3d-model-in-javafx-8
https://stackoverflow.com/questions/61231437/how-to-create-such-shape-using-javafx-trianglemesh
https://www.dummies.com/programming/java/javafx-add-a-mesh-object-to-a-3d-world/
https://www.genuinecoder.com/javafx-3d/

*/

public class AbstractPiece extends MeshView {
	private static final float COLOR_HEIGHT = 0.5f;
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
	private Transform t = new Rotate();
	private TriangleMesh triangleMesh;
    
	public AbstractPiece() {
		setCullFace(CullFace.NONE);
		// triangle mesh
		triangleMesh = new TriangleMesh();
		setMesh(triangleMesh);
		// colors
		triangleMesh.getTexCoords().addAll(COLOR_ARRAY);
		// material
		Image colorImage = new Image(AbstractPiece.class.getResourceAsStream("colors.png"));
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseMap(colorImage);
		setMaterial(mat);
	}

	public void addAllPoints(float[] points) {
		triangleMesh.getPoints().addAll(points);
	}

	public void addAllFaces(int[] faces) {
		triangleMesh.getFaces().addAll(faces);
	}

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
