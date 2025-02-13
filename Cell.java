/**
 * Cell: represents a single cell in the Game of Life.
 *
 * @author Tyler Youk
 */
import java.awt.Color;

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

    // New color field to store the cell's display color.
    private Color color;

    /**
     * The default color for alive cells. This field can be updated at runtime.
     */
    public static Color defaultAliveColor = new Color(128, 0, 128);

    /**
     * Constructor for Cell.
     * Initializes the cell state.
     * [EXTRA CREDIT] Added color field to store the cell's display color.
     * If alive is true, age is 1 and color purple.
     * Otherwise, age is 0 and color white.
     *
     * @param alive a boolean value on whether the cell should be alive upon creation.
     */
    public Cell(boolean alive) {
        this.alive = alive;
        if (alive) {
            this.age = 1;
            this.color = Cell.defaultAliveColor; // Use the configurable default alive color.
        } else {
            this.age = 0;
            this.color = Color.WHITE; // Default color for dead cell.
        }
    }

    /**
     * Returns the current color of the cell.
     * [EXTRA CREDIT]
     * 
     * @return the color of the cell.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Can set new color in GameOfLife.
     * [EXTRA CREDIT]
     * 
     * @param color the new color for the cell.
     */
    public void setColor(Color color) {
        this.color = color;
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
     * Also sets the cell color to purple.
     * O(1)
     */
    public void setAlive() {
        if (!alive) {
            alive = true;
            age = 1;
            this.color = Cell.defaultAliveColor; // Use the current default alive color.
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
     * Reset the cell, marking it as dead.
     * Sets the color to white.
     * O(1)
     */
    public void reset() {
        alive = false;
        age = 0;
        this.color = Color.WHITE; // reset color to white on dead.
    }
}
