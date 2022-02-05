package net.treimers.square1.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import net.treimers.square1.exception.Square1Exception;

public class MovePositionTest {
	private static class TestCase {
		private String beforePosition;
		private String moveString;
		private boolean exception;
		private String afterPosition;

		public TestCase(String beforePosition, String moveString, boolean exception, String afterPosition) {
			this.beforePosition = beforePosition;
			this.moveString = moveString;
			this.exception = exception;
			this.afterPosition = afterPosition;
		}

		public String getBeforePosition() {
			return beforePosition;
		}

		public String getMoveString() {
			return moveString;
		}

		public String getAfterPosition() {
			return afterPosition;
		}

		public boolean isException() {
			return exception;
		}
	}

	@Test
	public void topMoveTests() throws Square1Exception {
		System.out.println("topMoveTests");
		TestCase[] testCases = {
			new TestCase("A1B2C3D45E6F7G8H", "0,0", false, "A1B2C3D45E6F7G8H"),
			new TestCase("A1B2C3D45E6F7G8H", "1,0", false, "4A1B2C3D5E6F7G8H"),
			new TestCase("A1B2C3D45E6F7G8H", "12,0", false, "A1B2C3D45E6F7G8H"),
			new TestCase("A1B2C3D45E6F7G8H", "3,0", false, "D4A1B2C35E6F7G8H"),
			new TestCase("A1B2C3D45E6F7G8H", "-3,0", false, "B2C3D4A15E6F7G8H"),
			new TestCase("A1B2C3D45E6F7G8H-", "6,0", false, "C3D4A1B25E6F7G8H-"),
			new TestCase("A1B2C3D45E6F7G8H/", "6,0", false, "C3D4A1B25E6F7G8H/"),
		};
		runTest(testCases);
	}

	@Test
	public void bottomMoveTests() throws Square1Exception {
		System.out.println("bottomMoveTests");
		TestCase[] testCases = {
			new TestCase("A1B2C3D45E6F7G8H", "0,3", false, "A1B2C3D48H5E6F7G"),
			new TestCase("A1B2C3D45E6F7G8H", "0,12", false, "A1B2C3D45E6F7G8H"),
			new TestCase("A1B2C3D45E6F7G8H", "0,3", false, "A1B2C3D48H5E6F7G"),
			new TestCase("A1B2C3D45E6F7G8H", "0,-3", false, "A1B2C3D46F7G8H5E"),
			new TestCase("A1B2C3D45E6F7G8H-", "0,6", false, "A1B2C3D47G8H5E6F-"),
		};
		runTest(testCases);
	}

	@Test
	public void twistTests() throws Square1Exception {
		System.out.println("twistTests");
		TestCase[] testCases = {
			new TestCase("A1B2C3D45E6F7G8H", "0,0/", false, "A1B25E6FC3D47G8H/"),
			new TestCase("A1B2C3D45E6F7G8H", "0,3/", false, "A1B28H5EC3D46F7G/"),
			new TestCase("A1B2C3D45E6F7G8H", "6,6/", false, "C3D47G8HA1B25E6F/"),
			new TestCase("A1B2C3D45E6F7G8H", "6,6/", false, "C3D47G8HA1B25E6F/"),
			new TestCase("A1B2C3D45E6F7G8H", "0,1/", true, ""),
		};
		runTest(testCases);
	}

	@Test
	public void sequenceTests() {
		System.out.println("sequenceTests");
		TestCase[] testCases = {
			new TestCase("A1B2C3D45E6F7G8H", "6,6/6,6/-1,1", false, "G8H5E6F72C3D4A1B-"),
			new TestCase("A1B2C3D45E6F7G8H-", "6,6/6,6/-1,1", false, "G8H5E6F72C3D4A1B-"),
			new TestCase("A1B2C3D45E6F7G8H", "1,0/6,6/-1,0", false, "E6F7G8H54A1B2C3D-"),
			new TestCase("A1B2C3D45E6F7G8H", "-1,0/", true, ""),
			new TestCase("A1B2C3D45E6F7G8H", "/7,0/", true, ""),
			new TestCase("A1B2C3D45E6F7G8H", "0,1/", true, ""),
			new TestCase("A1B2C3D45E6F7G8H", "/0,2/", true, ""),
			new TestCase("8HE6A1B52C3DF47G/", "6,0/0,9/1,0/11,0/", false, "A1B25E6FC3D47G8H/"),
		};
		for (int i = 0; i < testCases.length; i++) {
			TestCase testCase = testCases[i];
			String beforePosition = testCase.getBeforePosition();
			Position position = new Position(beforePosition);
			String moveString = testCase.getMoveString();
			MoveSequence sequence = new MoveSequence(moveString);
			try {
				List<Position> positions = position.move(sequence);
				String result = positions.get(positions.size() - 1).toString();
				assertFalse(String.format("Expected exception missing position: %-18s move: %-6s", beforePosition,
						moveString), testCase.isException());
				assertEquals(testCase.getAfterPosition(), result);
				System.out.println(String.format("Position: %-18s, Move: %-6s -> Result: %-18s", beforePosition,
						moveString, positions.toString()));
			} catch (Square1Exception e) {
				assertTrue(String.format("Unexpected exception thrown position: %-18s move: %-6s", beforePosition,
						moveString), testCase.isException());
				System.out.println(
						String.format("Position: %-18s, Move: %-6s -> Result: Exception", beforePosition, moveString));
			}
		}
	}

	private void runTest(TestCase[] testCases) {
		for (int i = 0; i < testCases.length; i++) {
			TestCase testCase = testCases[i];
			String beforePosition = testCase.getBeforePosition();
			Position position = new Position(beforePosition);
			String moveString = testCase.getMoveString();
			try {
				Move move = new Move(moveString);
				position = position.move(move);
				String result = position.toString();
				assertFalse(String.format("Expected exception missing position: %-18s move: %-6s", beforePosition,
						moveString), testCase.isException());
				assertEquals(String.format("Error with Position %-18s and Move %-6s", beforePosition, moveString),
						testCase.getAfterPosition(), result);
				System.out.println(String.format("Position: %-18s, Move: %-6s -> Result: %-18s", beforePosition,
						moveString, result));
			} catch (Square1Exception e) {
				assertTrue(String.format("Unexpected exception thrown position: %-18s move: %-6s", beforePosition,
						moveString), testCase.isException());
				System.out.println(
						String.format("Position: %-18s, Move: %-6s -> Result: Exception", beforePosition, moveString));
			}
		}
	}
}
