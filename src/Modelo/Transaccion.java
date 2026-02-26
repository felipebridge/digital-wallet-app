package Modelo;

import Modelo.enums.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {
    private long id;
    private TipoTransaccion tipo;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private BigDecimal monto;
    private BigDecimal comision;
    private BigDecimal saldoPosteriorOrigen;
    private BigDecimal saldoPosteriorDestino;
    private LocalDateTime fecha;

    public Transaccion() { }

    public Transaccion(long id, TipoTransaccion tipo, Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto,
                       BigDecimal comision, BigDecimal saldoPosteriorOrigen, BigDecimal saldoPosteriorDestino, LocalDateTime fecha) {
        this.id = id;
        this.tipo = tipo;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.monto = monto;
        this.comision = comision;
        this.saldoPosteriorOrigen = saldoPosteriorOrigen;
        this.saldoPosteriorDestino = saldoPosteriorDestino;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public Long getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Long cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public Long getCuentaDestinoId() {
        return cuentaDestinoId;
    }

    public void setCuentaDestinoId(Long cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getSaldoPosteriorOrigen() {
        return saldoPosteriorOrigen;
    }

    public void setSaldoPosteriorOrigen(BigDecimal saldoPosteriorOrigen) {
        this.saldoPosteriorOrigen = saldoPosteriorOrigen;
    }

    public BigDecimal getSaldoPosteriorDestino() {
        return saldoPosteriorDestino;
    }

    public void setSaldoPosteriorDestino(BigDecimal saldoPosteriorDestino) {
        this.saldoPosteriorDestino = saldoPosteriorDestino;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}