package sudoku.field;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Textfield for sudoku.main.Sudoku.
 */
public final class SudokuTextField extends JTextField {
    private boolean modified;

    /**
     * Konstruktor.
     */
    public SudokuTextField() {

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                selectAll(); //Text markieren
            }
            @Override
            public void focusLost(FocusEvent e) {
                select(0, 0);
            }
        });

        setBorder(null);
        setHorizontalAlignment(JTextField.CENTER); //Um den Text in die Mitte zu schreiben
        setFont(getFont().deriveFont(Font.PLAIN, 18));

        //Nur für den Unit-Test und ist eigentlich Unnoetig
        setMinimumSize(new Dimension(40, 40));
        setPreferredSize(new Dimension(40, 40));
        setMaximumSize(new Dimension(40, 40));

    }

    /**
     * Auslesen des gedrueckten Keys.
     *
     * @param e KeyEvent
     */
    @Override
    public void processKeyEvent(KeyEvent e) {
        int i = e.getKeyCode(); // 0->48, 9->57
        //System.out.println(i);
        if (getText().equals("")) { //Wenn Feld leer, darf eine Zahl geschrieben werden
            if (i >= 48 && i <= 57) { //Zahlen unter den F1 Tasten
                setValue(i - 48);
                setModified(true);
            } else if (i == e.VK_BACK_SPACE || i == e.VK_DELETE) {
                setValue(0);
            } else if (i >= 96 && i <= 105) { //Numpad Zahlen
                setValue(i - 96);
                setModified(true);
            }
        } else if (getSelectedText() != null) { //Wenn Zahl neu ausgewaehlt wurde, darf auch geschrieben werden
            if (i >= 48 && i <= 57) {
                setValue(i - 48);
                setModified(true);
            } else if (i == e.VK_BACK_SPACE || i == e.VK_DELETE) {
                setValue(0);
            } else if (i >= 96 && i <= 105) {
                setValue(i - 96);
                setModified(true);
            }
        } else { //Um die Zahlen direkt zu korriegieren
            if (i == e.VK_BACK_SPACE || i == e.VK_DELETE) { //Backspace->8, ENTF->127 sollen den Wert entfernen
                setValue(0);
            }
        }
    }

    /**
     * Returns the current value in the field or zero if the field is empty.
     *
     * @return the number from 1 to 9 or zero if empty
     */
    public int getValue() {
        int v = 0;
        String text = getText();
        if (text.equals("")) {
            return 0; //Es ist besser mit 0 zu arbeiten
        } else {
            v = Integer.parseInt(text);
            return v;
        }
    }

    /**
     * Sets the field's value.
     *
     * @param v the number from 1 to 9 or zero to clear the field
     * @throws IllegalArgumentException if v is not between 0 and 9
     */
    public void setValue(int v) {
        if (v >= 0 && v <= 9) {
            if (v == 0) {
                setText(""); //Einfach nichts setzen
            } else {
                setText("" + v);
            }
        } else { //Alles andere wirft eine Exception
            throw new IllegalArgumentException();
        }
    }

    /**
     * Sets the modified state of this field.
     *
     * Modified fields are displayed in blue color.
     *
     * @param modified a flag indicating whether this field is modified
     */
    public void setModified(boolean modified) {
        this.modified = modified;
        if (modified) {
            setForeground(Color.blue);
        } else {
            setForeground(Color.BLACK);
        }
    }

    /**
     * Checks whether this field is in modified state.
     *
     * @return true if this field is modified
     */
    public boolean isModified() {
        return modified;
    }
}
