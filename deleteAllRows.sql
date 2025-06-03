-- Deshabilitar temporalmente las restricciones de clave foránea
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar datos de tablas con dependencias primero
TRUNCATE TABLE `myholylib`.`BIB_SANCION`;
TRUNCATE TABLE `myholylib`.`BIB_PRESTAMO_EJEMPLAR`;
TRUNCATE TABLE `myholylib`.`BIB_REPORTE_SEDE`;
TRUNCATE TABLE `myholylib`.`BIB_REPORTE_GENERAL`;
TRUNCATE TABLE `myholylib`.`BIB_PRESTAMO`;

-- Eliminar datos de tablas intermedias
TRUNCATE TABLE `myholylib`.`BIB_MATERIAL_TEMA`;
TRUNCATE TABLE `myholylib`.`BIB_MATERIAL_CREADOR`;

-- Eliminar datos de tablas principales
TRUNCATE TABLE `myholylib`.`BIB_EJEMPLAR`;
TRUNCATE TABLE `myholylib`.`BIB_PERSONA`;
TRUNCATE TABLE `myholylib`.`BIB_MATERIAL`;
TRUNCATE TABLE `myholylib`.`BIB_TEMA`;
TRUNCATE TABLE `myholylib`.`BIB_CREADOR`;
TRUNCATE TABLE `myholylib`.`BIB_EDITORIAL`;
TRUNCATE TABLE `myholylib`.`BIB_BIBLIOTECA_SEDE`;

-- Volver a habilitar las restricciones de clave foránea
SET FOREIGN_KEY_CHECKS = 1;
