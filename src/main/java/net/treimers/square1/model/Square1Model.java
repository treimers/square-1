package net.treimers.square1.model;

import java.util.ArrayList;
import java.util.List;

import net.treimers.square1.exception.Square1Exception;

public class Square1Model {
	private static final String ALL = "ABCDEFGH12345678";
	private StringBuilder all = new StringBuilder(ALL);
	private boolean mid;

	public void parsePosition(String postion) throws Square1Exception {
		if (postion.length() != 16 && postion.length() != 17)
			throw new Square1Exception("Position string has wrong length \"" + postion.length() + ", expected 16 or 17");
		int angle = 0;
		boolean top = true;
		List<Character> topPieces = new ArrayList<Character>();
		List<Character> bottomPieces = new ArrayList<Character>();
		for (int i = 0; i < 16; i++) {
			char c = postion.charAt(i);
			check(c);
			int length = getLenght(c);
			angle += length;
			if (angle == 6)
				top = false;
			if (top)
				topPieces.add(c);
			else
				bottomPieces.add(c);
		}
		if (postion.length() == 17) {
			char c = postion.charAt(16);
			if (c != '-' && c != '/')
				throw new Square1Exception("Illegal mid turn character \"" + c + "\", expected '-' or '/'");
			mid = c == '/';
		}
	}

	private void check(char c) throws Square1Exception {
		for (int i = 0; i < all.length(); i++) {
			if (all.charAt(i) == c) {
				all.deleteCharAt(i);
				return;
			}
		}
		throw new Square1Exception("Illegal character \"" + c + "\" found in position string");
	}

	private int getLenght(char c) {
		return (Character.isDigit(c)) ? 1 : 2;
	}
}
