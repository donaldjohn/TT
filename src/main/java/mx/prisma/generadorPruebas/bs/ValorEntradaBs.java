package mx.prisma.generadorPruebas.bs;

import java.util.Set;

import mx.prisma.generadorPruebas.dao.ValorEntradaDAO;
import mx.prisma.generadorPruebas.model.ValorEntrada;

public class ValorEntradaBs {
	public static void registrarValorEntrada(ValorEntrada valor) throws Exception{
		new ValorEntradaDAO().registrarValorEntrada(valor);
	}

	
}
