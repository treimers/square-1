package de.softquadrat.square1.solver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.softquadrat.square1.exception.Square1Exception;

/*
 This class represents a pruning table used in the solution search.
 Given index numbers for a shape, an edge colouring, and a corner colouring,
 it can return the number of moves to solve it.

 For example, suppose the 4 edges and 4 corners of the top layer are coloured,
 and the bottom layer is blank. This pruning table then gives the number of
 moves (plus 1) that it takes to solve that puzzle from any position.

 */

public class PrunTable {
	// pruning table data
	private byte table[];
	private boolean debug;

	// Initialise with p0=solved position
	public PrunTable(FullPosition p0, int cl, ShapeTranTable stt,
			ShpColTranTable scte, ShpColTranTable sctc, boolean turnMetric)
			throws IOException, Square1Exception {
		table = new byte[Sq1Shape.list.length * 4900];
		// Calculate pruning table
		String fname;
		if (turnMetric)
			if (cl == 0)
				fname = "sq1p1u.dat";
			else
				fname = "sq1p2u.dat";
		else if (cl == 0)
			fname = "sq1p1w.dat";
		else
			fname = "sq1p2w.dat";
		File file = new File(fname);
		if (!file.exists()) {
			// no file. calculate table.
			// clear table
			for (int i0 = 0; i0 < table.length; i0++) {
				table[i0] = 0;
			}
			// set start position
			int s0 = Sq1Shape.getShape(p0.getShape(), p0.getParityOdd());
			int e0 = p0.getEdgeColouring(cl);
			int c0 = p0.getCornerColouring(cl);
			e0 = ChoiceTable.getChoice2Idx(e0);
			c0 = ChoiceTable.getChoice2Idx(c0);
			if (turnMetric) {
				setEntry(s0, e0, c0, 0);
			} else {
				setAll(s0, e0, c0, 0, stt, scte, sctc);
			}

			int ln = 0; // current distance
			int n; // number of new positions found
			do {
				n = 0;
				if (turnMetric) {
					// for any position at distance ln
					for (int i0 = 0; i0 < Sq1Shape.list.length; i0++) {
						for (int i1 = 0; i1 < 70; i1++) {
							for (int i2 = 0; i2 < 70; i2++) {
								if (getEntry(i0, i1, i2) == ln) {
									// try each move type
									for (int m = 0; m < 3; m++) {
										int j0 = i0;
										int j1 = i1;
										int j2 = i2;
										// repeatedly do move
										do {
											j2 = sctc.getEntry(j0, j2, m);
											j1 = scte.getEntry(j0, j1, m);
											j0 = stt.getEntry(j0, m);
											// mark any unvisited positions as
											// distance ln+1
											if (getEntry(j0, j1, j2) == -1) {
												setEntry(j0, j1, j2, ln + 1);
												n++;
											}
											// repeat until position back to
											// where we started
										} while (j0 != i0 || j1 != i1
												|| j2 != i2);
									}
								}
							}
						}
					}
				} else {
					// for any position at distance ln
					for (int i0 = 0; i0 < Sq1Shape.list.length; i0++) {
						for (int i1 = 0; i1 < 70; i1++) {
							for (int i2 = 0; i2 < 70; i2++) {
								if (getEntry(i0, i1, i2) == ln) {
									// do twist
									int j0 = stt.getEntry(i0, 2);
									int j1 = scte.getEntry(i0, i1, 2);
									int j2 = sctc.getEntry(i0, i2, 2);
									// mark unvisited position as distance ln+1
									if (getEntry(j0, j1, j2) == -1) {
										n += setAll(j0, j1, j2, ln + 1, stt,
												scte, sctc);
									}
								}
							}
						}
					}
				}
				// next distance
				ln++;
				if (debug)
					System.out.println(" l=" + ln + "  n=" + n);
				// loop until no new positions visited
			} while (n != 0);
			// save to file
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(table, 0, table.length);
			bos.close();
		} else {
			// read from file
			BufferedInputStream zis = new BufferedInputStream(
					new FileInputStream(file));
			zis.read(table, 0, table.length);
			zis.close();
		}
	}

	// Mark a position to depth ln, as well as all layer rotations of it.
	// returns number of new positions found.
	private int setAll(int i0, int i1, int i2, int ln, ShapeTranTable stt,
			ShpColTranTable scte, ShpColTranTable sctc) {
		int n = 0;
		int j0 = i0;
		int j1 = i1;
		int j2 = i2;
		do {
			int k0 = j0;
			int k1 = j1;
			int k2 = j2;
			do {
				// mark unvisited position as distance ln
				if (getEntry(k0, k1, k2) == -1) {
					setEntry(k0, k1, k2, ln);
					n++;
				}
				// turn top layer
				k2 = sctc.getEntry(k0, k2, 0);
				k1 = scte.getEntry(k0, k1, 0);
				k0 = stt.getEntry(k0, 0);
				// loop until have done a full turn
			} while (j0 != k0 || j1 != k1 || j2 != k2);
			// turn bottom layer layer
			j2 = sctc.getEntry(j0, j2, 1);
			j1 = scte.getEntry(j0, j1, 1);
			j0 = stt.getEntry(j0, 1);
			// loop until have done a full turn
		} while (j0 != i0 || j1 != i1 || j2 != i2);
		return n;
	}

	int getEntry(int shape, int edgeCol, int cornerCol) {
		return table[shape * 4900 + edgeCol * 70 + cornerCol] - 1;
	}

	private void setEntry(int shape, int edgeCol, int cornerCol, int val) {
		table[shape * 4900 + edgeCol * 70 + cornerCol] = (byte) (val + 1);
	}
}
