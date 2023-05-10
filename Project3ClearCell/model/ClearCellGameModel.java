package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {

	/* Include whatever instance variables you think are useful. */
	private Random random;
	private int score;

	/**
	 * Defines a board with empty cells by passing in the parameters to the super
	 * constructor. Also sets initiates score to 0
	 * 
	 * @param rows   number of rows in board
	 * @param cols   number of columns in board
	 * @param random random number generator to be used during game when rows are
	 *               randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows, cols);
		this.random = random;
		score = 0;
	}

	/**
	 * The game is over when the last row (the one with index equal to
	 * board.length-1) contains at least one cell that is not empty.
	 * 
	 * @return true if game over
	 */
	public boolean isGameOver() {
		for (BoardCell b : board[board.length - 1]) {
			if (b != BoardCell.EMPTY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the player's score. The player should be awarded one point for each
	 * cell that is cleared.
	 * 
	 * @return player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * This method must do nothing in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do the following:
	 * 
	 * 1. Shift the existing rows down by one position. 2. Insert a row of random
	 * BoardCell objects at the top of the board. The row will be filled from left
	 * to right with cells of random color obtained by calling
	 * BoardCell.getNonEmptyRandomBoardCell(). (The Random number generator passed
	 * to the constructor of this class should be passed as the argument to this
	 * method call.)
	 */
	public void nextAnimationStep() {
		if (!isGameOver()) {
			BoardCell[][] boardCopy = new BoardCell[getRows()][getCols()]; // create a copy of array to alter original
																			// array
			for (int i = 0; i < getRows(); i++) {
				try {
					boardCopy[i + 1] = board[i]; // boardCopy is board but shifted down one
				} catch (ArrayIndexOutOfBoundsException e) { // breaks out of loop when boardCopy is full
					break;
				}
			}
			for (int i = 0; i < getCols(); i++) {
				boardCopy[0][i] = BoardCell.getNonEmptyRandomBoardCell(random);
			}
			for (int i = 0; i < getRows(); i++) { // refills original board
				board[i] = boardCopy[i];
			}
		}
	}

	/**
	 * This method is called when the user clicks a cell on the board. If the
	 * selected cell is not empty, it will be set to BoardCell.EMPTY, along with any
	 * adjacent cells that are the same color as this one. (This includes the cells
	 * above, below, to the left, to the right, and all in all four diagonal
	 * directions.) Any cells that are changed to empty result in a point added to
	 * the score.
	 * 
	 * If any rows on the board become empty as a result of the removal of cells
	 * then those rows will "collapse", meaning that all non-empty rows beneath the
	 * collapsing row will shift upward.
	 * 
	 * @param rowIndex index of which row cell is located
	 * @param colIndex index of which column cell is located
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for invalid
	 *                                  row or "Invalid column index" for invalid
	 *                                  column. We check for row validity first.
	 */
	public void processCell(int rowIndex, int colIndex) {
		if (rowIndex >= getRows()) {
			throw new IllegalArgumentException("Invalid row index");
		} else if (colIndex >= getCols()) {
			throw new IllegalArgumentException("Invalid column index");
		} else {
			BoardCell current = getBoardCell(rowIndex, colIndex); // used to compare to surrounding cells
			if (board[rowIndex][colIndex] != BoardCell.EMPTY) {
				setBoardCell(rowIndex, colIndex, BoardCell.EMPTY);
				score++;
				/**
				 * The following conditionals check for surrounding cells and set them to
				 * BoardCell.EMPTY if the color matches the processed cell
				 */
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						try {
							if (board[rowIndex - i][colIndex - j] == current && board[rowIndex - i][colIndex - j] != BoardCell.EMPTY) {
								setBoardCell(rowIndex - i, colIndex - j, BoardCell.EMPTY);
								score++;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							continue;
						}
					}
				}
				
				
				
				
				
//				if (rowIndex > 0 && board[rowIndex - 1][colIndex] == current
//						&& board[rowIndex - 1][colIndex] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex - 1, colIndex, BoardCell.EMPTY);
//					score++;
//				}
//				if (rowIndex < getRows() - 1 && board[rowIndex + 1][colIndex] == current
//						&& board[rowIndex + 1][colIndex] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex + 1, colIndex, BoardCell.EMPTY);
//					score++;
//				}
//				if (colIndex > 0 && board[rowIndex][colIndex - 1] == current
//						&& board[rowIndex][colIndex - 1] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex, colIndex - 1, BoardCell.EMPTY);
//					score++;
//				}
//				if (colIndex < getCols() - 1 && board[rowIndex][colIndex + 1] == current
//						&& board[rowIndex][colIndex + 1] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex, colIndex + 1, BoardCell.EMPTY);
//					score++;
//				}
//				if (rowIndex > 0 && colIndex < getCols() - 1 && board[rowIndex - 1][colIndex + 1] == current
//						&& board[rowIndex - 1][colIndex + 1] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex - 1, colIndex + 1, BoardCell.EMPTY);
//					score++;
//				}
//				if (rowIndex > 0 && colIndex > 0 && board[rowIndex - 1][colIndex - 1] == current
//						&& board[rowIndex - 1][colIndex - 1] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex - 1, colIndex - 1, BoardCell.EMPTY);
//					score++;
//				}
//				if (rowIndex < getRows() - 1 && colIndex > 0 && board[rowIndex + 1][colIndex - 1] == current
//						&& board[rowIndex + 1][colIndex - 1] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex + 1, colIndex - 1, BoardCell.EMPTY);
//					score++;
//				}
//				if (rowIndex < getRows() - 1 && colIndex < getCols() - 1 && board[rowIndex + 1][colIndex + 1] == current
//						&& board[rowIndex + 1][colIndex + 1] != BoardCell.EMPTY) {
//					setBoardCell(rowIndex + 1, colIndex + 1, BoardCell.EMPTY);
//					score++;
//				}
			}
			// checks to see if an entire row is empty
			for (int i = 0; i < getRows(); i++) {
				boolean remove = false; // will remain true if entire row is empty
				for (int j = 0; j < getCols(); j++) {
					if (board[i][j] == BoardCell.EMPTY) {
						remove = true;
					} else {
						remove = false; // breaks out of loop if not empty
						break;
					}
				}

				// if row is empty
				if (remove) {
					BoardCell[][] copy = new BoardCell[getRows()][getCols()]; // copy to alter array
					for (int k = 0, x = 0; k < getRows(); k++) {
						if (k == i) { // skips over empty row
							continue;
						}
						copy[x++] = board[k];
					}
					for (int j = 0; j < getCols(); j++) {
						copy[getRows() - 1][j] = BoardCell.EMPTY; // fills in last row with BoardCell.EMPTY
					}
					for (int k = 0; k < getRows(); k++) { // apply changes to original
						board[k] = copy[k];
					}
				}
			}
		}
	}
}