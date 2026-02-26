package DAO.mysql;

import Config.DBConnection;
import DAO.interfaces.UsuarioDAO;
import Exceptions.DataAccessException;
import Modelo.Usuario;

import java.sql.*;

public class UsuarioDAOMySQL implements UsuarioDAO {

    @Override
    public long crear(Usuario usuario) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_usuario_crear(?, ?, ?)}")) {

            cs.setString(1, usuario.getNombre());
            cs.setString(2, usuario.getEmail());
            cs.registerOutParameter(3, Types.BIGINT);
            cs.execute();

            return cs.getLong(3);
        } catch (SQLException e) {
            throw new DataAccessException("Error creando usuario", e);
        }
    }

    @Override
    public Usuario obtenerPorId(long id) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_usuario_obtener_por_id(?)}")) {

            cs.setLong(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (!rs.next()) return null;

                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setActivo(rs.getBoolean("activo"));
                return u;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo usuario", e);
        }
    }
}