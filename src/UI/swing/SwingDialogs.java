package UI.swing;

import javax.swing.*;

public final class SwingDialogs {

    private SwingDialogs() { }

    public static void info(JComponent parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "OK", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(JComponent parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}