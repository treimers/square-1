package net.treimers.square1.view.piece.corner;

import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;
import net.treimers.square1.view.piece.AbstractCornerPiece;

/**
 * Instances of this class are used as piece H objects in the Square-1 application.
 */
public class PieceH extends AbstractCornerPiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.INNER_HORIZONTAL,
		Constants.LEFT,
		Constants.FRONT,
		Constants.INNER_HORIZONTAL,
		Constants.BOTTOM,
	};

	/**
	 * Creates a new instance.
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 */
	public PieceH(int rotate, int position, ColorBean colorBean) {
		super(rotate, position, colorBean, SIDES);
	}
}
