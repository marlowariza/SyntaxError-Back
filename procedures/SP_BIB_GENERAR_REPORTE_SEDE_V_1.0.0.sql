DELIMITER $$

DROP PROCEDURE IF EXISTS SP_BIB_GENERAR_REPORTE_SEDE$$

CREATE PROCEDURE SP_BIB_GENERAR_REPORTE_SEDE(IN p_anho INT, IN p_mes INT)
BEGIN
    -- Borrar datos del mismo periodo si ya existen
    DELETE FROM BIB_REPORTE_SEDE 
    WHERE ANHIO = p_anho AND MES = p_mes;

    -- Insertar nuevos datos de préstamos realizados en el periodo
    INSERT INTO BIB_REPORTE_SEDE (ANHIO, MES, PRESTAMO_IDPRESTAMO, SEDE_IDSEDE, PERSONA_IDPERSONA)
    SELECT 
        p_anho AS ANHIO,
        p_mes AS MES,
        pr.ID_PRESTAMO,
        pe.SEDE_IDSEDE,
        pe.ID_PERSONA
    FROM BIB_PRESTAMO pr
    JOIN BIB_PERSONA pe ON pr.PERSONA_IDPERSONA = pe.ID_PERSONA
    WHERE YEAR(pr.FECHA_PRESTAMO) = p_anho AND MONTH(pr.FECHA_PRESTAMO) = p_mes;
END$$

DELIMITER ;
