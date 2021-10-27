package net.treimers.square1.view;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
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

public class AbstractPiece extends MeshView implements Constants {
    public static final float SMALL = (float) (SIZE * Math.tan(ANGLE_15));
    public static final float LARGE = (SIZE - SMALL) / 2.0f;
	private Transform t = new Rotate();
    
    // Embed image in Java Doc
    // https://stackoverflow.com/questions/10779169/including-images-in-javadocs
    /**
     * <p>The colorImage is used to define the available colors for the Square-1 pieces.
     * 
     * <p>It contains seven colored squares (see below). The colors are numbered consecutively
     * and thus identified by an index number from black (0) to green (6).
     * 
     * <p>
     * <img src=" data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAALYAAAAaCAYAAAAaLqaRAAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw1AUhU9TpVIrDnZQcchQnSyIijhqFYpQIdQKrTqYvPRHaNKQpLg4Cq4FB38Wqw4uzro6uAqC4A+Im5uToouUeF9SaBHjg8v7OO+dw333AUK9zDSrYwzQdNtMJxNiNrcihl4RxgBVCN0ys4xZSUrBd33dI8D3uzjP8r/35+pR8xYDAiLxDDNMm3ideGrTNjjvE0dZSVaJz4lHTWqQ+JHrisdvnIsuCzwzambSc8RRYrHYxkobs5KpEU8Sx1RNp3wh67HKeYuzVq6yZp/8hZG8vrzEdaohJLGARUgQoaCKDZRhI067ToqFNJ0nfPyDrl8il0KuDTByzKMCDbLrB/+D37O1ChPjXlIkAXS+OM7HMBDaBRo1x/k+dpzGCRB8Bq70lr9SB6Y/Sa+1tNgR0LsNXFy3NGUPuNwB+p8M2ZRdKUglFArA+xl9Uw7ouwXCq97cmuc4fQAyNKvUDXBwCIwUKXvN591d7XP7905zfj8KhXJ9SYr+0gAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+UKGQojGTqpelIAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAAYUlEQVR42u3SwREAIAgDwWjlWjl2kYez2wAD3EoyKZipjEmyOmNuaZ3TulrpP9P5zw58SNgIG4QNwgZhg7ARNggbhA3CBmGDsBE2CBuEDcIGYYOwETYIG4QNwgZhI2z4zwNxigcyMu+jygAAAABJRU5ErkJggg==" />
     */
	private static final Image colorImage;    

    static {
    	colorImage = new Image(AbstractPiece.class.getResourceAsStream("colors.png"));
    }
	/**
	 * <p>Color values read from a colored image. The pixel of all squares is taken from a position half the image height.
	 */
	public static final float COLOR_HEIGHT = 0.5f;

	/**
	 * Color values read from a colored image.
	 * The image contains colored squares. An index is used to pick the corresponding color from the image.
	 * 
	 * <pre>
	 * +--------+--------+--------+--------+--------+--------+--------+
	 * | black  | white  | yellow | orange | red    | blue   | green  |
	 * +--------+--------+--------+--------+--------+--------+--------+
	 * </pre>
	 * 
	 * For example the yellow area spans interval from 2/7 to 3/7 so the yellow color is taken
	 * from position 2.5/7 of the image width.
	 */
	public static final float[] COLOR_ARRAY = {
			// Colors
			(BLACK + 0.5f) / 7, COLOR_HEIGHT,     // 0 black
			(WHITE + 0.5f) / 7, COLOR_HEIGHT,     // 1 white
			(YELLOW  + 0.5f) / 7, COLOR_HEIGHT,   // 2 yellow
			(ORANGE + 0.5f) / 7, COLOR_HEIGHT,    // 3 orange
			(RED + 0.5f) / 7, COLOR_HEIGHT,       // 4 red
			(BLUE + 0.5f) / 7, COLOR_HEIGHT,      // 5 blue
			(GREEN + 0.5f) / 7, COLOR_HEIGHT,     // 6 green
	};


	public AbstractPiece() {
		setCullFace(CullFace.FRONT);
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseMap(colorImage);
		setMaterial(mat);
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
