package Modelo;

import Modelo.enums.EstadoCuenta;

import java.math.BigDecimal;

public class CuentaCorriente extends Cuenta {
    private BigDecimal limiteDescubierto;
    private BigDecimal comisionTransferencia;

    public CuentaCorriente() { }

    public CuentaCorriente(long id, long usuarioId, BigDecimal saldo, EstadoCuenta estado, BigDecimal limiteDescubierto, BigDecimal comisionTransferencia) {
        super(id, usuarioId, saldo, estado);
        this.limiteDescubierto = limiteDescubierto;
        this.comisionTransferencia = comisionTransferencia;
    }

    public BigDecimal getLimiteDescubierto() {
        return limiteDescubierto;
    }

    public void setLimiteDescubierto(BigDecimal limiteDescubierto) {
        this.limiteDescubierto = limiteDescubierto;
    }

    public BigDecimal getComisionTransferencia() {
        return comisionTransferencia;
    }

    public void setComisionTransferencia(BigDecimal comisionTransferencia) {
        this.comisionTransferencia = comisionTransferencia;
    }
}