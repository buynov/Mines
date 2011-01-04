/*
 * This class belongs to Mines project.
 */
package com.buynov.view;

import java.awt.GridLayout;

import javax.swing.JPanel;


import com.buynov.MConstants;
import com.buynov.model.MinesModel;
import com.buynov.model.event.MinesModelListener;
import com.buynov.view.event.MinesButtonEvent;
import com.buynov.view.event.MinesButtonListener;

/**
 * This is the visual Swing representation of an actual Mines game.
 * 
 * @author Stefan Buynov
 */
public class MinesGrid extends JPanel implements MinesButtonListener, MConstants, MinesModelListener {

	// The model for the current game.
	private MinesModel			model	= null;
	// The buttons for the current game. 
	private MinesButton[][]	buttons	= null;

	/**
	 * Creates a new grid, associated with the given model.
	 * 
	 * @param inModel
	 */
	public MinesGrid(MinesModel inModel) {
		setModel(inModel);
	}
	
	/**
	 * Creates and lays out the initail buttons for the grid. The buttons are not associated with a <code>MinesNode</code> yet. 
	 */
	public void initializeButtons() {
		// Remove the buttons, currently in this container.
		removeAll();
		int height = model.getHeight();
		int width = model.getWidth();

		GridLayout layout = new GridLayout(height, width);
		setLayout(layout);
		
		// Create the initial buttons
		buttons = new MinesButton[width][height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				buttons[j][i] = new MinesButton(j, i);
				add(buttons[j][i]);
				buttons[j][i].addMinesButtonListener(this);
			}
		}
		
		// When the content (the components that it contains) of a panel is changed, it needs revalidation.
		this.revalidate();
		this.repaint();
	}

	// Associates the buttons in the grid with the nodes from the model.
	private void setCorrespondingNodesFromModel() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setCorrespondingNode(model.getMinesNodes()[i][j]);
			}
		}
	}

	/** 
	 * Defined in <code>com.buynov.view.event.MinesButtonListener</code>.
	 * 
	 * @see com.buynov.view.event.MinesButtonListener#MinesButtonClicked(java.awt.event.MouseEvent)
	 */
	public void minesButtonClicked(MinesButtonEvent e) {
		int type = e.getEventType();
		
		switch (type) {
			case MINES_BUTTON_PRESS_EVENT:
				model.nodePressed(e.getX(), e.getY());
				break; 
			case MINES_BUTTON_MARK_EVENT:
				model.nodeMarked(e.getX(), e.getY());
				break; 
			case MINES_BUTTON_PRESS_NEIGHBOURS_EVENT:
				model.pressSurroundingNodes(e.getX(), e.getY());
				break; 
		}
		
	}

	/** 
	 * Defined in <code>com.buynov.view.event.MinesButtonListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#modelInitialized()
	 */
	public void modelInitialized() {
		setCorrespondingNodesFromModel();
	}

	/** 
	 * Defined in <code>com.buynov.view.event.MinesButtonListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#modelDirty()
	 */
	public void modelDirty() {
		this.repaint();
	}

	/** 
	 * Defined in <code>com.buynov.view.event.MinesButtonListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#gameOver()
	 */
	public void gameOver() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].removeMinesButtonListener(this);
			}
		}
	}

	/** 
	 * Defined in <code>com.buynov.view.event.MinesButtonListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#remainingMinesChanged()
	 */
	public void remainingMinesChanged() {
	}

	/**
	 * Returns the model, that this grid represents.
	 * 
	 * @return	the model, that this grid represents
	 */
	public MinesModel getModel() {
		return model;
	}

	/**
	 * Sets the model, that this grid represents.
	 * 
	 * @param inModel	the model, that this grid represents
	 */
	public void setModel(MinesModel inModel) {
		model = inModel;
		model.addMinesModelListener(this);
	}

}
