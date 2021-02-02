import javax.swing.*;
import java.awt.*;

/**
 * The panel displaying some actions for the Sudoku solver.
 */
public final class ButtonPanel extends JPanel {
    /**
     * Konstruktor.
     * @param l
     */
    public ButtonPanel(ActionHandler l) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        JButton btnNewButton = new JButton("Speichern");
        GridBagConstraints gbcBtnNewButton = new GridBagConstraints();
        gbcBtnNewButton.insets = new Insets(30, 10, 30, 30);
        add(btnNewButton, gbcBtnNewButton);
        btnNewButton.addActionListener(l);
        btnNewButton.setActionCommand("save");


        JButton btnNewButton1 = new JButton("Prüfen");
        GridBagConstraints gbcBtnNewButton1 = new GridBagConstraints();
        gbcBtnNewButton1.insets = new Insets(30, 0, 30, 30);
        add(btnNewButton1, gbcBtnNewButton1);
        btnNewButton1.addActionListener(l);
        btnNewButton1.setActionCommand("check");


        JButton btnNewButton2 = new JButton("Lösen");
        GridBagConstraints gbcBtnNewButton2 = new GridBagConstraints();
        gbcBtnNewButton2.insets = new Insets(30, 0, 30, 30);
        add(btnNewButton2, gbcBtnNewButton2);
        btnNewButton2.addActionListener(l);
        btnNewButton2.setActionCommand("solve");
    }
}
