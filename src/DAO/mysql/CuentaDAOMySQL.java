package DAO.mysql;

import Config.DBConnection;
import DAO.interfaces.CuentaDAO;
import Exceptions.*;
import Modelo.CuentaCorriente;
import Modelo.enums.EstadoCuenta;

import java.math.BigDecimal;
import java.sql.*;

public class CuentaDAOMySQL implements CuentaDAO {

    @Override
    public long crearCuentaCorriente(long usuarioId, BigDecimal limiteDescubierto, BigDecimal comisionTransferencia) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_cuenta_crear_corriente(?, ?, ?, ?)}")) {

            cs.setLong(1, usuarioId);
            cs.setBigDecimal(2, limiteDescubierto);
            cs.setBigDecimal(3, comisionTransferencia);
            cs.registerOutParameter(4, Types.BIGINT);
            cs.execute();
            return cs.getLong(4);

        } catch (SQLException e) {
            throw new DataAccessException("Error creando cuenta", e);
        }
    }

    @Override
    public CuentaCorriente obtenerCuentaCorrientePorId(long cuentaId) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_cuenta_obtener_corriente_por_id(?)}")) {

            cs.setLong(1, cuentaId);

            try (ResultSet rs = cs.executeQuery()) {
                if (!rs.next()) return null;

                CuentaCorriente c = new CuentaCorriente();
                c.setId(rs.getLong("id"));
                c.setUsuarioId(rs.getLong("usuario_id"));
                c.setSaldo(rs.getBigDecimal("saldo"));
                c.setEstado(EstadoCuenta.valueOf(rs.getString("estado")));
                c.setLimiteDescubierto(rs.getBigDecimal("limite_descubierto"));
                c.setComisionTransferencia(rs.getBigDecimal("comision_transferencia"));
                return c;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo cuenta", e);
        }
    }

    @Override
    public BigDecimal obtenerSaldo(long cuentaId) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_cuenta_obtener_saldo(?)}")) {

            cs.setLong(1, cuentaId);

            try (ResultSet rs = cs.executeQuery()) {
                if (!rs.next()) return null;
                return rs.getBigDecimal("saldo");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo saldo", e);
        }
    }

    @Override
    public long depositar(long cuentaId, BigDecimal monto) {
        return ejecutarOperacionMonetaria("{CALL sp_cuenta_depositar(?, ?, ?)}", cuentaId, monto);
    }

    @Override
    public long extraer(long cuentaId, BigDecimal monto) {
        return ejecutarOperacionMonetaria("{CALL sp_cuenta_extraer(?, ?, ?)}", cuentaId, monto);
    }

    @Override
    public long transferir(long cuentaOrigenId, long cuentaDestinoId, BigDecimal monto) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall("{CALL sp_cuenta_transferir(?, ?, ?, ?)}")) {

            cs.setLong(1, cuentaOrigenId);
            cs.setLong(2, cuentaDestinoId);
            cs.setBigDecimal(3, monto);
            cs.registerOutParameter(4, Types.BIGINT);

            cs.execute();
            return cs.getLong(4);

        } catch (SQLException e) {
            throw mapearSQLException("Error en transferencia", e);
        }
    }

    private long ejecutarOperacionMonetaria(String call, long cuentaId, BigDecimal monto) {
        try (Connection cn = DBConnection.getConnection();
             CallableStatement cs = cn.prepareCall(call)) {

            cs.setLong(1, cuentaId);
            cs.setBigDecimal(2, monto);
            cs.registerOutParameter(3, Types.BIGINT);

            cs.execute();
            return cs.getLong(3);

        } catch (SQLException e) {
            throw mapearSQLException("Error en operación", e);
        }
    }

    private RuntimeException mapearSQLException(String base, SQLException e) {
        String msg = e.getMessage() == null ? "" : e.getMessage().toUpperCase();

        if (msg.contains("MONTO_INVALIDO")) return new MontoInvalidoException("Monto inválido");
        if (msg.contains("CUENTA_INACTIVA")) return new CuentaInactivaException("Cuenta inactiva");
        if (msg.contains("SALDO_INSUFICIENTE")) return new SaldoInsuficienteException("Saldo insuficiente");
        if (msg.contains("LIMITE_DESCUBIERTO")) return new LimiteDescubiertoException("Se excede el límite de descubierto");

        return new DataAccessException(base, e);
    }
}