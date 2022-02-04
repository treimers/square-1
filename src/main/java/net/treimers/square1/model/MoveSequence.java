package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MoveSequence {
	private List<Move> moves;

	private MoveSequence(List<Move> moves) {
		this.moves = moves;
	}

	public static MoveSequence fromString(String moveString) {
		List<Move> list = new ArrayList<>();
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
			list.add(move);
		}
		return new MoveSequence(list);
	}

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
}
