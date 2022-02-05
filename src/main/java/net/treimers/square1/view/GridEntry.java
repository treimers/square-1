package net.treimers.square1.view;

/**
 * Instances of this class are used to define the piece sub scenes with name and their position in position dialog grid pane.
 */
public class GridEntry {
	/** The piece name. */
	private char name;
	/** The x position in grid pane. */
	private int posx;
	/** The y position in grid pane. */
	private int posy;

	/**
	 * Creates a new instance.
	 * 
	 * @param name the piece name.
	 * @param posx the x position in grid pane.
	 * @param posy the y position in grid pane.
	 */
	public GridEntry(char name, int posx, int posy) {
		this.name = name;
		this.posx = posx;
		this.posy = posy;
	}

	/**
	 * Gets the piece name.
	 * 
	 * @return the piece name.
	 */
	public char getName() {
		return name;
	}

	/**
	 * Gets the x position in grid pane.
	 * 
	 * @return the x position in grid pane.
	 */
	public int getPosx() {
		return posx;
	}

	/**
	 * Gets the y position in grid pane.
	 * 
	 * @return the y position in grid pane.
	 */
	public int getPosy() {
		return posy;
	}

	@Override
	public String toString() {
		return Character.toString(name);
	}
}
