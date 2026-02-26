package DAO.interfaces;

import Modelo.CuentaCorriente;

import java.math.BigDecimal;

public interface CuentaDAO {
    long crearCuentaCorriente(long usuarioId, BigDecimal limiteDescubierto, BigDecimal comisionTransferencia);
    CuentaCorriente obtenerCuentaCorrientePorId(long cuentaId);
    BigDecimal obtenerSaldo(long cuentaId);
    long depositar(long cuentaId, BigDecimal monto);
    long extraer(long cuentaId, BigDecimal monto);
    long transferir(long cuentaOrigenId, long cuentaDestinoId, BigDecimal monto);
}