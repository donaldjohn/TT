package mx.prisma.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mx.prisma.dao.GenericDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtil {
	
	public static void crearReporte(String formato, String nombre, Integer idProyecto) throws JRException, SQLException {
		String extension = "";
		String rutaJasper = "/home/lorena/git/AplicacionTTB064/src/main/webapp/resources/ireport/prisma.jasper";
		
		@SuppressWarnings("deprecation")
		JasperReport reporte = (JasperReport) JRLoader.loadObject(rutaJasper);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idProyecto", idProyecto);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, param, new GenericDAO().getConnection());
		
		JRExporter exporter = null;
		
		if(formato.equals("pdf")) {
			extension = "pdf";
			exporter = new JRPdfExporter();
		} else if(formato.equals("docx")) {
			extension = "docx";
			exporter = new JRDocxExporter();
		}
		
		
		//Configuración genérica (no importa del formato)
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("/home/lorena/git/AplicacionTTB064/src/main/webapp/resources/ireport/" + nombre + "." + extension));
		exporter.exportReport();

	}

}
