package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;



import mx.prisma.admin.model.Proyecto;
import mx.prisma.editor.bs.Referencia;
import mx.prisma.editor.bs.Referencia.TipoReferencia;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.ReglaNegocio;

public class ReglaNegocioDAO extends ElementoDAO {

	public ReglaNegocioDAO() {
	}

	public void registrarReglaNegocio(ReglaNegocio reglaNegocio) throws Exception {
		super.registrarElemento(reglaNegocio);
	}

	public ReglaNegocio consultarReglaNegocio(int id) {
		return (ReglaNegocio) super.consultarElemento(id);
	}

	public ReglaNegocio consultarReglaNegocio(String nombre, Proyecto proyecto) {
		return (ReglaNegocio) super.consultarElemento(nombre, proyecto,
				Referencia.getTabla(TipoReferencia.REGLANEGOCIO));
	}

	public List<ReglaNegocio> consultarReglasNegocio(int idProyecto) {
		List<ReglaNegocio> reglasNegocio = new ArrayList<ReglaNegocio>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.REGLANEGOCIO, idProyecto);
		for (Elemento elemento : elementos) {
			reglasNegocio.add((ReglaNegocio)elemento);
		}
		return reglasNegocio;
	}

	public String siguienteNumero(int idProyecto) {
		return super.siguienteNumero(TipoReferencia.REGLANEGOCIO, idProyecto);
	}

}
