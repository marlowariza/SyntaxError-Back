package com.syntaxerror.biblioteca.bibliows.reports;

import com.syntaxerror.biblioteca.db.DBManager;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReporteUtil {

    //objetivo de la clase ReporteUtil
    //1: crear un método genérico invocarReporte que permita invocar diversos reportes
    //con o sin parámetros
    //2: crear métodos específicos para invocar cada reporte
    //de forma tal que se puede invocar desde el servicio SOAP
    //entrada: nombre del reporte, parámetros (HashMap -> Map -> Progra2 -> STL -> Map)
    //salida: byte[], por que se retornará un pdf
    public static byte[] invocarReporte(String nombreReporte, HashMap parametros) {
        byte[] reporte = null;
        Connection conexion = DBManager.getInstance().getConnection();
        String nombreRecurso = "/" + nombreReporte + ".jasper";
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(ReporteUtil.class.getResource(nombreRecurso));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conexion);
            reporte = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reporte;
    }

    public static byte[] reportePrestamosPorSede(Integer sedeId, Integer anho, Integer mes) {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("SEDE_ID", sedeId);
        parametros.put("ANHO", anho);
        parametros.put("MES", mes);
        return invocarReporte("ReporteSede", parametros);
    }
}
