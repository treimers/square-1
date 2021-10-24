package de.softquadrat.square1.solver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.softquadrat.square1.exception.Square1Exception;

/*
 This class represents a transition table for the shapes.
 Given the indexnumber of a shape, and a move number, it returns the index number of
 the resulting shape after applying that move.
 */
public class ShapeTranTable {
	// the transition table data
	private int tranTable[];

	public ShapeTranTable() throws Square1Exception {
		// At last we can calculate full transition table
		tranTable = new int[Sq1Shape.list.length * 4];
		// see if can be found on file
		String fname = "sq1stt.dat";
		File file = new File(fname);
		if (!file.exists()) {
			// no file. calculate table.
			// for each move, shape
			for (int i = 0; i < Sq1Shape.list.length; i++) {
				for (int m = 0; m < 4; m++) {
					// find index of resulting shape after that move
					setEntry(i, m, Sq1Shape.list[i].domove(m));
				}
			}
			// save to file
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(file)));
			} catch (FileNotFoundException e) {
				throw new Square1Exception(e.getMessage(), e);
			} catch (IOException e) {
				throw new Square1Exception(e.getMessage(), e);
			}
			try {
				for (int i = 0; i < tranTable.length; i++) {
					oos.writeInt(tranTable[i]);
				}
			} catch (IOException e) {
				throw new Square1Exception(e.getMessage(), e);
			} finally {
				try {
					oos.close();
				} catch (IOException e) {
					throw new Square1Exception(e.getMessage(), e);
				}
			}
		} else {
			// read from file
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				throw new Square1Exception(e.getMessage(), e);
			} catch (IOException e) {
				throw new Square1Exception(e.getMessage(), e);
			}
			try {
				for (int i = 0; i < tranTable.length; i++) {
					tranTable[i] = ois.readInt();
				}
			} catch (IOException e) {
				throw new Square1Exception(e.getMessage(), e);
			} finally {
				try {
					ois.close();
				} catch (IOException e) {
					throw new Square1Exception(e.getMessage(), e);
				}
			}
		}
	}

	int getEntry(int shape, int move) {
		return tranTable[shape * 4 + move];
	}

	private void setEntry(int shape, int move, int val) {
		tranTable[shape * 4 + move] = val;
	}
}
