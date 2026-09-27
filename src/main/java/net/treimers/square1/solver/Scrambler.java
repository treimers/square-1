package net.treimers.square1.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.Position;

public class Scrambler {
	private static final int MAX_SCRAMBLE_LENGTH = 6;
	private final Random random;

	public Scrambler() {
		this(new Random());
	}

	public Scrambler(Random random) {
		this.random = Objects.requireNonNull(random);
	}

	/**
	 * Builds a scramble as the positions after each random move.
	 *
	 * @param position the position to scramble.
	 * @return the position after every applied move.
	 */
	public List<Position> generateScramble(Position position) {
		List<Position> positions = new ArrayList<>();
		Position next = position;
		for (Move move : generateMoves(position)) {
			try {
				next = next.move(move);
				positions.add(next);
			} catch (Square1Exception e) {
				// The move was legal when it was chosen.
			}
		}
		return positions;
	}

	/**
	 * Builds the random moves of a scramble. Illegal moves are skipped.
	 *
	 * @param position the position to scramble.
	 * @return the moves that were applied, in order.
	 */
	public List<Move> generateMoves(Position position) {
		List<Move> moves = new ArrayList<>();
		int tries = 0;
		Position next = position;
		while (moves.size() < MAX_SCRAMBLE_LENGTH && tries < MAX_SCRAMBLE_LENGTH * 10) {
			Move move = generateRandomMove();
			try {
				next = next.move(move);
				moves.add(move);
			} catch (Square1Exception e) {
				// Illegal move, einfach überspringen
			}
			tries++;
		}
		return moves;
	}

	private Move generateRandomMove() {
		int top = random.nextInt(12) - 6; // Drehung von -6 bis +5
		int bottom = random.nextInt(12) - 6; // Drehung von -6 bis +5
		return new Move(top, bottom, true);
	}
}
