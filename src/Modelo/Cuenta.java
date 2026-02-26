package Modelo;

import Modelo.enums.EstadoCuenta;

import java.math.BigDecimal;

public abstract class Cuenta {
    private long id;
    private long usuarioId;
    private BigDecimal saldo;
    private EstadoCuenta estado;

    protected Cuenta() { }

    protected Cuenta(long id, long usuarioId, BigDecimal saldo, EstadoCuenta estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.saldo = saldo;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
    }
}