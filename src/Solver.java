/**
 * Implements the backtracking algorithm for solving the Sudoku.
 */
public final class Solver {
    private int[] rowArray = new int[9];

    /**
     * Attempts to solve the given Sudoku field.
     *
     * The solution, if any, is directly entered into the field.
     * All solved fields will be in modified state.
     * The already given fields are left untouched.
     *
     * @param f the field to solve
     * @return true if a solution could be found, false if the field is unsolvable
     */
    public boolean solve(Field f) {
        //Wenn die Regeln nicht eingehalten werden, direkt false returnen
        if (check(f)) {
            //Funktion die die Koordinaten des letzten Feldes ermittelt
            int x = 0;
            int y = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (f.getCellValue(i, j) == 0) {
                        x = i;
                        y = j;
                    }
                }
            }
            if (solveSudokuWithBackTracking(f)) {
                for (int num = 1; num <= 9; num++) { //Korrigiert das letzte Feld, wenn der Algorithmus erfolgreich war
                    f.setCellValue(x, y, num);
                    if (check(f)) { //der Algorithmus schreibt in die letzte Zelle immer eine "1". Deshalb die Korrektur
                        return true;
                    } else {
                        f.setCellValue(x, y, 0);
                    }
                }
            }
        }
        return false; //Wird nichts gefunden, dann auch false returnen
    }

    /**
     * Loest das Feld mit dem Backtracking Algorithmus. (Inspiriert von "Geeks-For-Geeks").
     * @param f zu loesendes Feld
     * @return Wahr, wenn es eine Loesung gibt
     */
    private boolean solveSudokuWithBackTracking(Field f) {
        boolean noSolutionForField = true; //Variable die sich merkt ob eine Loesung fuer ein Feld gefunden wurde
        /*
         Der Algorithmus geht immer wieder rekursiv in diese Funktion und sucht das leere Feld auf dem er Arbeiten
         soll. Also wieder immer gucken ob Spaltenweise leere Felder existieren, wenn ja dann wird wieder probiert
         irgendwelche Zahlen reinzuschreiben und mit check immer geprueft, ob dies eine valide Loesung ist...
         */
        for (int x = 0; x < 9; x++) { //Insgesamt muss es für jedes Kaestchen gemacht werden
            for (int y = 0; y < 9; y++) {
                if (f.getCellValue(x, y) == 0) { //Wenn leeres Feld gefunden, führe Backtracking aus
                    for (int n = 1; n <= 9; n++) { //Zahlen von 1-9...
                        if (checkSpecifiedCell(x, y, f, n)) { //Prueft ob die eingetragene Zahl eine valide Lösung ist
                            f.setCellValue(x, y, n); //Setzen der Zahl in leeres Kaestchen, welche zuvor gefunden wird
                            f.setCellModified(x, y, true);
                            if (solveSudokuWithBackTracking(f)) {
                                return true;
                            } else {
                                /*
                                 Wenn der rekursive Aufruf, keine Lösung verifizieren kann, dann soll die Zahl
                                 zurückgesetzt werden.
                                 Danach wird wieder mit einer anderen Zahl getestet
                                 */
                                f.setCellValue(x, y, 0);
                            }
                        }
                    }
                    //damit jeder rekursive aufruf, false zurueck gibt, wenn keine Zahl in das Feld passt
                    noSolutionForField = false;
                    //Wenn ein Feld eine falsche Loesung hat, dann wird mit der "Loesung" auch nicht mehr weiterprobiert
                    break;
                }
            }
            /*
             Wenn keine Zahl zwischen 1 und 9 in ein Feld passt, dann kann, um Zeit zu sparen die For-Loop
             uebersprungen werden.
             Dann wird einfach die If mit den return statements aufgerufen und der rekursive aufruf gibt false zurueck.
             Das Codefragment ist
             also nur für die rekursiven aufrufe
             */
            if (!noSolutionForField) {
                break;
            }
        }
        if (noSolutionForField) {
            return true; //Returned true, wenn alles geklappt hat
        } else {
            return false; //False, wenn es entweder garkeine Loesung gibt oder ein Kaestchen keine Loesung hat
        }
    }

    /**
     * Funktion checkt eine bestimmte Zelle.
     * @param x Koordinate x
     * @param y Koordinate y
     * @param field zu pruefendes Feld
     * @param num uebergebene Zahl, auf die geprueft wird
     * @return Werden die Regeln verletzt, wird false returned
     */
    public boolean checkSpecifiedCell(int x, int y, Field field, int num) {
        return checkSpecifiedColumn(x, field, num) && checkSpecifiedRow(y, field, num) && checkSpecifiedSquare(x, y,
                field, num);
    }

    /**
     * Checkt eine Spalte.
     * @param x Position der Spalte
     * @param field zu pruefendes Feld
     * @param num zu pruefende Nummer
     * @return Werden die Regeln verletzt, wird false returned
     */
    private boolean checkSpecifiedColumn(int x, Field field, int num) {
        int[] arrayToTest;
        arrayToTest = field.getColumn(x);

        for (int i = 0; i < 9; i++) {
            if (arrayToTest[i] == num && arrayToTest[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checkt eine Reihe.
     * @param y Position der Reihe
     * @param field zu pruefendes Feld
     * @param num zu pruefende Nummer
     * @return Werden die Regeln verletzt, wird false returned
     */
    private boolean checkSpecifiedRow(int y, Field field, int num) {
        int[] arrayToTest;
        arrayToTest = field.getRow(y);

        for (int i = 0; i < 9; i++) {
            if (arrayToTest[i] == num && arrayToTest[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checkt ein Quadrat.
     * @param x Position x
     * @param y Position y
     * @param field zu pruefendes Feld
     * @param num Zu pruefende Nummer
     * @return Werden die Regeln verletzt, wird false returned
     */
    private boolean checkSpecifiedSquare(int x, int y, Field field, int num) {
        x = x / 3; //Durch 3 teilen bewirkt, dass man nur Koordinaten von 0-2 bekommt
        y = y / 3;
        int[][] array2D;
        int[] array1D = new int[9];
        array2D = field.getSquare(x, y); //Ruft square an bestimmter stelle ab
        int counter = 0; //Zaehlt hoch, damit die Zahlen von 0-8 eingetragen werden (in die Zellen)

        for (int i = 0; i < 3; i++) { //fuer jedes Quadrat muss ein 1D Array erstellt werden
            for (int j = 0; j < 3; j++) {
                array1D[counter] = array2D[i][j];
                counter++;
            }
        }
        for (int w = 0; w < 9; w++) { //Vergleichen des Arrays auf doppelte Zahlen
            if (array1D[w] == num && array1D[w] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs a fast check whether any field violates the Sudoku rules.
     *
     * @param f the field to check
     * @return true, if the check succeeds, false otherwise
     */
    public boolean check(Field f) {
        return checkRows(f) && checkSquare(f) && checkColumns(f);
    }

    /**
     * Prueft alle Reihen.
     * @param field Zu pruefendes Feld
     * @return Werden die Regeln verletzt, wird false returned
     */
    private boolean checkRows(Field field) {
        for (int y = 0; y < 9; y++) {
            rowArray = field.getRow(y); //fragt die Reihe ab

            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 9; i++) {
                    if (rowArray[j] == 0 || rowArray[i] == 0) { //0en werden ignoriert
                        continue;
                    } else {
                        if (rowArray[j] == rowArray[i] && i != j) { //Wenn gleich, dann direkt false
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checkt die Spalten.
     * @param field zu pruefendes Feld
     * @return Werden die Regeln verletzt, wird false returned
     */
    private boolean checkColumns(Field field) {
        for (int y = 0; y < 9; y++) {
            rowArray = field.getColumn(y); //fragt die Spalte ab

            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 9; i++) {
                    if (rowArray[j] == 0 || rowArray[i] == 0) { //0en werden ignoriert
                        continue;
                    } else {
                        if (rowArray[j] == rowArray[i] && i != j) { //Wenn gleich, dann direkt false
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checkt alle Quadrate.
     * @param field Das zu pruefende Feld
     * @return Werden die Regeln verletzt, wird false returned
     */
    private boolean checkSquare(Field field) {
        int[][] squareArray2D;
        int[] squareArray1D = new int[9];
        int counter;
        for (int t = 0; t < 3; t++) {
            for (int z = 0; z < 3; z++) { //jedes Square abfragen
                counter = 0;
                squareArray2D = field.getSquare(t, z);
                for (int x = 0; x < 3; x++) { //fuer jedes Quadrat muss ein 1D Array erstellt werden
                    for (int y = 0; y < 3; y++) {
                        squareArray1D[counter] = squareArray2D[x][y];
                        counter++;
                    }
                }
                for (int w = 0; w < 9; w++) {
                    for (int o = 0; o < 9; o++) {
                        if (squareArray1D[w] == 0 || squareArray1D[o] == 0) { //0en werden ignoriert
                            continue;
                        } else {
                            if (squareArray1D[w] == squareArray1D[o] && o != w) { //Wenn gleich, dann direkt false
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
