/*
 * This class belongs to Mines project.
 */
package com.buynov;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import com.buynov.model.MinesModel;
import com.buynov.model.event.MinesModelListener;
import com.buynov.view.MinesGrid;
import com.buynov.view.dialog.SettingsDialog;
import com.buynov.view.dialog.event.SettingsDialogListener;

/**
 * The main frame for the Mines game.
 * 
 * @author Stefan Buynov
 */
public class MinesFrame extends JFrame implements ActionListener, MinesModelListener, SettingsDialogListener {

	// Holds the tiles for the game.
	private MinesGrid		grid 	= null;

	// The label that holds the "Elapsed time" text.
	protected JLabel elapsedTimeTitleLabel = new JLabel("Elapsed time: ");
	// Shows the elapsed seconds from the start of the game
	protected JLabel elapsedTimeLabel = new JLabel();
	// The label that holds the "Remaining mines" text.
	protected JLabel remainingMinesTitleLabel = new JLabel("Remaining mines: ");
	// Shows the number of remaining mines  for the current game.
	protected JLabel remainingMinesLabel = new JLabel();
  
	// "New" menu item
	protected JMenuItem newItem = new JMenuItem("New");
	// "Exit" menu item
	protected JMenuItem exitItem = new JMenuItem("Exit");
	// "Settings" menu item
	protected JMenuItem settingsItem = new JMenuItem("Settings");
  
	// A timer and an int variable that count the elapsed time from the beginning of the current game.
	// When the timer fires the actionPerformed, the variable is increased by 1 (second)
	private Timer timer = new Timer(1000, this);
	private int time = 0;

	// The settings dialog
	private SettingsDialog	settingsDialog = null;
	  
	public MinesFrame() {
		super("Mines");
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		MinesModel model = new MinesModel();
		model.addMinesModelListener(this);
		grid = new MinesGrid(model);
		grid.initializeButtons();
		newItem.addActionListener(this);
		settingsItem.addActionListener(this);
		exitItem.addActionListener(this);
		remainingMinesLabel.setText(String.valueOf(grid.getModel().getMinesCount()));
		
		createGUI();
		centerFrame();
	}
	
	// Creates and lays out the UI components on the frame.
	protected void createGUI() {
	  JMenuBar bar = new JMenuBar();
	  JMenu menu = new JMenu("Game");
	  menu.add(newItem);
	  menu.add(settingsItem);
	  menu.addSeparator();
	  menu.add(exitItem);
	  bar.add(menu);
	  setJMenuBar(bar);
    
	  getContentPane().setLayout(new BorderLayout());
	  getContentPane().add(grid, BorderLayout.CENTER);
    
	  JPanel elapsedTimePanel = new JPanel(new BorderLayout());
	  elapsedTimePanel.setBorder(new EtchedBorder());
	  elapsedTimePanel.add(elapsedTimeTitleLabel, BorderLayout.WEST);
	  elapsedTimePanel.add(elapsedTimeLabel, BorderLayout.CENTER);
	  
	  JPanel remainingMinesPanel = new JPanel(new BorderLayout());
	  remainingMinesPanel.setBorder(new EtchedBorder());
	  remainingMinesPanel.add(remainingMinesTitleLabel, BorderLayout.WEST);
	  remainingMinesPanel.add(remainingMinesLabel, BorderLayout.CENTER);
	  JPanel p = new JPanel(new GridLayout(1,2));
	  p.add(elapsedTimePanel);
	  p.add(remainingMinesPanel);
	  getContentPane().add(p, BorderLayout.SOUTH);
	  
	  pack();
	}

	// Positions the frame in the center of the screen.
	private void centerFrame() {
	  Dimension d = getToolkit().getScreenSize();
	  int x = (int)(d.getWidth() / 2 - getWidth() / 2);
	  int y = (int)(d.getHeight() / 2 - getHeight() / 2);
	  setLocation(x, y);
	}
  
	// Resets the components for a new game.
  	private void newGame() {
		time = 0;
		timer.stop();
		grid.setModel(new MinesModel());
		grid.getModel().addMinesModelListener(this);
		grid.initializeButtons();
		elapsedTimeLabel.setText("");
		remainingMinesLabel.setText(String.valueOf(grid.getModel().getMinesCount()));
		this.getContentPane().repaint();
  	}
  
	/**
	 * Defined in <code>java.awt.event.ActionListener</code>.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == exitItem) {
		  System.exit(0);
		}
		else if (source == newItem) {
			newGame();
		}
		else if (source == timer) {
		  elapsedTimeLabel.setText(String.valueOf(++time));
		}
		else if (source == settingsItem) {
		  if (settingsDialog == null) {
		  	settingsDialog = new SettingsDialog(this, true);
			settingsDialog.addSettingsDialogListener(this);
		  }
		  
		  settingsDialog.show();
		}
	}

	public static void main(String[] args) {
		MinesFrame frame = new MinesFrame();
		frame.show();
	}
	
	/** 
	 * Defined in <code>com.buynov.model.event.MinesModelListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#gameOver()
	 */
	public void gameOver() {
		timer.stop();
	}

	/** 
	 * Defined in <code>com.buynov.model.event.MinesModelListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#modelDirty()
	 */
	public void modelDirty() {
	}

	/** 
	 * Defined in <code>com.buynov.model.event.MinesModelListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#modelInitialized()
	 */
	public void modelInitialized() {
		timer.start();
	}

	/** 
	 * Defined in <code>com.buynov.model.event.MinesModelListener</code>.
	 * 
	 * @see com.buynov.model.event.MinesModelListener#remainingMinesChanged()
	 */
	public void remainingMinesChanged() {
		remainingMinesLabel.setText(String.valueOf(grid.getModel().getRemainingMines()));
	}

	/** 
	 * Defined in <code>com.buynov.view.dialog.event.SettingsDialogListener</code>.
	 * 
	 * @see com.buynov.view.dialog.event.SettingsDialogListener#changesApplied()
	 */
	public void changesApplied() {
		newGame();
		pack();
		centerFrame();
	}

	/** 
	 * Defined in <code>com.buynov.view.dialog.event.SettingsDialogListener</code>.
	 * 
	 * @see com.buynov.view.dialog.event.SettingsDialogListener#changesCanceled()
	 */
	public void changesCanceled() {
	}

}
