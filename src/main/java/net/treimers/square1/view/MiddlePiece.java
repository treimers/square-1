package net.treimers.square1.view;

import javafx.scene.shape.TriangleMesh;

public class MiddlePiece extends AbstractPiece {
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
			-S,
			-EDGE_SIZE / 2.0f,
			SIZE,
			// Point B
			-S,
			EDGE_SIZE / 2.0f,
			SIZE,
			// Point C
			S,
			EDGE_SIZE / 2.0f,
			-SIZE,
			// Point D
			S,
			-EDGE_SIZE / 2.0f,
			-SIZE,
			// Point E
			SIZE,
			-EDGE_SIZE / 2.0f,
			SIZE,
			// Point F
			SIZE,
			-EDGE_SIZE / 2.0f,
			-SIZE,
			// Point G
			SIZE,
			EDGE_SIZE / 2.0f,
			-SIZE,
			// Point H
			SIZE,
			EDGE_SIZE / 2.0f,
			SIZE, };

	/**
	 * 
	 * @param colors array with two colors for top and front.
	 * @param flip position to flip from top to bottom position (must be 0 for no flip or 1 for flip).
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 */
	public MiddlePiece(int[] colors, boolean flip, int rotate) {
		TriangleMesh m = new TriangleMesh();
		m.getPoints().addAll(POINTS);
		m.getTexCoords().addAll(COLOR_ARRAY);
		int topColor = colors[0];
		int frontColor = colors[1];
		m.getFaces().addAll( // Faces
				//				A, BLACK, B, BLACK, C, BLACK, // Bottom face
				//				A, BLACK, D, BLACK, B, BLACK, // Left face 1
				//				D, BLACK, E, BLACK, B, BLACK, // Left face 2
				E, frontColor, C, frontColor, B, frontColor, // Front face 1
				E, frontColor, F, frontColor, C, frontColor, // Front face 2
				//				C, BLACK, D, BLACK, A, BLACK, // Right face 1
				//				C, BLACK, F, BLACK, D, BLACK, // Right face 2
				D, topColor, F, topColor, E, topColor // Bottom face
		);
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