package net.treimers.square1.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.treimers.square1.exception.Square1Exception;

public class MoveSequenceTest {
	private static class Case {
		private String moveString;
		private List<Move> result;

		public Case(String moveString, List<Move> list) {
			this.moveString = moveString;
			this.result = list;
		}

		public String getMoveString() {
			return moveString;
		}

		public List<Move> getResult() {
			return result;
		}
	}

	@Test
	public void constructorTest() throws Square1Exception {
		System.out.println("Move sequence tests");
		Case[] testCases = {
			new Case("/", Arrays.asList(
				new Move("/"))),
			new Case("7,8/3,4/5,6/", Arrays.asList(
				new Move("7,8/"),
				new Move("3,4/"),
				new Move("5,6/")))
			,
			new Case("/7,8/3,4/5,6/", Arrays.asList(
				new Move("/"),
				new Move("7,8/"),
				new Move("3,4/"),
				new Move("5,6/"))
			),
			new Case("/7,8/3,4/5,6", Arrays.asList(
				new Move("/"),
				new Move("7,8/"),
				new Move("3,4/"),
				new Move("5,6"))
			),
		};
		for (int i = 0; i < testCases.length; i++) {
			Case testCase = testCases[i];
			String moveString = testCase.getMoveString();
			MoveSequence sequence = new MoveSequence(moveString);
			List<Move> moves = sequence.getMoves();
			System.out.println(String.format("%s: %s", moveString, moves.toString()));
			assertEquals(testCase.getResult(), moves);
		}
	}
}
