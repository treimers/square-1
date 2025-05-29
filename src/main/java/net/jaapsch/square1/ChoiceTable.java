package net.jaapsch.square1;

/*
 * ----------------------------------------------------------------------------
 * This program was written by Jaap Scherphuis, copyright May 2001.
 * It may not be sold. It may be freely distributed provided that this
 * documentation is provided in some form without changes to the text.
 * 
 * The program can be downloaded from Jaap's Puzzle Page:
 * http://www.org2.com/jaap/puzzles
*/

/**
This static class provides functions to translate between an
  an index number 0..69
and
  an 8-bit "choice" number of which exactly 4 bits are set
Note: 8-Choose-4 = 8*7*6*5 / 4*3*2*1 = 70
*/
public class ChoiceTable {
	// Conversion look-up arrays
	private static int choice2Idx[] = new int[256];
	private static int idx2Choice[] = new int[70];
	static {
		// fill choice2Idx with bogus value
		for (int i = 0; i < 255; i++) {
			choice2Idx[i] = -1;
		}
		// for each combination of 4 distinct bits set
		int nc = 0; // index number of current choice
		for (int i = 1; i < 255; i <<= 1) {
			for (int j = i + i; j < 255; j <<= 1) {
				for (int k = j + j; k < 255; k <<= 1) {
					for (int l = k + k; l < 255; l <<= 1) {
						// store conversions
						choice2Idx[i + j + k + l] = nc;
						idx2Choice[nc++] = i + j + k + l;
					}
				}
			}
		}
	}

	// Don't instantiate this class
	private ChoiceTable() {
	}

	// convert choice number to index
	static int getChoice2Idx(int index) {
		return choice2Idx[index];
	}

	// convert index to a choice number
	static int getIdx2Choice(int index) {
		return idx2Choice[index];
	}
}
