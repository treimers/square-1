package net.treimers.square1.view.misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This library class provides image loading capabilities. 
 */
public class ImageLoader {
	private static final Image LOGO_IMAGE = new Image(ImageLoader.class.getResourceAsStream("/net/treimers/square1/square1.png"));

	/**
	 * Private constructor, never used.
	 */
	private ImageLoader() {
	}

	/**
	 * Gets the logo image.
	 * @return the logo image.
	 */
	public static Image getLogoImage() {
		return LOGO_IMAGE;
	}

	/**
	 * Gets the logo image viewer.
	 * @return the logo image viewer.
	 */
	public static ImageView getLogoImageView() {
		return new ImageView(LOGO_IMAGE);
	}
}
