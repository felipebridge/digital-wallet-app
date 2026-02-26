package UI.swing;

import Modelo.Transaccion;
import Service.TransaccionService;
import UI.swing.theme.UIStyles;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovimientosPanel extends JPanel {

    private final TransaccionService transaccionService;
    private final JLabel status;

    private final JTextField cuentaId = new JTextField();
    private final JTextField limite = new JTextField("10");
    private final JButton cargar = new JButton("Cargar");

    private final TxTableModel model = new TxTableModel();
    private final JTable table = new JTable(model);

    public MovimientosPanel(TransaccionService transaccionService, JLabel status) {
        this.transaccionService = transaccionService;
        this.status = status;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel top = new JPanel(new GridBagLayout());
        top.setOpaque(false);

        UIStyles.placeholder(cuentaId, "ID cuenta");
        UIStyles.placeholder(limite, "Cantidad");
        UIStyles.primaryButton(cargar);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        c.gridx = 0; c.gridy = y; c.gridwidth = 4; c.weightx = 1;
        top.add(UIStyles.h2("Movimientos"), c);
        y++;

        c.gridwidth = 1; c.weightx = 0;
        c.gridx = 0; c.gridy = y; top.add(new JLabel("Cuenta"), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1; top.add(cuentaId, c);
        c.gridx = 2; c.gridy = y; c.weightx = 0; top.add(new JLabel("Cantidad"), c);
        c.gridx = 3; c.gridy = y; c.weightx = 1; top.add(limite, c);
        y++;

        c.gridx = 3; c.gridy = y; c.weightx = 0;
        top.add(cargar, c);

        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        JScrollPane sp = new JScrollPane(table);

        add(UIStyles.card(top), BorderLayout.NORTH);
        add(UIStyles.card(sp), BorderLayout.CENTER);

        wire();
    }

    private void wire() {
        cargar.addActionListener(e -> {
            try {
                long id = Long.parseLong(cuentaId.getText().trim());
                int lim = Integer.parseInt(limite.getText().trim());
                List<Transaccion> list = transaccionService.listarUltimas(id, lim);
                model.setData(list);
                status.setText("Movimientos cargados: " + (list == null ? 0 : list.size()));
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, "ID inválido o error cargando movimientos");
                status.setText("Operación fallida");
            }
        });
    }
}