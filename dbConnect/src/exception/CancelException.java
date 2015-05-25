package exception;

@SuppressWarnings("serial")
public class CancelException extends Exception {
	public CancelException(String s) {
		super("Se ha cancelado: "+s);
	}
}
