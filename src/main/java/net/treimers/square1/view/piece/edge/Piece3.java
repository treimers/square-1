package net.treimers.square1.view.piece.edge;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractEdgePiece;

public class Piece3 extends AbstractEdgePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.INNER_HORIZONTAL,
		Constants.RIGHT,
		Constants.INNER_HORIZONTAL,
		Constants.TOP,
	};

	public Piece3(int rotate, int position, ColorBean colorBean) {
		super(rotate, position, colorBean, SIDES);
	}
}
