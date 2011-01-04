/*
 * This interface belongs to Mines project.
 */
package com.buynov;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * This interface holds some common constants for the Mines application.
 * 
 * @author Stefan Buynov
 */
public interface MConstants {

	// GENERAL
	/** Default height (number of tiles in height) for the Mines application. */
	public static final int	DEFAULT_HEIGHT 		= 16;
	/** Default width (number of tiles in width) for the Mines application. */
	public static final int	DEFAULT_WIDTH 		= 30;
	/** Default number of mines in the grid. */
	public static final int	DEFAULT_MINES_COUNT = 69;
	
	// UI
	/** Constant for raised border - this border is used for the unpressed tiles. */
	public static final Border	RAISED_BORDER 	= BorderFactory.createRaisedBevelBorder();
	/** Constant for lowered border - this border is used for the pressed tiles. */
	public static final Border	LOWERED_BORDER	= BorderFactory.createLoweredBevelBorder();
	/** Preferred size of the tile buttons. */
	public static final Dimension	BUTTON_PREFERRED_SIZE = new Dimension(30, 30);
	/** An array that holds the colors for the numbers, written in the pressed tiles. */
	public static final Color[] COLOR_TABLE =
			{null, Color.blue, Color.green,
			 Color.red, Color.blue.darker(), Color.red.darker(),
			 Color.cyan, Color.magenta.darker(), Color.orange};
	
	// MinesButton event types
	/** Mines button has been pressed */
	public static final short	MINES_BUTTON_PRESS_EVENT	= 1;
	/** Both mouse buttons have been pressed on a tile - reveal the neighbour tiles if possible */
	public static final short	MINES_BUTTON_PRESS_NEIGHBOURS_EVENT	= 2;
	/** Mines button has been marked (flagged) */
	public static final short	MINES_BUTTON_MARK_EVENT		= 3;
}
