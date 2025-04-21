package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import net.treimers.square1.exception.Square1Exception;

/**
 * Instances of this class are used to model a sequence of moves.
 */
public class MoveSequence {
	/** The moves in this sequence. */
	private List<Move> moves;

	/**
	 * Creates a new instance without any moves.
	 */
	public MoveSequence() {
		moves = new ArrayList<>();
	}

	/**
	 * Creates a new instance from a string representation.
	 * 
	 * @param moveString the string representation.
	 */
	public MoveSequence(String moveString) {
		moves = new ArrayList<>();
		Matcher m = Move.PATTERN.matcher(moveString);
		while (m.find()) {
			int top;
			int bottom;
			boolean twist;
			if (m.group(4) != null) {
				top = 0;
				bottom = 0;
				twist = m.group(4).equals("/");
			} else {
				top = m.group(1) == null ? 0 : Integer.parseInt(m.group(1));
				bottom = m.group(2) == null ? 0 : Integer.parseInt(m.group(2));
				twist = m.group(3) != null && m.group(3).equals("/");
			}
			Move move = new Move(top, bottom, twist);
			moves.add(move);
		}
	}

	public MoveSequence(List<Move> moves) {
		this.moves = moves;
	}

	/**
	 * Gets the move list in this sequence.
	 * 
	 * @return the move list.
	 */
	public List<Move> getMoves() {
		return moves;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < moves.size(); i++) {
			sb.append(moves.get(i));
		}
		return sb.toString();
	}

	public static MoveSequence fromSolverString(String solverMoves) throws Square1Exception {
		// Entferne Leerzeichen und Klammern: "(-3,0)/, (-1,-2)/" → "-3,0/-1,-2/"
		StringBuilder sb = new StringBuilder();
		for (String part : solverMoves.split(",")) {
			String clean = part.replaceAll("[()\\s]", "");
			sb.append(clean);
		}
		return new MoveSequence(sb.toString());
	}

	public static String toSolverString(MoveSequence sequence) {
		StringBuilder sb = new StringBuilder();
		for (Move move : sequence.getMoves()) {
			sb.append("(").append(move.getTopRotation())
					.append(",").append(move.getBottomRotation()).append(")");
			sb.append(move.isTwisted() ? "/" : "-");
			sb.append(", ");
		}
		if (sb.length() >= 2)
			sb.setLength(sb.length() - 2); // letztes ", " entfernen
		return sb.toString();
	}
}
