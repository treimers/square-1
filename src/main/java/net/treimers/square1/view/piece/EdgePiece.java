package net.treimers.square1.view.piece;

import java.util.Map;

import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;
import net.treimers.square1.model.Side;

/**
 * <p>Instances of this class represent a Square-1 edge piece.
 *
 * <p>An edge piece has two visible, colored faces: top and front. All other faces are
 * not visible by default and colored light or dark gray.
 * 
 * <p>The piece is created using 6 corner points named A to F. All faces are build and colored using triangles based on the corners.
 * <ul>
 * <li>bottom: C, B, A</li>
 * <li>left: A, B, E, D</li>
 * <li>front: B, C, F, E</li>
 * <li>right 1: D, F, C, A</li>
 * <li>top: D, E, F</li>
 * </ul>
 * 
 * <p><img src="data:image/png;base64,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YWRvYmUuY29tL3RpZmYvMS4wLyIKICAgIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIKICAgeG1wTU06RG9jdW1lbnRJRD0iZ2ltcDpkb2NpZDpnaW1wOmU3ODlkMzFmLWFiMWQtNGE1Ny1hMDg5LWJkZTZiZmUxOTgwOCIKICAgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozMzAwN2U3MS0yY2Y3LTQ5NjUtYTFjMy1iMTczYzJiNmI0OTEiCiAgIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDozZWY1MDBlYi05ODUzLTQ3ZGUtYmNhNC0xNzA1ZjMyOTNjNGMiCiAgIEdJTVA6QVBJPSIyLjAiCiAgIEdJTVA6UGxhdGZvcm09Ik1hYyBPUyIKICAgR0lNUDpUaW1lU3RhbXA9IjE2MzYwMzU5MzA5NDIzNzEiCiAgIEdJTVA6VmVyc2lvbj0iMi4xMC4yMiIKICAgZGM6Rm9ybWF0PSJpbWFnZS9wbmciCiAgIHRpZmY6T3JpZW50YXRpb249IjEiCiAgIHhtcDpDcmVhdG9yVG9vbD0iR0lNUCAyLjEwIj4KICAgPGlwdGNFeHQ6TG9jYXRpb25DcmVhdGVkPgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6TG9jYXRpb25DcmVhdGVkPgogICA8aXB0Y0V4dDpMb2NhdGlvblNob3duPgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6TG9jYXRpb25TaG93bj4KICAgPGlwdGNFeHQ6QXJ0d29ya09yT2JqZWN0PgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6QXJ0d29ya09yT2JqZWN0PgogICA8aXB0Y0V4dDpSZWdpc3RyeUlkPgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6UmVnaXN0cnlJZD4KICAgPHhtcE1NOkhpc3Rvcnk+CiAgICA8cmRmOlNlcT4KICAgICA8cmRmOmxpCiAgICAgIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiCiAgICAgIHN0RXZ0OmNoYW5nZWQ9Ii8iCiAgICAgIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6MTQ0OTUxMDEtMmNiYS00M2E3LTlhY2ItZjBhMmQzNzc0NjFlIgogICAgICBzdEV2dDpzb2Z0d2FyZUFnZW50PSJHaW1wIDIuMTAgKE1hYyBPUykiCiAgICAgIHN0RXZ0OndoZW49IjIwMjEtMTEtMDRUMTU6MjU6MzArMDE6MDAiLz4KICAgIDwvcmRmOlNlcT4KICAgPC94bXBNTTpIaXN0b3J5PgogICA8cGx1czpJbWFnZVN1cHBsaWVyPgogICAgPHJkZjpTZXEvPgogICA8L3BsdXM6SW1hZ2VTdXBwbGllcj4KICAgPHBsdXM6SW1hZ2VDcmVhdG9yPgogICAgPHJkZjpTZXEvPgogICA8L3BsdXM6SW1hZ2VDcmVhdG9yPgogICA8cGx1czpDb3B5cmlnaHRPd25lcj4KICAgIDxyZGY6U2VxLz4KICAgPC9wbHVzOkNvcHlyaWdodE93bmVyPgogICA8cGx1czpMaWNlbnNvcj4KICAgIDxyZGY6U2VxLz4KICAgPC9wbHVzOkxpY2Vuc29yPgogIDwvcmRmOkRlc2NyaXB0aW9uPgogPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgIAo8P3hwYWNrZXQgZW5kPSJ3Ij8+6C/kNAAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+ULBA4ZHhl+P6YAABUYSURBVHja7Z15nBTlmce/Mz3ODIwoIioKEoloVkGI4oF4EVE8k9Wo0bDRjce6GtcYj2yia6Ixmo0aTYwbRZOwUVGJGrzPxCuHaBRMEBWvKF6IIoMiA8zRvX/8ntpuh57uqp6u6qrq9/f51Kdnuququ9566nmf4/c8L3wajcDv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" />
 * 
 * <p>The edge width i.e. the distance between B and C is calculated and defined in the Constants class. 
 */
