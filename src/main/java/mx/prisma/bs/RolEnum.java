package mx.prisma.bs;

public class RolEnum {
	public enum Rol {
		LIDER, ANALISTA
	}
	public static int consultarIdRol(RolEnum.Rol rolEnum) {
		switch(rolEnum) {
		case ANALISTA:
			return 2;
		case LIDER:
			return 1;
		default:
			return -1;
		
		}
	}
}
