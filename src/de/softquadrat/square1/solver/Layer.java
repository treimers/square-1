package de.softquadrat.square1.solver;

/*
 An object of this class represents the shape of a set of pieces filling one layer.
 The class provides a list of all possible layer shapes.
 */

public class Layer {
	// static list of all possible layer shapes
	static Layer list[];
	// initialise list
	static {
		// build list of all possible layers
		list = new Layer[158];
		int lll = 0;
		// for each pair of half-layers
		for (int i = 0; i < HalfLayer.list.length; i++) {
			for (int j = 0; j < HalfLayer.list.length; j++) {
				// possible layer only if no more than 10 pieces
				if (HalfLayer.list[i].getNPieces()
						+ HalfLayer.list[j].getNPieces() <= 10) {
					list[lll++] = new Layer(
							HalfLayer.list[i],
							HalfLayer.list[j]);
				}
			}
		}
	}

	// ----------------

	private HalfLayer h1, h2; // the two half layers that form this layer

	private int turnt, turnb; // Amount to turn (clockwise, counter-cw) till
	// next unblocked seam

	private int nPieces; // total number of pieces in the layer

	private boolean turnParityOdd, turnParityOddb; // permutation parity of a
	// (cw/ccw) turn

	private int pieces; // 12-bit shape pattern (1=edge, 0=corner)

	private int tpieces, bpieces; // shape result after (cw/ccw) turn

	Layer(HalfLayer p1, HalfLayer p2) {
		// store half layers
		h1 = p1;
		h2 = p2;
		// combine pieces
		pieces = (h1.getPieces() << 6) + h2.getPieces();
		nPieces = h1.getNPieces() + h2.getNPieces();

		// get pattern of cuts through this layer
		int turnpat = h1.getTurn() & h2.getTurn();
		// find first cut when turning clockwise
		int m = 1;
		for (turnt = 1; turnt < 6; turnt++) {
			if ((turnpat & m) != 0)
				break;
			m <<= 1;
		}
		if (turnt == 6) // no other cuts at all
			turnb = 6;
		else {
			// find first cut when turning anti-clockwise
			m = 1 << 4;
			for (turnb = 1; turnb < 5; turnb++) {
				if ((turnpat & m) != 0)
					break;
				m >>= 1;
			}
		}

		// Get shape after clockwise turn to next free seam
		tpieces = pieces;
		int nEdges = 0;
		for (int i = 0; i < turnt; i++) {
			if ((tpieces & 1) != 0) {
				tpieces += (1 << 12);
				nEdges++;
			}
			tpieces >>= 1;
		}
		// find out parity of that layer turn
		// Is odd cycle if even # pieces, and odd number passes seam
		// Note (turn+edges)/2 = number of pieces crossing seam
		turnParityOdd = (nPieces & 1) == 0
				&& ((turnt + nEdges) & 2) != 0;

		// Get shape after counter-clockwise turn to next free seam
		bpieces = pieces;
		nEdges = 0;
		for (int i = 0; i < turnb; i++) {
			bpieces <<= 1;
			if ((bpieces & (1 << 12)) != 0) {
				bpieces -= (1 << 12) - 1;
				nEdges++;
			}
		}
		// find out parity of that layer turn
		// Is odd cycle if even # pieces, and odd number passes seam
		// Note (turn+edges)/2 = number of pieces crossing seam
		turnParityOddb = (nPieces & 1) == 0
				&& ((turnb + nEdges) & 2) != 0;

	}

	HalfLayer getH1() {
		return h1;
	}

	HalfLayer getH2() {
		return h2;
	}

	int getTPieces() {
		return tpieces;
	}

	int getBPieces() {
		return bpieces;
	}

	int getPieces() {
		return pieces;
	}

	int getNPieces() {
		return nPieces;
	}

	boolean getTurnParityOdd() {
		return turnParityOdd;
	}

	boolean getTurnParityOddb() {
		return turnParityOddb;
	}

	int getTurnB() {
		return turnb;
	}

	int getTurnT() {
		return turnt;
	}
}
