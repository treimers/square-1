package de.softquadrat.square1.solver;

/*
 An instance of this class represents a square-1 position,
 encoded into numbers for looking up into tables.
 Provides a solving algorithm.

 The puzzle position is split into three simpler puzzles with
 fewer colours.

 Suppose for example that the top layer was painted one colour,
 and the bottom layer another. This puzzle is solved whenever the
 puzzle is cube-shaped and its pieces are in their original layers.
 Similar pieces in a layer can be exchanged and it is still solved.

 Similarly you could paint all the pieces in the left half of the
 puzzle in one colour, and all the pieces on the right half another
 colour. This colour variant has the same number of positions as
 the previous layered colouring, but a different solved position.

 In the same way you could have a front/back half colouring.

 Note that each piece of the normally coloured square-1 is uniquely
 determined if you know whether it is a corner or edge, whether it
 belongs in the top or bottom layer, whether it belongs in the right
 or left half, and whether it belongs in the front or back half.

 This means that we can split the normal puzzle into these three simpler
 puzzles. If there is a move sequence that solves all three of the
 simpler puzzles, then it will also solve the normal one.

 This program uses a slightly different colouring than the last two
 above, so that we can re-use the same set of transition and pruning
 tables in the search. This was a clever idea from Mike Godfrey.

 One colouring splits the top layer into left/right halves, and the
 bototm layer into front/back halves. The other is the top/bottom
 mirror image of that, and so uses the top front/back halves and
 bottom left/right halves. In fact, we store the mirrored position
 of this mirrored colouring, so that we essentially have two identical
 puzzles to solve simultaneously - they just have different positions,
 and we have to apply mirrored moves to them.

 Note: this is why top layer moves are always clockwise, and bottom
 layer moves always counter-clockwise. It ensures reflection will map
 the moves to each other so that the same tables can be used.

 */

public class SimpPosition {
	// mirror mapping of the moves
	private static final int mirrmv[] = { 1, 0, 2 };

	// Current edge permutation, given by 3 colourings
	private int e0, e1, e2;
	// Current corner permutation, given by 3 colourings
	private int c0, c1, c2;

	// current shape, and current mirrored shape
	private int shp, shp2;
	// current middle layer shape
	private int middle;

	// transition tables
	private ShapeTranTable stt;
	private ShpColTranTable scte;
	private ShpColTranTable sctc;

	// pruning tables for two colourings (3rd is mirror of 2nd)
	private PrunTable pr1;
	private PrunTable pr2;

	// moves performed so far in search
	private int moveList[] = new int[50];

	// number of moves performed so far in search
	private int moveLen;

	// the last 3 pairs of top/bottom layer turns.
	private int lastTurns[] = new int[6];

	// set for turn metric, clear for twist metric
	private boolean turnMetric;

	// number of positions visited during search
	private int nodes;

	public SimpPosition(ShapeTranTable stt0, ShpColTranTable scte0,
			ShpColTranTable sctc0, PrunTable pr10, PrunTable pr20) {
		stt = stt0;
		scte = scte0;
		sctc = sctc0;
		pr1 = pr10;
		pr2 = pr20;
	}

	// set encoded position, given a full position
	public void set(FullPosition p, boolean turnMetric0) {
		// set colouring indices
		c0 = ChoiceTable.getChoice2Idx(p.getCornerColouring(0));
		c1 = ChoiceTable.getChoice2Idx(p.getCornerColouring(1));
		c2 = ChoiceTable.getChoice2Idx(p.getCornerColouring(2));
		e0 = ChoiceTable.getChoice2Idx(p.getEdgeColouring(0));
		e1 = ChoiceTable.getChoice2Idx(p.getEdgeColouring(1));
		e2 = ChoiceTable.getChoice2Idx(p.getEdgeColouring(2));
		// set shapes
		shp = Sq1Shape.getShape(p.getShape(), p.getParityOdd());
		shp2 = stt.getEntry(shp, 3);
		middle = p.getMiddle();
		turnMetric = turnMetric0;
	}

	// apply move to this position. Return amount of turn.
	public int doMove(int m) {
		// get amount of layer turn
		int r = 0;
		if (m == 0) {
			r = Sq1Shape.getShape(shp).getTopTurn();
		} else if (m == 1) {
			r = Sq1Shape.getShape(shp).getBotTurn();
		} else {
			// apply move to middle layer
			middle = -middle;
		}
		// apply move to each colouring
		c0 = sctc.getEntry(shp, c0, m);
		c1 = sctc.getEntry(shp, c1, m);
		e0 = scte.getEntry(shp, e0, m);
		e1 = scte.getEntry(shp, e1, m);
		shp = stt.getEntry(shp, m);
		// apply mirrored move to mirrored colouring
		c2 = sctc.getEntry(shp2, c2, mirrmv[m]);
		e2 = scte.getEntry(shp2, e2, mirrmv[m]);
		shp2 = stt.getEntry(shp2, mirrmv[m]);
		return r;
	}

	// solve this position.
	// Use IDA*, i.e. pruned depth-first search with tierative deepening
	public void solve() {
		int ln = -1; // length of solution to search for
		moveLen = 0;
		nodes = 0;
		// only try even lengths if twist metric and middle is square
		if (!turnMetric && middle == 1)
			ln = -2;
		// do ida
		do {
			// increase depth.
			ln++;
			// search only even/odd lengths if twist metric and care about
			// middle layer
			if (!turnMetric && middle != 0)
				ln++;
			// clear last turns array
			for (int i = 0; i < 6; i++)
				lastTurns[i] = 0;
			// search, loop if nothing found
		} while (!search(ln, 3));
	}

