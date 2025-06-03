DELIMITER $$

DROP PROCEDURE IF EXISTS SP_BIB_ELIMINAR_DATOS_PRUEBA_REPORTE_SEDE$$

CREATE PROCEDURE SP_BIB_ELIMINAR_DATOS_PRUEBA_REPORTE_SEDE()
BEGIN
    -- Eliminar del reporte por periodo fijo
    DELETE FROM BIB_REPORTE_SEDE 
    WHERE ANHIO = 2025 AND MES = 5;

    -- Eliminar préstamos de personas específicas
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

    -- Eliminar personas específicas
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
END$$

DELIMITER ;
