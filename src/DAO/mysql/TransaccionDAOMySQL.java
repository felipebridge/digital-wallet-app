package DAO.mysql;

import Config.DBConnection;
import DAO.interfaces.TransaccionDAO;
import Exceptions.DataAccessException;
import Modelo.Transaccion;
import Modelo.enums.TipoTransaccion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAOMySQL implements TransaccionDAO {

    @Override
    public List<Transaccion> listarPorCuenta(long cuentaId, int limite) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_transaccion_listar_por_cuenta(?, ?)}")) {

            cs.setLong(1, cuentaId);
            cs.setInt(2, limite);

            try (ResultSet rs = cs.executeQuery()) {
                List<Transaccion> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
                return out;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error listando transacciones", e);
        }
    }

    private Transaccion mapRow(ResultSet rs) throws SQLException {
        Transaccion t = new Transaccion();
        t.setId(rs.getLong("id"));
        t.setTipo(TipoTransaccion.valueOf(rs.getString("tipo")));
        long origen = rs.getLong("cuenta_origen_id");
        if (!rs.wasNull()) t.setCuentaOrigenId(origen);
        long destino = rs.getLong("cuenta_destino_id");
        if (!rs.wasNull()) t.setCuentaDestinoId(destino);
        t.setMonto(rs.getBigDecimal("monto"));
        t.setComision(rs.getBigDecimal("comision"));
        t.setSaldoPosteriorOrigen(rs.getBigDecimal("saldo_posterior_origen"));
        t.setSaldoPosteriorDestino(rs.getBigDecimal("saldo_posterior_destino"));

        Timestamp ts = rs.getTimestamp("fecha");
        if (ts != null) t.setFecha(ts.toLocalDateTime());
        else t.setFecha(LocalDateTime.now());

        return t;
    }
}