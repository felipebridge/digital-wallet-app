package UI.swing;

import Modelo.CuentaCorriente;
import Service.CuentaService;
import UI.swing.theme.UIStyles;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class CuentasPanel extends JPanel {

    private final CuentaService cuentaService;
    private final JLabel status;

    private final JTextField usuarioId = new JTextField();
    private final JTextField limite = new JTextField();
    private final JTextField comision = new JTextField();
    private final JButton crear = new JButton("Crear");

    private final JTextField cuentaId = new JTextField();
    private final JButton detalle = new JButton("Detalle");
    private final JButton saldo = new JButton("Saldo");

    private final JTextArea out = new JTextArea();

    public CuentasPanel(CuentaService cuentaService, JLabel status) {
        this.cuentaService = cuentaService;
        this.status = status;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        UIStyles.placeholder(usuarioId, "ID usuario");
        UIStyles.placeholder(limite, "Límite descubierto");
        UIStyles.placeholder(comision, "Comisión transferencia");
        UIStyles.placeholder(cuentaId, "ID cuenta");

        UIStyles.primaryButton(crear);
        UIStyles.primaryButton(detalle);
        UIStyles.primaryButton(saldo);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 4;
        c.weightx = 1;
        form.add(UIStyles.h2("Cuentas"), c);
        y++;

        c.gridwidth = 1;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = y;
        form.add(new JLabel("Usuario ID"), c);

        c.gridx = 1;
        c.gridy = y;
        c.weightx = 1;
        form.add(usuarioId, c);

        c.gridx = 2;
        c.gridy = y;
        c.weightx = 0;
        form.add(new JLabel("Límite"), c);

        c.gridx = 3;
        c.gridy = y;
        c.weightx = 1;
        form.add(limite, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        c.weightx = 0;
        form.add(new JLabel("Comisión"), c);

        c.gridx = 1;
        c.gridy = y;
        c.weightx = 1;
        c.gridwidth = 2;
        form.add(comision, c);

        c.gridx = 3;
        c.gridy = y;
        c.gridwidth = 1;
        c.weightx = 0;
        form.add(crear, c);
        y++;

        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 4;
        c.weightx = 1;
        form.add(new JSeparator(), c);
        y++;

        c.gridwidth = 1;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = y;
        form.add(new JLabel("Cuenta ID"), c);

        c.gridx = 1;
        c.gridy = y;
        c.weightx = 1;
        form.add(cuentaId, c);

        c.gridx = 2;
        c.gridy = y;
        c.weightx = 0;
        form.add(detalle, c);

        c.gridx = 3;
        c.gridy = y;
        form.add(saldo, c);

        out.setEditable(false);
        out.setFont(new Font(Font.MONOSPACED, Font.PLAIN, out.getFont().getSize()));
        JScrollPane sp = new JScrollPane(out);

        add(UIStyles.card(form), BorderLayout.NORTH);
        add(UIStyles.card(sp), BorderLayout.CENTER);

        wire();
    }

    private void wire() {
        crear.addActionListener(e -> {
            try {
                long u = Long.parseLong(usuarioId.getText().trim());
                BigDecimal lim = new BigDecimal(limite.getText().trim());
                BigDecimal com = new BigDecimal(comision.getText().trim());

                long id = cuentaService.crearCuentaCorriente(u, lim, com);

                out.setText("Cuenta creada\nID: " + id + "\nUsuario ID: " + u);
                usuarioId.setText("");
                limite.setText("");
                comision.setText("");
                status.setText("Cuenta creada: " + id);
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, msg(ex, "Error creando cuenta"));
                status.setText("Operación fallida");
            }
        });

        detalle.addActionListener(e -> {
            try {
                long id = Long.parseLong(cuentaId.getText().trim());
                CuentaCorriente c = cuentaService.obtenerCuenta(id);

                if (c == null) {
                    out.setText("No existe cuenta con ID: " + id);
                    status.setText("No encontrado");
                    return;
                }

                out.setText(
                        "Cuenta corriente\nID: " + c.getId() +
                                "\nUsuario ID: " + c.getUsuarioId() +
                                "\nEstado: " + (c.getEstado() == null ? "" : c.getEstado().name()) +
                                "\nSaldo: " + (c.getSaldo() == null ? "" : c.getSaldo().toPlainString()) +
                                "\nLímite descubierto: " + (c.getLimiteDescubierto() == null ? "" : c.getLimiteDescubierto().toPlainString()) +
                                "\nComisión transferencia: " + (c.getComisionTransferencia() == null ? "" : c.getComisionTransferencia().toPlainString())
                );

                status.setText("Cuenta cargada: " + id);
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, "ID inválido o error consultando cuenta");
                status.setText("Operación fallida");
            }
        });

        saldo.addActionListener(e -> {
            try {
                long id = Long.parseLong(cuentaId.getText().trim());
                BigDecimal s = cuentaService.obtenerSaldo(id);

                out.setText("Saldo cuenta " + id + ": " + (s == null ? "N/D" : s.toPlainString()));
                status.setText("Saldo actualizado");
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, "ID inválido o error consultando saldo");
                status.setText("Operación fallida");
            }
        });
    }

    private String msg(RuntimeException ex, String fallback) {
        return (ex.getMessage() == null || ex.getMessage().trim().isEmpty()) ? fallback : ex.getMessage();
    }
}