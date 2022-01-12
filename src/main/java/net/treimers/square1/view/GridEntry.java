package net.treimers.square1.view;

public class GridEntry {
	private char name;
	private int posx;
	private int posy;

	public GridEntry(char name, int posx, int posy) {
		super();
		this.name = name;
		this.posx = posx;
		this.posy = posy;
	}

	public char getName() {
		return name;
	}

	public int getPosx() {
		return posx;
	}

	public int getPosy() {
		return posy;
	}
	
	@Override
	public String toString() {
		return Character.toString(name);
	}
}
