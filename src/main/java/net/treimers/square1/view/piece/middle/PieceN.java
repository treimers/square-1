package net.treimers.square1.view.piece.middle;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractMiddlePiece;

public class PieceN extends AbstractMiddlePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.RIGHT,
		Constants.BACK,
		Constants.INNER_HORIZONTAL,
		Constants.FRONT,
		Constants.INNER_VERTICAL,
	};

	public PieceN(int rotate, ColorBean colorBean) {
		super(rotate, colorBean, SIDES);
	}
}
