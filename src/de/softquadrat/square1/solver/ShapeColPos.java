package de.softquadrat.square1.solver;

/*
 An instance of this class represents the position of a square-1 of which
 exactly 4 pieces are coloured and the rest are blank.

 For example, one colouring could have the edges belonging in the top layer
 as one colour, the rest blank.
 */

public class ShapeColPos {
	private static final int botmask = (1 << 12) - 1;

	private static final int topmask = (1 << 24) - (1 << 12);

	private static final int botrmask = (1 << 12) - (1 << 6);

	private static final int toprmask = (1 << 18) - (1 << 12);

	private static final int leftmask = botmask + topmask - botrmask - toprmask;

	private ShapeTranTable stt; // transition table for the shapes

	private Sq1Shape shape; // current shape
	private int shapeIx; // index number of current shape in global shape list

	private int colouring; // 24 bit string showing the coloured pieces

	private boolean edgesFlag; // set if the 4 coloured pieces are edges, clear
								// for corners.

	ShapeColPos(ShapeTranTable stt0) {
		stt = stt0;
	}

	// initialise from shape index and colour choice index, using either
	// coloured edges or corners.
	void set(int shp, int col, boolean edges) {
		// col is 8 bit colouring of one type of piece.
		// edges set then edge colouring, else corner colouring
		shapeIx = shp;
		shape = Sq1Shape.getShape(shapeIx);
		edgesFlag = edges;
		colouring = 0;
		// calculate the full 24 bit colouring pattern
		int c = ChoiceTable.getIdx2Choice(col);
		int s = shape.getPieces();
		if (edges) {
			// build edge colouring pattern
			int n = 1;
			for (int m = 1, i = 0; i < 24; m <<= 1, i++) {
				if ((s & m) != 0) {
					if ((c & n) != 0)
						colouring |= m;
					n <<= 1;
				}
			}
		} else {
			// build corner colouring pattern
			int n = 1;
			for (int m = 3, i = 0; i < 24; m <<= 1, i++) {
				if ((s & m) == 0) {
					if ((c & n) != 0)
						colouring |= m;
					n <<= 1;
					// skip second half of corner
					m <<= 1;
					i++;
				}
			}
		}
	}

	// Apply a move to this position
	void domove(int m) {
		// apply the move to the colouring pattern
		int b, t, tn = 0;
		if (m == 0) {
			tn = shape.getTopTurn();
			b = colouring & botmask;
			t = colouring & topmask;
			t += (t >> 12);
			t <<= (12 - tn);
			colouring = b + (t & topmask);
		} else if (m == 1) {
			tn = shape.getBotTurn();
			b = colouring & botmask;
			t = colouring & topmask;
			b += (b << 12);
			b >>= (12 - tn);
			colouring = t + (b & botmask);
		} else if (m == 2) {
			b = colouring & botrmask;
			t = colouring & toprmask;
			colouring = (colouring & leftmask) + (t >> 6) + (b << 6);
		}
		// apply the move to the shape
		shapeIx = stt.getEntry(shapeIx, m);
		shape = Sq1Shape.getShape(shapeIx);
	}

	// Calculate the index number for this colouring
	int getColIdx() {
		int c = 0, n = 1;
		int s = shape.getPieces();
		if (edgesFlag) {
			// build 8-bit edge colouring pattern
			for (int m = 1, i = 0; i < 24; m <<= 1, i++) {
				if ((s & m) != 0) {
					if ((colouring & m) != 0)
						c |= n;
					n <<= 1;
				}
			}
		} else {
			// build 8-bit corner colouring pattern
			for (int m = 3, i = 0; i < 24; m <<= 1, i++) {
				if ((s & m) == 0) {
					if ((colouring & m) != 0)
						c |= n;
					n <<= 1;
					// skip second half of corner
					m <<= 1;
					i++;
				}
			}
		}
		// get index number for this 8-bit colour pattern
		return ChoiceTable.getChoice2Idx(c);
	}
}
