package net.treimers.square1.view.piece.middle;

import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;
import net.treimers.square1.view.piece.AbstractMiddlePiece;

/**
 * Instances of this class are used as piece N objects in the Square-1 application.
 */
public class PieceN extends AbstractMiddlePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.RIGHT,
		Constants.BACK,
		Constants.INNER_HORIZONTAL,
		Constants.FRONT,
		Constants.INNER_VERTICAL,
	};

	/**
	 * Creates a new instance.
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 180 degrees).
	 * @param colorBean the bean with the Square-1 colors.
	 */
	public PieceN(int rotate, ColorBean colorBean) {
		super(rotate, colorBean, SIDES);
		rotateByZ(180);
	}
}
