package exceptions;

public class CancelacionException extends Exception {

	public CancelacionException(String s) {
		super("Se ha cancelado: "+s);
	}
	
}
