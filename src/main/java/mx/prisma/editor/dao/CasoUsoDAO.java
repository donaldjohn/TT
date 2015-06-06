package mx.prisma.editor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import mx.prisma.editor.model.CasoUso;
import mx.prisma.editor.model.Modulo;

public class CasoUsoDAO extends ElementoDAO {
    public void registrarCasoUso(CasoUso casodeuso) throws Exception {
    	System.out.println(casodeuso.getModulo().getNombre());
    	super.registrarElemento(casodeuso);
    }
}