	// Solve current position in exactly ln moves, last move was lm (0=top,
	// 1=bot, 2=twist)

	private boolean search(int ln, int lm) {
		int i;
		// search for ln more moves. previous move was lm.
		nodes++;
		if (ln < 0)
			return false;

		// prune based on transformation
		// (a,b)/(c,d)/(e,f) -> (6+a,6+b)/(d,c)/(6+e,6+f)
		if (turnMetric) {
			// (a,b)/(c,d)/(e,f) -> (6+a,6+b)/(d,c)/(6+e,6+f)
			// moves changes by:
			// a,b,e,f=0/6 -> m++/m--
			i = 0;
			if (lastTurns[0] == 0)
				i++;
			else if (lastTurns[0] == 6)
				i--;
			if (lastTurns[1] == 0)
				i++;
			else if (lastTurns[1] == 6)
				i--;
			if (lastTurns[4] == 0)
				i++;
			else if (lastTurns[4] == 6)
				i--;
			if (lastTurns[5] == 0)
				i++;
			else if (lastTurns[5] == 6)
				i--;
			// if can reduce number of sixes, prune this search.
			if (i < 0 || (i == 0 && lastTurns[0] >= 6))
				return false;
		}

		// check if it is now solved
		if (ln == 0) {
			// compare with the solved position data
			if (shp == 4163 && e0 == 69 && e1 == 44 && e2 == 44 && c0 == 69
					&& c1 == 44 && c2 == 44 && middle >= 0) {
				// found solution
				return true;
			} else if (turnMetric) {
				return false;
			}
		}

		// prune if would take too many moves to solve a colouring
		if (pr1.getEntry(shp, e0, c0) > ln)
			return false;
		if (pr2.getEntry(shp, e1, c1) > ln)
			return false;
		if (pr2.getEntry(shp2, e2, c2) > ln)
			return false;

		// try all top layer moves
		if (lm >= 2) { // last move was not top/bottom layer
			// do top layer move
			i = doMove(0);
			do {
				// if valid layer turn
				if (turnMetric || i < 6 || ln < 2) {
					// store move
					moveList[moveLen++] = i;
					lastTurns[4] = i;
					// search deeper
					boolean b = search(turnMetric ? ln - 1 : ln, 0);
					// quit if found solution
					if (b)
						return true;
					// forget move
					moveLen--;
				}
				// do top layer move
				i += doMove(0);
				// repeat until layer gone full circle
			} while (i < 12);
			// forget move
			lastTurns[4] = 0;
		}
		// try all bot layer moves
		if (lm != 1) { // last move was not bottom layer
			// do bottom layer move
			i = doMove(1);
			do {
				// store move
				moveList[moveLen++] = i + 12;
				lastTurns[5] = i;
				// search deeper
				boolean b = search(turnMetric ? ln - 1 : ln, 1);
				// quit if found solution
				if (b)
					return true;
				// forget move
				moveLen--;
				// do bottom layer move
				i += doMove(1);
				// repeat until layer gone full circle
			} while (i < 12);
			// forget move
			lastTurns[5] = 0;
		}
		// try twist move
		if (lm != 2) { // last move was not twist move
			// update last turns array
			int lt0 = lastTurns[0], lt1 = lastTurns[1];
			lastTurns[0] = lastTurns[2];
			lastTurns[1] = lastTurns[3];
			lastTurns[2] = lastTurns[4];
			lastTurns[3] = lastTurns[5];
			lastTurns[4] = 0;
			lastTurns[5] = 0;
			// do twist move
			doMove(2);
			// store move
			moveList[moveLen++] = 0;
			// search deeper
			boolean b = search(ln - 1, 2);
			// quit if found solution
			if (b)
				return true;
			// undo do twist move
			moveLen--;
			doMove(2);
			// undo last turns array update
			lastTurns[5] = lastTurns[3];
			lastTurns[4] = lastTurns[2];
			lastTurns[3] = lastTurns[1];
			lastTurns[2] = lastTurns[0];
			lastTurns[1] = lt1;
			lastTurns[0] = lt0;
		}
		return false;
	}

	public String getSolution() {
		StringBuffer sb = new StringBuffer();
		int tw = 0, tu = 0; // number of moves in twist/turn metrics
		int lm = 3; // type of move last printed
		for (int i = 0; i < moveLen; i++) {
			if (moveList[i] == 0) { // twist move
				// if last move was top layer, then print 0 for bottom layer
				if (lm == 0)
					sb.append("0");
				// print twist
				sb.append("/");
				// update metrics
				tu++;
				tw++;
				lm = 2;
			} else if (moveList[i] < 12) { // top layer move
				// print top layer move
				sb.append(moveList[i] + ",");
				// update metrics
				tu++;
				lm = 0;
			} else { // bottom layer move
				// if last move was not top layer, then print 0, for top layer
				if (lm >= 2)
					sb.append("0,");
				// print bottom layer move
				sb.append(24 - moveList[i]);
				// update metrics
				tu++;
				lm = 1;
			}
		}
		// if last move was top layer, then print 0 for bottom layer
		if (lm == 0)
			sb.append("0");
		return sb.toString();
	}
}
