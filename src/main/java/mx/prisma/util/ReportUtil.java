package mx.prisma.util;

import java.sql.SQLException;

import mx.prisma.dao.GenericDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtil {
	
	public static void crearReporte(String formato) throws JRException, SQLException {
		String extension = "";
		String rutaJasper = "/home/lorena/git/AplicacionTTB064/src/main/webapp/resources/ireport/prisma.jasper";
		
		@SuppressWarnings("deprecation")
		JasperReport reporte = (JasperReport) JRLoader.loadObject(rutaJasper);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, new GenericDAO().getConnection());
		
		JRExporter exporter = null;
		
		if(formato.equals("pdf")) {
			extension = "pdf";
			exporter = new JRPdfExporter();
		}
		
		
		//Configuración genérica (no importa del formato)
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("reportePDF." + extension));
		exporter.exportReport();

	}

}
