package Service;

import DAO.interfaces.CuentaDAO;
import Exceptions.MontoInvalidoException;
import Modelo.CuentaCorriente;

import java.math.BigDecimal;

public class CuentaService {
    private final CuentaDAO cuentaDAO;

    public CuentaService(CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }

    public long crearCuentaCorriente(long usuarioId, BigDecimal limiteDescubierto, BigDecimal comisionTransferencia) {
        if (usuarioId <= 0) throw new MontoInvalidoException("Usuario inválido");
        validarMontoNoNegativo(limiteDescubierto, "Límite inválido");
        validarMontoNoNegativo(comisionTransferencia, "Comisión inválida");
        return cuentaDAO.crearCuentaCorriente(usuarioId, limiteDescubierto, comisionTransferencia);
    }

    public BigDecimal obtenerSaldo(long cuentaId) {
        if (cuentaId <= 0) throw new MontoInvalidoException("Cuenta inválida");
        return cuentaDAO.obtenerSaldo(cuentaId);
    }

    public CuentaCorriente obtenerCuenta(long cuentaId) {
        if (cuentaId <= 0) throw new MontoInvalidoException("Cuenta inválida");
        return cuentaDAO.obtenerCuentaCorrientePorId(cuentaId);
    }

    public long depositar(long cuentaId, BigDecimal monto) {
        validarMontoPositivo(monto);
        return cuentaDAO.depositar(cuentaId, monto);
    }

    public long extraer(long cuentaId, BigDecimal monto) {
        validarMontoPositivo(monto);
        return cuentaDAO.extraer(cuentaId, monto);
    }

    public long transferir(long origenId, long destinoId, BigDecimal monto) {
        if (origenId <= 0 || destinoId <= 0) throw new MontoInvalidoException("Cuenta inválida");
        if (origenId == destinoId) throw new MontoInvalidoException("La cuenta destino debe ser distinta");
        validarMontoPositivo(monto);
        return cuentaDAO.transferir(origenId, destinoId, monto);
    }

    private void validarMontoPositivo(BigDecimal monto) {
        if (monto == null) throw new MontoInvalidoException("Monto inválido");
        if (monto.compareTo(BigDecimal.ZERO) <= 0) throw new MontoInvalidoException("Monto inválido");
    }

    private void validarMontoNoNegativo(BigDecimal monto, String msg) {
        if (monto == null) throw new MontoInvalidoException(msg);
        if (monto.compareTo(BigDecimal.ZERO) < 0) throw new MontoInvalidoException(msg);
    }
}