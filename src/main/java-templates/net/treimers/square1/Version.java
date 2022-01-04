package net.treimers.square1;

/**
 * Version class used by maven build to inject project data.
 */
public class Version {
	/** The application title. */
	private static final String TITLE = "@project.name@";
	/** The application version. */
	private static final String VERSION_NUMBER = "@project.version@";
	/** The application vendor. */
	private static final String APP_VENDOR = "@organization.name@";

	/**
	 * Don't let anyone instantiate this class.
	 */
	private Version() {
	}

	/**
	 * Returns the application title.
	 * 
	 * @return the application title.
	 */
	public static String getAppTitle() {
		return TITLE;
	}

	/**
	 * Returns the application version.
	 * 
	 * @return the application version.
	 */
	public static String getAppVersion() {
		return VERSION_NUMBER;
	}

	/**
	 * Returns the application vendor.
	 * 
	 * @return the application vendor.
	 */
	public static String getAppVendor() {
		return APP_VENDOR;
	}
}
