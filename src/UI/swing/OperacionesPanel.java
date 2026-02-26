package UI.swing;

import Service.CuentaService;
import UI.swing.theme.UIStyles;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class OperacionesPanel extends JPanel {

    private final CuentaService cuentaService;
    private final JLabel status;

    private final JTextField depCuenta = new JTextField();
    private final JTextField depMonto = new JTextField();
    private final JButton dep = new JButton("Depositar");

    private final JTextField extCuenta = new JTextField();
    private final JTextField extMonto = new JTextField();
    private final JButton ext = new JButton("Extraer");

    private final JTextField trOrigen = new JTextField();
    private final JTextField trDestino = new JTextField();
    private final JTextField trMonto = new JTextField();
    private final JButton tr = new JButton("Transferir");

    private final JTextArea out = new JTextArea();

    public OperacionesPanel(CuentaService cuentaService, JLabel status) {
        this.cuentaService = cuentaService;
        this.status = status;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        UIStyles.placeholder(depCuenta, "ID cuenta");
        UIStyles.placeholder(depMonto, "Monto");
        UIStyles.placeholder(extCuenta, "ID cuenta");
        UIStyles.placeholder(extMonto, "Monto");
        UIStyles.placeholder(trOrigen, "Origen");
        UIStyles.placeholder(trDestino, "Destino");
        UIStyles.placeholder(trMonto, "Monto");

        UIStyles.primaryButton(dep);
        UIStyles.primaryButton(ext);
        UIStyles.primaryButton(tr);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4; c.weightx = 1;
        form.add(UIStyles.h2("Operaciones"), c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4;
        form.add(new JLabel("Depósito"), c);
        y++;

        c.gridwidth = 1; c.weightx = 0;
        c.gridx = 0; c.gridy = y; form.add(new JLabel("Cuenta"), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1; form.add(depCuenta, c);
        c.gridx = 2; c.gridy = y; c.weightx = 0; form.add(new JLabel("Monto"), c);
        c.gridx = 3; c.gridy = y; c.weightx = 1; form.add(depMonto, c);
        y++;

        c.gridx = 3; c.gridy = y; c.weightx = 0;
        form.add(dep, c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4; c.weightx = 1;
        form.add(new JSeparator(), c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4;
        form.add(new JLabel("Extracción"), c);
        y++;

        c.gridwidth = 1; c.weightx = 0;
        c.gridx = 0; c.gridy = y; form.add(new JLabel("Cuenta"), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1; form.add(extCuenta, c);
        c.gridx = 2; c.gridy = y; c.weightx = 0; form.add(new JLabel("Monto"), c);
        c.gridx = 3; c.gridy = y; c.weightx = 1; form.add(extMonto, c);
        y++;

        c.gridx = 3; c.gridy = y; c.weightx = 0;
        form.add(ext, c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4; c.weightx = 1;
        form.add(new JSeparator(), c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4;
        form.add(new JLabel("Transferencia"), c);
        y++;

        c.gridwidth = 1; c.weightx = 0;
        c.gridx = 0; c.gridy = y; form.add(new JLabel("Origen"), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1; form.add(trOrigen, c);
        c.gridx = 2; c.gridy = y; c.weightx = 0; form.add(new JLabel("Destino"), c);
        c.gridx = 3; c.gridy = y; c.weightx = 1; form.add(trDestino, c);
        y++;

        c.gridx = 0; c.gridy = y; c.weightx = 0; form.add(new JLabel("Monto"), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1; c.gridwidth = 2; form.add(trMonto, c);
        c.gridx = 3; c.gridy = y; c.gridwidth = 1; c.weightx = 0; form.add(tr, c);

        out.setEditable(false);
        out.setFont(new Font(Font.MONOSPACED, Font.PLAIN, out.getFont().getSize()));
        JScrollPane sp = new JScrollPane(out);

        add(UIStyles.card(form), BorderLayout.NORTH);
        add(UIStyles.card(sp), BorderLayout.CENTER);

        wire();
    }

    private void wire() {
        dep.addActionListener(e -> {
            try {
                long c = Long.parseLong(depCuenta.getText().trim());
                BigDecimal m = new BigDecimal(depMonto.getText().trim());
                long tx = cuentaService.depositar(c, m);
                out.setText("Depósito OK\nTX: " + tx);
                depMonto.setText("");
                status.setText("Depósito OK (TX " + tx + ")");
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, msg(ex, "Error en depósito"));
                status.setText("Operación fallida");
            }
        });

        ext.addActionListener(e -> {
            try {
                long c = Long.parseLong(extCuenta.getText().trim());
                BigDecimal m = new BigDecimal(extMonto.getText().trim());
                long tx = cuentaService.extraer(c, m);
                out.setText("Extracción OK\nTX: " + tx);
                extMonto.setText("");
                status.setText("Extracción OK (TX " + tx + ")");
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, msg(ex, "Error en extracción"));
                status.setText("Operación fallida");
            }
        });

        tr.addActionListener(e -> {
            try {
                long o = Long.parseLong(trOrigen.getText().trim());
                long d = Long.parseLong(trDestino.getText().trim());
                BigDecimal m = new BigDecimal(trMonto.getText().trim());
                long tx = cuentaService.transferir(o, d, m);
                out.setText("Transferencia OK\nTX: " + tx);
                trMonto.setText("");
                status.setText("Transferencia OK (TX " + tx + ")");
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, msg(ex, "Error en transferencia"));
                status.setText("Operación fallida");
            }
        });
    }

    private String msg(RuntimeException ex, String fallback) {
        return (ex.getMessage() == null || ex.getMessage().trim().isEmpty()) ? fallback : ex.getMessage();
    }
}