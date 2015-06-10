package mx.prisma.util;

public class PRISMAException extends RuntimeException{

	/**
	 * 
	 */
	private String idMensaje;
	private static final long serialVersionUID = 1L;
	
		
		public PRISMAException(String message, String idMensaje) {
		super(message);
		this.idMensaje = idMensaje;
	}

		public PRISMAException (String message) {
	        super (message);
	    }
	
	    public PRISMAException (Throwable cause) {
	        super (cause);
	    }
	
	    public PRISMAException (String message, Throwable cause) {
	        super (message, cause);
	    }

		public String getIdMensaje() {
			return idMensaje;
		}

		public void setIdMensaje(String idMensaje) {
			this.idMensaje = idMensaje;
		}
	    
	    

}
