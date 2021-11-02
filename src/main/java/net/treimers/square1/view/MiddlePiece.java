package net.treimers.square1.view;

public class MiddlePiece extends AbstractPiece {
	// the point of the edge piece with their indices
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	private static final int E = 4;
	private static final int F = 5;
	private static final int G = 6;
	private static final int H = 7;

	/**
	 * 
	 * @param colors array with two colors for top and front.
	 * @param flip position to flip from top to bottom position (must be 0 for no flip or 1 for flip).
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 */
	public MiddlePiece(float size, int rotate, int... colors) {
		float edgeSize = (float) (2 * size * Math.sin(Constants.ANGLE_15));
		float[] points = {
			// Point A
			size,
			edgeSize / 2.0f,
			-size,
			// Point B
			size,
			edgeSize / 2.0f,
			size,
			// Point C
			(float) (size * Math.sin(Constants.ANGLE_15)),
			edgeSize / 2.0f,
			size,
			// Point D
			(float) (-size * Math.sin(Constants.ANGLE_15)),
			edgeSize / 2.0f,
			-size,
			// Point E
			size,
			-edgeSize / 2.0f,
			-size,
			// Point F
			size,
			-edgeSize / 2.0f,
			size,
			// Point G
			(float) (size * Math.sin(Constants.ANGLE_15)),
			-edgeSize / 2.0f,
			size,
			// Point H
			(float) (-size * Math.sin(Constants.ANGLE_15)),
			-edgeSize / 2.0f,
			-size,
		};
		addAllPoints(points);
		int[] faces = {
			// Faces
			// Bottom face
			A,
			colors[0],
			D,
			colors[0],
			C,
			colors[0],
			C,
			colors[0],
			B,
			colors[0],
			A,
			colors[0],
			// Left face
			A,
			colors[1],
			B,
			colors[1],
			F,
			colors[1],
			F,
			colors[1],
			E,
			colors[1],
			A,
			colors[1],
			// Front face
			F,
			colors[2],
			B,
			colors[2],
			C,
			colors[2],
			C,
			colors[2],
			G,
			colors[2],
			F,
			colors[2],
			// Right face
			D,
			colors[3],
			H,
			colors[3],
			G,
			colors[3],
			G,
			colors[3],
			C,
			colors[3],
			D,
			colors[3],
			// Back face
			A,
			colors[4],
			E,
			colors[4],
			H,
			colors[4],
			H,
			colors[4],
			D,
			colors[4],
			A,
			colors[4],
			// Top face
			E,
			colors[5],
			F,
			colors[5],
			G,
			colors[5],
			G,
			colors[5],
			H,
			colors[5],
			E,
			colors[5],
		};
		addAllFaces(faces);
		rotateByY(rotate * 180);
		setTranslateX((rotate == 0) ? 0.01f : -0.01f);
		setTranslateY(edgeSize / 2.0f);
	}
}