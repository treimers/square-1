package net.treimers.square1.view;

/**
 * <p>Instances of this class represent a Square-1 edge piece.
 *
 * <p>An edge piece has two visible and colored faces, top and front. All other faces are
 * not visible by default and colored gray.
 * 
 * <p>The piece is created using 6 corner points named A to F. All faces are build and colored using triangles based on the corners.
 * <ul>
 * <li>top: A, B, C</li>
 * <li>left 1: B, A, D</li>
 * <li>left 2: D, E, B</li>
 * <li>front 1: E, F, C</li>
 * <li>front 2: C, B, E</li>
 * <li>right 1: D, A, C</li>
 * <li>right 2: C, F, D</li>
 * <li>bottom: D, F, E</li>
 * </ul>
 * 
 * <p><img src="data:image/png;base64,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" />
 */
public class EdgePiece extends AbstractPiece {
	// the point of the edge piece with their indices
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	private static final int E = 4;
	private static final int F = 5;

	/**
	 * Creates a new instance.
	 * @param size the base size of the square-1.
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 * @param position set piece position (1 for top layer, -1 for bottom layer).
	 * @param colors array with colors for the 5 sides (bottom, left, front, right, top).
	 */
	public EdgePiece(float size, int rotate, int position, int... colors) {
		float s = (float) (size * Math.sin(Constants.ANGLE_15));
		float edgeWidth = (float) (2 * size * Math.sin(Constants.ANGLE_15));
		float cornerWidth = (float) (size - edgeWidth / 2.0f);
		float[] points = {
			// Point A
			0.0f,
			position * edgeWidth / 2.0f,
			0.0f,
			// Point B
			size,
			position * edgeWidth / 2.0f,
			-s,
			// Point C
			size,
			position * edgeWidth / 2.0f,
			s,
			// Point D
			0.0f,
			position * (cornerWidth + edgeWidth / 2.0f),
			0.0f,
			// Point E
			size,
			position * (cornerWidth + edgeWidth / 2.0f),
			-s,
			// Point F
			size,
			position * (cornerWidth + edgeWidth / 2.0f),
			s,
		};
		addAllPoints(points);
		int[] faces = {
			// Bottom face
			A,
			colors[0],
			C,
			colors[0],
			B,
			colors[0],
			// Left face
			A,
			colors[1],
			B,
			colors[1],
			E,
			colors[1],
			E,
			colors[1],
			D,
			colors[1],
			A,
			colors[1],
			// Front face
			E,
			colors[2],
			B,
			colors[2],
			C,
			colors[2],
			C,
			colors[2],
			F,
			colors[2],
			E,
			colors[2],
			// Right face
			D,
			colors[3],
			F,
			colors[3],
			C,
			colors[3],
			C,
			colors[3],
			A,
			colors[3],
			D,
			colors[3],
			// Top face
			D,
			colors[4],
			E,
			colors[4],
			F,
			colors[4],
		};
		addAllFaces(faces);
		rotateByY(90 * rotate);
	}
}