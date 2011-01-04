/*
 * This class belongs to Mines project.
 */
package com.buynov.model;

import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;


import com.buynov.MConstants;
import com.buynov.model.event.MinesModelListener;

/**
 * The model for the Mines game. Holds the data for the tiles and provides methods for its manipulation.
 * 
 * @author Stefan Buynov
 */
public class MinesModel implements MConstants {

	// Holds the height (number of tiles in height) for the current Mines game.
	private int	height 	= HEIGHT;
	// Holds the width (number of tiles in width) for the current Mines game.
	private int	width	= WIDTH;
	// Holds the number of mines for the current Mines game.
	private int	minesCount = MINES_COUNT;
	
	// This array holds the tiles (nodes) for the current game.
	private MinesNode[][]	minesNodes 	= null;
	// Holds the listeners, registered to recieve events from this model.
	private Vector			listeners	= new Vector();
	
	/** This value is used when creating new model with the default constructor. */
	public static int HEIGHT		=	DEFAULT_HEIGHT;
	/** This value is used when creating new model with the default constructor. */
	public static int WIDTH		=	DEFAULT_WIDTH;
	/** This value is used when creating new model with the default constructor. */
	public static int MINES_COUNT	=	DEFAULT_MINES_COUNT;
	
	/**
	 * The default constructor. Creates new model with default height, width and mines count.
	 */
	public MinesModel() {
	}

	/**
	 * Creates new model with the given height, width and mines count.
	 * 
	 * @param inHeight			the height (number of tiles in height) for this model
	 * @param inWidth			the width (number of tiles in width) for this model
	 * @param inMinesCount		the number of mines for this model
	 */
	public MinesModel(int inHeight, int inWidth, int inMinesCount) {
		this.height = inHeight;
		this.width = inWidth;
		this.minesCount = inMinesCount;
	}
	
