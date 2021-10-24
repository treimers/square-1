package de.softquadrat.square1.solver;

import de.softquadrat.square1.exception.Square1Exception;

/*
 An instance of this class represents full details of a square-1 position.
 */

public class FullPosition {
	// pos stores the current pieces. Pieces are numbered 0,,7 = corners, 8..15
	// = edges.
	// Note each corner uses up 2 entries in this array.
	// Order is clockwise from the front seam, top layer first.
	private int pos[] = new int[24];
	// State of the middle layer. 1=square, -1=kite, 0=don't care.
	private int middle;
	private boolean debug;

	public FullPosition() {
		reset();
	}

	int getMiddle() {
		return middle;
	}

	// reset to solved position
	void reset() {
		middle = 1;
		for (int i = 0; i < 24; i++)
			pos[i] = "AAIBBJCCKDDLMEENFFOGGPHH".charAt(i) - 'A';
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 24; i++) {
			sb.append("ABCDEFGH12345678".charAt(pos[i]));
			// skip second half of a corner piece
			if (pos[i] < 8)
				i++;
		}
		sb.append("/ -".charAt(middle + 1));
		return sb.toString();
	}

	// generate a random position
	void random() {
		int tmp[] = new int[16];
		middle = Math.random() < .5 ? -1 : 1;
		do {
			// get random permutation of numbers 0..15
			for (int i = 0; i < 16; i++)
				tmp[i] = i;
			for (int i = 0; i < 16; i++) {
				int j = (int) Math.floor(Math.random()
						* (16 - i));
				int k = tmp[i];
				tmp[i] = tmp[i + j];
				tmp[i + j] = k;
			}
			// store pieces in that order
			int j = 0;
			for (int i = 0; i < 16; i++) {
				pos[j++] = tmp[i];
				if (tmp[i] < 8)
					pos[j++] = tmp[i];
			}
			// test legal and twistable (i.e. no corner over seam or split over
			// layers)
		} while (pos[5] == pos[6] || pos[11] == pos[12]
				|| pos[17] == pos[18]);
	}

	// set position to given value. No consistency checking.
	void set(int p[], int m) {
		for (int i = 0; i < 24; i++)
			pos[i] = p[i];
		middle = m;
	}

	// Rotate the top layer by m twelfths of a turn.
	private void doTop(int m) {
		// convert to range 0..11
		m %= 12;
		if (m < 0)
			m += 12;
		// turn it m times
		while (m > 0) {
			// shift all the pieces
			int c = pos[11];
			for (int i = 11; i > 0; i--)
				pos[i] = pos[i - 1];
			pos[0] = c;
			m--;
		}
	}

	// Rotate the bottom layer by m twelfths of a turn.
	private void doBot(int m) {
		// convert to range 0..11
		m %= 12;
		if (m < 0)
			m += 12;
		// turn it m times
		while (m > 0) {
			// shift all the pieces
			int c = pos[23];
			for (int i = 23; i > 12; i--)
				pos[i] = pos[i - 1];
			pos[12] = c;
			m--;
		}
	}

	// Twist the right hand side
	private boolean doTwist() {
		// error if not possible
		if (!isTwistable())
			return false;
		// swap top/bottom pieces in right hand side
		for (int i = 6; i < 12; i++) {
			int c = pos[i];
			pos[i] = pos[i + 6];
			pos[i + 6] = c;
		}
		// flip middle layer
		middle = -middle;
		return true;
	}

	// Check whether seam is blocked by a corner
	private boolean isTwistable() {
		return (pos[0] != pos[11] && pos[5] != pos[6]
				&& pos[12] != pos[23] && pos[17] != pos[18]);
	}

	// Get a 24-bitpattern that encodes the current shape (bit set for edge,
	// clear for corner)
	int getShape() {
		int s = 0;
		for (int m = 1 << 23, i = 0; i < 24; i++, m >>= 1) {
			if (pos[i] >= 8)
				s |= m;
		}
		return s;
	}

	// Get parity of the current piece permutation
	boolean getParityOdd() {
		boolean p = false;
		// for each pair of pieces
		for (int i = 0; i < 24; i++) {
			for (int j = i; j < 24; j++) {
				// parity changes for every inversion
				if (pos[j] < pos[i])
					p = !p;
				// skip second half of a corner piece
				if (pos[j] < 8)
					j++;
			}
			// skip second half of a corner piece
			if (pos[i] < 8)
				i++;
		}
		return p;
	}

	// Get bit pattern for edges of colouring cl
	int getEdgeColouring(int cl) {
		final int EDGE_clp[][] = { { 8, 9, 10, 11 },
				{ 8, 9, 13, 14 }, { 15, 14, 10, 9 } };
		int c = 0;
		// set mask to bit for first edge
		int m = (cl != 2) ? 1 << 7 : 1;
		// for each edge
		for (int i = 0; i < 24; i++) {
			if (pos[i] >= 8) {
				// if edge is part of the requested colouring
				for (int j = 0; j < 4; j++) {
					if (pos[i] == EDGE_clp[cl][j]) {
						// set the bit
						c |= m;
						break;
					}
				}
				// go to bit for next edge
				if (cl != 2)
					m >>= 1;
				else
					m <<= 1;
			}
		}
		return c;
	}

	// Get bit pattern for corners of colouring cl
	int getCornerColouring(int cl) {
		final int CORNER_clp[][] = { { 0, 1, 2, 3 },
				{ 0, 1, 5, 6 }, { 7, 6, 2, 1 } };
		int c = 0;
		// set mask to bit for first edge
		int m = (cl != 2) ? 1 << 7 : 1;
		// for each corner
		for (int i = 0; i < 24; i++) {
			if (pos[i] < 8) {
				// if corner is part of the requested colouring
				for (int j = 0; j < 4; j++) {
					if (pos[i] == CORNER_clp[cl][j]) {
						c |= m;
						break;
					}
				}
				// go to bit for next corner
				if (cl != 2)
					m >>= 1;
				else
					m <<= 1;
				i++;
			}
		}
		return c;
	}

	// parse input string; either position or move sequence that solves the
	// position
	public void parseInput(String inp) throws Square1Exception {
		// check characters
		int f = 0;
		for (int i = 0; i < inp.length(); i++) {
			char c = inp.charAt(i);
			if (c == ',' || c == '(' || c == ')'
					|| c == '9' || c == '0') {
				f |= 1; // cannot be position string, but may be movelist
			} else if ((c >= 'a' && c <= 'h')
					|| (c >= 'A' && c <= 'H')) {
				f |= 2; // cannot be movelist, but may be position string
			} else if (c != '/' && c != '-'
					&& (c < '1' || c > '8')) {
				f |= 3; // cannot be either
			}
		}
		if (f == 3 || f == 0) {
			throw new Square1Exception("Unrecognised argument");
		}
		// f: 1=movesequence, 2=position

		reset();
		if (f == 1) {
			// move sequence of a solution. start parsing from end
			int lw = 0, lu = 0; // sequence length in twist/turn metrics
			int bt = 0, tt = 0; // amount of layer twists
			int vl = 0; // multiplier for next digit
			int md = 0; // state of this state machine
			boolean br = false; // inside set of brackets
			for (int i = inp.length() - 1; i >= 0; i--) {
				int k = inp.charAt(i);
				if (md == 0) { // parsing any move
					if (k == '/') {
						if (!doTwist())
							throw new Square1Exception("Twist is blocked by corner");
						lu++;
						lw++;
					} else {
						bt = 0;
						vl = 1;
						md++;
						i++; // try this character again, assuming it starts
						// bottom layer turn
					}
				} else if (md == 1) { // parsing bottom layer turn
					if (k == ')' && vl == 1) { // skip bracket
						br = true;
					} else if (k >= '0' && k <= '9') { // store digit
						bt += vl * (k - '0');
						vl *= 10;
					} else if (vl == 1) { // error if no digits at all
						throw new Square1Exception("Bottom layer turn expected");
					} else if (k == '-') { // parse minus sign
						bt = -bt;
						md++; // end of bottom layer turn
					} else if (k == ',') { // comma signals end of bottom layer
						// turn
						i++;
						md++;
					} else
						throw new Square1Exception("Bottom layer turn expected");
				} else if (md == 2) { // parsing comma, finish bottom layer
					if (k != ',')
						throw new Square1Exception("Comma expected");
					// normalise bottom layer turn amount
					bt %= 12;
					if (bt > 0)
						bt -= 12;
					// adjust layer metric count
					if (bt != 0)
						lu++;
					// do the move
					doBot(-bt);
					// start top layer turn
					tt = 0;
					vl = 1;
					md++;
				} else if (md == 3) { // parsing top layer turn
					if (k >= '0' && k <= '9') { // store digit
						tt += vl * (k - '0');
						vl *= 10;
					} else if (vl == 1) { // error if no digits at all
						throw new Square1Exception("Top layer turn expected");
					} else if (k == '-') { // parse minus sign
						tt = -tt;
						md++; // end of top layer turn
					} else { // anything else signals end of top layer turn
						i++;
						md++;
					}
				} else if (md == 4) { // finish top layer
					// check matching brackets, if any
					if (!br && k == '(')
						throw new Square1Exception("Unexpected bracket (");
					else if (br && k != '(')
						throw new Square1Exception("Bracket ( expected");
					else if (k != '(')
						i++;
					// normalise top layer turn amount
					tt %= 12;
					if (tt > 0)
						tt -= 12;
					// adjust layer metric count
					if (tt != 0)
						lu++;
					// do the move
					doTop(-tt);
					// start next turn
					md = 0;
				}
			}
			// check for unexpected end of string
			if (md == 1 && vl == 1)
				throw new Square1Exception("Bottom layer turn expected");
			else if (md == 1 || md == 2)
				throw new Square1Exception("Comma expected");
			else if (md == 3 && vl == 1)
				throw new Square1Exception("Top layer turn expected");
			else if (md >= 3 && br)
				throw new Square1Exception("Bracket ( expected");
			else if (md >= 3) { // finish off final top layer turn
				// normalise top layer turn amount
				tt %= 12;
				if (tt > 0)
					tt -= 12;
				// adjust layer metric count
				if (tt != 0)
					lu++;
				// do the move
				doTop(-tt);
			}
			if (debug)
				System.out.println("Input:" + inp + " [" + lw
					+ "|" + lu + "]");
		} else {
			// position
			if (inp.length() != 16 && inp.length() != 17)
				throw new Square1Exception("Position should be 16 or 17 characters");

			int j = 0;
			int pi[] = new int[24];
			// for each character
			for (int i = 0; i < 16; i++) {
				int k = inp.charAt(i);
				// convert to piece number
				if (k >= 'a' && k <= 'h')
					k -= 'a';
				else if (k >= 'A' && k <= 'H')
					k -= 'A';
				else if (k >= '1' && k <= '8')
					k -= '1' - 8;
				else
					throw new Square1Exception("Expected A-H or 1-8");
				// store piece
				pi[j++] = k;
				// store corners in two location entries
				if (k < 8)
					pi[j++] = k;
			}
			// set middle layer shape, if given
			int midLayer = 0;
			if (inp.length() == 17) {
				int k = inp.charAt(16);
				if (k != '-' && k != '/')
					throw new Square1Exception("Expected - or /");
				midLayer = (k == '-') ? 1 : -1;
			}
			// initialise position
			set(pi, midLayer);
		}
	}
}
