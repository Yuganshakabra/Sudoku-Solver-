package SudokuSolver;

import java.util.Scanner;

public class SudokuSolver {

    private static final int N = 9; // Size of Sudoku grid

    // Function to print the Sudoku grid
    private static void printGrid(int[][] grid) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Function to check if it's safe to place a number in a cell
    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < N; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 box
        int startRow = row - (row % 3);
        int startCol = col - (col % 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Basic stack implementation
    private static class Stack {
        private int[] rows;
        private int[] cols;
        private int[] nums;
        private int top;

        public Stack() {
            rows = new int[N * N];
            cols = new int[N * N];
            nums = new int[N * N];
            top = -1;
        }
        public boolean isEmpty() {
            return top == -1;
        }
        public void push(int row, int col, int num) {
            if (top < N * N - 1) {
                rows[++top] = row;
                cols[top] = col;
                nums[top] = num;
            }
        }
        public void pop() {
            if (!isEmpty()) {
                top--;
            }
        }
        public int getRow() {
            return rows[top];
        }
        public int getCol() {
            return cols[top];
        }
        public int getNum() {
            return nums[top];
        }
    }
    // Function to solve Sudoku using backtracking and a stack
    private static boolean solveSudoku(int[][] grid) {
        Stack cellStack = new Stack();
        int row;
        int col=0;

        // Find an empty cell
        boolean foundEmpty = false;
        for (row = 0; row < N; row++) {
            for (col = 0; col < N; col++) {
                if (grid[row][col] == 0) {
                    foundEmpty = true;
                    break;
                }
            }
            if (foundEmpty) {
                break;
            }
        }
        // If no empty cell is found, the puzzle is solved
        if (!foundEmpty) {
            return true;
        }
        for (int num = 1; num <= N; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num; // Place the number
                cellStack.push(row, col, num);

                if (solveSudoku(grid)) {
                    return true; // If the next cells are also solved, return true
                }
                grid[row][col] = 0; // If not solved, backtrack
                cellStack.pop();
            }
        }
        return false; // No valid number for this cell, backtrack
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] grid = new int[N][N];

        System.out.println("Enter the Sudoku grid (9x9) with 0 for empty cells:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }
        if (solveSudoku(grid)) {
            System.out.println("Sudoku Solution:");
            printGrid(grid);
        } else {
            System.out.println("No solution exists.");
        }
        scanner.close();
    }
}
