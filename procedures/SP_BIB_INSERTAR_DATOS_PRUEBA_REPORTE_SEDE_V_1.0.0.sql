DELIMITER $$

DROP PROCEDURE IF EXISTS SP_BIB_INSERTAR_DATOS_PRUEBA_REPORTE_SEDE$$

CREATE PROCEDURE SP_BIB_INSERTAR_DATOS_PRUEBA_REPORTE_SEDE()
BEGIN
    DECLARE v_idSede1 INT;
    DECLARE v_idSede2 INT;
    DECLARE v_idPersona1 INT;
    DECLARE v_idPersona2 INT;
    DECLARE v_idPersona3 INT;
    DECLARE v_idPersona4 INT;
    DECLARE v_idPrestamo1 INT;
    DECLARE v_idPrestamo2 INT;
    DECLARE v_idPrestamo3 INT;
    DECLARE v_idPrestamo4 INT;

    -- Eliminar del reporte
    DELETE FROM BIB_REPORTE_SEDE 
    WHERE ANHIO = 2025 AND MES = 5;

    -- Eliminar préstamos asociados a las personas de prueba
    DELETE FROM BIB_PRESTAMO 
    WHERE PERSONA_IDPERSONA IN (
        SELECT ID_PERSONA FROM (
            SELECT ID_PERSONA FROM BIB_PERSONA
            WHERE CORREO IN (
                'juan.perez@correo.com',
                'ana.ramos@correo.com',
                'luis.fernandez@correo.com',
                'carla.diaz@correo.com'
            )
        ) AS sub
    );

    -- Eliminar personas de prueba
    DELETE FROM BIB_PERSONA 
    WHERE ID_PERSONA IN (
        SELECT ID_PERSONA FROM (
            SELECT ID_PERSONA FROM BIB_PERSONA
            WHERE CORREO IN (
                'juan.perez@correo.com',
                'ana.ramos@correo.com',
                'luis.fernandez@correo.com',
                'carla.diaz@correo.com'
            )
        ) AS sub
    );

    -- Eliminar sedes de prueba
    DELETE FROM BIB_BIBLIOTECA_SEDE 
    WHERE ID_SEDE IN (
        SELECT ID_SEDE FROM (
            SELECT ID_SEDE FROM BIB_BIBLIOTECA_SEDE
            WHERE CORREO_CONTACTO IN ('sede@central.com', 'sede@sur.com')
        ) AS sub
    );

    -- Insertar sedes
    INSERT INTO BIB_BIBLIOTECA_SEDE (NOMBRE, DIRECCION, DISTRITO, TELEFONO_CONTACTO, CORREO_CONTACTO, ACTIVA)
    VALUES ('Sede Central', 'Av. Lima 100', 'Lima Centro', '999888777', 'sede@central.com', TRUE);
    SET v_idSede1 = LAST_INSERT_ID();

    INSERT INTO BIB_BIBLIOTECA_SEDE (NOMBRE, DIRECCION, DISTRITO, TELEFONO_CONTACTO, CORREO_CONTACTO, ACTIVA)
    VALUES ('Sede Sur', 'Av. Sur 200', 'Lima Sur', '988776655', 'sede@sur.com', TRUE);
    SET v_idSede2 = LAST_INSERT_ID();

    -- Insertar personas lectoras
    INSERT INTO BIB_PERSONA (NOMBRE, PATERNO, MATERNO, DIRECCION, TELEFONO, CORREO, CONTRASENHA,
                             TIPO_PERSONA, NIVEL, TURNO, FECHA_CONTRATO_INI, FECHA_CONTRATO_FIN, VIGENTE, SEDE_IDSEDE)
    VALUES ('Juan', 'Pérez', 'Gómez', 'Calle Uno 111', '987654321', 'juan.perez@correo.com', '123456',
            'LECTOR', 'AVANZADO', NULL, NULL, NULL, NULL, v_idSede1);
    SET v_idPersona1 = LAST_INSERT_ID();

    INSERT INTO BIB_PERSONA (NOMBRE, PATERNO, MATERNO, DIRECCION, TELEFONO, CORREO, CONTRASENHA,
                             TIPO_PERSONA, NIVEL, TURNO, FECHA_CONTRATO_INI, FECHA_CONTRATO_FIN, VIGENTE, SEDE_IDSEDE)
    VALUES ('Ana', 'Ramos', 'Torres', 'Av. Arequipa 123', '912345678', 'ana.ramos@correo.com', 'abcdef',
            'LECTOR', 'INTERMEDIO', NULL, NULL, NULL, NULL, v_idSede1);
    SET v_idPersona2 = LAST_INSERT_ID();

    INSERT INTO BIB_PERSONA (NOMBRE, PATERNO, MATERNO, DIRECCION, TELEFONO, CORREO, CONTRASENHA,
                             TIPO_PERSONA, NIVEL, TURNO, FECHA_CONTRATO_INI, FECHA_CONTRATO_FIN, VIGENTE, SEDE_IDSEDE)
    VALUES ('Luis', 'Fernández', 'Cruz', 'Jr. San Martín 456', '911222333', 'luis.fernandez@correo.com', 'xyz789',
            'LECTOR', 'BASICO', NULL, NULL, NULL, NULL, v_idSede2);
    SET v_idPersona3 = LAST_INSERT_ID();

    INSERT INTO BIB_PERSONA (NOMBRE, PATERNO, MATERNO, DIRECCION, TELEFONO, CORREO, CONTRASENHA,
                             TIPO_PERSONA, NIVEL, TURNO, FECHA_CONTRATO_INI, FECHA_CONTRATO_FIN, VIGENTE, SEDE_IDSEDE)
    VALUES ('Carla', 'Díaz', 'Morales', 'Calle Falsa 123', '910000111', 'carla.diaz@correo.com', 'pass456',
            'LECTOR', 'INTERMEDIO', NULL, NULL, NULL, NULL, v_idSede2);
    SET v_idPersona4 = LAST_INSERT_ID();

    -- Insertar préstamos
    INSERT INTO BIB_PRESTAMO (FECHA_SOLICITUD, FECHA_PRESTAMO, FECHA_DEVOLUCION, PERSONA_IDPERSONA)
    VALUES ('2025-05-01', '2025-05-02', '2025-05-10', v_idPersona1);
    SET v_idPrestamo1 = LAST_INSERT_ID();

    INSERT INTO BIB_PRESTAMO (FECHA_SOLICITUD, FECHA_PRESTAMO, FECHA_DEVOLUCION, PERSONA_IDPERSONA)
    VALUES ('2025-05-05', '2025-05-06', '2025-05-12', v_idPersona2);
    SET v_idPrestamo2 = LAST_INSERT_ID();

    INSERT INTO BIB_PRESTAMO (FECHA_SOLICITUD, FECHA_PRESTAMO, FECHA_DEVOLUCION, PERSONA_IDPERSONA)
    VALUES ('2025-05-08', '2025-05-09', '2025-05-15', v_idPersona3);
    SET v_idPrestamo3 = LAST_INSERT_ID();

    INSERT INTO BIB_PRESTAMO (FECHA_SOLICITUD, FECHA_PRESTAMO, FECHA_DEVOLUCION, PERSONA_IDPERSONA)
    VALUES ('2025-05-10', '2025-05-11', '2025-05-18', v_idPersona4);
    SET v_idPrestamo4 = LAST_INSERT_ID();

    -- Insertar en la tabla de reporte
    INSERT INTO BIB_REPORTE_SEDE (ANHIO, MES, PRESTAMO_IDPRESTAMO, SEDE_IDSEDE, PERSONA_IDPERSONA)
    VALUES 
        (2025, 5, v_idPrestamo1, v_idSede1, v_idPersona1),
        (2025, 5, v_idPrestamo2, v_idSede1, v_idPersona2),
        (2025, 5, v_idPrestamo3, v_idSede2, v_idPersona3),
        (2025, 5, v_idPrestamo4, v_idSede2, v_idPersona4);
END$$

DELIMITER ;




