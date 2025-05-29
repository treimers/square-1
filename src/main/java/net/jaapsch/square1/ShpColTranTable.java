package net.jaapsch.square1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
This class represents a transition table for an edge/corner colouring.
Given the indexnumber of a shape, an index number of a colouring, and a move number, it
  returns the new index number of the colouring after that move is applied.
*/
public class ShpColTranTable {
	// the transition table data
	private byte table[];

	ShpColTranTable(ShapeTranTable stt0, boolean edges) throws Square1Exception {
		try {
			ShapeColPos p = new ShapeColPos(stt0);
			table = new byte[Sq1Shape.list.length * 210];
			// see if can be found on file
			String fname;
			if (edges)
				fname = "sq1scte.dat";
			else
				fname = "sq1sctc.dat";
			InputStream resource = getClass().getResourceAsStream(fname);
			File file = new File(fname);
			if (resource == null) {
				if (file.exists())
					resource = new FileInputStream(file);
			}
			if (resource == null) {
				// no file. calculate table.
				// Calculate transition table
				// for each move, shape, colouring
				for (int m = 0; m < 3; m++) {
					for (int i = 0; i < Sq1Shape.list.length; i++) {
						for (int j = 0; j < 70; j++) {
							// get that shape/colouring
							p.set(i, j, edges);
							// apply move
							p.domove(m);
							// store result
							setEntry(i, j, m, p.getColIdx());
							// if (p.getColIdx() == 255) System.exit(0);
						}
					}
				}
				// save to file
				BufferedOutputStream oos = new BufferedOutputStream(new FileOutputStream(file));
				oos.write(table, 0, table.length);
				oos.close();
			} else {
				// read from file
				BufferedInputStream ois = new BufferedInputStream(resource);
				ois.read(table, 0, table.length);
				ois.close();
			}
		} catch (IOException e) {
			throw new Square1Exception(e.getMessage(), e);
		}
	}

	byte getEntry(int shape, int col, int move) {
		// if (shape >= Constants.NUMSHAPES || move >= 3 || col >= 70 )
		// System.out.println("" + shape + " " + move + " " + col);
		return table[shape * 210 + col * 3 + move];
	}

	private void setEntry(int shape, int col, int move, int val) {
		table[shape * 210 + col * 3 + move] = (byte) val;
	}
}
