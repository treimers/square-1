package net.jaapsch.square1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.treimers.square1.exception.Square1Exception;

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
This class represents a transition table for the shapes.
Given the indexnumber of a shape, and a move number, it returns the index number of
 the resulting shape after applying that move.
*/
public class ShapeTranTable {
	// the transition table data
	private int tranTable[];

	ShapeTranTable() throws Square1Exception {
		try {
		// At last we can calculate full transition table
		tranTable = new int[Sq1Shape.list.length * 4];
		// see if can be found on file
		String fname = "sq1stt.dat";
		InputStream resource = getClass().getResourceAsStream(fname);
		File file = new File(fname);
		if (resource == null) {
			if (file.exists())
				resource = new FileInputStream(file);
		}
		if (resource == null) {
			// no file. calculate table.
			// for each move, shape
			for (int i = 0; i < Sq1Shape.list.length; i++) {
				for (int m = 0; m < 4; m++) {
					// find index of resulting shape after that move
					setEntry(i, m, Sq1Shape.list[i].domove(m));
				}
			}
			// save to file
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			for (int i = 0; i < tranTable.length; i++) {
				oos.writeInt(tranTable[i]);
			}
			oos.close();
		} else {
			// read from file
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(resource));
			for (int i = 0; i < tranTable.length; i++) {
				tranTable[i] = ois.readInt();
			}
			ois.close();
		}
	} catch (IOException e) {
		throw new Square1Exception(e.getMessage(), e);
	}
	}

	int getEntry(int shape, int move) {
		return tranTable[shape * 4 + move];
	}

	private void setEntry(int shape, int move, int val) {
		tranTable[shape * 4 + move] = val;
	}
}
