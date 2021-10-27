package net.treimers.square1.view;

import javafx.scene.shape.TriangleMesh;

public class EdgePiece extends AbstractPiece {
	// the point of the edge piece with their indices
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	private static final int E = 4;
	private static final int F = 5;
	private static final float S = (float) (SIZE * Math.sin(ANGLE_15));
	private static final float[] POINTS = {
			// Point A
			0.0f,
			0.0f,
			0.0f,
			// Point B
			(float) (-S),
			0.0f,
			SIZE,
			// Point C
			S,
			0.0f,
			SIZE,
			// Point D
			0.0f,
			CORNER_SIZE,
			0.0f,
			// Point E
			-S,
			CORNER_SIZE,
			SIZE,
			// Point F
			S,
			CORNER_SIZE,
			SIZE, };

	/**
	 * 
	 * @param colors array with two colors for top and front.
	 * @param flip position to flip from top to bottom position (must be 0 for no flip or 1 for flip).
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 */
	public EdgePiece(int[] colors, boolean flip, int rotate) {
		TriangleMesh m = new TriangleMesh();
		m.getPoints().addAll(POINTS);
		m.getTexCoords().addAll(COLOR_ARRAY);
		int topColor = colors[0];
		int frontColor = colors[1];
		int[] faces = {
				// Faces
				//				A, BLACK, B, BLACK, C, BLACK, // Bottom face
				//				A, BLACK, D, BLACK, B, BLACK, // Left face 1
				//				D, BLACK, E, BLACK, B, BLACK, // Left face 2
				E,
				frontColor,
				C,
				frontColor,
				B,
				frontColor, // Front face 1
				E,
				frontColor,
				F,
				frontColor,
				C,
				frontColor, // Front face 2
				//				C, BLACK, D, BLACK, A, BLACK, // Right face 1
				//				C, BLACK, F, BLACK, D, BLACK, // Right face 2
				D,
				topColor,
				F,
				topColor,
				E,
				topColor // Bottom face
		};
		m.getFaces().addAll(faces);
		if (flip) {
			rotateByX(180);
			rotateByY(90 * rotate);
			setTranslateY(-EDGE_SIZE / 2.0f);
		} else {
			rotateByY(90 * rotate);
			setTranslateY(EDGE_SIZE / 2.0f);
		}
		setMesh(m);
	}
}