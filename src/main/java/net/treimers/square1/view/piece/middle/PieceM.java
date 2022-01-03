package net.treimers.square1.view.piece.middle;

import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;
import net.treimers.square1.view.piece.AbstractMiddlePiece;

/**
 * Instances of this class are used as piece M objects in the Square-1 application.
 */
public class PieceM extends AbstractMiddlePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.LEFT,
		Constants.FRONT,
		Constants.INNER_HORIZONTAL,
		Constants.BACK,
		Constants.INNER_VERTICAL,
	};

	/**
	 * Creates a new instance.
	 * @param colorBean the bean with the Square-1 colors.
	 */
	public PieceM(ColorBean colorBean) {
		super(0, colorBean, SIDES);
	}
}
