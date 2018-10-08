package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;

/**
 * Predstavlja entitet senzora za mjerenje temperature zraka. Klasa nasljeduje
 * klasu Senzor.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.Senzor
 */
public class SenzorTemperature extends Senzor {
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	private String nazivKomponente;

	/**
	 * Inicijalizira podatake o mjernoj jedinici, preciznosti i nazivu komponente za
	 * senzor.
	 * 
	 * @param mjernaJedinica
	 *            podatak o jedinici u kojoj se mjeri vrijednost tlaka zraka
	 * @param preciznost
	 *            podatak o odstupanju senzora
	 * @param nazivKomponente
	 *            podatak nazivu komponente senzora
	 */
	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente) {
		super(mjernaJedinica, preciznost);
		this.nazivKomponente = nazivKomponente;

	}

	/**
	 * Dohvaca naziv komponente senzora.
	 * 
	 * @return vraca podatak o nazivu komponente senzora
	 */
	public String getNazivKomponente() {
		return nazivKomponente;
	}

	/**
	 * Postavlja podatak o komponenti senzora.
	 * 
	 * @param nazivKomponente
	 *            podatak o nazivu komponente senzora
	 */
	public void setNazivKomponente(String nazivKomponente) {
		this.nazivKomponente = nazivKomponente;
	}

	@Override
	String dohvatiVrijednost() {

		return "Komponenta: " + nazivKomponente + ", vrijednost: " + getVrijednost() + " " + getMjernaJedinica()
				+ ", Nacin rada: "+getRadSenzora();
	}

	/**
	 * Generira random vrijednos senzora za svaku mjernu postaju. Nakon generiranja
	 * se poziva metoda provjeraTemperature koja vrsi provjeru nad podatkom. Ako je
	 * temperatura previsoka ili preniska, baca se iznimka.
	 * 
	 * @param novaMjernaPostaja
	 *            mjerna postaja u kojoj se mjeri temperatura
	 * @param i
	 *            iterator koji odreduje redni broj mjerne postaje
	 * @exception NiskaTemperaturaException
	 *                generirana vrijednost temperature je preniska
	 * @exception VisokaTemperaturaException
	 *                generirana vrijednost temperature je previsoka
	 */
	public void generirajVrjednost(String imePostaje) {
		int randomInt = 0;
		boolean nastavi = false;
		Random rand = new Random();
		do {

			randomInt = rand.nextInt(50 - (-50) + 1) - 50;

			try {
				nastavi = provjeraTemperature(randomInt);
				nastavi = false;
			} catch (NiskaTemperaturaException ex) {
				System.out.println("Mjerna postaja: " + imePostaje);
				ex.printStackTrace();
				logger.error("Pogresna temperatura postaje: " + imePostaje, ex);
				nastavi = true;
			} catch (VisokaTemperaturaException ex1) {
				System.out.println("Mjerna postaja: " + imePostaje);
				ex1.printStackTrace();
				logger.error("Pogresna temperatura postaje: " + imePostaje, ex1);
				nastavi = true;
			}
		} while (nastavi);

		BigDecimal randomBroj = new BigDecimal(randomInt);
		System.out.println("Nova vrijednost senzora temperature je: " + randomBroj + "°C");
		setVrijednost(randomBroj);

	}

	/**
	 * Provjerava je li genrirani podatak unutar zadanih granica. Ako nije, baca se
	 * exception.
	 * 
	 * @param randomInt
	 *            generirana vrijednost
	 * @return vraca vrijednost varijable koja kontrolira hice li se petlja
	 *         zaustaviti ili ne
	 * @throws VisokaTemperaturaException
	 *             baca iznimku ako je temperatra previsoka
	 * @throws NiskaTemperaturaException
	 *             baca se iznimka ako je temperatura preniska
	 */
	private boolean provjeraTemperature(int randomInt) throws VisokaTemperaturaException {
		boolean nastavi;
		if (randomInt < -10) {
			throw new NiskaTemperaturaException("Temperatura je preniska! (" + randomInt + ")");
		}
		nastavi = false;
		if (randomInt > 40) {
			throw new VisokaTemperaturaException("Temperatura je previsoka! (" + randomInt + ")");
		}
		return nastavi;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return dohvatiVrijednost();
	}

}