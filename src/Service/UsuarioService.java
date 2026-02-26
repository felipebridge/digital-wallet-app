package Service;

import DAO.interfaces.UsuarioDAO;
import Exceptions.MontoInvalidoException;
import Modelo.Usuario;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public long crearUsuario(String nombre, String email) {
        if (nombre == null || nombre.trim().isEmpty()) throw new MontoInvalidoException("Nombre inválido");
        if (email == null || email.trim().isEmpty()) throw new MontoInvalidoException("Email inválido");
        Usuario u = new Usuario(0, nombre.trim(), email.trim(), true);
        return usuarioDAO.crear(u);
    }

    public Usuario obtener(long id) {
        return usuarioDAO.obtenerPorId(id);
    }
}