package net.jaapsch.square1;

import net.treimers.square1.exception.Square1Exception;

/*
 * ----------------------------------------------------------------------------
 * This program was written by Jaap Scherphuis, copyright May 2001.
 * It may not be sold. It may be freely distributed provided that this
 * documentation is provided in some form without changes to the text.
 * 
 * The program can be downloaded from Jaap's Puzzle Page:
 * http://www.org2.com/jaap/puzzles
*/

/**
 * Instances of this class are used to run the solving algorithm.
 */
public class Solver {
	private boolean turnMetric;
	private ShapeTranTable st;
	private ShpColTranTable scte;
	private ShpColTranTable sctc;
	private PrunTable pr1;
	private PrunTable pr2;

	public Solver() throws Square1Exception {
		// Use turn-based metric instead of twist-based
		turnMetric = false;
		// calculate transition tables
		st = new ShapeTranTable();
		scte = new ShpColTranTable(st, true);
		sctc = new ShpColTranTable(st, false);
		// Get starting position
		FullPosition q = new FullPosition();
		// calculate pruning tables for two colourings
		pr1 = new PrunTable(q, 0, st, scte, sctc, turnMetric);
		pr2 = new PrunTable(q, 1, st, scte, sctc, turnMetric);
	}

	public String solve(String position) {
		// Get position to solve from the input
		FullPosition p = new FullPosition();
		String err = p.parseInput(position);
		if (err != null) {
			System.err.println(err);
			System.exit(1);
		}
		// now we have a position p to solve
		// show position
		System.out.print("Position to solve: ");
		p.print();
		// conver full position to encoded position
		SimpPosition s = new SimpPosition(st, scte, sctc, pr1, pr2);
		s.set(p, turnMetric);
		// solve position
		long start = System.currentTimeMillis();
		String solution = s.solve();
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + " ms");
		System.out.println("Solution=" + solution);
		return solution;
	}
}
