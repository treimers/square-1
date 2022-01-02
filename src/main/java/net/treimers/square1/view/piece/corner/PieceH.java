package net.treimers.square1.view.piece.corner;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractCornerPiece;

public class PieceH extends AbstractCornerPiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.INNER_HORIZONTAL,
		Constants.LEFT,
		Constants.FRONT,
		Constants.INNER_HORIZONTAL,
		Constants.BOTTOM,
	};

	public PieceH(int rotate, int position, ColorBean colorBean) {
		super(rotate, position, colorBean, SIDES);
	}
}
