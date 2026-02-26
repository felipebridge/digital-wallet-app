package Exceptions;

public class CuentaInactivaException extends BusinessException {
    public CuentaInactivaException(String message) {
        super(message);
    }
}