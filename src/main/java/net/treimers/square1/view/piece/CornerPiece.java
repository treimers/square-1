package net.treimers.square1.view.piece;

import java.util.Map;

import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;
import net.treimers.square1.model.Side;

/**
 * <p>Instances of this class represent a Square-1 corner piece.
 *
 * <p>A corner piece has three visible, colored faces: left front, right front and bottom or top. All other faces are
 * not visible by default and colored light or dark gray.
 * 
 * <p>The piece is created using 8 corner points named A to H. All faces are build and colored using triangles based on the corners.
 * <ul>
 * <li>bottom: D, C, B, A</li>
 * <li>left rear: F, E, A, B</li>
 * <li>left front: C, G, F, B</li>
 * <li>right front: C, D, H, G</li>
 * <li>right rear: A, E, H, D</li>
 * <li>top: E, F, G, H</li>
 * </ul>
 * 
 * <p><img src="data:image/png;base64,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bnMuYWRvYmUuY29tL3RpZmYvMS4wLyIKICAgIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIKICAgeG1wTU06RG9jdW1lbnRJRD0iZ2ltcDpkb2NpZDpnaW1wOjhhOWQ4YmJlLWZjMTUtNGI2Zi05ZjAzLTgzMzg2MzdlMGYxMCIKICAgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDplMDk1YmRiMy03NmU1LTQzZDktYTE3Ni00YWJiMmQwOGMzMmMiCiAgIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpjOTgyMzdiMy05ZGMwLTQ3NDAtOTc1Mi0yOGFiNjc4M2M2MTYiCiAgIEdJTVA6QVBJPSIyLjAiCiAgIEdJTVA6UGxhdGZvcm09Ik1hYyBPUyIKICAgR0lNUDpUaW1lU3RhbXA9IjE2MzYwMzU5MDg1NjQ3NjMiCiAgIEdJTVA6VmVyc2lvbj0iMi4xMC4yMiIKICAgZGM6Rm9ybWF0PSJpbWFnZS9wbmciCiAgIHRpZmY6T3JpZW50YXRpb249IjEiCiAgIHhtcDpDcmVhdG9yVG9vbD0iR0lNUCAyLjEwIj4KICAgPGlwdGNFeHQ6TG9jYXRpb25DcmVhdGVkPgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6TG9jYXRpb25DcmVhdGVkPgogICA8aXB0Y0V4dDpMb2NhdGlvblNob3duPgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6TG9jYXRpb25TaG93bj4KICAgPGlwdGNFeHQ6QXJ0d29ya09yT2JqZWN0PgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6QXJ0d29ya09yT2JqZWN0PgogICA8aXB0Y0V4dDpSZWdpc3RyeUlkPgogICAgPHJkZjpCYWcvPgogICA8L2lwdGNFeHQ6UmVnaXN0cnlJZD4KICAgPHhtcE1NOkhpc3Rvcnk+CiAgICA8cmRmOlNlcT4KICAgICA8cmRmOmxpCiAgICAgIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiCiAgICAgIHN0RXZ0OmNoYW5nZWQ9Ii8iCiAgICAgIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NjZjZTE0YjktMzY1NC00ZGM1LWI1NDQtMTJmNmM3OGZhZjgxIgogICAgICBzdEV2dDpzb2Z0d2FyZUFnZW50PSJHaW1wIDIuMTAgKE1hYyBPUykiCiAgICAgIHN0RXZ0OndoZW49IjIwMjEtMTEtMDRUMTU6MjU6MDgrMDE6MDAiLz4KICAgIDwvcmRmOlNlcT4KICAgPC94bXBNTTpIaXN0b3J5PgogICA8cGx1czpJbWFnZVN1cHBsaWVyPgogICAgPHJkZjpTZXEvPgogICA8L3BsdXM6SW1hZ2VTdXBwbGllcj4KICAgPHBsdXM6SW1hZ2VDcmVhdG9yPgogICAgPHJkZjpTZXEvPgogICA8L3BsdXM6SW1hZ2VDcmVhdG9yPgogICA8cGx1czpDb3B5cmlnaHRPd25lcj4KICAgIDxyZGY6U2VxLz4KICAgPC9wbHVzOkNvcHlyaWdodE93bmVyPgogICA8cGx1czpMaWNlbnNvcj4KICAgIDxyZGY6U2VxLz4KICAgPC9wbHVzOkxpY2Vuc29yPgogIDwvcmRmOkRlc2NyaXB0aW9uPgogPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgIAo8P3hwYWNrZXQgZW5kPSJ3Ij8+RdsA3AAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+ULBA4ZCO2qivcAABtGSURBVHja7Z15mBxlncc/PZNzJncChCRGI0IUghDQBGJAI14IKKxCkMUYVlEUTTxYD4LuI666AiuLq6grsqIiQQTxVg4DEkhICMEEkwCJQZKQm2SSyTWZnt4/vr/arnS6u6qnq6qrp9/v89TTx1TXVL31rd/7u18Ih1Yg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" />
 * 
 * <p>The corner width i.e. the distance between B and C is defined in the Constants class. 
 */
