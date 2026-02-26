package UI.swing.theme;

import javax.swing.*;
import java.awt.*;

public final class UIStyles {

    private UIStyles() { }

    public static JPanel card(JComponent content) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 25), 1, true),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        p.add(content, BorderLayout.CENTER);
        return p;
    }

    public static JLabel h1(String text) {
        JLabel l = new JLabel(text);
        Font f = l.getFont();
        l.setFont(f.deriveFont(Font.BOLD, f.getSize() + 6f));
        return l;
    }

    public static JLabel h2(String text) {
        JLabel l = new JLabel(text);
        Font f = l.getFont();
        l.setFont(f.deriveFont(Font.BOLD, f.getSize() + 2f));
        return l;
    }

    public static void placeholder(JTextField field, String text) {
        field.setToolTipText(text);
    }

    public static void primaryButton(AbstractButton b) {
        b.setFont(b.getFont().deriveFont(Font.BOLD));
    }

    public static void quietButton(AbstractButton b) {
        b.setFocusPainted(false);
    }
}