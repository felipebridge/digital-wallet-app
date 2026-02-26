package Main;

import DAO.interfaces.CuentaDAO;
import DAO.interfaces.TransaccionDAO;
import DAO.interfaces.UsuarioDAO;
import DAO.mysql.CuentaDAOMySQL;
import DAO.mysql.TransaccionDAOMySQL;
import DAO.mysql.UsuarioDAOMySQL;
import Service.CuentaService;
import Service.TransaccionService;
import Service.UsuarioService;
import UI.swing.MainFrame;
import UI.swing.theme.ThemeManager;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        ThemeManager.apply();

        UsuarioDAO usuarioDAO = new UsuarioDAOMySQL();
        CuentaDAO cuentaDAO = new CuentaDAOMySQL();
        TransaccionDAO transaccionDAO = new TransaccionDAOMySQL();

        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        CuentaService cuentaService = new CuentaService(cuentaDAO);
        TransaccionService transaccionService = new TransaccionService(transaccionDAO);

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(usuarioService, cuentaService, transaccionService);
            frame.setVisible(true);
        });
    }
}