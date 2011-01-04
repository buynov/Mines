/*
 * This class belongs to Mines project.
 */
package com.buynov.view.event;

/**
 * This interface defines the methods, that the listeners to the <code>MinesButton</code> instances should implement.
 * 
 * @author Stefan Buynov
 */
public interface MinesButtonListener {

	/** This method is invoked when a mouse button is clicked on the <code>MinesButton</code> object.*/
	public void minesButtonClicked(MinesButtonEvent e);
}
