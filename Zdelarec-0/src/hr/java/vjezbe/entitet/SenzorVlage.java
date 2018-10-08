package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NeispravanSenzorVlageException;


/**
 * Predstavlja entitet senzora za mjerenje vlage zraka. Klasa nasljeduje klasu
 * Senzor.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.Senzor
 */
public class SenzorVlage extends Senzor {
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	private static BigDecimal vlagaZraka;
	/**
	 * Inicijalizira podatake o mjernoj jedinici i preciznosti senzora.
	 * 
	 * @param mjernaJedinica
	 *            podatak o jedinici u kojoj se mjeri vrijednost tlaka zraka
	 * @param preciznost
	 *            podatak o odstupanju senzora
	 */
	public SenzorVlage(String mjernaJedinica, byte preciznost) {
		super(mjernaJedinica, preciznost);

	}

	static public BigDecimal kontrolaVlage(Scanner unos) {
		boolean zastavica = false;
		do {
			try {
				vlagaZraka = unos.nextBigDecimal();
				provjeraVlage(vlagaZraka);
				zastavica = false;
			} catch (NeispravanSenzorVlageException ex) {
				System.out.println("Unesite ispravnu vrijednost senzora vlage");
				logger.error("Unesena je pogresna vrijednost senzora vlage("+vlagaZraka+"%)", ex);
				unos.nextLine();
				zastavica = true;
			}
			catch (InputMismatchException ex) {
				System.out.println("Unesite ispravan format");
				logger.error("Unesen je pogresan format", ex);
				unos.nextLine();
				zastavica = true;
			}
		} while (zastavica);

		return vlagaZraka;
	}
	

	private static boolean provjeraVlage(BigDecimal vlaga) throws NeispravanSenzorVlageException {
		boolean nastavi;
		if (vlaga.compareTo(new BigDecimal(0)) < 0) {
			throw new NeispravanSenzorVlageException("Vlaga je preniska! (" + vlaga + "%)");
		}
		nastavi = false;
		if (vlaga.compareTo(new BigDecimal(100)) > 0) {
			throw new NeispravanSenzorVlageException("Vlaga je previsoka! (" + vlaga + "%)");
		}
		return nastavi;
	}

	@Override
	String dohvatiVrijednost() {

		return "Vrijednost senzora: " + getVrijednost() + " " + getMjernaJedinica() + " vlage zraka";
	}

}
