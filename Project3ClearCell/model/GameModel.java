package model;

/**
 * This class represents the logic of a game where a
 * board is updated on each step of the game animation.
 * The board can also be updated by selecting a board cell.
 * 
 * @author Dept of Computer Science, UMCP
 */

public abstract class GameModel {
	protected BoardCell[][] board;
	private int rows, cols;

	/**
	 * Creates a rectangular board of the specified size, filling it with
	 * BoardCell.EMPTY cells.
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 */
	public GameModel(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		board = new BoardCell[rows][cols];
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++){
				board[i][j] = BoardCell.EMPTY;
			}
		}
	}

	/** Returns the number of rows on the board
	 * 
	 * @return number of rows
	 */
	public int getRows() {
		return rows;
	}

	/** Returns the number of columns on the board
	 * 
	 * @return number of columns
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * Returns a reference to the board without making any kind of copy.
	 * 
	 * @return the board
	 */
	public BoardCell[][] getBoard() {
		return board;   // This is done for you. Just return the reference.
	}

	/** Sets the cell at the specified location to value provided.
	 * 
	 * @param rowIndex row to set
	 * @param colIndex column to set
	 * @param boardCell value to assign to the board
	 */
	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		getBoard()[rowIndex][colIndex] = boardCell;
	}
	
	/** Returns the value at a given location of the board
	 * 
	 * @param rowIndex row to access
	 * @param colIndex column to access
	 * @return value of board at the specified location
	 */
	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return getBoard()[rowIndex][colIndex];
	}
	
	/** Provides a string representation of the board 
	 * We have implemented this method for you.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Board(Rows: " + board.length + ", Cols: " + board[0].length + ")\n");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++)
				buffer.append(board[row][col].getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public abstract boolean isGameOver();
	
	public abstract int getScore();

	/**
	 * Advances the animation one step. 
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board
	 * state and the selected cell.
	 * @param rowIndex row that user has clicked
	 * @param colIndex column that user has clicked
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}