	/**
	 * Initializes this model. In order to initialize the model, we need the coordinates of the tile, 
	 * where the mouse was first pressed, because this tile should not be "mined".
	 * 
	 * @param inX		the x coordinate of the tile, that was first pressed
	 * @param inY		the y coordinate of the tile, that was first pressed
	 */
	public void initModel(int inX, int inY) {
		minesNodes = new MinesNode[width][height];
		// First, create the nodes for this model
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				minesNodes[i][j] = new MinesNode(i, j);
			}
		}
		
		// Generate the positions of the mines
		int[] minePositions = generateMinePositions();
		// Get single dimension index of the tile, that was frst pressed
		int unminedIndex = convertCoordinatesToIndex(inX, inY);
		// Set the nodes, holding mines accordingly.
		for (int i = 0; i < minePositions.length; i++) {
			int currentIndex = minePositions[i];
			// All indexes, bigger than the first pressed index, should be incremented by 1.
			if (currentIndex >= unminedIndex)
				currentIndex++;
			Point p = convertIndexToCoordinates(currentIndex);
			minesNodes[p.x][p.y].setMine(true);
		}

		// Set the number of surrounding mines for the nodes.
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				minesNodes[i][j].setSurroundingMinesCount(getSurroundingMinesCount(i, j));
			}
		}
		
		// Notify listeners that the model has been initialized.
		fireModelInitialized();
	}
	
	// Generated random single dimension indexes for the mines positions.
	// The highest index, that might be generated is smaller with 1 than the highest possible index,
	// because the index that was first pressed should not contain mine, and the indexes bigger than it are
	// incremented by 1. 
	private int[] generateMinePositions() {
		final int MAX_INDEX = height * width - 1;

		int[] result = new int[minesCount];
		int pos = 0;

		for (int i = 0; i < MAX_INDEX; i++) {
			double mineProb = ((float)(minesCount - pos) /(float)(MAX_INDEX - i));
			if (Math.random() <= mineProb) {
				result[pos] = i;
				pos++;
			}
		}

		return result;
	}
	
	// Converts the given coordinates to a single dimension index. 
	private int convertCoordinatesToIndex(int inX, int inY) {
		int result = inY*width + inX;
		return result;
	}
	
	// Converts the given single dimension index to the proper two dimensional coordinates. 
	private Point convertIndexToCoordinates(int inIndex) {
		Point result = new Point();
		result.x = inIndex%width;
		result.y = (int)Math.floor((float)inIndex/(float)width);
		
		return result;
	}
	
	// Returns the number of mines, surrounding the tile with the given coordinates.
	private int getSurroundingMinesCount(int inX, int inY) {
		MinesNode[] nodes = getSurroundingNodes(inX, inY);
		int result = 0;
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].hasMine())
				result++;
		}
		
		return result;
	}
	
	// Returns the number of marks (flags), surrounding the tile with the given coordinates.
	private int getSurroundingMarkedCount(int inX, int inY) {
		MinesNode[] nodes = getSurroundingNodes(inX, inY);
		int result = 0;
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].isMarked())
				result++;
		}
		
		return result;
	}
	
	// Returns the nodes, surrounding the node with the given coordinates.
	private MinesNode[] getSurroundingNodes(int inX, int inY) {
		Vector v = new Vector();
		for (int i = (inX-1); i <= (inX+1); i++) {
			for (int j = (inY-1); j <= (inY+1); j++) {
				if (i >= 0 && j >= 0 && i < width && j < height && !(i == inX && j == inY))
					v.addElement(minesNodes[i][j]);
			}
		}
		
		MinesNode[] result = new MinesNode[v.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (MinesNode)v.elementAt(i);
		}
		
		return result;
	}
	
	/**
	 * This method is called when the node at the specified position has been pressed.
	 * 
	 * @param inX
	 * @param inY
	 */
	public void nodePressed(int inX, int inY) {
		// Initialize the model, if it is not initialized yet
		if (!isModelInitialized())
			initModel(inX, inY);
			
		MinesNode node =  getMinesNodes()[inX][inY];
		node.press();
		
		// If a node with a mine is being pressed, the game is over.
		if (node.hasMine()) {
			fireGameOver();
			return;
		}
		
		// If the node is not surrounded by any mines, press its neighbours as well.
		if (node.getSurroundingMinesCount() == 0) {
			MinesNode[] neighbours = getSurroundingNodes(inX, inY);
			for (int i = 0; i < neighbours.length; i++) {
				if (!neighbours[i].isPressed())
					nodePressed(neighbours[i].getX(), neighbours[i].getY());
			}
		}
		
		// Check if the game is not finished.
		if (isGameFinished()) {
			pressUnmarkedNodes();
			fireGameOver();
		}
		
		// Notify listeners that this model has been changed (its visual representation needs to be repainted).
		fireModelDirty();
	}
	
	/**
	 * This method is called when the node at the specified position has been marked or unmarked.
	 * 
	 * @param inX
	 * @param inY
	 */
	public void nodeMarked(int inX, int inY) {
		// Initialize the model, if it is not initialized yet
		if (!isModelInitialized())
			initModel(inX, inY);

		getMinesNodes()[inX][inY].mark();
		fireRemainingMinesChanged();
		// Notify listeners that this model has been changed (its visual representation needs to be repainted).
		fireModelDirty();

		// Check if the game is not finished.
		if (isGameFinished()) {
			pressUnmarkedNodes();
			fireGameOver();
		}
	}
	
	/**
	 * This method invokes the nodePressed method on the nodes, surrounding the node a the given position, 
	 * that are not pressed already.
	 * 
	 * @param inX
	 * @param inY
	 */
	public void pressSurroundingNodes(int inX, int inY) {
		MinesNode node = getMinesNodes()[inX][inY];
		if (!node.isPressed() || getSurroundingMarkedCount(inX, inY) != node.getSurroundingMinesCount())
			return;
			
		MinesNode[] neighbours = getSurroundingNodes(inX, inY);
		for (int i = 0; i < neighbours.length; i++) {
			if (!neighbours[i].isPressed() && !neighbours[i].isMarked())
				nodePressed(neighbours[i].getX(), neighbours[i].getY());
		}
		
		// Check if the game is not finished.
		if (isGameFinished()) {
			pressUnmarkedNodes();
			fireGameOver();
		}
		
		// Notify listeners that this model has been changed (its visual representation needs to be repainted).
		fireModelDirty();
	}
	
	/**
	 * Reveals all nodes in this model (the state of the nodes is visible, no matter if they are pressed or not).
	 * This method is usually called when the game is over.
	 */
	public void revealNodes() {
		if (minesNodes == null)
			return;
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				minesNodes[i][j].reveal();
			}
		}
		
		// Notify listeners that this model has been changed (its visual representation needs to be repainted).
		fireModelDirty();
	}
	
	/**
	 * Performs nodePressed on all nodes, that are not pressed, and not marked.
	 * This method is usually called when the number of marked tiles is equal to the number of mines in the model (all mines have been marked).
	 */
	public void pressUnmarkedNodes() {
		if (minesNodes == null)
			return;
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				if (!minesNodes[i][j].isPressed() && !minesNodes[i][j].isMarked())
					minesNodes[i][j].press();
			}
		}
		
		// Notify listeners that this model has been changed (its visual representation needs to be repainted).
		fireModelDirty();
	}
	
	/**
	 * Marks all nodes, that are not pressed.
	 * This method is usually called when the number of unpressed and unmarked tiles is equal to the number of remaining mines.
	 */
	public void markUnpressedNodes() {
		if (minesNodes == null)
			return;
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				if (!minesNodes[i][j].isPressed() && !minesNodes[i][j].isMarked())
					minesNodes[i][j].mark();
			}
		}
		
		fireModelDirty();
	}
	
	/**
	 * Returns the number of remaining mines.
	 * 
	 * @return	the number of remaining mines
	 */
	public int getRemainingMines() {
		int result = minesCount;
		
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				if (minesNodes[i][j].isMarked())
					result--;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns the number of unpressed and unmarked nodes.
	 * 
	 * @return	the number of unpressed and unmarked nodes
	 */
	public int getUnpressedNodes() {
		int result = 0;
		
		for (int i = 0; i < minesNodes.length; i++) {
			for (int j = 0; j < minesNodes[i].length; j++) {
				if (!minesNodes[i][j].isPressed() && !minesNodes[i][j].isMarked())
					result++;
			}
		}
		
		return result;
	}
	
	/**
	 * Checks if the game is finished (all mines have been discovered).
	 * 
	 * @return	true if the game is finished
	 */
	public boolean isGameFinished() {
		int remainingMines = getRemainingMines();
		int unpressedNodes = getUnpressedNodes();
		if ((remainingMines == unpressedNodes) && unpressedNodes > 0) {
			markUnpressedNodes();
			fireRemainingMinesChanged();
			return true;
		}
		if (remainingMines == 0 || (remainingMines == unpressedNodes))
			return true;
		else return false;
	}
	
	/**
	 * Returns the height (number of tiles in height) for the current Mines game.
	 * 
	 * @return	the height (number of tiles in height) for the current Mines game.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the number of mines for the current Mines game.
	 * 
	 * @return	the number of mines for the current Mines game.
	 */
	public int getMinesCount() {
		return minesCount;
	}

	/**
	 * Returns the width (number of tiles in width) for the current Mines game.
	 * 
	 * @return	the width (number of tiles in width) for the current Mines game.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns two dimensional array with the nodes for this model.
	 * 
	 * @return	two dimensional array with the nodes for this model.
	 */
	public MinesNode[][] getMinesNodes() {
		return minesNodes;
	}

	/**
	 * Checks if the model has been initialized.
	 * 
	 * @return	true if the model is initialized already
	 */
	public boolean isModelInitialized() {
		return (minesNodes != null);
	}

	/**
	 * Registers the given <code>MinesModelListener</code> to receive events from this model.
	 * 
	 * @param l	the listener to be added
	 */
	public void addMinesModelListener(MinesModelListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}
	
	/**
	 * Unregisters the given <code>MinesModelListener</code> from this model.
	 * 
	 * @param l
	 */
	public void removeMinesModelListener(MinesModelListener l) {
		listeners.remove(l);
	}
	
	// Invokes modelInitialized method for the registered listeners.
	private void fireModelInitialized() {
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			MinesModelListener l = (MinesModelListener) en.nextElement();
			l.modelInitialized();
		}
	}

	// Invokes modelDirty method for the registered listeners.
	private void fireModelDirty() {
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			MinesModelListener l = (MinesModelListener) en.nextElement();
			l.modelDirty();
		}
	}

	// Invokes gameOver method for the registered listeners.
	private void fireGameOver() {
		revealNodes();
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			MinesModelListener l = (MinesModelListener) en.nextElement();
			l.gameOver();
		}
	}

	// Invokes remainingMinesChanged method for the registered listeners.
	private void fireRemainingMinesChanged() {
		for (Enumeration en =listeners.elements(); en.hasMoreElements();) {
			MinesModelListener l = (MinesModelListener) en.nextElement();
			l.remainingMinesChanged();
		}
	}
}
