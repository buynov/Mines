/*
 * This class belongs to Mines project.
 */
package com.buynov.resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.buynov.MConstants;


/**
 * This is an image that contains a single number drawn on it. 
 * 
 * @author Stefan Buynov
 */
public class NumberImage extends BufferedImage implements MConstants {

	/**
	 * Creates a new <code>NumberImage</code> instance.
	 * 
	 * @param inHeight	the height of the image
	 * @param inWidth	the width of the image
	 * @param inNumber	the number, drawn on the image
	 */
	public NumberImage(int inHeight, int inWidth, int inNumber) {
		super(inWidth, inHeight, TYPE_INT_RGB);
		generateImageData(inHeight, inWidth, inNumber);
	}
	
	// Generates the image data
	private void generateImageData(int inHeight, int inWidth, int inNumber) {
			Graphics2D g = createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
								RenderingHints.VALUE_ANTIALIAS_ON);
			g.setBackground(Color.lightGray);
		
			g.clearRect(0, 0, inWidth, inHeight);
		if (inNumber > 0) {
			g.setColor(MConstants.COLOR_TABLE[inNumber]);
			g.setFont(new Font("Courier", Font.BOLD, 18));

			String displayText = Integer.toString(inNumber);
			adjustProperFontSize(displayText, g, inHeight, inWidth);
			Rectangle2D rect = g.getFont().getStringBounds(displayText, g.getFontRenderContext()); 

			int textX = (int)Math.round(((inWidth-rect.getWidth())/2));
			int textY = (int)Math.round(inHeight - ((inHeight-rect.getHeight())/2));
			g.drawString(displayText, textX, textY);
		}
	}
	
	// Adjusts the font size, according to the given dimension and the given text.
	private static Font adjustProperFontSize(String text, Graphics2D g, int height, int width) {
		Rectangle2D rect = g.getFont().getStringBounds(text, g.getFontRenderContext());
		while (rect.getHeight() > (height*9/10)) {
			float fontSize = (float)(g.getFont().getSize()-1);
			if (fontSize < 5)
				break;
			g.setFont(g.getFont().deriveFont(fontSize)); 
			rect = g.getFont().getStringBounds(text, g.getFontRenderContext());
		}
		while (rect.getHeight() < (height*7/10)) {
			float fontSize = (float)(g.getFont().getSize()+1);
			if (fontSize > 50)
				break;
			g.setFont(g.getFont().deriveFont(fontSize)); 
			rect = g.getFont().getStringBounds(text, g.getFontRenderContext());
		}
		
		return g.getFont();
	}

}
