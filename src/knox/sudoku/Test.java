package knox.sudoku;

public class Test {
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        sudoku.load("easy1.txt");
        System.out.println(sudoku);

        System.out.println(sudoku.getLegalValues(6, 8));
    }
}
