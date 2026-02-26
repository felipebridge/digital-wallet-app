package DAO.interfaces;

import Modelo.Transaccion;

import java.util.List;

public interface TransaccionDAO {
    List<Transaccion> listarPorCuenta(long cuentaId, int limite);
}