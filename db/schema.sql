CREATE DATABASE IF NOT EXISTS digital_wallet;
USE digital_wallet;

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE cuenta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    estado VARCHAR(20) NOT NULL,
    limite_descubierto DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    comision_transferencia DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_cuenta_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE transaccion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo VARCHAR(30) NOT NULL,
    cuenta_origen_id BIGINT,
    cuenta_destino_id BIGINT,
    monto DECIMAL(15,2) NOT NULL,
    comision DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_tx_cuenta_origen
        FOREIGN KEY (cuenta_origen_id) REFERENCES cuenta(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_tx_cuenta_destino
        FOREIGN KEY (cuenta_destino_id) REFERENCES cuenta(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_cuenta_usuario ON cuenta(usuario_id);
CREATE INDEX idx_transaccion_fecha ON transaccion(fecha);
CREATE INDEX idx_transaccion_origen ON transaccion(cuenta_origen_id);
CREATE INDEX idx_transaccion_destino ON transaccion(cuenta_destino_id);