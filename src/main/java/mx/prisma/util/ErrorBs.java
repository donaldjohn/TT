package mx.prisma.util;

public class ErrorBs {
	private String nombreCampo;
	private String idMensaje;
	private String[] parametrosMensaje;
	
	public ErrorBs(String nombreCampo, String idMensaje,
			String[] parametrosMensaje) {
		super();
		this.nombreCampo = nombreCampo;
		this.idMensaje = idMensaje;
		this.parametrosMensaje = parametrosMensaje;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String[] getParametrosMensaje() {
		return parametrosMensaje;
	}

	public void setParametrosMensaje(String[] parametrosMensaje) {
		this.parametrosMensaje = parametrosMensaje;
	}
	
	
}
