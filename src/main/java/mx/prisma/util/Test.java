package mx.prisma.util;

import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import mx.prisma.editor.controller.CuCtrl;
import mx.prisma.editor.dao.CasoUsoDAO;
import mx.prisma.editor.model.CasoUso;




public class Test {
	public static void main(String[] args) {
		try {
			ReportUtil.crearReporte("pdf");
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	


}
