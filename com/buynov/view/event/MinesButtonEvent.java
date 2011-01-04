/*
 * This class belongs to Mines project.
 */
package com.buynov.view.event;


import com.buynov.MConstants;
import com.buynov.view.MinesButton;

/**
 * This class describes the type of click, the <code>MinesButton</code> object has received.
 * 
 * @author Stefan Buynov
 */
public class MinesButtonEvent implements MConstants {

	// The type of the event.
	private int	 		eventType			= 0;
	// The button that is being affected by this event.
	private MinesButton	sourceButton		= null;

	/**
	 * Creates a new <code>MinesButtonEvent</code> with the given parameters.
	 * 
	 * @param inSource			the button that is being affected by this event
	 * @param inLeftPressed	if the left mouse button is currently being pressed
	 * @param inRightPressed	if the right mouse button is currently being pressed
	 */
	public MinesButtonEvent(MinesButton inSource, boolean inLeftPressed, boolean inRightPressed) {
		this.eventType = retrieveEventType(inLeftPressed, inRightPressed);
		sourceButton = inSource; 	
	}
	
	// Resolves the event type from the state of the mouse buttons.
	private static int retrieveEventType(boolean leftButtonDown, boolean rightButtonDown) {
		if (!rightButtonDown && leftButtonDown)
			return MINES_BUTTON_PRESS_EVENT;
		else if (rightButtonDown && !leftButtonDown)
			return MINES_BUTTON_MARK_EVENT;
		else if (rightButtonDown && leftButtonDown)
			return MINES_BUTTON_PRESS_NEIGHBOURS_EVENT;

		return 0;
	}
	
	/**
	 * Returns the event type of this event. Possible values are:
	 * 		- MINES_BUTTON_PRESS_EVENT	The button has been pressed.
	 * 		- MINES_BUTTON_MARK_EVENT	The button has been marked.
	 * 		- MINES_BUTTON_PRESS_NEIGHBOURS_EVENT	The button neighbours should be revealed.
	 * 
	 * @return	the event type of this event
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * Returns the button that is being affected by this event. 
	 * 
	 * @return	the button that is being affected by this event
	 */
	public MinesButton getSourceButton() {
		return sourceButton;
	}
	
	/**
	 * Returns the x coordinate of the source button.
	 * 
	 * @return	the x coordinate of the source button
	 */
	public int getX() {
		return sourceButton.getGridX();
	}

	/**
	 * Returns the y coordinate of the source button.
	 * 
	 * @return	the y coordinate of the source button
	 */
	public int getY() {
		return sourceButton.getGridY();
	}

}
