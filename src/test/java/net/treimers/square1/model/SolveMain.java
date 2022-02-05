package net.treimers.square1.model;

import java.util.List;

import net.treimers.square1.exception.Square1Exception;

@SuppressWarnings("all")
public class SolveMain {
	public static void main(String[] args) throws Square1Exception {
		if (args.length != 2)
		{
			System.out.println("Usage: SolveMain <position> <moves>");
			System.exit(0);
		}
		String positionString = args[0];
		String moveString = args[1];
		Position position = new Position(positionString);
		MoveSequence sequence = new MoveSequence(moveString);
		List<Position> positions = position.move(sequence);
		System.out.println(String.format("%s [%s] -> %s", positionString, sequence, positions.toString()));
	}
}
