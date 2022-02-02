package net.treimers.square1.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.treimers.square1.exception.Square1Exception;

public class MoveTest {
	private static class Case {
		private String moveString;
		private boolean exceptional;
		private String result;
	
		public Case(String moveString, boolean exceptional, String result) {
			super();
			this.moveString = moveString;
			this.exceptional = exceptional;
			this.result = result;
		}
	
		public String getMoveString() {
			return moveString;
		}
	
		public boolean isExceptional() {
			return exceptional;
		}
	
		public String getResult() {
			return result;
		}
	}

	@Test
	public void constructorTest() {
		System.out.println("Move tests");
		Case[] testCases = {
			new Case("1,2/", false, "1,2/"),
			new Case("1,2", false, "1,2-"),
			new Case("1,2-", false, "1,2-"),
			new Case("/", false, "/"),
			new Case("1,2/-", true, ""),
			new Case("1,2/3,4/", true, ""),
		};
		
		for (int i = 0; i < testCases.length; i++) {
			Case result = testCases[i];
			String moveString = result.getMoveString();
			try {
				Move move = new Move(moveString);
				System.out.println(String.format("%s: %s", moveString, result.getResult()));
				assertEquals(move.toString(), result.getResult());
				assertFalse(result.isExceptional());
			} catch (Square1Exception e) {
				System.out.println(String.format("%s: %s", moveString, e.getMessage()));
				assertTrue(result.isExceptional());
			}
		}
	}
}
