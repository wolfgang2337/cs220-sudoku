package knox.sudoku;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 
 * This is the MODEL class. This class knows all about the
 * underlying state of the Sudoku game. We can VIEW the data
 * stored in this class in a variety of ways, for example,
 * using a simple toString() method, or using a more complex
 * GUI (Graphical User Interface) such as the SudokuGUI 
 * class that is included.
 * 
 * @author jaimespacco
 *
 */
public class Sudoku {
	int[][] board = new int[9][9];
	
	public int get(int row, int col) {
		// TODO: check for out of bounds
		return board[row][col];
	}
	
	public void set(int row, int col, int val) {
		// TODO: make sure val is legal
		board[row][col] = val;
	}
	
	public boolean isLegal(int row, int col, int val) {
		Set<Integer> legalNums = (Set<Integer>) getLegalValues(row, col);
		return legalNums.contains(val);
	}
	
	public Collection<Integer> getLegalValues(int row, int col) {
		// get all values that do not occur in either the row or col given, or the 3 x 3 area it is in
		Set<Integer> result = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

		//remove from rows and cols
		for(int i = 0; i < 9; i++) {
			result.remove(board[row][i]);
			result.remove(board[i][col]);
		}

		//remove from 3x3 area
		int rowStart = row / 3 * 3;
		int colStart = col / 3 * 3;
		for(int r=rowStart; r<rowStart+3; r++) {
			for(int c=colStart; c<colStart+3; c++) {
				result.remove(board[r][c]);
			}
		}

		return result;
	}
	
/**

_ _ _ 3 _ 4 _ 8 9
1 _ 3 2 _ _ _ _ _
etc


0 0 0 3 0 4 0 8 9

 */
	public void load(File file) {
		try {
			Scanner scan = new Scanner(file);
			for (int r=0; r<9; r++) {
				for (int c=0; c<9; c++) {
					int val = scan.nextInt();
					board[r][c] = val;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void load(String filename) {
		load(new File(filename));
	}
	
	/**
	 * Return which 3x3 grid this row is contained in.
	 * 
	 * @param row
	 * @return
	 */
	public int get3x3row(int row) {
		return row / 3;
	}
	
	/**
	 * Convert this Sudoku board into a String
	 */
	public String toString() {
		String result = "";
		for (int r=0; r<9; r++) {
			for (int c=0; c<9; c++) {
				int val = get(r, c);
				if (val == 0) {
					result += "_ ";
				} else {
					result += val + " ";
				}
			}
			result += "\n";
		}
		return result;
	}

	public String toFileString() {
		String result = "";
		for (int r=0; r<9; r++) {
			for (int c=0; c<9; c++) {
				int val = get(r, c);
				result += val + " ";
			}
			result += "\n";
		}
		return result;
	}
	
	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		sudoku.load("easy1.txt");
		System.out.println(sudoku);
		
		Scanner scan = new Scanner(System.in);
		while (!sudoku.gameOver()) {
			System.out.println("enter value r, c, v :");
			int r = scan.nextInt();
			int c = scan.nextInt();
			int v = scan.nextInt();
			sudoku.set(r, c, v);

			System.out.println(sudoku);
		}
	}

	public boolean gameOver() {
		for (int[] row : board) {
			for (int val : row) {
				if(val == 0) return false;
			}
		}
		return true;
	}

	public boolean didIwin() {
		if (!gameOver())
			return false;
		for(int r=0; r<9; r++) {
			for (int c=0; c<9; c++) {
				if (isLegal(r, c, board[r][c])) //!isLegal(r, c, board[r][c]) does not work for this as once the board is full, every square has no legal values
					return false;
			}
		}
		return true;
	}

	public boolean isBlank(int row, int col) {
		return board[row][col] == 0;
	}

}
