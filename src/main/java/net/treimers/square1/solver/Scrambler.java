package net.treimers.square1.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.Position;

public class Scrambler {
	private static final int MAX_SCRAMBLE_LENGTH = 6;
	private static final Random random = new Random();

	public List<Position> generateScramble(Position position) {
		List<Position> positions = new ArrayList<>();
		int tries = 0;
		Position next = position;
		while (positions.size() < MAX_SCRAMBLE_LENGTH && tries < MAX_SCRAMBLE_LENGTH * 10) {
			Move move = generateRandomMove();
			try {
				next = next.move(move);
				positions.add(next);
			} catch (Square1Exception e) {
				// Illegal move, einfach überspringen
			}
			tries++;
		}
		return positions;
	}

	private Move generateRandomMove() {
		int top = random.nextInt(12) - 6; // Drehung von -6 bis +5
		int bottom = random.nextInt(12) - 6; // Drehung von -6 bis +5
		return new Move(top, bottom, true);
	}
}
