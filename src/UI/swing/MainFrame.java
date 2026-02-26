package UI.swing;

import Service.CuentaService;
import Service.TransaccionService;
import Service.UsuarioService;
import UI.swing.theme.UIStyles;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JLabel status = new JLabel("Listo");

    public MainFrame(UsuarioService usuarioService, CuentaService cuentaService, TransaccionService transaccionService) {
        setTitle("Digital Wallet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1150, 760));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        root.add(buildTopBar(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Usuarios", new UsuariosPanel(usuarioService, status));
        tabs.addTab("Cuentas", new CuentasPanel(cuentaService, status));
        tabs.addTab("Operaciones", new OperacionesPanel(cuentaService, status));
        tabs.addTab("Movimientos", new MovimientosPanel(transaccionService, status));

        root.add(tabs, BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JComponent buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout(12, 12));

        JLabel title = UIStyles.h1("Digital Wallet");

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton infoBtn = new JButton("Info");
        UIStyles.quietButton(infoBtn);
        infoBtn.addActionListener(e -> SwingDialogs.info((JComponent) getContentPane(), "Digital Wallet • Swing"));

        right.add(infoBtn);

        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setOpaque(false);
        content.add(title, BorderLayout.WEST);
        content.add(right, BorderLayout.EAST);

        return UIStyles.card(content);
    }

    private JComponent buildStatusBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        status.setFont(status.getFont().deriveFont(status.getFont().getSize() - 1f));
        p.add(status, BorderLayout.CENTER);
        return p;
    }
}