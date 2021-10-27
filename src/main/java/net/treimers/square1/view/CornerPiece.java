package net.treimers.square1.view;

import javafx.scene.shape.TriangleMesh;

public class CornerPiece extends AbstractPiece {
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	private static final int E = 4;
	private static final int F = 5;
	private static final int G = 6;
	private static final int H = 7;
	private static final float S = (float) (SIZE / Math.cos(ANGLE_15));
	private static final float[] POINTS = {
			// Point A
			0.0f,
			0.0f,
			0.0f,
			// Point B
			(float) (-S * Math.sin(ANGLE_30)),
			0.0f,
			(float) (S * Math.cos(ANGLE_30)),
			// Point C
			0.0f,
			0.0f,
			(float) (S * (Math.cos(ANGLE_30) + Math.sin(ANGLE_30))),
			// Point D
			(float) (S * Math.sin(ANGLE_30)),
			0.0f,
			(float) (S * Math.cos(ANGLE_30)),
			// Point E
			0.0f,
			CORNER_SIZE,
			0.0f,
			// Point F
			(float) (-S * Math.sin(ANGLE_30)),
			CORNER_SIZE,
			(float) (S * Math.cos(ANGLE_30)),
			// Point G
			0.0f,
			CORNER_SIZE,
			(float) (S * (Math.cos(ANGLE_30) + Math.sin(ANGLE_30))),
			// Point H
			(float) (S * Math.sin(ANGLE_30)),
			CORNER_SIZE,
			(float) (S * Math.cos(ANGLE_30)) };

	public CornerPiece(int[] colors, boolean flip, int rotate) {
		int topColor = colors[0];
		int leftColor = colors[1];
		int rightColor = colors[2];
		int[] faces = {
				// Bottom face 1
				//				A, BLACK, B, BLACK, C, BLACK, 
				// Bottom face 2
				//				C, BLACK, D, BLACK, A, BLACK,
				// Left front face 1
				C,
				leftColor,
				B,
				leftColor,
				F,
				leftColor,
				// Left front face 2
				F,
				leftColor,
				G,
				leftColor,
				C,
				leftColor,
				// Right front face 1
				C,
				rightColor,
				G,
				rightColor,
				H,
				rightColor,
				// Right front face 2
				H,
				rightColor,
				D,
				rightColor,
				C,
				rightColor,
				// Left back face 1
				// E, BLACK, F, BLACK, B, BLACK,
				// Left back face 2
				// B, BLACK, A, BLACK, E, BLACK, 
				// Right back face 1
				// E, BLACK, A, BLACK, D, BLACK, 
				// Right back face 2
				// D, BLACK, H, BLACK, E, BLACK,
				// Top face 1
				G,
				topColor,
				F,
				topColor,
				E,
				topColor,
				// Top face 2
				E,
				topColor,
				H,
				topColor,
				G,
				topColor };
		TriangleMesh m = new TriangleMesh();
		m.getPoints().addAll(POINTS);
		m.getTexCoords().addAll(COLOR_ARRAY);
		m.getFaces().addAll(faces);
		if (flip) {
			rotateByX(180);
			rotateByY(90 * rotate + 45);
			setTranslateY(-EDGE_SIZE / 2.0f);
		} else {
			rotateByY(90 * rotate + 45);
			setTranslateY(EDGE_SIZE / 2.0f);
		}
		setMesh(m);
	}
}