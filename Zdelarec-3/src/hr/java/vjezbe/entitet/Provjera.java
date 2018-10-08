package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;

/**
 * Predstavlja klasu za provjeru unosa podataka od strane korisnika.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Provjera {

	private static BigDecimal decimalniBroj;
	private static int cijeliBroj;
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	/**
	 * Provjerava unos decimalnog broja. U slucaju unosa krivog formata, vraca gresu
	 * te trazi unos podatka u ispravnom formatu.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca broj ispravnog formata
	 * @exception InputMismatchException
	 *                se baca ukoliko dode do unosa pogresnog formata
	 */
	static public BigDecimal provjeraBigDecimala(Scanner unos) {
		boolean zastavica = false;
		do {
			try {
				decimalniBroj = unos.nextBigDecimal();
				zastavica = false;
			} catch (InputMismatchException ex) {
				System.out.println("Unesite ispravan format");
				logger.error("Unesen je pogresan format", ex);
				unos.nextLine();
				zastavica = true;
			}
		} while (zastavica);

		return decimalniBroj;
	}

	/**
	 * Provjerava unos cijelog broja. U slucaju unosa krivog formata, vraca gresu te
	 * trazi unos podatka u ispravnom formatu
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca broj ispravnog formata
	 * @exception InputMismatchException
	 *                se baca ukoliko dode do unosa pogresnog formata
	 */
	static public int provjeraIntegera(Scanner unos) {
		boolean zastavica = false;
		do {
			try {
				cijeliBroj = unos.nextInt();
				zastavica = false;
			} catch (InputMismatchException ex1) {
				System.out.println("Unesite ispravan format");
				logger.error("Unesen je pogresan format", ex1);
				unos.nextLine();
				zastavica = true;
			}
		} while (zastavica);

		return cijeliBroj;
	}

}
