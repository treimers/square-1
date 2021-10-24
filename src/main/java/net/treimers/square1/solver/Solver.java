package net.treimers.square1.solver;

import java.io.IOException;

import net.treimers.square1.exception.Square1Exception;
/**
 * <p>Notation</p>
 * 
 * <p>First we need a notation for the moves used on the puzzle. Hold the
 * puzzle so that the yellow middle layer piece is on the left hand side
 * with its 'Square-1' inscription the right way up. Denote a 180 degree
 * twist of the right hand side of the puzzle by a / sign (a slash). This
 * kind of move will be called simply a 'twist'. Turns of the top and bottom
 * layers are denoted by a pair of numbers (n,m). These numbers are the
 * multiple of 30 degrees clockwise that the top/bottom layers are to turn
 * respectively. Thus (3,0) means turn only the top layer clockwise 90
 * degrees, and (0,-1) means turn only the bottom layer 30 degrees
 * anti-clockwise (i.e. one edge along).</p>
 * 
 * <p>There are generally two ways to count the length of a sequence:</p>
 * <p>Twist Metric: The length is simply the number of twists.
 * For example, / is one move, and /(6,6)/(-1,1) is 2 moves.</p>
 * </p>Turn Metric: The length is the number of (non-zero) turns and twists.
 * For example, (3,0) is one move, as is /, and /(6,6)/(-1,1)
 * is 6 moves.</p>
 * <p>This version of the program can use either metric.</p>
 * 
 * <p>Note that there is an equivalence. The move sequence</p>
 *   (a,b)/(c,d)/(e,f)
 * <p>has the same effect as the sequence</p>
 *   (6+a,6+b)/(d,c)/(6+e,6+f)
 * 
 * <p>The program can only make use of this if the twist metric is used. In this
 * case the bottom layer is never turned 6 or more, except possibly just before
 * of just after the final twist.</p> 
 * 
 * <p>We also need a notation for each position of the puzzle. All the corner
 * pieces are denoted by letters, the edges by digits. On the solved puzzle,
 * the top layer pieces are A1B2C3D4 reading clockwise from the front left
 * corner, and the bottom pieces are 5E6F7G8H clockwise from the front edge.
 * The solved position is then denoted by A1B2C3D45E6F7G8H. Any mixed
 * position can be similarly coded, simply listing the pieces in the top
 * layer clockwise from cut in the front of the middle layer, followed by the
 * bottom layer pieces in a similar manner. If shape of the middle layer
 * should be considered, it can denoted by appending a - or a / to indicate
 * whether it is square or kite-shaped respectively.</p>
 * 
 * <p>The standard colour scheme of the puzzle has the following colours:</p>
 * <ul>
 * <li>Left: Yellow</li>
 * <li>Front:  Orange</li>
 * <li>Right:  Blue</li>
 * <li>Back:   Red</li>
 * <li>Top:    White</li>
 * <li>Bottom: Green</li>
 * </ul>
 * 
 * <p>For easy reference, here are the letters/numbers used in the position notation above:</p>
 * <ul>
 * <li>Letter   Colours</li>
 * <li>A        WYO</li>
 * <li>B        WRY</li>
 * <li>C        WBR</li>
 * <li>D        WOB</li>
 * <li>E        GOB</li>
 * <li>F        GBR</li>
 * <li>G        GRY</li>
 * <li>H        GYO</li>
 * </ul>
 * <ul>
 * <li>Number   Colours</li>
 * <li>1        WY</li>
 * <li>2        WR</li>
 * <li>3        WB</li>
 * <li>4        WO</li>
 * <li>5        GO</li>
 * <li>6        GB</li>
 * <li>7        GR</li>
 * <li>8        GY</li>
 * </ul>
 *   
 */
public class Solver {
	private ShapeTranTable shapeTranTable;
	private ShpColTranTable scte;
	private ShpColTranTable sctc;
	private PrunTable pr1;
	private PrunTable pr2;
	private boolean turnMetric;

	public Solver() throws Square1Exception, IOException {
		shapeTranTable = new ShapeTranTable();
		scte = new ShpColTranTable(shapeTranTable, true);
		sctc = new ShpColTranTable(shapeTranTable, false);
		FullPosition q = new FullPosition();
		turnMetric = true;
		pr1 = new PrunTable(q, 0, shapeTranTable, scte, sctc, turnMetric);
		pr2 = new PrunTable(q, 1, shapeTranTable, scte, sctc, turnMetric);
	}

	public String solve(String position) throws Square1Exception {
		FullPosition fullPosition = new FullPosition();
		fullPosition.parseInput(position);
		SimpPosition s = new SimpPosition(shapeTranTable, scte, sctc, pr1, pr2);
		s.set(fullPosition, turnMetric);
		s.solve();
		return s.getSolution();
	}
	
	public static void main(String[] args) throws Square1Exception, IOException {
		if (args.length != 1) {
			System.out.println("Usage: Solver <Position>");
			System.exit(0);
		}
		Solver s = new Solver();
		System.out.println(s.solve(args[0]));
	}
}
