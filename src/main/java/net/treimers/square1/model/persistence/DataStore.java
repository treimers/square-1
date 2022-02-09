package net.treimers.square1.model.persistence;

import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.StoreData;

/**
 * Instances of this class are used to persist or restore Square-1 data (color scheme
 * and position).
 */
public interface DataStore {
	/**
	 * Stores Square-1 data to a storage.
	 * 
	 * @param data the Square-1 data.
	 * @throws Square1Exception in case of any errors.
	 */
	public void store(StoreData data) throws Square1Exception;

	/**
	 * Restores Square-1 data from a storage.
	 * @return the Square-1 data.
	 * @throws Square1Exception in case of any errors.
	 */
	public StoreData restore() throws Square1Exception;
}
