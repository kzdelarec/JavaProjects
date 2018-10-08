package hr.java.vjezbe.entitet;

/**
  * Predstavlja entitet senzora za mjerenje vlage zraka. Klasa nasljeduje klasu Senzor.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.Senzor
 */
public class SenzorVlage extends Senzor {

	/**
	 * Inicijalizira podatake o mjernoj jedinici i preciznosti senzora.
	 * 
	 * @param mjernaJedinica podatak o jedinici u kojoj se mjeri vrijednost tlaka zraka
	 * @param preciznost podatak o odstupanju senzora
	 */
	public SenzorVlage(String mjernaJedinica, byte preciznost) {
		super(mjernaJedinica, preciznost);

	}

	@Override
	String dohvatiVrijednost() {

		return "Vrijednost senzora: "+getVrijednost() +" "+getMjernaJedinica() + " vlage zraka"+ ", Nacin rada: "+getRadSenzora();
	}

	public String toString() {
		
		return dohvatiVrijednost();
	}
}
