DELIMITER //

CREATE EVENT IF NOT EXISTS evt_actualizar_prestamos_vencidos
ON SCHEDULE EVERY 1 DAY 
STARTS CURRENT_TIMESTAMP
DO
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_prestamo_id INT;
    DECLARE v_persona_id INT;
    DECLARE v_fecha_sancion_actual DATE;
    DECLARE v_nueva_fecha_sancion DATE;
    
    -- Cursor para obtener préstamos atrasados que necesitan sanción
    DECLARE cur_prestamos_atrasados CURSOR FOR
        SELECT DISTINCT p.ID_PRESTAMO, p.PERSONA_IDPERSONA, per.FECHA_SANCION_FIN
        FROM myholylib.BIB_PRESTAMOS p
        INNER JOIN myholylib.BIB_PRESTAMOS_DE_EJEMPLARES pde ON p.ID_PRESTAMO = pde.PRESTAMO_IDPRESTAMO
        INNER JOIN myholylib.BIB_PERSONAS per ON p.PERSONA_IDPERSONA = per.ID_PERSONA
        WHERE p.FECHA_DEVOLUCION IS NOT NULL 
          AND pde.FECHA_REAL_DEVOLUCION IS NULL 
          AND CURDATE() > p.FECHA_DEVOLUCION 
          AND pde.ESTADO IN ('PRESTADO');
          
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- PASO 1: Limpiar sanciones vencidas (fechas pasadas)
    UPDATE myholylib.BIB_PERSONAS 
    SET FECHA_SANCION_FIN = NULL
    WHERE FECHA_SANCION_FIN IS NOT NULL 
      AND FECHA_SANCION_FIN < CURDATE();
    
    -- PASO 2: Actualizar préstamos que no se han devuelto y ya pasaron la fecha límite
    UPDATE myholylib.BIB_PRESTAMOS_DE_EJEMPLARES pde 
    INNER JOIN myholylib.BIB_PRESTAMOS p ON pde.PRESTAMO_IDPRESTAMO = p.ID_PRESTAMO
    SET pde.ESTADO = 'ATRASADO'
    WHERE p.FECHA_DEVOLUCION IS NOT NULL 
      AND pde.FECHA_REAL_DEVOLUCION IS NULL 
      AND CURDATE() > p.FECHA_DEVOLUCION 
      AND pde.ESTADO IN ('PRESTADO');
    
    -- PASO 3: Actualizar préstamos que se devolvieron tarde
    UPDATE myholylib.BIB_PRESTAMOS_DE_EJEMPLARES pde 
    INNER JOIN myholylib.BIB_PRESTAMOS p ON pde.PRESTAMO_IDPRESTAMO = p.ID_PRESTAMO
    SET pde.ESTADO = 'ATRASADO'
    WHERE p.FECHA_DEVOLUCION IS NOT NULL 
      AND pde.FECHA_REAL_DEVOLUCION IS NOT NULL 
      AND pde.FECHA_REAL_DEVOLUCION > p.FECHA_DEVOLUCION 
      AND pde.ESTADO != 'ATRASADO';
    
    -- PASO 4: Procesar sanciones individuales por préstamo atrasado
    OPEN cur_prestamos_atrasados;
    
    read_loop: LOOP
        FETCH cur_prestamos_atrasados INTO v_prestamo_id, v_persona_id, v_fecha_sancion_actual;
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Verificar si ya existe una sanción por DEMORA para este préstamo
        IF NOT EXISTS (
            SELECT 1 FROM myholylib.BIB_SANCIONES 
            WHERE PRESTAMO_IDPRESTAMO = v_prestamo_id 
              AND TIPO = 'DEMORA'
        ) THEN
            -- Calcular nueva fecha de sanción
            IF v_fecha_sancion_actual IS NULL OR v_fecha_sancion_actual < CURDATE() THEN
                -- Caso A: Sin sanción activa - nueva sanción de 2 días
                SET v_nueva_fecha_sancion = DATE_ADD(CURDATE(), INTERVAL 2 DAY);
            ELSE
                -- Caso B: Con sanción activa - extender 2 días más
                SET v_nueva_fecha_sancion = DATE_ADD(v_fecha_sancion_actual, INTERVAL 2 DAY);
            END IF;
            
            -- Actualizar fecha de sanción en BIB_PERSONAS
            UPDATE myholylib.BIB_PERSONAS 
            SET FECHA_SANCION_FIN = v_nueva_fecha_sancion
            WHERE ID_PERSONA = v_persona_id;
            
            -- Crear registro de sanción en BIB_SANCIONES
            INSERT INTO myholylib.BIB_SANCIONES (
                TIPO, 
                FECHA, 
                MONTO, 
                DURACION, 
                DESCRIPCION, 
                PRESTAMO_IDPRESTAMO
            ) VALUES (
                'DEMORA',
                CURDATE(),
                NULL,
                v_nueva_fecha_sancion,
                CONCAT('Sanción automática por préstamo atrasado. Sanción hasta: ', DATE_FORMAT(v_nueva_fecha_sancion, '%d/%m/%Y')),
                v_prestamo_id
            );
        END IF;
        
    END LOOP;
    
    CLOSE cur_prestamos_atrasados;
    
END //

DELIMITER ;
