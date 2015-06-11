package exceptions;

public class CancelacionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6520577334714435433L;

	public CancelacionException(String s) {
		super("Se ha cancelado: "+s);
	}
	
}
