package net.treimers.square1.model;

import java.beans.PropertyChangeListener;

import javafx.scene.paint.Color;

/**
 * An instance of this class holds the colors of the outer and inner sides of a Square-1.
 * It is used by the pieces in order to get notice via property change events
 * about any changes to the colors by the user.
 */
public interface ColorBean {
	/**
	 * Add a PropertyChangeListener to the listener list.
	 * The listener is registered for all properties.
	 * The same listener object may be added more than once, and will be called
	 * as many times as it is added.
	 * If {@code listener} is null, no exception is thrown and no action
	 * is taken.
	 *
	 * @param listener  The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove a PropertyChangeListener from the listener list.
	 * This removes a PropertyChangeListener that was registered
	 * for all properties.
	 * If {@code listener} was added more than once to the same event
	 * source, it will be notified one less time after being removed.
	 * If {@code listener} is null, or was never added, no exception is
	 * thrown and no action is taken.
	 *
	 * @param listener  The PropertyChangeListener to be removed
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Gets the default colors of Square-1 sides.
	 * @return the default colors of Square-1 sides.
	 */
	public Color[] getColors();
}
