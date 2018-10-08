package hr.java.vjezbe.iznimke;

/**
 * Predstavlja iznimku koja se desi ako je temperatura preniska.

 * 
 * @author Kristijan Zdelarec
 *
 */
public class VisokaTemperaturaException extends Exception {
	//oznacena iznimka

	private static final long serialVersionUID = -2464415359284430992L;

	/**
	 * Vraca poruku nakon sto se desi iznimka.
	 * 
	 */
	public VisokaTemperaturaException() {
		super("Temperatura je previsoka!");
	}

	/**
	 * Vraca poruku koju primi kada se desi iznimka.
	 * 
	 * @param poruka poruka koju prima
	 */
	public VisokaTemperaturaException(String poruka) {
		super(poruka);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Vraca uzrok kada se desi iznimka.
	 * 
	 * @param uzrok uzrok iznimke
	 */
	public VisokaTemperaturaException(Throwable uzrok) {
		super(uzrok);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Vraca poruku i uzrok kada se desi iznimka.
	 * 
	 * @param poruka poruka koju prima
	 * @param uzrok uzrok iznimke
	 */
	public VisokaTemperaturaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
		// TODO Auto-generated constructor stub
	}



}
