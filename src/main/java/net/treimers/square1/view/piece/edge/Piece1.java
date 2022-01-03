package net.treimers.square1.view.piece.edge;

import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Constants;
import net.treimers.square1.view.piece.AbstractEdgePiece;

/**
 * Instances of this class are used as piece 1 objects in the Square-1 application.
 */
public class Piece1 extends AbstractEdgePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.INNER_HORIZONTAL,
		Constants.LEFT,
		Constants.INNER_HORIZONTAL,
		Constants.TOP,
	};

	/**
	 * Creates a new instance.
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 * @param position the position (0 = top, 1 = bottom)
	 * @param colorBean the bean with the Square-1 colors.
	 */
	public Piece1(int rotate, int position, ColorBean colorBean) {
		super(rotate, position, colorBean, SIDES);
	}
}
