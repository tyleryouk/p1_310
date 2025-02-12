// TO DO: add your implementation and JavaDocs.
// some javadocs are already done. if you add helper methods, be
// sure to write javadocs for those.

// big-O requirements are listed if they exist.
// if no requirement is listed, you may do as you wish.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Point;
import java.util.HashMap;

/**
 * The simulator: manages state of cell in a grid.
 * Implmentation with HashMap in Simulation2.java (replacing DynamicArray with HashMap)
 * 
 * @author Tyler Youk   
 */
public class Simulation2 {
    /**
     * The grid that holds the cell data.
     * YOU MUST USE THIS.
     * Automated testing will be done on this variable directly.
     */
    private HashMap<Point, Cell> grid;

    /**
     * The number of rows the grid has.
     */
    private int rows;

    /**
     * The number of cols the grid has.
     */
    private int cols;

    /**
     * The number of generations this sim has gone through.
     */
    private int generations;

    /**
     * Main constructor.
     * 
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     */
    public Simulation2(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.generations = 0;
        initializeGrid();
    }

    /**
     * Helper method: initailize the grid of cells.
     */
    private void initializeGrid() {
        grid = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid.put(new Point(i, j), new Cell(false));
            }
        }
    }

    /**
     * DO NOT CHANGE THIS, FOR GRADING PURPOSE ONLY.
     * 
     * @return grid for automatic testing
     */
    public HashMap<Point, Cell> getGrid() {
        return grid;
    }

    /**
     * This is called when the user manually interacts with the grid.
     * Sets the cell to Alive
     * 
     * @param row the row where the action happened
     * @param col the column where the action happened
     */
    public void toggleCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }
        Cell cell = grid.get(new Point(row, col));
        if (cell != null && !cell.isAlive()) {
            cell.setAlive();
        }
    }

    /**
     * Logic for evolution of grid by one generation.
     */
    public void evolve() {
        HashMap<Point, Cell> newGrid = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell currentCell = grid.get(new Point(i, j));
                int liveNeighbors = countLiveNeighbors(i, j);
                Cell newCell;
                if (currentCell.isAlive()) {
                    // alive cell dies
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newCell = new Cell(false);
                    } else { // alive cell survives
                        newCell = new Cell(true);
                        newCell.setAge(currentCell.getAge() + 1);
                    }
                } else {
                    // dead cell comes back to life
                    if (liveNeighbors == 3) {
                        newCell = new Cell(true);
                    } else {
                        newCell = new Cell(false);
                    }
                }
                newGrid.put(new Point(i, j), newCell);
            }
        }
        grid = newGrid;
        generations++;
    }

    /**
     * Helper method for evolve to count the live neighbors of a specific cell at row/col.
     *
     * @param row the row of the cell in question.
     * @param col the column of the cell in question.
     * @return the number of live neighbors.
     */
    public int countLiveNeighbors(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // skip self
                int r = row + dr;
                int c = col + dc;
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    Cell neighbor = grid.get(new Point(r, c));
                    if (neighbor != null && neighbor.isAlive()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Reset all cells in the grid. Generation count should also reset to zero.
     */
    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = grid.get(new Point(i, j));
                if (cell != null) {
                    cell.reset();
                }
            }
        }
        generations = 0;
    }

    /**
     * Returns the count of live cells in the grid.
     * 
     * @return the number of alive cells.
     */
    public int getAliveCells() {
        int aliveCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = grid.get(new Point(i, j));
                if (cell != null && cell.isAlive()) {
                    aliveCount++;
                }
            }
        }
        return aliveCount;
    }

    /**
     * Returns the average age of all alive cells in the grid.
     *
     * @return the average age, or 0.0 if there are no alive cells.
     */
    public double getAverageAge() {
        int totalAge = 0;
        int aliveCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = grid.get(new Point(i, j));
                if (cell != null && cell.isAlive()) {
                    totalAge += cell.getAge();
                    aliveCount++;
                }
            }
        }
        return aliveCount == 0 ? 0.0 : (double) totalAge / aliveCount;
    }

    /**
     * Returns the maximum age of all alive cells in the grid.
     *
     * @return the maximum age among alive cells.
     */
    public int getMaxAge() {
        int maxAge = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = grid.get(new Point(i, j));
                if (cell != null && cell.isAlive() && cell.getAge() > maxAge) {
                    maxAge = cell.getAge();
                }
            }
        }
        return maxAge;
    }

    /**
     * Returns the number of generations the simulation has gone through.
     *
     * @return the current generation count.
     */
    public int getGenerations() {
        return generations;
    }

    /**
     * Parsing header then RLE data (debugging for both).
     * Using custom debugging with a helper log class (commented out).
     * Logic to parse RLE data and apply it to the simulation grid.
     *
     * @param lines a DynamicArray of strings representing lines of the RLE file.
     */
    public void parseRle(DynamicArray<String> lines) {
        // 1. Parse header to get pattern dimensions.
        int patternWidth = 0, patternHeight = 0;
        boolean headerFound = false;
        for (String line : lines) {
            if (line.startsWith("x")) {
                String[] tokens = line.split(",");
                for (String token : tokens) {
                    token = token.trim();
                    if (token.startsWith("x")) {
                        patternWidth = Integer.parseInt(token.split("=")[1].trim());
                    } else if (token.startsWith("y")) {
                        patternHeight = Integer.parseInt(token.split("=")[1].trim());
                    }
                }
                headerFound = true;
                break;
            }
        }
        if (!headerFound) {
            // DebugLogger.println("RLE header not found.");
            return;
        }
        // DebugLogger.println("Parsed dimensions: width = " + patternWidth + ", height
        // = " + patternHeight);

        // 2. Concatenate RLE data from remaining lines (skip header and comments).
        StringBuilder patternBuilder = new StringBuilder();
        boolean headerSkipped = false;
        for (String line : lines) {
            if (!headerSkipped && line.startsWith("x")) {
                headerSkipped = true;
                continue;
            }
            if (line.startsWith("#"))
                continue;
            patternBuilder.append(line.trim());
        }
        String patternString = patternBuilder.toString();

        // 3. Decode patternString into a boolean 2D array.
        boolean[][] pattern = new boolean[patternHeight][patternWidth];
        for (int i = 0; i < patternHeight; i++) {
            for (int j = 0; j < patternWidth; j++) {
                pattern[i][j] = false;
            }
        }

        int count = 0;
        int currentRow = 0, currentCol = 0;
        for (int i = 0; i < patternString.length(); i++) {
            char ch = patternString.charAt(i);
            if (Character.isDigit(ch)) {
                count = count * 10 + (ch - '0');
                // DebugLogger.println("Digit found: " + ch + ", updated count: " + count);
            } else {
                if (count == 0)
                    count = 1;
                if (ch == 'o') {
                    for (int j = 0; j < count; j++) {
                        if (currentRow < patternHeight && currentCol < patternWidth) {
                            pattern[currentRow][currentCol] = true;
                        } else {
                            // DebugLogger.println("Skipping live cell at invalid index: row=" + currentRow
                            // + " col=" + currentCol);
                        }
                        currentCol++;
                    }
                    // DebugLogger.println("Processed 'o': count=" + count + " at row=" + currentRow
                    // + ", new currentCol=" + currentCol);
                } else if (ch == 'b') {
                    currentCol += count;
                    // DebugLogger.println("Processed 'b': count=" + count + " at row=" + currentRow
                    // + ", new currentCol=" + currentCol);
                } else if (ch == '$') {
                    int rowIncrement = (count > 0 ? count : 1);
                    // DebugLogger.println("Encountered '$': count=" + rowIncrement + " at row=" +
                    // currentRow + ", currentCol before newline=" + currentCol);
                    currentRow += rowIncrement;
                    currentCol = 0;
                    // DebugLogger.println("After '$': new currentRow=" + currentRow + ", reset
                    // currentCol=" + currentCol);
                } else if (ch == '!') {
                    // DebugLogger.println("Encountered '!': terminating pattern parse at row=" +
                    // currentRow);
                    break;
                } else {
                    // DebugLogger.println("Unexpected character encountered: " + ch);
                }
                count = 0;
            }
        }
        // DebugLogger.println("Finished decoding: final currentRow=" + currentRow + ",
        // currentCol=" + currentCol);

        // 4. Allocate the simulation grid to be exactly rows x cols.
        grid = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid.put(new Point(i, j), new Cell(false));
            }
        }

        // 5. Determine offsets for centering or cropping.
        int simRows = rows, simCols = cols;
        int simStartRow, simStartCol, patternCropRow, patternCropCol;
        if (simRows >= patternHeight && simCols >= patternWidth) {
            // Center the pattern within the simulation grid.
            simStartRow = (simRows - patternHeight) / 2;
            simStartCol = (simCols - patternWidth) / 2;
            patternCropRow = 0;
            patternCropCol = 0;
        } else {
            // If simulation grid is smaller, crop the pattern to its center.
            simStartRow = 0;
            simStartCol = 0;
            patternCropRow = (patternHeight - simRows) / 2;
            patternCropCol = (patternWidth - simCols) / 2;
        }

        // 6. Apply the (cropped/centered) pattern to the simulation grid.
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                int prow, pcol;
                if (simRows >= patternHeight && simCols >= patternWidth) {
                    prow = i - simStartRow;
                    pcol = j - simStartCol;
                } else {
                    prow = i + patternCropRow;
                    pcol = j + patternCropCol;
                }
                if (prow >= 0 && prow < patternHeight && pcol >= 0 && pcol < patternWidth) {
                    if (pattern[prow][pcol]) {
                        grid.get(new Point(i, j)).setAlive();
                    } else {
                        grid.get(new Point(i, j)).reset();
                    }
                }
            }
        }
    }

    /**
     * Translates a boolean 2d array into actual cell data in our grid.
     *
     * @param pattern a 2D boolean array representing the pattern (true for alive,
     *                false for dead).
     */
    public void applyPatternToGrid(boolean[][] pattern) {
        reset();
        int startRow = rows / 2 - pattern.length / 2;
        int startCol = cols / 2 - pattern[0].length / 2;
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                if (pattern[i][j]) {
                    int gridRow = startRow + i;
                    int gridCol = startCol + j;
                    if (gridRow >= 0 && gridRow < rows && gridCol >= 0 && gridCol < cols) {
                        grid.get(new Point(gridRow, gridCol)).setAlive();
                    }
                }
            }
        }
    }

    /**
     * This is provided as an optional way to load RLE files if you want to do
     * testing in main.
     * This is basically a copy of the loadRleFile method in GameOfLife class, but
     * without any GUI stuff.
     * 
     * @param filename relative path to the file
     */
    private void loadRleFile(String filename) {
        File file = new File(filename);
        try {
            DynamicArray<String> lines = new DynamicArray<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#")) { // Ignore comments
                        lines.add(line);
                    }
                }
                parseRle(lines);
            }
        } catch (IOException e) {
            // do nothing
        }
    }

    /**
     * Gets the number of rows in the simulation grid.
     * 
     * @return the number of rows in the grid
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows in the simulation grid.
     * 
     * @param rows the new number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Gets the number of columns in the simulation grid.
     * 
     * @return the number of columns in the grid
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns in the simulation grid.
     * 
     * @param cols the new number of columns
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * The main method for testing the Simulation class.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String args[]) {
        // create a new sim using Simulation2
        Simulation2 sim = new Simulation2(50, 50);

        // make the cell at 1,1 to be alive
        sim.toggleCell(1, 1);

        // if the cell at 1,1 is alive, you did good
        if (sim.grid.get(new Point(1, 1)).isAlive() == true) {
            System.out.println("Yay 1");
        }
        // the number of alive cells should be 1
        if (sim.getAliveCells() == 1) {
            System.out.println("Yay 2");
        }

        // progress the sim by one generation
        sim.evolve();

        // the cell at 1,1 should now be dead (starvation rule)
        if (sim.grid.get(new Point(1, 1)).isAlive() == false) {
            System.out.println("Yay 3");
        }
        // the number of alive cells should be zero
        if (sim.getAliveCells() == 0) {
            System.out.println("Yay 4");
        }
        // generation counter should have advanced
        if (sim.getGenerations() == 1) {
            System.out.println("Yay 5");
        }

        // write more tests as needed!
    }
}

