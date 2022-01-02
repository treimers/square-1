package net.treimers.square1.view.piece.middle;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractMiddlePiece;

/**
 * Instances of this class are used as piece A objects in the Square-1 application.
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
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 */
	public PieceM(int rotate, ColorBean colorBean) {
		super(rotate, colorBean, SIDES);
	}
}
