/**
 * Cell: represents a single cell in the Game of Life.
 *
 * @author Tyler Youk
 */
public class Cell {

    // Private instance variables:
    /**
     * Indicates whether the cell is alive or dead.
     */
    private boolean alive;
    /**
     * The age of the cell.
     */
    private int age;

    /**
     * Constructor for Cell.
     * Initializes the cell state.
     * If alive is true, the cell's age is set to 1.
     * Otherwise, the cell age is set to 0.
     *
     * @param alive a boolean value on whether the cell should be alive upon creation.
     */
    public Cell(boolean alive) {
        this.alive = alive;
        if (alive) {
            this.age = 1;
        } else {
            this.age = 0;
        }
    }

    /**
     * Returns state of alive.
     * O(1)
     * 
     * @return true if the cell is alive, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the cell state to alive.
     * Resets the age to 1 if the cell was not previously alive.
     * If the cell is already alive, the age is not reset.
     * O(1)
     */
    public void setAlive() {
        if (!alive) {
            alive = true;
            age = 1;
        }
    }

    /**
     * Returns age of cell.
     * O(1)
     * 
     * @return the age of the cell.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age of the cell.
     * If the provided age is negative, no change is made.
     * O(1)
     *  
     * @param age the new age value to replace.
     */
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    /**
     * Reset the cell, similar to setting it to a dead state.
     * O(1)
     */
    public void reset() {
        alive = false;
        age = 0;
    }
}
