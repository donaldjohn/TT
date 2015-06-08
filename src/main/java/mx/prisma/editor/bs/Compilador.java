package mx.prisma.editor.bs;

import java.util.ArrayList;
import java.util.Objects;

import mx.prisma.editor.dao.ActorDAO;
import mx.prisma.editor.model.Actor;

public class Compilador {
	String tokenBegin = "${";
	String tokenEnd = "}";
	String tokenSeparator = ".";

	public ArrayList<Object> procesarTokenIpunt(String cadena) {
		ArrayList<String> tokens = new ArrayList<String>();
		char caracterToken;
		char caracterCadena;
		int j = 0;
		for (int i = 0; i < cadena.length(); i++) {
			i += j;
			j = 0;
			if ((i + 1) < cadena.length() && i < cadena.length()) {
				caracterCadena = cadena.charAt(i);
				if ((caracterCadena + "" + cadena.charAt(i + 1)).equals(tokenBegin)) {
					String token = tokenBegin;
					for (j = i + 2; j < cadena.length(); j++) {
						caracterToken = cadena.charAt(j);
						if ((caracterToken + "").equals(tokenEnd)) {
							tokens.add(token + tokenEnd);
							break;
						} else {
							token += caracterToken;							
						}						
					}
				}
			}
		}
		
		return convertirToken_Objeto(tokens);	
	}
	
	public ArrayList<Object> convertirToken_Objeto(ArrayList<String> tokens){
		ArrayList<Object> objetos = new ArrayList<Object>();
		ArrayList<String> segmentos;
		String tipoObjeto;
		
		
		for (String token : tokens){
			segmentos = segmentarToken(token);
			switch(Referencia.getTipoReferencia(segmentos.get(0))){
			
			case ACCION:			
				break;
			case ACTOR:
				//Actor actor = new ActorDAO().consultarActor(Integer.parseInt(segmentos.get(1)));
				break;
			case CASOUSO:
				break;
			case ENTIDAD:
				break;
			case GLOSARIO:
				break;
			case INTERFAZUSUARIO:
				break;
			case MENSAJE:
				break;
			case REGLANEGOCIO:
				break;
			case TRAYECTORIA:
				break;
			default:
				break;
				
			}
		}
		
		
		return objetos;
	}
	
	public ArrayList<String> segmentarToken(String token){
		String tokenBruto = token.substring(2, token.length() - 1);
		String segmento = "";
		ArrayList<String> segmentos = new ArrayList<String>();
		String caracterAt;
		
		for(int i = 0; i < tokenBruto.length(); i++){
			caracterAt = tokenBruto.charAt(i)+"";
			if (caracterAt.equals(tokenSeparator)){
				segmentos.add(segmento);
				segmento = "";
			} else {
				segmento += tokenBruto.charAt(i);
			}
		}
		segmentos.add(segmento);

		return segmentos;
	}
	
}
