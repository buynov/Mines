/*
 * This class belongs to Mines project.
 */
package com.buynov.resources;

import java.awt.Image;
import javax.swing.ImageIcon;

import com.buynov.MConstants;

/**
 * This class loads and holds the resources, used in the Mines application.
 * 
 * @author Stefan Buynov
 */
public class Resources implements MConstants {

	/** The flag image. */
	public static ImageIcon FLAG_IMAGE = null;
	/** The mine image */
	public static ImageIcon MINE_IMAGE = null;
	/** The stricken out flag image */
	public static ImageIcon FAKE_FLAG_IMAGE = null;
	/** Array, that contains the number images. Index corresponds to the number drawn on the image */
	public static ImageIcon[] NUMBER_IMAGES = new ImageIcon[8];
	
	// Flag image, scaled to the current button size.
	private static ImageIcon 	ScaledFlagImage = null;
	// Mine image, scaled to the current button size.
	private static ImageIcon 	ScaledMineImage = null;
	// Stricken out flag image, scaled to the current button size.
	private static ImageIcon 	ScaledFakeFlagImage = null;
	// Array of number images, scaled to the current button size.
	private static ImageIcon[] ScaledNumberImages = new ImageIcon[8];
	
	// Current button height.
	private static int		CurrentHeight = BUTTON_PREFERRED_SIZE.height;
	// Current button width.
	private static int		CurrentWidth = BUTTON_PREFERRED_SIZE.width;
	
	// Loads resources on class load.
	static {
		FLAG_IMAGE = new ImageIcon(Resources.class.getResource("flag.gif"));
		MINE_IMAGE = new ImageIcon(Resources.class.getResource("mine.gif"));
		FAKE_FLAG_IMAGE = new ImageIcon(Resources.class.getResource("fake_flag.gif"));
		
		for (int i = 0; i < NUMBER_IMAGES.length; i++) {
			NUMBER_IMAGES[i] = new ImageIcon(new NumberImage(CurrentHeight, CurrentWidth, i));
		}
		
		updateScaledInstances(CurrentHeight, CurrentWidth);
	}

	/**
	 * Returns the flag image, scaled to the given height and width.
	 * 
	 * @param inHeight	current button height
	 * @param inWidth	current button width
	 * @return	scaled image
	 */
	public static ImageIcon getScaledFlagImage(int inHeight, int inWidth) {
		if (inHeight != CurrentHeight || inWidth != CurrentWidth)
			updateScaledInstances(inHeight, inWidth);
			
		return ScaledFlagImage;
	}

	/**
	 * Returns the stricken out flag image, scaled to the given height and width.
	 * 
	 * @param inHeight	current button height
	 * @param inWidth	current button width
	 * @return	scaled image
	 */
	public static ImageIcon getScaledFakeFlagImage(int inHeight, int inWidth) {
		if (inHeight != CurrentHeight || inWidth != CurrentWidth)
			updateScaledInstances(inHeight, inWidth);
			
		return ScaledFakeFlagImage;
	}

	/**
	 * Returns the mine image, scaled to the given height and width.
	 * 
	 * @param inHeight	current button height
	 * @param inWidth	current button width
	 * @return	scaled image
	 */
	public static ImageIcon getScaledMineImage(int inHeight, int inWidth) {
		if (inHeight != CurrentHeight || inWidth != CurrentWidth)
			updateScaledInstances(inHeight, inWidth);
			
		return ScaledMineImage;
	}

	/**
	 * Returns the requested number image, scaled to the given height and width.
	 * 
	 * @param inHeight	current button height
	 * @param inWidth	current button width
	 * @param inNumber	the number image
	 * @return	scaled image
	 */
	public static ImageIcon getScaledNumberImage(int inHeight, int inWidth, int inNumber) {
		if (inHeight != CurrentHeight || inWidth != CurrentWidth)
			updateScaledInstances(inHeight, inWidth);
			
		return ScaledNumberImages[inNumber];
	}

	// Updates the scaled image variables according to the given dimension.
	private static void updateScaledInstances(int inHeight, int inWidth) {
		ScaledFlagImage = getScaledImage(FLAG_IMAGE, inHeight, inWidth);
		ScaledMineImage = getScaledImage(MINE_IMAGE, inHeight, inWidth);
		ScaledFakeFlagImage = getScaledImage(FAKE_FLAG_IMAGE, inHeight, inWidth);
		
		for (int i = 0; i < ScaledNumberImages.length; i++) {
			ScaledNumberImages[i] = getScaledImage(NUMBER_IMAGES[i], inHeight, inWidth);
		}
		
		CurrentHeight = inHeight;
		CurrentWidth = inWidth;
	}

	// Scales the given image to the given dimenstion.
	private static ImageIcon getScaledImage(ImageIcon inImage, int inHeight, int inWidth) {
		return new ImageIcon(inImage.getImage().getScaledInstance(inWidth, inHeight, Image.SCALE_DEFAULT));
	}

}
