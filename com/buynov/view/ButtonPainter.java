/*
 * This class belongs to Mines project.
 */
package com.buynov.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import com.buynov.model.MinesNode;
import com.buynov.resources.Resources;

/**
 * This class is used for painting the proper images on the <code>MinesButton</code> instances.
 * 
 * @author Stefan Buynov
 */
public class ButtonPainter {

	// Current button height.
	private static int		CurrentHeight = 0;
	// Current button width.
	private static int		CurrentWidth = 0;
	// Current font
	private static Font		CurrentFont = null;
	
	/**
	 * Paints the proper image on the given button, according to its associated node.
	 * 
	 * @param inButton	the button, that needs to be painted
	 * @param inG		the graphics, passed to the paintComponent method of the specified button
	 */
	public static void paintButton(MinesButton inButton, Graphics inG) {
		MinesNode node = inButton.getCorrespondingNode();
		int x = inButton.getInsets().top;
		int y = inButton.getInsets().left;
		int height = inButton.getHeight() - inButton.getInsets().top - inButton.getInsets().bottom; 
		int width = inButton.getWidth() - inButton.getInsets().left - inButton.getInsets().right;
		
		inG.clearRect(x, y, width, height); 

		if (node.isMarked()) {
			if (node.isRevealed() && !node.hasMine())
				drawImage((Graphics2D)inG, inButton, Resources.getScaledFakeFlagImage(height, width));
			else drawImage((Graphics2D)inG, inButton, Resources.getScaledFlagImage(height, width));
		}
		else if (node.isPressed() || node.isRevealed()) {
			int count = node.getSurroundingMinesCount();
			if (node.hasMine())
				drawImage((Graphics2D)inG, inButton, Resources.getScaledMineImage(height, width));
			else if (count >= 0) {
				drawImage((Graphics2D)inG, inButton, Resources.getScaledNumberImage(height, width, count));
				//drawNumber((Graphics2D)inG, inButton, count);
			}
		}
	}
	
	// Draws the given image, on the given button's graphics object.
	private static void drawImage(Graphics2D inG, MinesButton inButton, ImageIcon inImage) {
		inImage.paintIcon(inButton, inG, inButton.getInsets().top, inButton.getInsets().left);
	}
	
}
