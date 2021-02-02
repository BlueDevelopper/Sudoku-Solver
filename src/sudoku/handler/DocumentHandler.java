package sudoku.handler;

import sudoku.field.Field;

import javax.swing.*;
import java.io.*;

/**
 * Implements load and save routines.
 */
public class DocumentHandler {
    private String fileName = null;

    /**
     * Loads data into the specified field.
     *
     * @param field the field to populated with the loaded data
     * @throws IOException if loading fails or no file name has been set before
     * @see #setFilename(String)
     */
    public void load(Field field) throws IOException {
        String inputString = "";
        boolean testSudokuFile = false; //Flag der auf true geht, wenn die Textdatei ein gueltiges Sudokufeld enthaelt
        int testCounter = 0; //Zum zaehlen der Laenge der Textdatei

        if (isFilenameSet()) {
            FileReader reader = new FileReader(fileName);
            int dataByte = reader.read();
            while (dataByte != -1) {
                char dataChar = (char) dataByte; //Byte zu Char konversation
                dataByte = reader.read();
                inputString = inputString + dataChar;
            }
            //Entfernt alle Whitespaces/Absaetze
            String parsedString = inputString.replaceAll("\\s+", "");
            for (int i = 0; i < parsedString.length(); i++) {
                char parsedHelpChar = parsedString.charAt(i);
                if ((Character.getNumericValue(parsedHelpChar) >= 1 && Character.getNumericValue(parsedHelpChar) <= 9)
                        || parsedHelpChar == '_') {
                    testCounter++;
                }
            }
            if (testCounter == 81) { //nur wenn das Feld die Länge 81 hat, wird das Einlesen ausgeführt
                testSudokuFile = true;
            }
            if (testSudokuFile) {
                int counter = 0;
                for (int y = 0; y < 9; y++) {
                    for (int x = 0; x < 9; x++) {
                        char parsedChar = parsedString.charAt(counter);
                        int i = Character.getNumericValue(parsedChar);
                        if (parsedChar == '_') {
                            field.setCellValue(x, y, 0); //Damit beim oeffnen, dass Feld ueberschrieben wird
                            counter++;
                        } else {
                            field.setCellValue(x, y, 0); //Damit beim oeffnen, dass Feld ueberschrieben wird
                            field.setCellValue(x, y, i); //Danach Aenderung eintragen
                            counter++;
                        }
                    }
                }
                field.setAllCellsModified(false);
            } else {
                clearFilename(); //Wenn das oeffnen nicht gekalppt hat, muss der Dateiname zurueckgesetzt werden
                JOptionPane.showMessageDialog(null, "Kein sudoku.main.Sudoku-Feld enthalten", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            throw new IOException("Keine Datei ausgewaehlt");
        }
    }

    /**
     * Saves the specified field to a file.
     * On success, the modified state of all cells is set to false.
     *
     * @param field the field to save
     * @throws IOException if saving fails or the file name has not been set before
     * @see #setFilename(String)
     */
    public void save(Field field) throws IOException {
        if (isFilenameSet()) {
            String output = buildString(field);
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] strToBytes = output.getBytes();
            fout.write(strToBytes);
            fout.close();
            field.setAllCellsModified(false);
        } else {
            throw new IOException("Bitte Datei Auswaehlen");
        }
    }

    /**
     * Hilfsmethode zum auslesen der Feldelemente.
     * @param field Uebergebenes Feld um den String zusammenzubauen
     * @return Den fertigen String
     */
    private String buildString(Field field) {
        String out = "";
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (field.getCellValue(x, y) == 0 && x < 8) {
                    out = out + "_ ";
                } else if (field.getCellValue(x, y) != 0 && x < 8) {
                    out = out + field.getCellValue(x, y) + " ";
                } else { //Nur damit am Ende keine Whitespaces vorkommen
                    if (field.getCellValue(x, y) == 0) {
                        out = out + "_";
                    } else {
                        out = out + field.getCellValue(x, y);
                    }
                }
            }
            out = out + System.lineSeparator(); //Bei jedem Zeilenwechsel, eine Newline beginnen...
        }
        return out;
    }

    /**
     * Sets the file name for loading and saving data.
     *
     * @param filename the file name
     */
    public void setFilename(String filename) {
        this.fileName = filename;
    }

    /**
     * Clears the file name.
     */
    public void clearFilename() {
        fileName = null;
    }

    /**
     * Checks whether a file name has been set.
     *
     * @return true if a file name is known, false otherwise
     */
    public boolean isFilenameSet() {
        if (fileName != null) { //Mit dem Dateinamen wird ueberprueft, ob eine Datei ausgewaehlt ist
            return true;
        } else {
            return false;
        }
    }
}
