package UI.swing;

import Modelo.Usuario;
import Service.UsuarioService;
import UI.swing.theme.UIStyles;

import javax.swing.*;
import java.awt.*;

public class UsuariosPanel extends JPanel {

    private final UsuarioService usuarioService;
    private final JLabel status;

    private final JTextField nombre = new JTextField();
    private final JTextField email = new JTextField();
    private final JButton crear = new JButton("Crear");

    private final JTextField buscarId = new JTextField();
    private final JButton buscar = new JButton("Buscar");

    private final JTextArea out = new JTextArea();

    public UsuariosPanel(UsuarioService usuarioService, JLabel status) {
        this.usuarioService = usuarioService;
        this.status = status;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        UIStyles.placeholder(nombre, "Nombre completo");
        UIStyles.placeholder(email, "email@dominio.com");
        UIStyles.placeholder(buscarId, "ID usuario");

        UIStyles.primaryButton(crear);
        UIStyles.primaryButton(buscar);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        int y = 0;

        c.gridx = 0; c.gridy = y; c.gridwidth = 3;
        form.add(UIStyles.h2("Usuarios"), c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        form.add(new JLabel("Nombre"), c);
        c.gridx = 1; c.gridy = y; c.gridwidth = 2; c.weightx = 1;
        form.add(nombre, c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        form.add(new JLabel("Email"), c);
        c.gridx = 1; c.gridy = y; c.gridwidth = 2; c.weightx = 1;
        form.add(email, c);
        y++;

        c.gridx = 2; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        form.add(crear, c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 3; c.weightx = 1;
        form.add(new JSeparator(), c);
        y++;

        c.gridx = 0; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        form.add(new JLabel("Buscar por ID"), c);
        c.gridx = 1; c.gridy = y; c.gridwidth = 1; c.weightx = 1;
        form.add(buscarId, c);
        c.gridx = 2; c.gridy = y; c.gridwidth = 1; c.weightx = 0;
        form.add(buscar, c);

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
                String n = nombre.getText().trim();
                String em = email.getText().trim();
                long id = usuarioService.crearUsuario(n, em);

                out.setText("Usuario creado\nID: " + id + "\nNombre: " + n + "\nEmail: " + em);
                nombre.setText("");
                email.setText("");
                status.setText("Usuario creado: " + id);
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, msg(ex, "Error creando usuario"));
                status.setText("Operación fallida");
            }
        });

        buscar.addActionListener(e -> {
            try {
                long id = Long.parseLong(buscarId.getText().trim());
                Usuario u = usuarioService.obtener(id);
                if (u == null) {
                    out.setText("No existe usuario con ID: " + id);
                    status.setText("No encontrado");
                    return;
                }
                out.setText("Usuario\nID: " + u.getId() + "\nNombre: " + u.getNombre() + "\nEmail: " + u.getEmail() + "\nActivo: " + u.isActivo());
                status.setText("Usuario cargado: " + id);
            } catch (RuntimeException ex) {
                SwingDialogs.error(this, "ID inválido o error de búsqueda");
                status.setText("Operación fallida");
            }
        });
    }

    private String msg(RuntimeException ex, String fallback) {
        return (ex.getMessage() == null || ex.getMessage().trim().isEmpty()) ? fallback : ex.getMessage();
    }
}