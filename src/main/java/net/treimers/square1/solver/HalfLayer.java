package net.treimers.square1.solver;

/*
 An object of this class represents the shape of a set of pieces filling half of one layer.
 The class provides a list of all possible half layer shapes.
 */

public class HalfLayer {

	// static list of all possible half layer shapes
	static HalfLayer list[];
	// initialise list
	static {
		// build list of all possible halflayers
		list = new HalfLayer[13];
		final int hi[] = { 0, 3, 12, 48, 9, 36, 33, 15, 39,
				51, 57, 60, 63 };
		for (int i = 0; i < list.length; i++) {
			list[i] = new HalfLayer(hi[i]);
		}
	}

	// pieces turn
	//  0 000000 42 101010
	//  3 000011 43 101011
	// 12 001100 46 101110
	// 48 110000 58 111010
	//  9 001001 45 101101
	// 36 100100 54 110110
	// 33 100001 53 110101
	// 15 001111 47 101111
	// 39 100111 55 110111
	// 51 110011 59 111011
	// 57 111001 61 111101
	// 60 111100 62 111110
	// 63 111111 63 111111
	// ----------------

	private int nPieces; // number of pieces in this half layer
	private int pieces; // 6-bit pattern of the shape (1=edge, 0=corner)
	private int turn; // 6-bit pattern for arrangements of seams

	HalfLayer(int p) {
		pieces = p;
		// count edges
		int nCorners = 0;
		turn = 63;
		for (int i = 0, m = 1; i < 6; i++, m <<= 1) {
			if ((pieces & m) == 0) {
				// count corner piece
				nCorners++;
				// mark blocked seam
				turn -= m;
				// skip second part of corner
				i++;
				m <<= 1;
			}
		}
		nPieces = 6 - nCorners;
	}

	int getPieces() {
		return pieces;
	}

	int getTurn() {
		return turn;
	}

	int getNPieces() {
		return nPieces;
	}
}
