package exception;

@SuppressWarnings("serial")
public class RepeatedSongException extends Exception {
	public RepeatedSongException(String s) {
		super(s);
	}
}
