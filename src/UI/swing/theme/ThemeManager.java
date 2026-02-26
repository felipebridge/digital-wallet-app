package UI.swing.theme;

import javax.swing.*;
import java.awt.*;

public final class ThemeManager {

    private ThemeManager() { }

    public static void apply() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        refresh();
    }

    public static void refresh() {
        for (Window w : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(w);
            w.revalidate();
            w.repaint();
        }
    }
}