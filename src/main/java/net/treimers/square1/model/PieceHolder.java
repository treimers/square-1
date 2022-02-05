package net.treimers.square1.model;

import java.beans.PropertyChangeListener;

/**
 * Instances of this interface are used to register any observers that will be notified
 * on any changes to Square-1 pieces.
 */
public interface PieceHolder {
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
	public void addPieceChangeListener(PropertyChangeListener listener);

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
	public void removePieceChangeListener(PropertyChangeListener listener);
}
