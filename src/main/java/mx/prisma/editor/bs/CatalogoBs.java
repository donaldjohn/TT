package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.List;

import mx.prisma.editor.dao.CardinalidadDAO;
import mx.prisma.editor.model.Cardinalidad;

import org.hibernate.annotations.common.test.reflection.java.generics.deep.ANN612IssueTest.C;




public class CatalogoBs {
	private CardinalidadDAO cardinalidadDao;
	
	public List<Cardinalidad> buscaCardinalidad() {
		List<Cardinalidad> listCardinalidad = new ArrayList<Cardinalidad>();
		
		//for(int i = 1; i < 4; i++)
		//{
			cardinalidadDao = new CardinalidadDAO();
			listCardinalidad.add(cardinalidadDao.consultarCardinalidad(1));
			System.out.println(listCardinalidad.get(0).getNombre());
		//}
		
		return listCardinalidad;
	}
}
