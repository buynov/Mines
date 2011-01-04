/*
 * This interface belongs to Mines project.
 */
package com.buynov.model.event;

/**
 * This interface defines the methods, that the listeners to the <code>MinesModel</code> instances should implement.
 * 
 * @author Stefan Buynov
 */
public interface MinesModelListener {

	/** This method is invoked after the model has been initialized.*/
	public void modelInitialized();
	/** This method is invoked after the model has changed (its visual representation needs to be repainted).*/
	public void modelDirty();
	/** This method is invoked when the game is over.*/
	public void gameOver();
	/** This method is invoked when the remaining mines for the game are changed.*/
	public void remainingMinesChanged();
}
