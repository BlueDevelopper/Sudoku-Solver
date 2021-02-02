package sudoku.main;

import sudoku.field.Field;
import sudoku.handler.ActionHandler;
import sudoku.menu.ButtonPanel;
import sudoku.menu.MainMenu;

import java.awt.*;
import javax.swing.*;

/**
 * Main class of the application.
 */
public final class Sudoku extends JFrame {
    /**
     * Constructs the sudoku.main.Sudoku main window.
     */
    public Sudoku() {
        setTitle("Sudoku sudoku.solver.Solver V1.0");
        Field field = new Field();
        ActionHandler actionHandler = new ActionHandler(field);

        //Panel benutzen um es als contentPane zu initialisieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        setContentPane(panel);

        setJMenuBar(new MainMenu(actionHandler));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 0, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(field, gbc);
        gbc.gridy = gbc.gridy + 2;
        add(new ButtonPanel(actionHandler), gbc);
        pack(); //Berechnet groeße des Fensters automatisch
    }

    /**
     * Main method starting the sudoku.main.Sudoku solver.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Sudoku().setVisible(true);
    }
}