package hr.java.vjezbe.iznimke;


//Oznacena iznimka

/**
 * Klasa koja koristi 4 konstuktora i nasljeduje klasu Exception
 */
public class NemoguceOdreditiGrupuOsiguranjaException extends Exception{

	
	private static final long serialVersionUID = -2291535264076831445L;
	
	/**
	 * Konstruktor bez parametara
	 */
	public NemoguceOdreditiGrupuOsiguranjaException() {
		super("Dogodila se pogreska u radu programa!");
	}
	
	/**
	 * Konstruktor koji prima poruku o pogresci
	 * @param message
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima oba parametra
	 * @param message
	 * @param cause
	 */
	public NemoguceOdreditiGrupuOsiguranjaException (String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Konstruktor koji prima uzrocnu iznimku
	 * @param cause
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(Throwable cause) {
		super(cause);
	}
}
