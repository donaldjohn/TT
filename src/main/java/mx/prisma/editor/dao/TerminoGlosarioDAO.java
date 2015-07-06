package mx.prisma.editor.dao;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.Referencia;
import mx.prisma.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.TerminoGlosario;

public class TerminoGlosarioDAO extends ElementoDAO{
	public TerminoGlosario consultarTerminoGlosario(String nombre, Proyecto proyecto) {
		return (TerminoGlosario) super.consultarElemento(nombre, proyecto, Referencia.getTabla(TipoReferencia.TERMINOGLS));
	}
	public TerminoGlosario consultarTerminoGlosario(int id) {
		return (TerminoGlosario) super.consultarElemento(id);
	}
}
