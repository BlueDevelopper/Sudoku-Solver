package sudoku.field;

import javax.swing.*;
import java.awt.*;

/**
 * A panel rendering the sudoku.main.Sudoku field.
 * <p>
 * Cells are identified by zero-based indices from top-left to bottom-right.
 */
public final class Field extends JPanel {
    private SudokuTextField[][] array = new SudokuTextField[9][9];
    /**
     * Constructs a new 9x9 sudoku.main.Sudoku grid.
     */
    public Field() {
        setLayout(null); //Absolutes Layout
        setBackground(Color.black); //Fuellt die Linien
        int varx = 2; //x-Verschiebung
        int vary = 2;

        //Counter und Flag zum zaehlen der Abstaende (1 und 2 Pixel)
        boolean flagx = true;
        boolean flagy = true;
        int countx = 0;
        int county = 0;

        for (int y = 0; y < 9; y++) {
            varx = 2;
            for (int x = 0; x < 9; x++) {
                array[y][x] = new SudokuTextField();
                add(array[y][x]);
                array[y][x].setBounds(varx, vary, 40, 40);

                if (flagx) {
                    varx = varx + 41; //Duenne Linie
                    countx++;
                    if (countx == 2) {
                        countx = 0;
                        flagx = false;
                    }
                } else {
                    varx = varx + 42; //Dicke Linie
                    flagx = true;
                }
            }
            if (flagy) {
                vary = vary + 41;
                county++;
                if (county == 2) {
                    county = 0;
                    flagy = false;
                }
            } else {
                vary = vary + 42;
                flagy = true;
            }
        }
        setPreferredSize(new Dimension(374, 374));
    }

    /**
     * Paints the grid and all contained cells.
     *
     * @param graphics the graphics context
     */
    @Override
    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        //Wird nicht benoetigt
        super.paintChildren(graphics);
    }

    /**
     * Checks whether a cell is empty.
     *
     * @param x horizontal position
     * @param y vertical position
     * @return true if the cell is empty, false otherwise
     */
    public boolean isCellEmpty(int x, int y) {
        if (array[y][x].getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns value of a specific cell.
     *
     * @param x horizontal position
     * @param y vertical position
     * @return the cell's value
     */
    public int getCellValue(int x, int y) {
        int v = array[y][x].getValue();
        return v;
    }

    /**
     * Sets the value of a specific cell.
     *
     * @param x horizontal position
     * @param y vertical position
     * @param v the cells value
     * @throws IllegalArgumentException if v is not between 0 and 9
     */
    public void setCellValue(int x, int y, int v) {
        if (v >= 0 && v <= 9) {
            array[y][x].setValue(v);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Clears the value of a specific cell.
     *
     * @param x horizontal position
     * @param y vertical position
     */
    public void clearCellValue(int x, int y) {
        array[y][x].setValue(0);
    }

    /**
     * Sets the modified state of a specific cell.
     *
     * @param x        horizontal position
     * @param y        vertical position
     * @param modified the modified state
     */
    public void setCellModified(int x, int y, boolean modified) {
        array[y][x].setModified(modified);
    }

    /**
     * Checks the modified state of a specific cell.
     *
     * @param x horizontal position
     * @param y vertical position
     * @return the modified state
     */
    public boolean isCellModified(int x, int y) {
        return array[y][x].isModified();
    }

    /**
     * Sets the modified state of all cells.
     *
     * @param modified the modified state
     */
    public void setAllCellsModified(boolean modified) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                array[y][x].setModified(modified);
            }
        }
    }

    /**
     * Checks whether any cell is modified.
     *
     * @return true if any cell is modified, false otherwise
     */
    public boolean isAnyCellModified() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (array[y][x].isModified()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clears all cells.
     */
    public void clear() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                array[y][x].setValue(0);
            }
        }
    }

    /**
     * Returns a square identified by square coordinates.
     * <p>
     * Cells within the square are identified by the same coordinate system.
     *
     * @param x horizontal position from 0 to 2
     * @param y vertical position from 0 to 2
     * @return a two-dimensional array containing the square cell values
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */
    public int[][] getSquare(int x, int y) {
        if ((x >= 0 && x <= 2) && (y >= 0 && y <= 2)) {
            int[][] array = new int[3][3];
            int newX = 0; //neues x, weil das x gespeichert werden muss, um die Variable richtig zurückzusetzen
            //Je nachdem, welches Quadrat man haben will, müssen Koordinaten angepasst werden
            newX = x * 3;
            y = y * 3;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    array[j][i] = this.array[y][newX].getValue(); //Achtung: Hier vertauscht
                    newX++;
                }
                newX = x * 3; //Zuruecksetzen
                y++;
            }
            return array;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns an entire row.
     *
     * @param y the row position
     * @return an array containing the row values
     * @throws IllegalArgumentException if y is not between 0 and 8
     */
    public int[] getRow(int y) {
        int[] intArray = new int[9];
        if (y >= 0 && y <= 8) {
            for (int x = 0; x < 9; x++) {
                intArray[x] = getCellValue(x, y);
            }
            return intArray;
        } else {
            throw new IllegalArgumentException("Eingabe nicht zwischen 0 und 8");
        }
    }

    /**
     * Returns an entire column.
     *
     * @param x the column position
     * @return an array containing the column values
     * @throws IllegalArgumentException if x is not between 0 and 8
     */
    public int[] getColumn(int x) {
        int[] intArray = new int[9];
        if (x >= 0 && x <= 8) {
            for (int y = 0; y < 9; y++) {
                intArray[y] = getCellValue(x, y);
            }
            return intArray;
        } else {
            throw new IllegalArgumentException("Eingabe nicht zwischen 0 und 8");
        }
    }
}
