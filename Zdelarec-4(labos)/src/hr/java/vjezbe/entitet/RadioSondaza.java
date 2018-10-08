package hr.java.vjezbe.entitet;

/**
 * Predstavlja svojstva radiosondazne postaje koja su implementirana u klasi RadioSondaznaMjernaPostaja.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja
 */
public interface RadioSondaza {
	/**
	 * Postavlja visinu radiosondazne mjerne postaje. Metoda je nasljedena iz sucelja RadioSondaza.
	 *  
	 * @param visinaPostaje podatak o visini radiosondazne mjerne postaje
	 */
	void podesiVisinuPostaje(int visinaPostaje);
	
	/**
	 * Vraca podatak o visini radiosondazne mjerne postaje Metoda je nasljedena iz sucelja RadioSondaza.
	 * @return vraca visinu radiosondazne mjerne postaje
	 */
	int dohvatiVisinuPostaje();

	/**
	 * Provjerava je li unesena visina veca od 1000. Ako je, visinu fiksno postavlja na 1000.
	 * 
	 * @param visinaPostaje podatak o visini radiosondazne mjerne postaje
	 */
	private void provjeriVisinu(int visinaPostaje) {
		if (visinaPostaje > 1000)
			visinaPostaje = 1000;
	}

	/**
	 * Povecava visinu radiosondazne mjerne postaje te provjerava je li visina unutar zadanih 1000.
	 * 
	 * @param visinaPostaje podatak o visini radiosondazne mjerne postaje
	 * @return vraca podatak o visini radiosondazne mjerne postaje
	 * @see hr.java.vjezbe.entitet.RadioSondaza#ProvjeriVisinu(int)
	 */
	default int povecajVisinu(int visinaPostaje) {
		visinaPostaje=visinaPostaje+1;
		provjeriVisinu(visinaPostaje);
		return visinaPostaje;
	}

}