public class CornerPiece extends AbstractPiece {
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
	/** Index of point G. */
	private static final int POINT_G = 6;
	/** Index of point H. */
	private static final int POINT_H = 7;
	/** The map with the piece names and their side defitions. */
	private static final Map<Character, int[]> SIDE_MAP = Map.of(
			// Piece A
			'A', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.LEFT.ordinal(),
				Side.FRONT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece B
			'B', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BACK.ordinal(),
				Side.LEFT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece C
			'C', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.RIGHT.ordinal(),
				Side.BACK.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece D
			'D', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.FRONT.ordinal(),
				Side.RIGHT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.TOP.ordinal(),
			},
			// Piece E
			'E', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.RIGHT.ordinal(),
				Side.FRONT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal(),
			},
			// Piece F
			'F', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BACK.ordinal(),
				Side.RIGHT.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal()
			},
			// Piece G
			'G', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.LEFT.ordinal(),
				Side.BACK.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.BOTTOM.ordinal(),
			},
			// Piece H
			'H', new int[] {
				Side.INNER_HORIZONTAL.ordinal(),
				Side.INNER_VERTICAL.ordinal(),
				Side.FRONT.ordinal(),
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
	 * @param name the name of the corner piece 'A' to 'H'.
	 */
	public CornerPiece(ColorBean colorBean, char name) {
		super(colorBean);
		int[] sides = SIDE_MAP.get(name);
		float[] points = {
			// Point A
			0.0f + Constants.DELTA,
			0.0f - Constants.DELTA,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point B
			Constants.SIZE,
			-Constants.EDGE_WIDTH / 2.0f - Constants.DELTA,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point C
			Constants.SIZE,
			-Constants.SIZE,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point D
			Constants.EDGE_WIDTH / 2.0f + Constants.DELTA,
			-Constants.SIZE,
			-1 * (Constants.EDGE_WIDTH / 2.0f + Constants.DELTA),
			// Point E
			0.0f + Constants.DELTA,
			0.0f - Constants.DELTA,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
			// Point F
			Constants.SIZE,
			-Constants.EDGE_WIDTH / 2.0f - Constants.DELTA,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
			// Point G
			Constants.SIZE,
			-Constants.SIZE,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
			// Point H
			Constants.EDGE_WIDTH / 2.0f + Constants.DELTA,
			-Constants.SIZE,
			-1 * (Constants.CORNER_WIDTH + Constants.EDGE_WIDTH / 2.0f - Constants.DELTA),
		};
		addAllPoints(points);
		int[] faces = {
			// Bottom face
			POINT_A,
			sides[0],
			POINT_D,
			sides[0],
			POINT_C,
			sides[0],
			POINT_C,
			sides[0],
			POINT_B,
			sides[0],
			POINT_A,
			sides[0],
			// Left rear face
			POINT_A,
			sides[1],
			POINT_B,
			sides[1],
			POINT_F,
			sides[1],
			POINT_F,
			sides[1],
			POINT_E,
			sides[1],
			POINT_A,
			sides[1],
			// Left front face
			POINT_B,
			sides[2],
			POINT_C,
			sides[2],
			POINT_G,
			sides[2],
			POINT_G,
			sides[2],
			POINT_F,
			sides[2],
			POINT_B,
			sides[2],
			// Right front face
			POINT_G,
			sides[3],
			POINT_C,
			sides[3],
			POINT_D,
			sides[3],
			POINT_D,
			sides[3],
			POINT_H,
			sides[3],
			POINT_G,
			sides[3],
			// Right rear face
			POINT_E,
			sides[4],
			POINT_H,
			sides[4],
			POINT_D,
			sides[4],
			POINT_D,
			sides[4],
			POINT_A,
			sides[4],
			POINT_E,
			sides[4],
			// Top face
			POINT_E,
			sides[5],
			POINT_F,
			sides[5],
			POINT_G,
			sides[5],
			POINT_G,
			sides[5],
			POINT_H,
			sides[5],
			POINT_E,
			sides[5],
		};
		addAllFaces(faces);
	}
}