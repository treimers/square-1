package net.treimers.square1.controller;

/**
 * Instances of this interface are used to provide functionality to enable or disable
 * the application menu of the Square-1 application.
 */
public interface MenuHandler {
	/**
	 * Enables or disables the application menu of the Square-1 application.
	 * 
	 * @param indicator enable menus if true, disable otherwise.
	 */
	public void setMenusEnabled(boolean indicator);
}
