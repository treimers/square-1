package net.jaapsch.square1;

import net.treimers.square1.exception.Square1Exception;

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
// A18H6FC3B27G5ED4/
// /3,9/0,9/9,9/0,3/ [5|11]
// A2B3C1D45E6F7G8H/
// /3,0/1,0/0,9/5,0/6,0/3,0/1,0/0,3/11,0 [9|18]
// A1D4B2C38H6F7B5E-
// ??? (invalid: Doppeltes B
// A1D4B2C36F7G5E8H-
// 3,9/3,0/3,3/3,0/ [4|10]
// A1B2C3D45E6F7GH8-
