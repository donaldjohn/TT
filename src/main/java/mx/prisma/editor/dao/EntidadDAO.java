package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import mx.prisma.admin.model.Proyecto;
import mx.prisma.bs.ReferenciaEnum;
import mx.prisma.bs.ReferenciaEnum.TipoReferencia;
import mx.prisma.editor.model.Elemento;
import mx.prisma.editor.model.Entidad;
import mx.prisma.util.HibernateUtil;

import org.hibernate.Session;

public class EntidadDAO extends ElementoDAO {

	public EntidadDAO() {
		super();
	}
	public void registrarEntidad(Entidad entidad) {
		super.registrarElemento(entidad);
	}
	
	public Entidad consultarEntidad(String nombre, Proyecto proyecto) {
		return (Entidad) super.consultarElemento(nombre, proyecto, ReferenciaEnum.getTabla(ReferenciaEnum.TipoReferencia.ENTIDAD));
	}
	
	public Entidad consultarEntidad(int idEntidad) {
		return (Entidad) super.consultarElemento(idEntidad);
	}
	
	public List<Entidad> consultarEntidades(int idProyecto) {
		List<Entidad> entidades = new ArrayList<Entidad>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.ENTIDAD,  idProyecto);
		if (elementos != null)
		for (Elemento elemento : elementos) {
			entidades.add((Entidad) elemento);
		}
		return entidades;
	}
	
	public String siguienteNumeroEntidad(int idProyecto) {
		return super.siguienteNumero(TipoReferencia.ENTIDAD, idProyecto);
	}
}
