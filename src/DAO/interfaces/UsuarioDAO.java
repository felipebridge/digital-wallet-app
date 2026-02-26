package DAO.interfaces;

import Modelo.Usuario;

public interface UsuarioDAO {
    long crear(Usuario usuario);
    Usuario obtenerPorId(long id);
}