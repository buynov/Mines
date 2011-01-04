/*
 * This class belongs to Mines project.
 */
package com.buynov.view.dialog;

import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import com.buynov.MConstants;
import com.buynov.model.MinesModel;

/**
 * This panel holds the input fields for the <code>SettingsDialog</code>.
 * 
 * @author Stefan Buynov
 */
public class SettingsPanel extends JPanel implements MConstants {

	// The label that holds the "Height" text.
	private JLabel		tilesGridHeightTitle 	= new JLabel("Height: ");
	// Allows to input height for the game grid.
	private JTextField	tilesGridHeight			= new JTextField(3);			
	// The label that holds the "Width" text.
	private JLabel		tilesGridWidthTitle 	= new JLabel("Width: ");
	// Allows to input width for the game grid.
	private JTextField	tilesGridWidth			= new JTextField(3);			
	// The label that holds the "Mines" text.
	private JLabel		numberOfMinesTitle 		= new JLabel("Mines: ");
	// Allows to input the number of mines for the game.
	private JTextField	numberOfMines			= new JTextField(3);			

	/** Creates new <code>SettingsPanel</code> instance. */
	public SettingsPanel() {
		createGUI();
	}
	
	// Creates and lays out the UI components on the panel.
	private void createGUI() {
		setLayout(new GridLayout(3,3));
		
		add(tilesGridHeightTitle);
		add(tilesGridHeight);
		add(tilesGridWidthTitle);
		add(tilesGridWidth);
		add(numberOfMinesTitle);
		add(numberOfMines);
		
		TextFieldFocusListener l = new TextFieldFocusListener();
		tilesGridHeight.addFocusListener(l);
		tilesGridWidth.addFocusListener(l);
		numberOfMines.addFocusListener(l);
	}
	
	// Initializes the input fields with the current game settings.
	public void initValues() {
		tilesGridHeight.setText(String.valueOf(MinesModel.HEIGHT));
		tilesGridWidth.setText(String.valueOf(MinesModel.WIDTH));
		numberOfMines.setText(String.valueOf(MinesModel.MINES_COUNT));
	}
	
	// Trys to apply the settings and returns the result from this operation.
	// If it returns false, than the settings are not applied due to errors.
	public boolean applyValues() {
		try {
			int height = Integer.parseInt(tilesGridHeight.getText());
			int width = Integer.parseInt(tilesGridWidth.getText());
			int minesCount = Integer.parseInt(numberOfMines.getText());
			
			if (height <= 0 || width <= 0 || minesCount <= 0)
				return false;
				
			if (minesCount >= (height*width))
				return false;
			
			MinesModel.HEIGHT  = height;
			MinesModel.WIDTH = width;
			MinesModel.MINES_COUNT = minesCount;
			
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	// This FocusListener selects the TextField content on focusGained. 
	private class TextFieldFocusListener extends FocusAdapter {
		
		/** 
		 * Defined in <code>java.awt.event.FocusListener</code>.
		 * 
		 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
		 */
		public void focusGained(FocusEvent e) {
			JTextField f = (JTextField)e.getSource();
			f.selectAll();
		}

	}
}
