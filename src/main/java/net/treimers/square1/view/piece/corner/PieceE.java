package net.treimers.square1.view.piece.corner;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractCornerPiece;

public class PieceE extends AbstractCornerPiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.INNER_HORIZONTAL,
		Constants.FRONT,
		Constants.RIGHT,
		Constants.INNER_HORIZONTAL,
		Constants.BOTTOM,
	};

	public PieceE(int rotate, int position, ColorBean colorBean) {
		super(rotate, position, colorBean, SIDES);
	}
}