public class EdgePiece extends AbstractPiece {
	/** Index of point A. */
	private static final int POINT_A = 0;
	/** Index of point B. */
	private static final int POINT_B = 1;
	/** Index of point C. */
	private static final int POINT_C = 2;
	/** Index of point D. */
	private static final int POINT_D = 3;
	/** Index of point E. */
	private static final int POINT_E = 4;
	/** Index of point F. */
	private static final int POINT_F = 5;
	/** The map with the piece names and their side defitions. */
	private static final Map<Character, int[]> SIDE_MAP = Map.of(
			// Piece 1
			'1', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.LEFT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece 2
			'2', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BACK.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece 3
			'3', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.RIGHT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece 4
			'4', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.FRONT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece 5
			'5', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.FRONT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal(),
			},
			// Piece 6
			'6', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.RIGHT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal(),
			},
			// Piece 7
			'7', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BACK.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal(),
			},
			// Piece 8
			'8', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.LEFT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal(),
			}
	//
	);

	/**
	 * Creates a new instance.
	 * 
	 * @param colorBean the bean used to get the colors.
	 * @param name the name of the edge piece '1' to '8'.
	 */
	public EdgePiece(ColorBean colorBean, char name) {
		super(colorBean);
		int[] sides = SIDE_MAP.get(name);
		float[] points = {
			// Point A
			0.0f,
			0.0f,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point B
			Constants.SIZE,
			Constants.EDGE_WIDTH / 2.0f - Constants.DELTA,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point C
			Constants.SIZE,
			-Constants.EDGE_WIDTH / 2.0f + Constants.DELTA,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point D
			0.0f + Constants.DELTA,
			0.0f,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
			// Point E
			Constants.SIZE,
			Constants.EDGE_WIDTH / 2.0f - Constants.DELTA,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
			// Point F
			Constants.SIZE,
			-Constants.EDGE_WIDTH / 2.0f + Constants.DELTA,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
		};
		addAllPoints(points);
		int[] faces = {
			// Bottom face
			POINT_A,
			sides[0],
			POINT_C,
			sides[0],
			POINT_B,
			sides[0],
			// Left face
			POINT_A,
			sides[1],
			POINT_B,
			sides[1],
			POINT_E,
			sides[1],
			POINT_E,
			sides[1],
			POINT_D,
			sides[1],
			POINT_A,
			sides[1],
			// Front face
			POINT_E,
			sides[2],
			POINT_B,
			sides[2],
			POINT_C,
			sides[2],
			POINT_C,
			sides[2],
			POINT_F,
			sides[2],
			POINT_E,
			sides[2],
			// Right face
			POINT_D,
			sides[3],
			POINT_F,
			sides[3],
			POINT_C,
			sides[3],
			POINT_C,
			sides[3],
			POINT_A,
			sides[3],
			POINT_D,
			sides[3],
			// Top face
			POINT_D,
			sides[4],
			POINT_E,
			sides[4],
			POINT_F,
			sides[4],
		};
		addAllFaces(faces);
		rotateByZ(-60);
	}
}