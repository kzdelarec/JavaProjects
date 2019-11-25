package hr.java.vjezbe.iznimke;


//neoznacena iznimka

/**
 *  Klasa koja koristi 4 konstuktora i nasljeduje klasu RuntimeException
 */
public class CijenaJePreniskaException extends RuntimeException{

	
	private static final long serialVersionUID = 2555341954833609776L;
	
	/**
	 * Konstruktor bez parametara
	 */
	public CijenaJePreniskaException() {
		super("Dogodila se pogreska u radu programa!");
	}
	
	/**
	 * Konstruktor koji prima poruku o pogresci
	 * @param message
	 */
	public CijenaJePreniskaException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima oba parametra
	 * @param message
	 * @param cause
	 */
	public CijenaJePreniskaException (String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Konstruktor koji prima uzrocnu iznimku
	 * @param cause
	 */
	public CijenaJePreniskaException(Throwable cause) {
		super(cause);
	}
}
