/*
 * This class belongs to Mines project.
 */
package com.buynov.model;


/**
 * This class holds information for the state of a single tile in the grid.
 * 
 * @author Stefan Buynov
 */
public class MinesNode {
	
	// Specifies if there is a mine on this node.
	private boolean 	mine	= false;
	// Specifies if this node is marked.
	private boolean 	marked 	= false;
	// Specifies if this node is pressed.
	private boolean 	pressed	= false;
	// Specifies if this node is revealed (its content is visible to the user.
	private boolean	revealed = false;
	// Holds the number of mines, that reside arround this node.
	private int		surroundingMinesCount = 0;
	
	// Holds the x coordinate of this node
	private int		x = -1;
	// Holds the y coordinate of this node
	private int		y = -1;
	
	/**
	 * Creates a new node, that has the given coordinates.
	 * 
	 * @param inX
	 * @param inY
	 */
	public MinesNode(int inX, int inY) {
		this.x = inX;
		this.y = inY;
	}

	/**
	 * Returns if this node is marked. 
	 * 
	 * @return	true if this node is marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * Returns if this node contains a mine.
	 * 
	 * @return	true if this node contains a mine
	 */
	public boolean hasMine() {
		return mine;
	}

	/**
	 * Specifies if this node contains a mine or not.
	 * 
	 * @param b	true if this node contains a mine.
	 */
	protected void setMine(boolean b) {
		mine = b;
	}

	/**
	 * Returns if this node is pressed already.
	 * 
	 * @return	true if this node is pressed already
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * Returns if this node is revealed already.
	 * 
	 * @return	true if this node is revealed already
	 */
	public boolean isRevealed() {
		return revealed;
	}

	/**
	 * Returns the number of mines that surround this node.
	 * 
	 * @return	the number of mines that surround this node
	 */
	public int getSurroundingMinesCount() {
		return surroundingMinesCount;
	}

	/**
	 * Specifies the number of mines that surround this node.
	 * 
	 * @param i	the number of mines that surround this node
	 */
	public void setSurroundingMinesCount(int i) {
		surroundingMinesCount = i;
	}

	// Sets the pressed state of this node.
	protected void press() {
		if (isPressed() || isMarked())
			return;
		pressed = true;
	}

	// Marks/unmarks this node (according to its state)
	protected void mark() {
		if (isPressed())
			return;
		marked = !marked;
	}

	// Sets the revealed state of this node.
	protected void reveal() {
		revealed = true;
	}
	
	/**
	 * Returns the x coordinate of this node.
	 * 
	 * @return	the x coordinate of this node
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of this node.
	 * 
	 * @return	the y coordinate of this node
	 */
	public int getY() {
		return y;
	}

}
