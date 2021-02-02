import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Main menu bar.
 */
public class MainMenu extends JMenuBar {

    /**
     * Konstruktor initialisiert das Menue.
     * @param h
     */
    public MainMenu(ActionHandler h) {
        JMenu newMenu1 = new JMenu("Datei");
        add(newMenu1);
        newMenu1.setMnemonic('d');

        JSeparator separator = new JSeparator();
        JSeparator separator1 = new JSeparator();

        JMenuItem newItem = new JMenuItem("Neu");
        JMenuItem open = new JMenuItem("Öffnen");
        JMenuItem save = new JMenuItem("Speichern");
        JMenuItem saveAs = new JMenuItem("Speichern als");
        JMenuItem check = new JMenuItem("Prüfen");
        JMenuItem solve = new JMenuItem("Lösen");
        JMenuItem exit = new JMenuItem("Beenden");

        newMenu1.add(newItem);
        newMenu1.add(open);
        newMenu1.add(save);
        newMenu1.add(saveAs);
        newMenu1.add(separator);
        newMenu1.add(check);
        newMenu1.add(solve);
        newMenu1.add(separator1);
        newMenu1.add(exit);

        createNewBar(newItem, h);
        createOpenBar(open, h);
        createSaveBar(save, h);
        createSaveAsBar(saveAs, h);
        createCheckBar(check, h);
        createSolveBar(solve, h);
        createExitBar(exit, h);

        JMenu newMenu2 = new JMenu("Info");
        add(newMenu2);
        newMenu2.setMnemonic('i');
        JMenuItem info = new JMenuItem("Über");
        newMenu2.add(info);
        KeyStroke controlF1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        info.setAccelerator(controlF1);
        info.setMnemonic('b');
        info.addActionListener(h);
        info.setActionCommand("info");
    }

    /**
     * Erstellt den "neu" Knopf.
     * @param newItem
     * @param h
     */
    private void createNewBar(JMenuItem newItem, ActionHandler h) {
        newItem.setMnemonic('N');
        KeyStroke controlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        newItem.setAccelerator(controlN);
        newItem.addActionListener(h);
        newItem.setActionCommand("new");
    }

    /**
     * Erstellt den "oeffnen" Knopf.
     * @param open
     * @param h
     */
    private void createOpenBar(JMenuItem open, ActionHandler h) {
        open.setMnemonic('f');
        KeyStroke controlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
        open.setAccelerator(controlO);
        open.addActionListener(h);
        open.setActionCommand("open");
    }

    /**
     * Erstellt den "Speichern" Knopf.
     * @param save
     * @param h
     */
    private void createSaveBar(JMenuItem save, ActionHandler h) {
        save.setMnemonic('s');
        KeyStroke controlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        save.setAccelerator(controlS);
        save.addActionListener(h);
        save.setActionCommand("save");
    }

    /**
     * Erstellt den "speichernAls" Knopf.
     * @param saveAs
     * @param h
     */
    private void createSaveAsBar(JMenuItem saveAs, ActionHandler h) {

        saveAs.setMnemonic('a');
        saveAs.addActionListener(h);
        saveAs.setActionCommand("saveAs");
    }

    /**
     * Erstellt den "pruefen" Knopf.
     * @param check
     * @param h
     */
    private void createCheckBar(JMenuItem check, ActionHandler h) {
        check.setMnemonic('p');
        KeyStroke controlP = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
        check.setAccelerator(controlP);
        check.addActionListener(h);
        check.setActionCommand("check");
    }

    /**
     * Erstellt den "loesen" Knopf.
     * @param solve
     * @param h
     */
    private void createSolveBar(JMenuItem solve, ActionHandler h) {
        solve.setMnemonic('l');
        KeyStroke controlL = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
        solve.setAccelerator(controlL);
        solve.addActionListener(h);
        solve.setActionCommand("solve");
    }

    /**
     * Erstellt den "beenden" Knopf.
     * @param exit
     * @param h
     */
    private void createExitBar(JMenuItem exit, ActionHandler h) {
        exit.setMnemonic('e');
        exit.addActionListener(h);
        exit.setActionCommand("exit");
    }
}
