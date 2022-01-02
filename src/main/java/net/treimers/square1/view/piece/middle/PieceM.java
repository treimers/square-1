package net.treimers.square1.view.piece.middle;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractMiddlePiece;

public class PieceM extends AbstractMiddlePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.LEFT,
		Constants.FRONT,
		Constants.INNER_HORIZONTAL,
		Constants.BACK,
		Constants.INNER_VERTICAL,
	};

	public PieceM(int rotate, ColorBean colorBean) {
		super(rotate, colorBean, SIDES);
	}
}
