package Service;

import DAO.interfaces.TransaccionDAO;
import Exceptions.MontoInvalidoException;
import Modelo.Transaccion;

import java.util.List;

public class TransaccionService {
    private final TransaccionDAO transaccionDAO;

    public TransaccionService(TransaccionDAO transaccionDAO) {
        this.transaccionDAO = transaccionDAO;
    }

    public List<Transaccion> listarUltimas(long cuentaId, int limite) {
        if (cuentaId <= 0) throw new MontoInvalidoException("Cuenta inválida");
        if (limite <= 0) limite = 10;
        return transaccionDAO.listarPorCuenta(cuentaId, limite);
    }
}