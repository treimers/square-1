package net.treimers.square1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import net.jaapsch.square1.Solver;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.MoveSequence;
import net.treimers.square1.model.Position;
import net.treimers.square1.solver.Scrambler;

/**
 * Tests scramble and solve without the JavaFX UI: scramble a solved cube
 * several times, solve the resulting position, apply the solution, and assert
 * the cube is solved again.
 */
public class ScrambleSolveTest {
	private static final int REPEAT_COUNT = 100;
	/** How often scramble is applied before each solve. */
	private static final int SCRAMBLES_BEFORE_SOLVE = 5;
	private static final long SCRAMBLE_SEED = 42L;

	private static Solver solver;

	@BeforeClass
	public static void setUpClass() throws Square1Exception {
		solver = new Solver();
	}

	@Test
	public void scrambleProducesCompletePositions() {
		Scrambler scrambler = new Scrambler(new Random(SCRAMBLE_SEED));
		Position solved = new Position();
		List<Position> scramble = scrambler.generateScramble(solved);

		assertFalse("scramble must contain at least one move", scramble.isEmpty());
		for (Position position : scramble) {
			assertEquals("each scramble step must be a complete position", 17, position.toString().length());
		}
	}

	@Test
	public void solveAlreadySolvedPosition() throws Square1Exception {
		Position solved = new Position();
		Position result = applySolution(solved);

		assertEquals(solved, result);
	}

	@Test
	public void scrambleAndSolveRepeatedly() throws Square1Exception {
		Scrambler scrambler = new Scrambler(new Random(SCRAMBLE_SEED));
		Position solved = new Position();

		for (int i = 0; i < REPEAT_COUNT; i++) {
			Position scrambled = applyScrambles(scrambler, solved, SCRAMBLES_BEFORE_SOLVE);
			assertEquals("incomplete scramble position on run " + i, 17, scrambled.toString().length());

			Position result = applySolution(scrambled);
			assertEquals(
					"run " + i + " did not restore solved position; scramble=" + scrambled,
					solved,
					result);
		}
	}

	@Test
	public void solveWhenCornerBlocksSlice() throws Square1Exception {
		// Bottom corner G lies across the slice, so the shape tables have no entry
		// for this orientation. The solver must turn to a sliceable orientation first.
		Position position = new Position("A5E681DFC327G4BH-");
		Position result = applySolution(position);
		assertEquals(new Position(), result);
		// Next example
		position = new Position("B25EDC36847GFAH1/");
		result = applySolution(position);
		assertEquals(new Position(), result);
	}

	@Test
	public void scrambleThenSolveKnownPath() throws Square1Exception {
		// Deterministic multi-scramble via seed 0, then solve back to solved
		Scrambler scrambler = new Scrambler(new Random(0L));
		Position solved = new Position();
		Position scrambled = applyScrambles(scrambler, solved, SCRAMBLES_BEFORE_SOLVE);

		String solution = solver.solve(scrambled.toString());
		assertNotNull(solution);

		Position result = applySolution(scrambled, solution);
		assertEquals(solved, result);
	}

	/**
	 * Applies scramble {@code times} times in succession, each time starting
	 * from the previous scramble result.
	 */
	private static Position applyScrambles(Scrambler scrambler, Position start, int times) {
		Position current = start;
		for (int s = 0; s < times; s++) {
			List<Position> scramble = scrambler.generateScramble(current);
			assertFalse("scramble empty on pass " + s, scramble.isEmpty());
			current = scramble.get(scramble.size() - 1);
		}
		return current;
	}

	private static Position applySolution(Position position) throws Square1Exception {
		return applySolution(position, solver.solve(position.toString()));
	}

	private static Position applySolution(Position position, String solution) throws Square1Exception {
		MoveSequence sequence = new MoveSequence(solution);
		List<Position> path = position.move(sequence);
		return path.get(path.size() - 1);
	}
}
