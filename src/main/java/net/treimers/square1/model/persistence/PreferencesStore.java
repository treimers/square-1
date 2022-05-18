package net.treimers.square1.model.persistence;

import java.util.prefs.Preferences;

import javafx.scene.paint.Color;
import net.treimers.square1.Square1;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.MoveSequence;
import net.treimers.square1.model.Position;
import net.treimers.square1.model.Side;
import net.treimers.square1.model.Square1Data;
import net.treimers.square1.util.Utils;

/**
 * Instances of this class are used to persist or restore Square-1 data to or from the user preferences respectively.
 */
public class PreferencesStore implements DataStore {
	/** The color lead-in string in the prefs tree. */
	private static final String COLOR = "color.";
	/** The solution lead-in string in the prefs tree. */
	private static final String SOLUTION = "solution";
	/** The position lead-in string in the prefs tree. */
	private static final String POSITION = "position";
	/** The ColorBean with the default colors. */
	private ColorBean colorBean;

	/**
	 * Creates a new instance.
	 * 
	 * @param colorBean the ColorBean with the default colors. 
	 */
	public PreferencesStore(ColorBean colorBean) {
		this.colorBean = colorBean;
	}

	@Override
	public void store(Square1Data data) throws Square1Exception {
		String[] colors = data.getColorStrings();
		Side[] allSides = Side.values();
		Preferences userNode = Preferences.userNodeForPackage(Square1.class);
		for (Side side : allSides) {
			String color = colors[side.ordinal()];
			userNode.put(COLOR + side.name().toLowerCase(), color);
		}
		userNode.put(POSITION, data.getPositionString());
		userNode.put(SOLUTION, data.getSolution().toString());
	}

	@Override
	public Square1Data restore() throws Square1Exception {
		Side[] allSides = Side.values();
		String[] colors = new String[allSides.length];
		Preferences userNode = Preferences.userNodeForPackage(Square1.class);
		Color[] defaultColors = colorBean.getDefaultColors();
		for (Side side : allSides) {
			Color defaultColor = defaultColors[side.ordinal()];
			colors[side.ordinal()] = userNode.get(COLOR + side.name().toLowerCase(), Utils.colorToString(defaultColor));
		}
		String positonString = userNode.get(POSITION, Position.SOLVED_POSITION_STRING);
		MoveSequence solutionString = new MoveSequence(userNode.get(SOLUTION, ""));
		return new Square1Data(colors, positonString, solutionString);
	}

}
