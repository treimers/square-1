package net.treimers.square1.model;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

import net.treimers.square1.exception.Square1Exception;

public class MoveSequenceTest {
	private static class Case {
		private String moveString;
		private Move[] result;

		public Case(String moveString, Move[] result) {
			this.moveString = moveString;
			this.result = result;
		}

		public String getMoveString() {
			return moveString;
		}

		public Move[] getResult() {
			return result;
		}
	}

	@Test
	public void constructorTest() throws Square1Exception {
		System.out.println("Move sequence tests");
		Case[] testCases = {
			new Case("/", new Move[] {
				new Move("/")
			}),
			new Case("7,8/3,4/5,6/", new Move[] {
				new Move("7,8/"),
				new Move("3,4/"),
				new Move("5,6/")
			}),
			new Case("/7,8/3,4/5,6/", new Move[] {
				new Move("/"),
				new Move("7,8/"),
				new Move("3,4/"),
				new Move("5,6/")
			}),
			new Case("/7,8/3,4/5,6", new Move[] {
				new Move("/"),
				new Move("7,8/"),
				new Move("3,4/"),
				new Move("5,6")
			}),
		};
		for (int i = 0; i < testCases.length; i++) {
			Case testCase = testCases[i];
			String moveString = testCase.getMoveString();
			MoveSequence sequence = MoveSequence.fromString(moveString);
			Move[] moves = sequence.getMoves();
			System.out.println(String.format("%s: %s", moveString, Arrays.toString(moves)));
			assertArrayEquals(testCase.getResult(), moves);
		}
	}
}
