package hr.java.vjezbe.iznimke;

/**
 * Predstavlja iznimku koja se desi ako je temperatura preniska.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class NiskaTemperaturaException extends RuntimeException {

	private static final long serialVersionUID = 4787462950417267751L;

	/**
	 * Vraca poruku nakon sto se desi iznimka.
	 * 
	 */
	public NiskaTemperaturaException() {
		super("Temperatura je preniska!");
	}

	/**
	 * Vraca poruku koju primi kada se desi iznimka.
	 * 
	 * @param poruka poruka koju prima
	 */
	public NiskaTemperaturaException(String poruka) {
		super(poruka);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Vraca uzrok kada se desi iznimka.
	 * 
	 * @param uzrok uzrok iznimke
	 */
	public NiskaTemperaturaException(Throwable uzrok) {
		super(uzrok);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Vraca poruku i uzrok kada se desi iznimka.
	 * 
	 * @param poruka poruka koju prima
	 * @param uzrok uzrok iznimke
	 */
	public NiskaTemperaturaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
		// TODO Auto-generated constructor stub
	}

	

}
