package com.buynov;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.buynov.view.dialog.SettingsPanel;

@SuppressWarnings("serial")
public class MinesApplet extends JApplet implements ActionListener {

	// The panel with the input fields.
	private SettingsPanel	settingsPanel 	= new SettingsPanel();
	// The OK button.
	private JButton		okButton 		= new JButton("Play!");

	@Override
	public void init() {
	    try {
	        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
	            public void run() {
	                createGUI();
	            }
	        });
	    } catch (Exception e) {
	        System.err.println("createGUI didn't successfully complete");
	    }

	}

	protected void createGUI() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		okButton.addActionListener(this);
		
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		settingsPanel.initValues();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == okButton) {
			if (settingsPanel.applyValues()) {
				MinesFrame frame = new MinesFrame();
				frame.show();
			}
			else {
				JOptionPane.showMessageDialog(this, "There are invalid inputs. Please verify.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	
}
