package mx.prisma.util;

public class PRISMAException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public PRISMAException (String message) {
	        super (message);
	    }
	
	    public PRISMAException (Throwable cause) {
	        super (cause);
	    }
	
	    public PRISMAException (String message, Throwable cause) {
	        super (message, cause);
	    }

}
