package de.softquadrat.square1.solver;

/*
 An object of this class represents the shape of the square-1
 (excluding middle layer, including piece parity)
 The class provides a list of all possible puzzle shapes.
 */

public class Sq1Shape {

	// static list of all possible layer shapes
	static Sq1Shape list[];
	// initialise list
	static {
		// build list of all possible shapes
		list = new Sq1Shape[7356];
		int nShape = 0;
		// for each pair of layers
		for (int i = 0; i < Layer.list.length; i++) {
			for (int j = 0; j < Layer.list.length; j++) {
				// possible shape only if no more exactly 16 pieces
				if (Layer.list[i].getNPieces() + Layer.list[j].getNPieces() == 16) {
					list[nShape++] = new Sq1Shape(Layer.list[i], Layer.list[j],
							true);
					list[nShape++] = new Sq1Shape(Layer.list[i], Layer.list[j],
							false);
				}
			}
		}
	}

	static int getShape(int s, boolean p) {
		for (int i = 0; i < list.length; i++) {
			if (list[i].pieces == s && list[i].parityOdd == p)
				return i;
		}
		// treimers
		return 255;
	}

	static Sq1Shape getShape(int idx) {
		return list[idx];
	}

	// ----------------

	private Layer topl, botl; // the two layers that form this shape

	private int pieces; // 24-bit shape pattern (1=edge, 0=corner)

	private boolean parityOdd; // parity of this position

	private int tpieces[] = new int[4]; // Resulting shape pattern after move or
										// after reflection

	private boolean tparity[] = new boolean[4]; // Resulting parity after move
												// or after reflection

	Sq1Shape(Layer l1, Layer l2, boolean p) {
		// store layers/parity
		topl = l1;
		botl = l2;
		parityOdd = p;
		// Get shape after each move
		pieces = (l1.getPieces() << 12) + l2.getPieces();
		tpieces[0] = (l1.getTPieces() << 12) + l2.getPieces();
		tpieces[1] = (l1.getPieces() << 12) + l2.getBPieces();
		tpieces[2] = (l1.getH1().getPieces() << 18)
				+ (l2.getH1().getPieces() << 12)
				+ (l1.getH2().getPieces() << 6) + (l2.getH2().getPieces());
		// calculate mirrored shape
		tpieces[3] = 0;
		for (int m = 1, i = 0; i < 24; i++, m <<= 1) {
			tpieces[3] <<= 1;
			if ((pieces & m) != 0)
				tpieces[3]++;
		}
		// get parity after each move
		tparity[0] = parityOdd ^ l1.getTurnParityOdd();
		tparity[1] = parityOdd ^ l2.getTurnParityOddb();
		tparity[2] = parityOdd
				^ ((l1.getH2().getNPieces() & l2.getH1().getNPieces() & 1) != 0);
		tparity[3] = parityOdd;
	}

	int domove(int m) {
		// find index of resulting shape after that move
		for (int j = 0; j < Sq1Shape.list.length; j++) {
			Sq1Shape shape2 = list[j];
			if (tpieces[m] == shape2.pieces && tparity[m] == shape2.parityOdd) {
				// found it
				return j;
			}
		}
		// should not happen
		return -1;
	}

	int getTopTurn() {
		return topl.getTurnT();
	}

	int getBotTurn() {
		return botl.getTurnB();
	}

	int getPieces() {
		return pieces;
	}

}
