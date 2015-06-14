package exceptions;

/**
 * Excepción que se lanza cuando se cancela un proceso, como por ejemplo, cuando se cancela la carga de nuevas canciones.
 * @author unaipme
 *
 */
public class CancelationException extends Exception {

	private static final long serialVersionUID = -6520577334714435433L;

	public CancelationException(String s) {
		super("Se ha cancelado: "+s);
	}
	
}
