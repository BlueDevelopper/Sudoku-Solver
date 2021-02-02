package sudoku.handler;
import sudoku.field.Field;
import sudoku.solver.Solver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Handles all user issued actions in the application.
 */
public final class ActionHandler implements ActionListener {
    private Field field;
    private DocumentHandler dHandler = new DocumentHandler();
    private Solver solver = new Solver();

    /**
     * Constructs a new action handler instance.
     *
     * @param f a reference to the playing field
     */
    public ActionHandler(Field f) {
        field = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        switch (event) {
            case "new":
                //Wenn etwas geaendert wurde, erst nach fragen ob gespeichert werden soll..
                if (field.isAnyCellModified()) {
                    int i = JOptionPane.showOptionDialog(null,
                            "Ungespeicherte Änderungen speichern?", "Info",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, new String[]{"Ja", "Nein", "Abbrechen"}, "Ja");
                    if (i == JOptionPane.YES_OPTION) {
                        save();
                        dHandler.setFilename(null);
                        field.clear();
                    } else if (i == JOptionPane.NO_OPTION) {
                        dHandler.setFilename(null);
                        field.setAllCellsModified(false);
                        field.clear();
                    }
                } else {
                    dHandler.setFilename(null);
                    field.clear();
                }
                break;

            case "open":
                //Wenn etwas geaendert wurde, erst nach fragen ob gespeichert werden soll..
                if (field.isAnyCellModified()) {
                    int i = JOptionPane.showOptionDialog(null,
                            "Ungespeicherte Änderungen speichern?", "Info",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,
                            new String[]{"Ja", "Nein", "Abbrechen"}, "Ja");
                    if (i == JOptionPane.YES_OPTION) {
                        save(); //Speichert
                        choose(); //Waehlt aus
                    } else if (i == JOptionPane.NO_OPTION) {
                        choose();
                    }
                } else {
                    choose();
                }
                break;

            case "save":
                save();
                break;

            case "saveAs":
                saveAs();
                break;

            case "solve":
                if (!solver.solve(field)) {
                    JOptionPane.showMessageDialog(null, "Es gibt keine Lösung!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                break;

            case "check":
                if (solver.check(field)) {
                    JOptionPane.showMessageDialog(null, "keine Fehler gefunden", "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Fehler!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                break;

            case "exit":
                System.exit(0);
                break;

            case "info":
                JOptionPane.showMessageDialog(null, "Autor: Lennart Lutz", "Info",
                        JOptionPane.INFORMATION_MESSAGE, null);
                break;

            default:
                //default Case ist eigentlich nicht noetig.
                System.out.println("Das hat nicht funktioniert");
        }
    }

    /**
     * Methode zum "Verhalten wie Speichern als".
     */
    private void saveAs() {
        JFileChooser fileSaver = new JFileChooser("/home");
        fileSaver.setDialogTitle("Datei zum speichern Auswählen");
        int result1 = fileSaver.showSaveDialog(null);
        if (result1 == JFileChooser.APPROVE_OPTION) {
            if (fileSaver.getSelectedFile().exists()) { //Dialog: Überschreiben...
                dHandler.setFilename(fileSaver.getSelectedFile().getAbsolutePath());
                int i = JOptionPane.showOptionDialog(null, "Soll die Datei überschrieben werden?",
                        "Info",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        new String[]{"Ja", "Nein"}, "Ja");
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        dHandler.save(field);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, ioException.getMessage());
                    }
                }
            } else {
                //Setze Dateiname zum merken der Datei, falls keiner Vorhanden
                dHandler.setFilename(fileSaver.getSelectedFile().getAbsolutePath());
                try {
                    dHandler.save(field);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, ioException.getMessage());
                }
            }
        }
    }

    /**
     * Methode zum "Verhalten wie Speichern".
     */
    private void save() {
        if (dHandler.isFilenameSet()) {
            try {
                dHandler.save(field);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, ioException.getMessage());
            }
        } else {
            saveAs();
        }
    }

    /**
     * Methode zum auswaehlen einer Datei.
     */
    private void choose() {
        JFileChooser fileOpener = new JFileChooser("/home");
        fileOpener.setDialogType(JFileChooser.OPEN_DIALOG);
        fileOpener.setVisible(true);
        int result = fileOpener.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            //Damit man spaeter ueberpruefen kann, ob eine Datei gewaehlt wurde
            dHandler.setFilename(fileOpener.getSelectedFile().getAbsolutePath());
            try {
                dHandler.load(field);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, ioException.getMessage());
            }
        }
    }
}
