/*
 * This class belongs to Mines project.
 */
package com.buynov.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.buynov.view.dialog.event.SettingsDialogListener;

/**
 * This is the dialog, used to change the game settings (drig height and width, and the number of mines in the grid).
 * 
 * @author Stefan Buynov
 */
public class SettingsDialog extends JDialog implements ActionListener {

	// The panel with the input fields.
	private SettingsPanel	settingsPanel 	= new SettingsPanel();
	
	// The OK button.
	private JButton		okButton 		= new JButton("OK");
	// The Cancel button.
	private JButton		cancelButton 	= new JButton("Cancel");
	
	// The objects, registered to receive events from this dialog.
	private Vector			listeners		= new Vector();

	/**
	 * The constructor for this dialog.
	 * 
	 * @param owner	see the constructor in javax.swing.JDialog
	 * @param modal	see the constructor in javax.swing.JDialog
	 * @throws HeadlessException	see the constructor in javax.swing.JDialog
	 */
	public SettingsDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, "Settings", modal);
		createGUI();
		centerDialog();
	}
	
	// Creates and lays out the UI components on the dialog.
	private void createGUI() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		pack();
	}

	// Positions the dialog in the center of the screen.
	private void centerDialog() {
	  Dimension d = getToolkit().getScreenSize();
	  int x = (int)(d.getWidth() / 2 - getWidth() / 2);
	  int y = (int)(d.getHeight() / 2 - getHeight() / 2);
	  setLocation(x, y);
	}

	/**
	 * Defined in <code>java.awt.Dialog</code>.
	 * 
	 * @see java.awt.Dialog#show()
	 */
	public void show() {
		settingsPanel.initValues();
		super.show();
	}

	/**
	 * Defined in <code>java.awt.event.ActionListener</code>.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == okButton) {
			if (settingsPanel.applyValues()) {
				hide();
				fireChangesApplied();
			}
			else {
				JOptionPane.showMessageDialog(this, "There are invalid inputs. Please verify.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (source == cancelButton) {
			hide();
			fireChangesCanceled();
		}
	}

	/**
	 * Registers the given <code>SettingsDialogListener</code> to receive events from this dialog.
	 * 
	 * @param l
	 */
	public void addSettingsDialogListener(SettingsDialogListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}
	
	/**
	 * Unregisters the given <code>SettingsDialogListener</code> from this dialog.
	 * 
	 * @param l
	 */
	public void removeSettingsDialogListener(SettingsDialogListener l) {
		listeners.remove(l);
	}
	
	// Invokes changesApplied method for the registered listeners.
	protected void fireChangesApplied() {
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			SettingsDialogListener l = (SettingsDialogListener) en.nextElement();
			l.changesApplied();
		}
	}

	// Invokes changesCanceled method for the registered listeners.
	protected void fireChangesCanceled() {
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			SettingsDialogListener l = (SettingsDialogListener) en.nextElement();
			l.changesCanceled();
		}
	}

}
