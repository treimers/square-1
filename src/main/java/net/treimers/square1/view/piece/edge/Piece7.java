package net.treimers.square1.view.piece.edge;

import net.treimers.square1.controller.ColorBean;
import net.treimers.square1.controller.Constants;
import net.treimers.square1.view.piece.AbstractEdgePiece;

/**
 * Instances of this class are used as piece A objects in the Square-1 application.
 */
public class Piece7 extends AbstractEdgePiece {
	private static final int[] SIDES = {
		Constants.INNER_VERTICAL,
		Constants.INNER_HORIZONTAL,
		Constants.BACK,
		Constants.INNER_HORIZONTAL,
		Constants.BOTTOM,
	};

	/**
	 * Creates a new instance.
	 * @param rotate value to rotate piece around top (allowed values are 0 for no rotation, 1 for 90 degrees, 2 for 180 degrees
	 * or 3 for 270 degrees).
	 */
	public Piece7(int rotate, int position, ColorBean colorBean) {
		super(rotate, position, colorBean, SIDES);
	}
}
