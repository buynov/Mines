/*
 * This class belongs to Mines project.
 */
package com.buynov.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;


import com.buynov.MConstants;
import com.buynov.model.MinesNode;
import com.buynov.view.event.MinesButtonEvent;
import com.buynov.view.event.MinesButtonListener;

/**
 * This is the visual Swing representation of a single <code>MinesNode</code> object.
 * 
 * @author Stefan Buynov
 */
public class MinesButton extends JPanel implements MConstants, MouseListener {

	// The node that his button represents.
	private MinesNode	correspondingNode 	= null;
	// The objects, registered to receive events from this button.
	private Vector		listeners			= new Vector();
	
	// Holds the x coordinate of this button
	private int		gridX = -1;
	// Holds the y coordinate of this button
	private int		gridY = -1;
	
	// Specifies if the left mouse button is being pressed.
	private boolean	leftMouseButtonPressed = false;
	// Specifies if the right mouse button is being pressed.
	private boolean	rightMouseButtonPressed = false;
	
	/**
	 * Creates a new <code>MinesButton</code>, which is placed on the given coordinates.
	 * 
	 * @param inX
	 * @param inY
	 */
	public MinesButton(int inX, int inY) {
		this.gridX = inX;
		this.gridY = inY;

		setLayout(new BorderLayout());
		this.setBorder(RAISED_BORDER);
		setMinimumSize(BUTTON_PREFERRED_SIZE);
		setPreferredSize(BUTTON_PREFERRED_SIZE);
		addMouseListener(this);
	}
	
	/** 
	 * Defined in <code>javax.swing.JComponent</code>.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (correspondingNode != null) {
			if (correspondingNode.isPressed())
				this.setBorder(LOWERED_BORDER);
			else this.setBorder(RAISED_BORDER);
			ButtonPainter.paintButton(this, g);
		}
	}

	/** 
	 * Defined in <code>java.awt.event.MouseListener</code>.
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/** 
	 * Defined in <code>java.awt.event.MouseListener</code>.
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/** 
	 * Defined in <code>java.awt.event.MouseListener</code>.
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		leftMouseButtonPressed = false;
		rightMouseButtonPressed = false;
	}

	/** 
	 * Defined in <code>java.awt.event.MouseListener</code>.
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (isLeftButtonEvent(e))
			leftMouseButtonPressed = true;
		if (isRightButtonEvent(e))
			rightMouseButtonPressed = true;
	}

	/** 
	 * Defined in <code>java.awt.event.MouseListener</code>.
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (isLeftButtonEvent(e)) {
			fireMinesButtonClicked(new MinesButtonEvent(this, leftMouseButtonPressed, rightMouseButtonPressed));
			leftMouseButtonPressed = false;
			rightMouseButtonPressed = false;
		}
		if (isRightButtonEvent(e)) {
			fireMinesButtonClicked(new MinesButtonEvent(this, leftMouseButtonPressed, rightMouseButtonPressed));
			leftMouseButtonPressed = false;
			rightMouseButtonPressed = false;
		}
	}
	
	// Verifies if the left mouse button is being pressed, from the given MouseEvent.
	private boolean isLeftButtonEvent(MouseEvent me) {
		return ((me.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK);
	}
	
	// Verifies if the right mouse button is being pressed, from the given MouseEvent.
	private boolean isRightButtonEvent(MouseEvent me) {
		return ((me.getModifiers() & MouseEvent.BUTTON3_MASK) == MouseEvent.BUTTON3_MASK);
	}
	
	/**
	 * Registers the given <code>MinesButtonListener</code> to receive events from this button.
	 * 
	 * @param l
	 */
	public void addMinesButtonListener(MinesButtonListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}
	
	/**
	 * Unregisters the given <code>MinesButtonListener</code> from this button.
	 * 
	 * @param l
	 */
	public void removeMinesButtonListener(MinesButtonListener l) {
		listeners.remove(l);
	}
	
	// Invokes minesButtonClicked method for the registered listeners.
	private void fireMinesButtonClicked(MinesButtonEvent mbe) {
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			MinesButtonListener l = (MinesButtonListener) en.nextElement();
			l.minesButtonClicked(mbe);
		}
	}

	/**
	 * Returns the node, that this button represents.
	 * 
	 * @return	the node, that this button represents
	 */
	public MinesNode getCorrespondingNode() {
		return correspondingNode;
	}

	/**
	 * Sets the node, that this button represents.
	 * 
	 * @param node	the node, that this button represents
	 */
	public void setCorrespondingNode(MinesNode node) {
		correspondingNode = node;
	}

	/**
	 * Returns the x coordinate for this button in the grid.
	 * 
	 * @return	the x coordinate for this button in the grid
	 */
	public int getGridX() {
		return gridX;
	}

	/**
	 * Returns the y coordinate for this button in the grid.
	 * 
	 * @return	the y coordinate for this button in the grid
	 */
	public int getGridY() {
		return gridY;
	}

}
