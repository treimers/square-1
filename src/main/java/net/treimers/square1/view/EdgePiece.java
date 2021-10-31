package net.treimers.square1.view;

public class EdgePiece extends AbstractPiece {
	// the point of the edge piece with their indices
	private static final int POINT_A = 0;
	private static final int POINT_B = 1;
	private static final int POINT_C = 2;
	private static final int POINT_D = 3;
	private static final int POINT_E = 4;
	private static final int POINT_F = 5;

	/**
	 * Creates a new instance.
	 * @param size the base size of the square-1.
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 * @param position set piece position (1 for top layer, -1 for bottom layer).
	 * @param colors array with colors for the 5 sides (top, left, front, right, bottom).
	 */
	public EdgePiece(float size, int rotate, int position, int... colors) {
		float s = (float) (size * Math.sin(Constants.ANGLE_15));
		float edgeSize = (float) (2 * size * Math.sin(Constants.ANGLE_15));
		float cornerSize = (float) (size - edgeSize / 2.0f);
		float[] points = {
			// Point A
			0.0f,
			0.0f,
			0.0f,
			// Point B
			-s,
			0.0f,
			size,
			// Point C
			s,
			0.0f,
			size,
			// Point D
			0.0f,
			cornerSize,
			0.0f,
			// Point E
			-s,
			cornerSize,
			size,
			// Point F
			s,
			cornerSize,
			size,
		};
		addAllPoints(points);
		int[] faces = {
			// Top face
			POINT_A,
			colors[0],
			POINT_B,
			colors[0],
			POINT_C,
			colors[0],
			// Left face 1
			POINT_B,
			colors[1],
			POINT_A,
			colors[1],
			POINT_D,
			colors[1],
			// Left face 2
			POINT_D,
			colors[1],
			POINT_E,
			colors[1],
			POINT_B,
			colors[1],
			// Front face 1
			POINT_E,
			colors[2],
			POINT_F,
			colors[2],
			POINT_C,
			colors[2],
			// Front face 2
			POINT_C,
			colors[2],
			POINT_B,
			colors[2],
			POINT_E,
			colors[2],
			// Right face 1
			POINT_D,
			colors[3],
			POINT_A,
			colors[3],
			POINT_C,
			colors[3],
			// Right face 2
			POINT_C,
			colors[3],
			POINT_F,
			colors[3],
			POINT_D,
			colors[3],
			// Bottom face
			POINT_D,
			colors[4],
			POINT_F,
			colors[4],
			POINT_E,
			colors[4],
		};
		addAllFaces(faces);
		rotateByY(90 * rotate);
		setTranslateY(position * edgeSize);
	}
}